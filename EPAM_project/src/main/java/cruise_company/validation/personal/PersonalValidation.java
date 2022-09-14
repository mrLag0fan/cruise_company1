package cruise_company.validation.personal;

import java.util.regex.Pattern;

//^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$
public class PersonalValidation {

	public static boolean validPhone(String phone) {
		return Pattern.matches("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\\\s\\\\./0-9]*$", phone);
	}
	
	public static boolean validName(String name) {
		return Pattern.matches("^[a-zA-Z ,.'-]+$", name);
	}
	
	public static boolean validExperience(int experience) {
		return experience <= 60;
	}
	
}
