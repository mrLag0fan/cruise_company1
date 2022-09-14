package cruise_company.servlets.port;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.route.port.PortController;

/**
 * Servlet implementation class PortDelServlet
 */
public class PortDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortDelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("portReg");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PortController pc = new PortController();
		try {
			pc.delete(Integer.parseInt(request.getParameter("id")));
			response.sendRedirect("portReg");
		}catch(DAOException ex) { 
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		
	}

}
