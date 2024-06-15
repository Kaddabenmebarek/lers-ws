package org.research.kadda.labinventory.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.ApplicationContextHolder;
import org.research.kadda.labinventory.constants.Constants;
import org.research.kadda.labinventory.entity.SynthesisLibraryOrder;
import org.springframework.beans.factory.annotation.Autowired;

import org.research.kadda.osiris.OsirisService;
import org.research.kadda.osiris.data.EmployeeDto;

public class SynthesisOrderThread extends Thread {
	
	private static Logger log = LogManager.getLogger(SynthesisOrderThread.class);
	@Autowired
	private EmailService emailService;
	
	public SynthesisOrderThread() {
		super("synthesisOrderThread");
		setDaemon(true);
	}
	
	public void reInitThread() {
		new SynthesisOrderThread().start();
	}
	
	/**
	 * toDate1 = now -2min 
	 * toDate2 = now
	 * check orders where ending date between toDate1 and toDate2 and send email to user who recorded the event
	 * 
	 */
	@Override
	public void run() {
		try {
			do {
				try {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PATTERN);
					Date date1 = new Date(System.currentTimeMillis() - Constants.INTERVAL);
					String toDate1 = simpleDateFormat.format(date1);
					String toDate2 = simpleDateFormat.format(new Date());
					String fromDateTime =  simpleDateFormat.format(toDate1);
					String toDateTime =  simpleDateFormat.format(toDate2);
					SynthesisOrderService synthesisOrderService = ApplicationContextHolder.getContext().getBean(SynthesisOrderService.class);
					List<SynthesisLibraryOrder>  endedOrders = (List<SynthesisLibraryOrder>) synthesisOrderService.findAllInRange(fromDateTime, toDateTime);
					log.info("ended orders found: " + endedOrders.size());
					for (SynthesisLibraryOrder order : endedOrders) {
						processEmailNotification(order);
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				Thread.sleep(Constants.INTERVAL);
			} while (!isInterrupted());
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private void processEmailNotification(SynthesisLibraryOrder synthesisOrder) {
		try {
			String message = buildSynthesisOrderMailMessage(synthesisOrder);
			if (message != null && !"".equals(message)) {
				EmployeeDto employee = OsirisService.getEmployeeByUserId(synthesisOrder.getUsername().toLowerCase());
				String to = employee.getEmail();
				log.info("Send to: " + to);
				String subject = Constants.ORDER_END_MSG;
				getEmailService().sendJavaMail(message, to, null, subject);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String buildSynthesisOrderMailMessage(SynthesisLibraryOrder synthesisOrder) {
		String mailContent = "";
		StringBuilder message = new StringBuilder("Following Order ");
		message.append(" <br /><b>");
		message.append(synthesisOrder.getTitle()).append("</b><br /><br />");
		message.append("Made by <br />").append(synthesisOrder.getUsername());
		message.append("<br /><br />");
		message.append("Starting from <br />").append(synthesisOrder.getToTime().toString());
		message.append("<br /><br />");
		message.append("<b>Is ready for the requester.</b><br />");
		message.append("<br />");
		String projectInfo = synthesisOrder.getProject() != null && !"".equals(synthesisOrder.getProject())
				? "Project: " + synthesisOrder.getProject() + "<br />"
				: null;
		String linkInfo = synthesisOrder.getLink() != null && !"".equals(synthesisOrder.getLink())
				? "Link: " + synthesisOrder.getLink() + "<br />"
				: null;
		String libraryOutcomeInfo = synthesisOrder.getLibraryoutcome() != null
				&& !"".equals(synthesisOrder.getLibraryoutcome())
						? "Library outcome: " + synthesisOrder.getLibraryoutcome() + "<br />"
						: null;
		if (projectInfo != null || linkInfo != null) {
			message.append("Addition informations:");
			message.append("<br />");
			message.append(projectInfo != null ? projectInfo : "")
					.append(linkInfo != null ? "<a href='" + linkInfo + "'>" + linkInfo + "</a>" : "")
					.append(libraryOutcomeInfo != null ? libraryOutcomeInfo : "");
		}
		message.append("<br />");
		try {
			mailContent = getEmailService().generateContent("email.html", message.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mailContent;
	}

	public EmailService getEmailService() {
		if(emailService == null) emailService = ApplicationContextHolder.getContext().getBean(org.research.kadda.labinventory.service.EmailService.class);
		return emailService;
	}
	
	

}
