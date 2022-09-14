package cruise_company.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.UserController;

/**
 * Servlet implementation class UserReceiptsServlet
 */
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserController uc = new UserController();
		try {
			Integer currentPage = null;
			try {  
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}catch(NumberFormatException e){ 
				currentPage = 1;
			}
			Integer recordsPerPage = 10;
			request.setAttribute("users", uc.getAllWithLimit(currentPage, recordsPerPage));
			int rows = uc.getNumberOfRows("SELECT COUNT(id) AS total FROM user");
			int nOfPages = rows / recordsPerPage;

	        if (nOfPages % recordsPerPage > 0) {

	            nOfPages++;
	        }

	        request.setAttribute("noOfPages", nOfPages);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("recordsPerPage", recordsPerPage);
	        getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
		} catch (DAOException e) {
			request.setAttribute("msg", e.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		
	}
}
 