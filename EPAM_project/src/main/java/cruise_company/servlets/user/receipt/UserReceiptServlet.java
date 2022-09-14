package cruise_company.servlets.user.receipt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.receipt.ReceiptController;
import cruise_company.dao.user.receipt.ReceiptStatusController;

public class UserReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReceiptController rc = new ReceiptController();
		ReceiptStatusController rsc = new ReceiptStatusController();
		try {
			Integer currentPage = null; 
			Integer id = Integer.parseInt(request.getParameter("id"));
			try {  
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}catch(NumberFormatException e){ 
				currentPage = 1;
			}
			Integer recordsPerPage = 10;
			request.setAttribute("receipts", rc.getAllWithLimit(id, currentPage, recordsPerPage));
			request.setAttribute("receipt_statuses", rsc.getAll());
			int rows = rc.getNumberOfRows("SELECT COUNT(id) AS total FROM port");
			int nOfPages = rows / recordsPerPage;

	        if (nOfPages % recordsPerPage > 0) {

	            nOfPages++;
	        }

	        request.setAttribute("noOfPages", nOfPages);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("recordsPerPage", recordsPerPage);
	        request.setAttribute("id", id);
			getServletContext().getRequestDispatcher("/user_receipts.jsp").forward(request, response);
		} catch (NumberFormatException | DAOException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage()); 
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		} 
	}

}
