package cruise_company.servlets.user.receipt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.UserController;
import cruise_company.dao.user.receipt.ReceiptController;
import cruise_company.entity.user.receipt.Receipt;

/**
 * Servlet implementation class ConfirmReceiptServlet
 */
public class ConfirmReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReceiptController rc = new ReceiptController();
		try { 
			Receipt receipt = rc.getEntityByID(Integer.parseInt(request.getParameter("id")));
			receipt.setReceiptStatusId(9);
			rc.updateStatus(receipt);
			response.sendRedirect("users");
		} catch (DAOException e) {
			request.setAttribute("msg", e.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	} 

}
