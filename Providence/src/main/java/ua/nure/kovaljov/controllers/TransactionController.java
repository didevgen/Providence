package ua.nure.kovaljov.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.services.TransactionService;

@RestController
public class TransactionController {
	
	@RequestMapping(value = "/transaction/get/last", method = RequestMethod.POST)
	public String getLastDate() {
		Date date  = new TransactionService().getLastDate();
		if (date == null) {
			return new Date(0L).toString();
		}
		return date.toString();
	}
	
	@RequestMapping(value = "/transaction/get/all", method = RequestMethod.GET)
	public List<History> getAllHistory() {
		System.out.println("here");
		List<History> history = new TransactionService().getAllHistory();
		return history;
	}
}
