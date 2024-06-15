package org.research.kadda.labinventory.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.ApplicationContextHolder;
import org.research.kadda.labinventory.constants.Constants;
import org.research.kadda.labinventory.entity.Instrument;
import org.research.kadda.labinventory.entity.InstrumentDeputy;
import org.research.kadda.labinventory.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import org.research.kadda.osiris.OsirisService;
import org.research.kadda.osiris.data.EmployeeDto;

public class EmailEndReservationThread extends Thread {

	private static Logger log = LogManager.getLogger(EmailEndReservationThread.class);
	@Autowired
	private EmailService emailService;

	public EmailEndReservationThread() {
		super("EmailEndReservationThread");
		setDaemon(true);
	}

	@Override
	public void run() {
		try {
			do {
				try {
					// toDate1 = now -2min, toDate2 = now
					// check reservation where ending date between toDate1 and toDate2 and send email to owner and deputies 
					// when email notification is sent for the instruments
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PATTERN);
					Date date1 = new Date(System.currentTimeMillis() - Constants.INTERVAL);
					String toDate1 = simpleDateFormat.format(date1);
					String toDate2 = simpleDateFormat.format(new Date());
					ReservationService reservationService = ApplicationContextHolder.getContext().getBean(ReservationService.class);
					List<Reservation> endedReservations = reservationService.findJustEndedReservations(toDate1,
							toDate2);
					log.info("endedReservations found: " + endedReservations.size());
					for (Reservation res : endedReservations) {
						checkIfEmailNotification(res);
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

	public void reInitThread() {
		new EmailEndReservationThread().start();
	}

	private void checkIfEmailNotification(Reservation reservation) {
		InstrumentService instrumentService = ApplicationContextHolder.getContext().getBean(InstrumentService.class);
		Optional<Instrument> instrument = instrumentService.findById(String.valueOf(reservation.getInstrid()));
		if (instrument != null) {
			Instrument inst = instrument.get();
			boolean emailToAll = inst.getEmailNotification() == 1;
			boolean emailToRequesters = inst.getEmailNotification() == 2;
			if (emailToAll || emailToRequesters) {
				try {
					String message = buildBookingMailMessage(reservation, inst);
					if (message != null && !"".equals(message)) {
						EmployeeDto employee = OsirisService.getEmployeeByUserId(inst.getUsername().toLowerCase());
						String to = employee.getEmail();
						log.info("Send to: " + to);
						List<String> ccs = new ArrayList<String>();
						if(emailToAll) {
							InstrumentDeputyService instrumentDeputyService = ApplicationContextHolder.getContext().getBean(InstrumentDeputyService.class);
							List<InstrumentDeputy> deputies = instrumentDeputyService
									.findByInstrumentId(String.valueOf(inst.getId()));
							if (deputies != null) {
								for (InstrumentDeputy instDep : deputies) {
									EmployeeDto dep = OsirisService.getEmployeeByUserId(instDep.getDeputy().toLowerCase());
									ccs.add(dep.getEmail());
								}
								log.info("CC to: " + ccs);
							}
						}
						String subject = Constants.RESA_END_MSG;
						getEmailService().sendJavaMail(message, to, ccs, subject + inst.getName());
					}
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public String buildBookingMailMessage(Reservation reservation, Instrument inst) throws FileNotFoundException, IOException {
		String mailContent = "";
		StringBuilder message = new StringBuilder(Constants.RESA_END_MSG);
		message.append(" <br /><b>");
		message.append(inst.getName()).append("</b>").append("<br /><br />");
		message.append("Starting from <br /><b>").append(reservation.getFromTime().toString());
		message.append("</b><br /><br />");
		message.append("Ending at <br /><b>").append(reservation.getToTime().toString());
		message.append("</b><br /><br />");
		message.append("Booked by <br /><b>").append(reservation.getUsername());
		message.append("</b><br />");
		mailContent = getEmailService().generateContent(Constants.ABSOLUTE_PATH + "email.html", message.toString());
		//mailContent = generateContent("email.html", message.toString());
		return mailContent;
	}

	public EmailService getEmailService() {
		if(emailService == null) {
			emailService = ApplicationContextHolder.getContext().getBean(org.research.kadda.labinventory.service.EmailService.class);
		}
		return emailService;
	}	
	
	
}
