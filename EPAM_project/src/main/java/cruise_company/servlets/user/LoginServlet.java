package cruise_company.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.UserController;
import cruise_company.entity.user.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserController uc = new UserController();
		User user = null;
		try {
			 user = uc.getUserForLogin(email, password);
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		if(user != null) {
			request.getSession().setAttribute("user", user);
			response.sendRedirect("account.jsp");
		}else{
			request.setAttribute("msg", "Email or password is incorrect");
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		};
	}

}
