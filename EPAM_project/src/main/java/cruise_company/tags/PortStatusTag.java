package cruise_company.tags;

import java.io.IOException;
import java.util.List;

import cruise_company.dao.DAOException;
import cruise_company.dao.language.LanguageController;
import cruise_company.dao.linar.route.port.PortController;
import cruise_company.dao.linar.route.port.PortStatusController;
import cruise_company.entity.language.Language;
import cruise_company.entity.linar.route.port.Port;
import cruise_company.entity.linar.route.port.PortStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class PortStatusTag extends TagSupport{
	
	private static final long serialVersionUID = 1L;
	private int portId;  
	
	public void setPortId(int portId) {  
	    this.portId = portId;  
	}  
	
	@Override
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			LanguageController lc = new LanguageController();
			PortStatusController psc = new PortStatusController();
			PortController pc = new PortController();
			Language language = lc.getEntityByShortName(request.getSession().getAttribute("language").toString());
			List<PortStatus> portStatuses = psc.getEntityByLanguage(language.getId());
			Port port = pc.getEntityByID(portId);
			if(port.getPortStatusId() % 2 == 0) {
				out.print(portStatuses.get(1).getName());
			}else {
				out.print(portStatuses.get(0).getName());
			}
		}catch(DAOException | IOException e){
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

}
