package cruise_company.entity.linar.route.port;

import cruise_company.entity.Status;

public class PortStatus extends Status{
	
	private int languageId;

	
	
	public int getLanguageId() {
		return languageId;
	}



	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}



	@Override
	public String toString() {
		return "PortStatus [getId()=" + getId() + ", getName()=" + getName() + "]";
	}
	
}
