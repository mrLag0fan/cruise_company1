package cruise_company.servlets.user.receipt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.language.LanguageController;
import cruise_company.dao.linar.LinerController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.dao.user.receipt.ReceiptController;
import cruise_company.dao.user.receipt.ReceiptStatusController;
import cruise_company.entity.language.Language;
import cruise_company.entity.linar.Liner;
import cruise_company.entity.user.User;
import cruise_company.entity.user.receipt.Receipt;

public class ReceiptRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ReceiptRegServlet() {
        super();
    }
 
    
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LinerController lc = new LinerController();
		PortController poc = new PortController();
		ReceiptController rc = new ReceiptController(); 
		ReceiptStatusController rsc = new ReceiptStatusController();
		LanguageController lgc = new LanguageController();
		User user = (User)req.getSession().getAttribute("user");
		try {
			Integer lcurrentPage = null; 
			Integer rcurrentPage = null;
			try {
				lcurrentPage = Integer.parseInt(req.getParameter("lcurrentPage"));
				rcurrentPage = Integer.parseInt(req.getParameter("rcurrentPage"));
			}catch(NumberFormatException e){
				lcurrentPage = 1;
				rcurrentPage = 1; 
			} 
			Integer lrecordsPerPage = 3;
			Integer rrecordsPerPage = 5;
			if(req.getParameter("language") != null) {
				Language lang = lgc.getEntityByShortName(req.getParameter("language").toString());
				req.setAttribute("receipt_statuses", rsc.getEntityByLanguage(lang.getId()));
			}else {
				Language lang = lgc.getEntityByShortName(req.getSession().getAttribute("language").toString());
				req.setAttribute("receipt_statuses", rsc.getEntityByLanguage(lang.getId()));
			}
			req.setAttribute("liners", lc.getAllWithLimit(lcurrentPage, lrecordsPerPage));
			req.setAttribute("ports", poc.getAll());
			req.setAttribute("receipts", rc.getAllWithLimit(user.getId(), rcurrentPage, rrecordsPerPage));
			
			int lrows = lc.getNumberOfRows("SELECT COUNT(id) AS total FROM liner");
			int rrows = rc.getNumberOfRowsForUser(user.getId());
			int lnOfPages = lrows / lrecordsPerPage;
			int rnOfPages = rrows / rrecordsPerPage;

	        if (lnOfPages % lrecordsPerPage > 0) {

	            lnOfPages++;
	        }
	        if (rnOfPages % rrecordsPerPage > 0) {

	            rnOfPages++;
	        }


	        req.setAttribute("lnoOfPages", lnOfPages);
	        req.setAttribute("lcurrentPage", lcurrentPage);
	        req.setAttribute("lrecordsPerPage", lrecordsPerPage);
	        req.setAttribute("rnoOfPages", rnOfPages);
	        req.setAttribute("rcurrentPage", rcurrentPage);
	        req.setAttribute("rrecordsPerPage", rrecordsPerPage);
			getServletContext().getRequestDispatcher("/receipt.jsp").forward(req, resp);
		}catch(DAOException ex) {
			req.setAttribute("msg", ex.getMessage());
			ex.printStackTrace();
			getServletContext().getRequestDispatcher("/error500.jsp").forward(req, resp);
		}
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinerController lc = new LinerController();
		ReceiptController rc = new ReceiptController();
		User user = (User)request.getSession().getAttribute("user");
		int linerId = Integer.parseInt(request.getParameter("liner_id"));
		double price = -1;
		try {
			Liner liner = lc.getEntityByID(linerId);
			price = liner.getPrice();
			if(rc.countReceiptForLiner(linerId) >= liner.getPassengerCapacity()) {
				request.setAttribute("msg", "There are too many applications for this liner");
				getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
			}
			Receipt receipt = new Receipt();
			receipt.setPrice(price);
			receipt.setReceiptStatusId(7);
			receipt.setLinerId(linerId);
			receipt.setUserId(user.getId());
			request.getSession().setAttribute("receipt", receipt);
			response.sendRedirect("uploadFile.jsp");
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			ex.printStackTrace();
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
