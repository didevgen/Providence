package ua.nure.kovaljov.controllers;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.services.TransactionService;

@RestController
public class TransactionController {
	private Logger log = LogManager.getLogger(TransactionController.class);
	@RequestMapping(value = "/transaction/get/last", method = RequestMethod.POST)
	public String getLastDate() {
		log.entry();
		Date date  = new TransactionService().getLastDate();
		if (date == null) {
			return new Date(0L).toString();
		}
		return date.toString();
	}
	
	@RequestMapping(value = "/transaction/get/all", method = RequestMethod.POST)
	public List<History> getAllHistory() {
		log.entry();
		List<History> history = new TransactionService().getAllHistory();
		return history;
	}
	
	@RequestMapping(value = "/transaction/get/month", method = RequestMethod.POST)
	public List<History> getMonthHistory() {
		log.entry();
		List<History> history = new TransactionService().getLastMonthHistory();
		return history;
	}
	
	@RequestMapping(value = "/transaction/get/{cardNumber}", method = RequestMethod.POST)
	public List<History> getUserHistory(@PathVariable long cardNumber) {
		log.entry(cardNumber);
		List<History> history = new TransactionService().getUserHistory(cardNumber);
		return history;
	}
}
