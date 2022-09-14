package cruise_company.servlets.port;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import cruise_company.dao.DAOException;
import cruise_company.dao.language.LanguageController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.dao.linar.route.port.PortStatusController;
import cruise_company.entity.language.Language;
import cruise_company.entity.linar.route.port.Port;
import cruise_company.entity.user.User;
import cruise_company.validation.personal.PersonalValidation;

/**
 * Servlet implementation class PortRegServlet
 */
public class PortRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortRegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PortController pc = new PortController();
		PortStatusController psc = new PortStatusController();
		LanguageController lc = new LanguageController();
		try {
			Integer currentPage = null;
			try {  
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}catch(NumberFormatException e){
				currentPage = 1;
			}
			Integer recordsPerPage = 3;
			List<Port> ports = pc.getAllWithLimit(currentPage, recordsPerPage);
			if(request.getParameter("language") != null) {
				Language lang = lc.getEntityByShortName(request.getParameter("language").toString());
				request.setAttribute("port_statuses", psc.getEntityByLanguage(lang.getId()));
			}else {
				Language lang = lc.getEntityByShortName(request.getSession().getAttribute("language").toString());
				request.setAttribute("port_statuses", psc.getEntityByLanguage(lang.getId()));
			}
			request.setAttribute("ports", ports);
			int rows = pc.getNumberOfRows("SELECT COUNT(id) AS total FROM port");
			int nOfPages = rows / recordsPerPage;

	        if (nOfPages % recordsPerPage > 0) { 

	            nOfPages++;
	        }

	        request.setAttribute("noOfPages", nOfPages);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("recordsPerPage", recordsPerPage);
	        getServletContext().getRequestDispatcher("/port.jsp").forward(request, response);
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			ex.printStackTrace();
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int portStatus = Integer.parseInt(request.getParameter("status"));
		PortController pc = new PortController();
		Port port = new Port();
		if(PersonalValidation.validName(name)) {
			port.setName(name);
		}else {
			request.setAttribute("msg", "Data is invalid");
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		
		port.setPortStatusId(portStatus);
		try {
			pc.create(port);
			response.sendRedirect("portReg");
		}catch(DAOException ex){
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
