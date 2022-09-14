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
import cruise_company.dao.user.receipt.ReceiptStatusController;
import cruise_company.entity.user.User;
import cruise_company.entity.user.receipt.Receipt;

/**
 * Servlet implementation class CancelOrderServlet
 */
public class CancelOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReceiptController rc = new ReceiptController();
		ReceiptStatusController rsc = new ReceiptStatusController();
		UserController uc = new UserController();
		User user = (User)request.getSession().getAttribute("user");
		try {
			Receipt receipt = rc.getEntityByID(Integer.parseInt(request.getParameter("id")));
			if(rsc.getEntityByID(receipt.getReceiptStatusId()).getName().equals("Paid")) {
				user.setBalance(receipt.getPrice());
				uc.update(user); 
				request.getSession().setAttribute("user", user);
				rc.delete(receipt.getId());
			}else{
				rc.delete(receipt.getId());
			}
			response.sendRedirect("account.jsp");
		} catch (NumberFormatException | DAOException e) {
			request.setAttribute("msg", e.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
