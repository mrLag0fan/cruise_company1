package cruise_company.servlets.liner;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.LinerController;

/**
 * Servlet implementation class LinerDelServlet
 */ 
public class LinerDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("linerReg");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinerController lc = new LinerController();
		try {
			lc.delete(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect("linerReg");
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
