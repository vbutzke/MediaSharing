package app.singletons;

public enum Email {

	RESET_PASSWORD_EMAIL("", "", "Password Reset", "Hello, "
												+ "\n Have you forgotten your password? No problem! :) "
												+ "\n Just click on the link below and define a new password for your account. "
												+ "\n If it was not you, please just ignore this email."), 
	CONFIRMATION_EMAIL("", "", "Email added!", "Hello, "
												+ "\n This email has been added to your universitary sharing media account. :)");
//link
    private String from;
    private String to;
    private String subject;
    private String body;

    private Email(String from, String to, String subject, String body){
        this.from    = from;
        this.to      = to;
        this.subject = subject;
        this.body    = body;
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
    
}