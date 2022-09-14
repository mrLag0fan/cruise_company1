package cruise_company.servlets.liner.personal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import cruise_company.dao.DAOException;
import cruise_company.dao.language.LanguageController;
import cruise_company.dao.linar.LinerController;
import cruise_company.dao.linar.personal.PersonalController;
import cruise_company.dao.linar.personal.role.PersonalRoleController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.entity.language.Language;
import cruise_company.entity.linar.personal.Personal;
import cruise_company.validation.personal.PersonalValidation;


public class PersonalRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PersonalRegServlet() { 
        super();
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonalController pc = new PersonalController();
		PersonalRoleController prc = new PersonalRoleController();
		PortController poc = new PortController();
		LinerController lc = new LinerController(); 
		LanguageController lgc = new LanguageController();
		try { 
			Integer lcurrentPage = null;
			Integer pcurrentPage = null;
			try {
				lcurrentPage = Integer.parseInt(request.getParameter("lcurrentPage"));
				pcurrentPage = Integer.parseInt(request.getParameter("pcurrentPage"));
			}catch(NumberFormatException e){
				lcurrentPage = 1;
				pcurrentPage = 1;
			} 
			Integer lrecordsPerPage = 3;
			Integer precordsPerPage = 5;
			if(request.getParameter("language") != null) {
				Language lang = lgc.getEntityByShortName(request.getParameter("language").toString());
				request.setAttribute("personal_roles", prc.getEntityByLanguage(lang.getId()));
			}else {
				Language lang = lgc.getEntityByShortName(request.getSession().getAttribute("language").toString());
				request.setAttribute("personal_roles", prc.getEntityByLanguage(lang.getId()));
			}
			request.setAttribute("personal", pc.getAllWithLimit(pcurrentPage, precordsPerPage));
			request.setAttribute("liners", lc.getAllWithLimit(lcurrentPage, lrecordsPerPage));
			request.setAttribute("ports", poc.getAll());
			int lrows = lc.getNumberOfRows("SELECT COUNT(id) AS total FROM liner");
			int prows = pc.getNumberOfRows("SELECT COUNT(id) AS total FROM personal");
			int lnOfPages = lrows / lrecordsPerPage;
			int pnOfPages = prows / precordsPerPage;

	        if (lnOfPages % lrecordsPerPage > 0) {

	            lnOfPages++;
	        }
	        if (pnOfPages % precordsPerPage > 0) {

	            pnOfPages++;
	        }


	        request.setAttribute("lnoOfPages", lnOfPages);
	        request.setAttribute("lcurrentPage", lcurrentPage);
	        request.setAttribute("lrecordsPerPage", lrecordsPerPage);
	        request.setAttribute("pnoOfPages", pnOfPages);
	        request.setAttribute("pcurrentPage", pcurrentPage);
	        request.setAttribute("precordsPerPage", precordsPerPage);
			getServletContext().getRequestDispatcher("/personal.jsp").forward(request, response);
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonalController pc = new PersonalController();
		Personal personal = new Personal();
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		int experience = Integer.parseInt(request.getParameter("experience"));
		if(PersonalValidation.validPhone(phone) && PersonalValidation.validName(name + " " + surname)) {
			personal.setName(name);
			personal.setSurname(surname);
			personal.setPhone(phone);
			personal.setExperience(experience);
		}else {
			request.setAttribute("msg", "Data is invalid");
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
		personal.setPersonalRoleId(Integer.parseInt(request.getParameter("personal_role_id")));
		personal.setLinerId(Integer.parseInt(request.getParameter("liner_id")));
		try {
			pc.create(personal);
			response.sendRedirect("personalReg");
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
