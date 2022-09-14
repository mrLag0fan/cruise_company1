package cruise_company.entity.user.receipt;

import cruise_company.entity.Status;

public class ReceiptStatus extends Status{
	
	private int languageId;

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	
	
}
