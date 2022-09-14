package cruise_company.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class LoginFillter extends HttpFilter {

	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request.getSession().getAttribute("user") == null) {
			chain.doFilter(request, response);
		}else {
			response.sendRedirect("account.jsp");
		}	
	}

}
