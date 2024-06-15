package org.research.kadda.labinventory.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.constants.Constants;
import org.springframework.util.ResourceUtils;

public class EmailService {

	private static Logger log = LogManager.getLogger(EmailService.class);
	
	public void sendJavaMail(String message, String recipe, List<String> ccs, String subject)
			throws AddressException, MessagingException, IOException {

		Properties emailProp = fetchProperties();

		// to remove when test done
		 recipe = emailProp.getProperty("mail.testto");
		 ccs = new ArrayList<String>();
		 ccs.add(emailProp.getProperty("mail.testcc"));
		//

		Properties props = new Properties();
		props.setProperty("mail.smtp.host", emailProp.getProperty("mail.host"));
		log.info("mail.host: " + emailProp.getProperty("mail.host"));
		props.setProperty("mail.smtp.starttls.enable", emailProp.getProperty("mail.smtp.starttls.enable"));
		log.info("mail.smtp.starttls.enable: " + emailProp.getProperty("mail.smtp.starttls.enable"));

		Session mailConnection = Session.getInstance(props, null);
		mailConnection.setDebug(false);
		Message msg = new MimeMessage(mailConnection);
		msg.setSentDate(new Date());
		Address recip = new InternetAddress(recipe);
		msg.setRecipient(Message.RecipientType.TO, recip);
		List<Address> recipeCcs = new ArrayList<Address>();
		ccs.removeAll(Collections.singleton(null));
		for (String cc : ccs) {
			Address recipcc = new InternetAddress(cc);
			recipeCcs.add(recipcc);
		}
		msg.setRecipients(Message.RecipientType.CC, recipeCcs.toArray(new Address[recipeCcs.size()]));
		Address from = new InternetAddress(emailProp.getProperty("mail.from"));
		log.info("mail.from: " + emailProp.getProperty("mail.from"));
		msg.setFrom(from);
		String purpose = subject == null ? emailProp.getProperty("mail.subject") : subject;
		log.info("mail.subject: " + emailProp.getProperty("mail.subject"));
		msg.setSubject(purpose);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		msg.setContent(multipart);
		Transport.send(msg);

	}

	public String generateContent(String inputfileName, String mailContent) throws FileNotFoundException, IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			//File sourceFile = ResourceUtils.getFile("classpath:" + inputfileName);
			File sourceFile = ResourceUtils.getFile(inputfileName);
			System.out.println(sourceFile.getPath());
			FileReader fr = new FileReader(sourceFile);
			br = new BufferedReader(fr);
			String s = br.readLine();
			while (s != null) {
				//System.out.println(s);
				if (s.equals("{content}")) {
					s = mailContent;
				}
				sb.append(s);
				s = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {			
			try {br.close();log.info("Buffered reader closed");} catch (IOException e) {e.printStackTrace();}
		}
		
		return sb.toString();
	}
	

	public Properties fetchProperties() {
		Properties properties = new Properties();
		try {
			File file = ResourceUtils.getFile(Constants.ABSOLUTE_PATH + "email.properties");
			InputStream in = new FileInputStream(file);
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
