package at.reisisoft.fsm;

public class PersonalInformation {

	private String firstName, lastname, idcard;

	public PersonalInformation(String firstName, String lastname, String idcard) {

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

	public boolean isValid() {
		return firstName != null && lastname != null && idcard != null
				&& lastname.length() <= 50 && firstName.length() <= 50
				&& idcard.length() <= 50 && lastname.length() > 0
				&& firstName.length() > 0 && idcard.length() > 0;
	}
}
