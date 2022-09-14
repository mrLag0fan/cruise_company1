package cruise_company.servlets.liner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.LinerController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.entity.linar.Liner;
import cruise_company.entity.linar.route.Route;

/**
 * Servlet implementation class LinerRouteServlet
 */
public class LinerRouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LinerRouteServlet() {
        super();
        
    }
    
    private static List<Route> sortRoute(List<Route> routes, int start, int end) {
    	 List<Route> res = new ArrayList<Route>();
    	 int i = 0;
    	 do {
    		 for (int j = 0; j < routes.size(); j++) {
        		 if (routes.get(j).getFrom() ==  start)
                 {
                     res.add(routes.get(j));
                     routes.remove(j);
                     //System.out.print(routes.get(j) + " ");
                     i++;
                     j--;
                 }
        	 	if(res.size() > 0 && res.get(i-1).getTo() == routes.get(j).getFrom()){
        	 		res.add(routes.get(j));
        	 		//System.out.print(routes.get(j) + " ");
                    routes.remove(j);
                    j--;
                    i++;
        	 	}
        	 }
    	 }while(i < routes.size());
         return res;
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinerController lc = new LinerController();
		PortController pc = new PortController();
		try {
			Liner liner = lc.getEntityByID(Integer.parseInt(request.getParameter("id")));
			
			Integer currentPage = null;
			try {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}catch(NumberFormatException e){
				currentPage = 1;
			}
			Integer recordsPerPage = 5;
			
			List<Route> route = lc.getLinerRoute(liner);
			int start = liner.getStart();
			int end = liner.getEnd();
			route = sortRoute(route, start, end);
			List<Route> res = new ArrayList();
			for(int i = (currentPage-1) * recordsPerPage; i < (currentPage-1) * recordsPerPage+recordsPerPage  && i < route.size();i++) {
				res.add(route.get(i));
			}
			request.setAttribute("ports", pc.getAll());
			
			int rows = lc.getNumberOfRowsForLinerRoute(liner.getId());
			int nOfPages = rows / recordsPerPage;

	        if (nOfPages % recordsPerPage > 0) {

	            nOfPages++;
	        }

	        request.setAttribute("noOfPages", nOfPages);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("recordsPerPage", recordsPerPage);
			
	        request.setAttribute("id", liner.getId());
			request.setAttribute("routes", res);
			request.setAttribute("end", res.get(res.size()-1).getTo());
			getServletContext().getRequestDispatcher("/routeShow.jsp").forward(request, response);
		} catch (NumberFormatException | DAOException e) {
			request.setAttribute("msg", e.getMessage());
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
