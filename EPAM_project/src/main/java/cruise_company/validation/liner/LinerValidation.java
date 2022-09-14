package cruise_company.validation.liner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cruise_company.entity.linar.route.Route;

public class LinerValidation {

	public static boolean validDate(LocalDate date) {
		LocalDate past = LocalDate.of(1997, 01, 01);
		LocalDate future = LocalDate.of(2222, 01, 01);
		return date.isAfter(past) && date.isBefore(future);
	}
	public static boolean validPassengerCapacity(int passengerCapacity) {
		return passengerCapacity <= 3000;
	}
	
	public static boolean validRoute(int start, int end, List<Route> linerRoutes) {
		List<Route> copy = new ArrayList<>(linerRoutes);
		while(copy.size() > 0) {
			int prevStart = start;
			for(Route route: copy) {
				if(start == route.getFrom()) {
					start = route.getTo();
					copy.remove(route);
					break;
				}
			}
			if(prevStart == start) {
				break;
			}
		}
		if(end==start) {
			return true;
		}
		return false;
	}
}
