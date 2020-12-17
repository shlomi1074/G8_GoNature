package controllers;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import logic.GoNatureFinals;
import logic.Messages;
import server.GoNatureServer;
import sqlHandler.mysqlFunctions;

public class EmailControl {
	
	private static mysqlFunctions mysqlFunction = new mysqlFunctions(GoNatureServer.mysqlconnection);
    private static String senderEmail = GoNatureFinals.GO_NATURE_EMAIL;
    private static String senderPassword = GoNatureFinals.GO_NATURE_EMAIL_PASSWORD;
    
    
    public static boolean sendEmail(Messages msg) {
    	String sendTo = mysqlFunction.getEmailByOrderID(msg.getOrderId());
    	String subject = msg.getSubject();
		String messageToSend = msg.getContent();
		return sendEmail(sendTo, subject, messageToSend);
    	
    }
    
	/* Send an email
	 * Returns true on success
	 */
	private static boolean sendEmail(String sendTo, String subject, String messageToSend) {
		
        Properties props = getProperties();

        Session session = Session.getInstance(props, new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(senderEmail, senderPassword);
        	}
		});
        
        
        Message msg = prepareMessage(session, senderEmail, sendTo, messageToSend, subject);
        if (msg != null) {
        	try {
                // Send message  
                Transport.send(msg);  
                return true;
			} catch (Exception e) {
				System.out.println("Faild to send email");
		        return false;
			}
        }

        return false;
	}
	
	/* Create the Message object
	 * The message object includes all the details needed to send an email:
	 * Sender email, Recipient Email, Email's subject and the email's body
	 */
	private static Message prepareMessage(Session session, String from, String to, String subject, String messageToSend) {
		Message message = new MimeMessage(session); 
        try {
			message.setFrom(new InternetAddress(from));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  
            message.setSubject(subject);
            message.setText(messageToSend);
            message.setContent(messageToSend, "text/plain; charset=\"UTF-8\"");
            return message;
		} catch (MessagingException e) {
			System.out.println("Something went wrong while creating the message object");
			e.printStackTrace();
		}
        return null;

	}
	
	/* Create the Properties object
	 * The Properties object includes all the server properties.
	 * We are using the Gmail server to send an email
	 */
	private static Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");     
        props.setProperty("mail.host", "smtp.gmail.com");  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.port", "465");  
        props.put("mail.debug", "false");  
        props.put("mail.smtp.socketFactory.port", "465");  
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.socketFactory.fallback", "false");
        
		return props; 
	}

}
