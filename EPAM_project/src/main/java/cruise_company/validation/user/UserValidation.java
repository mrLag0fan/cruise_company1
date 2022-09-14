package cruise_company.validation.user;

import java.util.regex.Pattern;

public class UserValidation {
	
	public static boolean validEmail(String email) {
		return Pattern.matches("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
	}
	
	public static boolean validPassword(String email) {
		return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", email);
	}

}
