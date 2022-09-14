package cruise_company.servlets.liner.personal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.personal.PersonalController;

/**
 * Servlet implementation class PersonalDelServlet
 */
public class PersonalDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonalDelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("personalReg");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonalController pc = new PersonalController();
		try {
			pc.delete(Integer.parseInt(request.getParameter("id")));
		}catch(DAOException ex) { 
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error505.jsp").forward(request, response);
		}
		response.sendRedirect("personalReg");
	}

}
