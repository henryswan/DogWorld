package com.dwn.dogworld.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dwn.dogworld.entities.CustomerInquiry;
import com.dwn.dogworld.entities.DogRequest;

public class SendEmail implements EmailNotifications {
	private String username;
	private String password;
	private String emailMessage;
	private String subject;
	private String recipient;
	private String timeOfRequest;
	
	private Session session;
	
	public SendEmail(){
		this.session = prepSession();
	}
	
	public SendEmail(String emailMessage, String subject, String recipient, String timeOfRequest){
		this.emailMessage = emailMessage;
		this.subject = subject;
		this.recipient = recipient;
		this.timeOfRequest = timeOfRequest;
		this.session = prepSession();
	}
	
	/**
	 * sets required smtp properties and does a 
	 * password authentication
	 * @return mail session object
	 */
	public Session prepSession(){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		//get username and password from db/elsewhere
		
		return Session.getInstance(props, 
				new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("info@dogworldnigeria.com", "Nnanna2090");
			}
		});
	}
	
	public void sendPlainTextEmail(String subject, String messageBody,
			String receipient, boolean withAttachment, File attachment) {
		// TODO Auto-generated method stub
		
	}

	public void sendHTMLEmail(String subject, String messageBody,
			String receipient, boolean withAttachment, File attachment) {
		// TODO Auto-generated method stub
		
	}

	public void sendDogRequestNotification(DogRequest request) {
		if(session == null)
			return;
		Message message = new MimeMessage(session);
		String messageBody = "Hello!<br>";
		try {
			message.setFrom(new InternetAddress("nnanna.madu@gmail.com")); //change email later - retrieve from db 
																			//(load it into db first time app starts)
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(request.getContactEmail()));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("nnanna.madu@gmail.com"));
			message.setSentDate(new Date());
			message.setSubject("Dog Request Received - " + new SimpleDateFormat(Constants.EMAIL_DATE_FORMAT).format(new Date()));
			messageBody += "<br>We have received your request for a dog. See details below:<br><hr>";
			messageBody += "Breed: <strong>" + request.getPreferredBreed() + "</strong><br>";
			messageBody += "Gender: <strong>" + request.getPreferredGender() + "</strong><br>";
			messageBody += "Color: <strong>" + request.getPreferredColour() + "</strong><br>";
			messageBody += "Contact Email: <strong>" + request.getContactEmail() + "</strong><br>";
			messageBody += "Contact Telephone: <strong>" + request.getContactPhone() + "</strong><br>";
			messageBody += "Further Description: <strong>" + request.getPreferredBreed() + "</strong><br>";
			messageBody += "Location: <strong>" + request.getRequestDescription() + "</strong><br>";
			messageBody += "<hr><br>We are currently searching for the best dog deals for you. Our representative will get in touch with you shortly.<br>";
			messageBody += Constants.EMAIL_SIGNATURE;
			message.setContent(messageBody, "text/html; charset=utf-8");
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	public void sendCustomerInquiryNotification(CustomerInquiry inquiry) {
		if(session == null)
			return;
		Message message = new MimeMessage(session);
		String messageBody = "Hello!<br>";
		try {
			message.setFrom(new InternetAddress("nnanna.madu@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(inquiry.getContactEmail()));
			message.setSentDate(new Date());
			message.setSubject("Inquiry Received - " + new SimpleDateFormat(Constants.EMAIL_DATE_FORMAT).format(new Date()));
			messageBody += "<br>Thank you for contacting us.";
			messageBody += "<br><br>We will review your message and a representative will get in touch with you shortly.<br><br>";
			messageBody += Constants.EMAIL_SIGNATURE;
			message.setContent(messageBody, "text/html; charset=utf-8");
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendInquiryToAdmin(CustomerInquiry inquiry) {
		if(session == null)
			return;
		Message message = new MimeMessage(session);
		String messageBody = "Hello!<br>";
		try {
			message.setFrom(new InternetAddress("nnanna.madu@gmail.com")); 
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("nnanna.madu@gmail.com"));
			message.setSentDate(new Date());
			message.setSubject("Customer Inquiry Received - " + new SimpleDateFormat(Constants.EMAIL_DATE_FORMAT).format(new Date()));
			messageBody += "<br>The following inquiry has been received by a customer:<br><hr>";
			messageBody += "Name: <strong>" + inquiry.getContactName() + "</strong><br>";
			messageBody += "Subject: <strong>" + inquiry.getSubject() + "</strong><br>";
			messageBody += "Email: <strong>" + inquiry.getContactEmail() + "</strong><br>";
			messageBody += "Phone Number: <strong>" + inquiry.getContactPhone() + "</strong><br>";
			messageBody += "Message: <strong>" + inquiry.getMessage() + "</strong><br><hr><br>";
			messageBody += Constants.EMAIL_SIGNATURE;
			message.setContent(messageBody, "text/html; charset=utf-8");
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
