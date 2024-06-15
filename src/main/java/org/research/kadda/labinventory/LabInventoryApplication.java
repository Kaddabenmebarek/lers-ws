package org.research.kadda.labinventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.service.EmailEndReservationThread;
import org.research.kadda.labinventory.service.SynthesisOrderThread;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Author: Kadda
 */

@SpringBootApplication
@EntityScan({"",""})
public class LabInventoryApplication implements CommandLineRunner {
    static final Logger logger = LogManager.getLogger(LabInventoryApplication.class.getName());
    private static Thread emailEndReservationThread;
    private static Thread synthesisOrderThread;

    public static void main(String[] args) {
        SpringApplication.run(LabInventoryApplication.class, args);
        startEmailEndReservationThread();
        //startSynthesisOrderThread();
    }

    /**
     * used to send automatically email to user who recorded the event, when event is over.
     */
    @SuppressWarnings("unused")
	private static void startSynthesisOrderThread() {
    	synthesisOrderThread = new 	SynthesisOrderThread();
    	synthesisOrderThread.start();
	}

	/**
	 * used to send automatically email to the reservation owner when booking has ended.
	 */
	private static void startEmailEndReservationThread() {
    	logger.debug("Starting EmailEndReservationThread.");
    	emailEndReservationThread = new EmailEndReservationThread();
    	emailEndReservationThread.start();
    }

	@Override
    public void run(String... args) throws Exception {
        logger.info("Application started");
    }
}
