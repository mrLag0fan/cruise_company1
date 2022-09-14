package cruise_company.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
 

public class AdminFilterRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminFilterRedirect() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("msg", "You do not have sufficient privileges for this resource");
		getServletContext().getRequestDispatcher("/error403.jsp").forward(request, response);
	}

}
