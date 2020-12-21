package haw.Ausleihe;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;

public class UserValidator extends  CompoundValidator<String>{
	private static final long serialVersionUID = 1L;
	
	public UserValidator() {
		 
		//add(StringValidator.lengthBetween(5, 15));
		add(new PatternValidator("[a-z0-9_-]+"));
 
	}
}
