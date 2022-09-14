package cruise_company.servlets.port;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.entity.linar.route.port.Port;

/**
 * Servlet implementation class PortUpdateServlet
 */
public class PortUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortUpdateServlet() {
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
		Port port = new Port();
		port.setPortStatusId(Integer.parseInt(request.getParameter("status")));
		port.setId(Integer.parseInt(request.getParameter("id")));
		try {
			pc.update(port);
			response.sendRedirect("portReg");
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error505.jsp").forward(request, response);
		}
	}

}
