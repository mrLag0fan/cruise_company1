package cruise_company.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import cruise_company.entity.user.User;
/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter extends HttpFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1610882464857048385L;



	public AuthenticationFilter() {
        super();
        // TODO Auto-generated constructor stub
    }



	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.sendRedirect("AuthenticationFilterRedirect");
		}else {
			chain.doFilter(request, response);
		}
	}

}
