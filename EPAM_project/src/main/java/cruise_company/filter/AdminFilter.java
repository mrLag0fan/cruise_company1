package cruise_company.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import cruise_company.entity.user.User;

 
/**
 * Servlet Filter implementation class AdminFilter
 */
public class AdminFilter extends HttpFilter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -7588519356247672408L;

	public AdminFilter() {
        super();
    }

	public void destroy() {
		
	}

	
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		User user = (User)request.getSession().getAttribute("user");
		if(user != null && user.getUserRoleId() == 1) {
			chain.doFilter(request, response);
		}else if(user != null){
			response.sendRedirect("AdminFilterRedirect");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
