package cruise_company.servlets.liner;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.LinerController;
import cruise_company.dao.linar.route.RouteController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.entity.linar.Liner;
import cruise_company.entity.linar.route.Route;
import cruise_company.validation.liner.LinerValidation;

public class LinerRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LinerRegServlet() { 
        super(); 
    }

    private List<Liner> filterLiners(List<Liner> liners, LocalDate start_date, LocalDate end_date){
    	List<Liner> res = new ArrayList<>();
    	
    	for(Liner liner: liners) {
    		if(liner.getDateStart().isAfter(start_date) && liner.getDateEnd().isBefore(end_date)) {
    			res.add(liner);
    		}
    	} 
    	
    	return res;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinerController lc = new LinerController();
		PortController pc = new PortController();
		RouteController rc = new RouteController();
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
		try {
			Integer currentPage = null;
			try {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}catch(NumberFormatException e){
				currentPage = 1;
			}
			
			Integer recordsPerPage = 7;
			List<Liner> liners = lc.getAllWithLimit(currentPage, recordsPerPage);
			try {
				LocalDate start_date = LocalDate.parse( request.getParameter("f_date_start") , f );
				LocalDate end_date = LocalDate.parse( request.getParameter("f_date_end") , f );
				request.setAttribute("liners", filterLiners(liners, start_date, end_date));
			}catch(NullPointerException e) {
				request.setAttribute("liners", liners);
			}
			
			int rows = lc.getNumberOfRows("SELECT COUNT(id) AS total FROM liner");
			int nOfPages = rows / recordsPerPage;

	        if (nOfPages % recordsPerPage > 0) {

	            nOfPages++;
	        }

	        request.setAttribute("noOfPages", nOfPages);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("recordsPerPage", recordsPerPage);
			
			request.setAttribute("ports", pc.getAll());
			request.setAttribute("routes", rc.getAll());
			getServletContext().getRequestDispatcher("/liner.jsp").forward(request, response);
		}catch(DAOException ex) {
			ex.printStackTrace();
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinerController lc = new LinerController();
		RouteController rc = new RouteController();
		Liner liner = new Liner();
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ;
		LocalDate startDate = LocalDate.parse( request.getParameter("date_start") , f );
		LocalDate endDate = LocalDate.parse( request.getParameter("date_end") , f );
		int passengerCapacity = Integer.parseInt(request.getParameter("passenger_capacity"));
		int start = Integer.parseInt(request.getParameter("start"));
		int end = Integer.parseInt(request.getParameter("end"));
		if(LinerValidation.validDate(startDate) && LinerValidation.validDate(endDate)
				&& startDate.isBefore(endDate)  
				&& LinerValidation.validPassengerCapacity(passengerCapacity)) 
		{
			liner.setDateEnd(endDate);
			liner.setDateStart(startDate); 
			liner.setPassengerCapacity(passengerCapacity);
			liner.setStart(start);
			liner.setEnd(end);
			Period period = Period.between(liner.getDateStart(), liner.getDateEnd());
			liner.setDurationInDays(period.getDays());
			liner.setPrice(Double.parseDouble(request.getParameter("price")));
			List<Route> linerRoutes = new ArrayList<>();
			String[] routesId = request.getParameterValues("routes");
			try {
				for(String routeId: routesId) {
					linerRoutes.add(rc.getEntityByID(Integer.parseInt(routeId)));
				}
				liner.setVisitedPorts(linerRoutes.size()+1);
				if(LinerValidation.validRoute(start, end, linerRoutes)) {
					System.out.println(linerRoutes.toString());
					lc.setRouteToLiner(lc.getEntityByID(lc.create(liner)), linerRoutes);
				}else {
					request.setAttribute("msg", "Route is invalid");
					getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
				}
				
				response.sendRedirect("linerReg");
			}catch(DAOException ex) {
				request.setAttribute("msg", ex.getMessage());
				getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
			}
		}else {
			request.setAttribute("msg", "Data is invalid");
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		
	}

}
