package app.singletons;

public enum Exceptions {

	ACCESS_CODE_TRIGGERING_VIOLATION("Non-admin users aren't supposed to be able to trigger access code action. Security violation."),
	PERMISSION_DENIED("Your user has no permission to perform this action."),
	INVALID_ACCESS_CODE("The informed access code does not exists."),
	EMAIL_SERVICE_ERROR("There was an error sending the email."),
	MISSING_MANDATORY_FIELDS("Please fill all mandatory fields"),
	NULL_OBJECT("The object provided for the operation is null"),
	EMAIL_NOT_REGISTERED("Seems like the email informed is not yet registered. Please make sure you've typed it correclty."),
	PASSWORD_MISMATCH("The password you've just informed doesn't match the one we have in our records. Please make sure you've typed it correctly.");
	
	private String message;
	
	private Exceptions(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}