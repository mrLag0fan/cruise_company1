package cruise_company.servlets.route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.route.RouteController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.entity.linar.route.Route;

/**
 * Servlet implementation class RouteRegServlet
 */
public class RouteRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RouteRegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PortController pc = new PortController();
		RouteController rc = new RouteController();
		try {
			request.setAttribute("ports", pc.getAll());
			Integer currentPage = null;
			try {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}catch(NumberFormatException e){
				currentPage = 1;
			}
			Integer recordsPerPage = 2;
			request.setAttribute("routes", rc.getAllWithLimit(currentPage, recordsPerPage));
			int rows = rc.getNumberOfRows("SELECT COUNT(id) AS total FROM route");
			int nOfPages = rows / recordsPerPage;

	        if (nOfPages % recordsPerPage > 0) {

	            nOfPages++;
	        }

	        request.setAttribute("noOfPages", nOfPages);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("recordsPerPage", recordsPerPage);
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		
		getServletContext().getRequestDispatcher("/route.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RouteController rc = new RouteController();
		Route route = new Route();
		int from = Integer.parseInt(request.getParameter("from"));
		int to = Integer.parseInt(request.getParameter("to"));
		if(from == to) {
			request.setAttribute("msg", "The starting point must be different from the end point");
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}else {
			route.setFrom(from);
			route.setTo(to);
			try {
				rc.create(route);
				response.sendRedirect("routeReg");
			}catch(DAOException ex) {
				request.setAttribute("msg", ex.getMessage());
				getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
			}
		}
	}

}
