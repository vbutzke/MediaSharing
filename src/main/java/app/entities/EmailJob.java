package app.entities;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

import app.singletons.Email;

@Controller
public class EmailJob {
	
	@Autowired
	private JavaMailSender sender;

    public EmailJob(){
    }

    public void sendEmail(Email emailType) throws MessagingException {
    	MimeMessage message = sender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setFrom(emailType.getFrom());
        helper.setTo(emailType.getTo());
        helper.setSubject(emailType.getSubject());
        helper.setText(emailType.getBody());

        sender.send(message);
    }
 
}