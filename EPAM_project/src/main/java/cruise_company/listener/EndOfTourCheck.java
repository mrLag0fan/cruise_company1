package cruise_company.listener;

import java.time.LocalDate;

import cruise_company.dao.DAOException;
import cruise_company.dao.linar.LinerController;
import cruise_company.dao.user.receipt.ReceiptController;
import cruise_company.entity.linar.Liner;
import cruise_company.entity.user.receipt.Receipt;

public class EndOfTourCheck implements Runnable{

	@Override
	public void run() {
		ReceiptController rc = new ReceiptController();
		LinerController lc = new LinerController();
		try {
			for(Receipt receipt: rc.getAll()) {
				Liner liner = lc.getEntityByID(receipt.getLinerId());
				if(liner.getDateEnd().isBefore(LocalDate.now()) && (receipt.getReceiptStatusId() == 5 || receipt.getReceiptStatusId() == 11)) {
					receipt.setReceiptStatusId(12);
					rc.updateStatus(receipt);
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}
