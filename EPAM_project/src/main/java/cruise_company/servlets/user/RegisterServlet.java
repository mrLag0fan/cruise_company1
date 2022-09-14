package cruise_company.servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.UserController;
import cruise_company.entity.user.User;
import cruise_company.validation.user.UserValidation;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) request.getParameter("email");
		String password = (String) request.getParameter("password"); 
		String repPassword = (String) request.getParameter("repPassword");
		//System.out.print(email + " " + password + " " + repPassword);
		if(password.equals(repPassword)) {
			User user = new User(email, password);
			UserController uc = new UserController();
			try { 
				if(UserValidation.validEmail(email) && UserValidation.validPassword(password)) {
					uc.create(user);
					response.sendRedirect("login.jsp");
				}else {
					if(!UserValidation.validEmail(email)) {
						request.setAttribute("msg", "Email is invalid");
					}else {
						request.setAttribute("msg", "Password is invalid");
					}
					getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
				}
			}catch(DAOException ex) {
				request.setAttribute("msg", ex.getMessage());
				getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
			}
		}else {
			request.setAttribute("msg", "Passwords do not match");
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
