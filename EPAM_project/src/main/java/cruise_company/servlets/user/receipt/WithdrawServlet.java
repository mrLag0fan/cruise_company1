package cruise_company.servlets.user.receipt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.LinerController;
import cruise_company.dao.user.UserController;
import cruise_company.dao.user.receipt.ReceiptController;
import cruise_company.entity.user.User;
import cruise_company.entity.user.receipt.Receipt;


public class WithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		double price = Double.parseDouble(request.getParameter("payment"));
		if(price <= user.getBalance()) {
			ReceiptController rc = new ReceiptController();
			UserController uc = new UserController();
			LinerController lc = new LinerController();
			try {
				Receipt receipt = rc.getEntityByID(Integer.parseInt(request.getParameter("id")));
				if(user.getBalance()-receipt.getPrice() >= 0 ) {
					if(rc.countReceiptForLiner(receipt.getLinerId()) >= lc.getEntityByID(receipt.getLinerId()).getPassengerCapacity()) {
						request.setAttribute("msg", "There are too many applications for this liner");
						getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
					}else {
						user.setBalance(-receipt.getPrice());
						receipt.setReceiptStatusId(11);
						rc.updateStatus(receipt);
						uc.update(user);
						request.getSession().setAttribute("user", user);
						response.sendRedirect("account.jsp");
					}
				}else {
					throw new DAOException("You don't have enough money", null);
				}
			} catch (NumberFormatException | DAOException e) {
				e.printStackTrace();
				request.setAttribute("msg", e.getMessage());
				getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
			}
		}
	}

}
