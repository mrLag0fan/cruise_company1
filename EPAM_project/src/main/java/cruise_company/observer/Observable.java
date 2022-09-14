package cruise_company.observer;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

	List<Observer> observers = new ArrayList<Observer>();
	
	default void addObserver(Observer o) {
		observers.add(o);
	}
	default void removeObserver(Observer o) {
		observers.remove(o);
	}
	default void notifyObservers(String msg) {
		for(Observer observer: observers) {
			observer.handleEvent(msg);
		}
	}
}
