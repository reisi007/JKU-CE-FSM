package at.reisisoft.fsm;

/**
 * A helper class which holds the personal information for one passenger
 * 
 * @author Florian
 *
 */
public class PersonalInformation {

	private String firstName, lastname, idcard;

	public PersonalInformation(String firstName, String lastname, String idcard) {
		assert firstName != null && lastname != null && idcard != null : "Trying to initialize with NULL";
		this.firstName = firstName;
		this.lastname = lastname;
		this.idcard = idcard;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	/**
	 * 
	 * @return true} , if the entered data is valid, otherwise false}
	 */
	public boolean isValid() {
		return firstName != null && lastname != null && idcard != null
				&& lastname.length() <= 50 && firstName.length() <= 50
				&& idcard.length() <= 50 && lastname.length() > 0
				&& firstName.length() > 0 && idcard.length() > 0;
	}
}
