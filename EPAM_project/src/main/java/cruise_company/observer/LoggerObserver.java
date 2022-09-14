package cruise_company.observer;

import org.apache.log4j.Logger;

import cruise_company.dao.AbstractController;

public class LoggerObserver<E> implements Observer{

	private static Logger log;
	
	@Override
	public void handleEvent(String msg) {
		log.info(msg);
	}
	
	public LoggerObserver(AbstractController<E> ac) {
		log = Logger.getLogger(ac.getClass());
	}
}
