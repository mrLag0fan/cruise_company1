package cruise_company.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.UserController;
import cruise_company.entity.user.User;

/**
 * Servlet implementation class PromoteUserServlet
 */
public class PromoteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserController uc = new UserController();
		try { 
			request.setAttribute("users", uc.getAll());
			getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
		} catch (DAOException e) {
			request.setAttribute("msg", e.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserController uc = new UserController();
		try {
			User user = uc.getEntityByID(Integer.parseInt(request.getParameter("id")));
			user.setUserRoleId(1);
			uc.promote(user);
			response.sendRedirect("account.jsp");
		} catch (DAOException e) {
			request.setAttribute("msg", e.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
