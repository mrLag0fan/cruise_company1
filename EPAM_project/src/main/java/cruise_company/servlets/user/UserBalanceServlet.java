package cruise_company.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.UserController;
import cruise_company.entity.user.User;

/**
 * Servlet implementation class UserBalanceServlet
 */
public class UserBalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("balanse.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserController uc = new UserController();
		User user = (User)request.getSession().getAttribute("user");
		user.setBalance(Double.parseDouble(request.getParameter("payment")));
		request.getSession().setAttribute("user", user);
		try { 
			uc.update(user);
			response.sendRedirect("account.jsp");
		} catch (DAOException e) {
			request.setAttribute("msg", e.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
