package ua.nure.kovaljov.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.kovaljov.services.TransactionService;

@RestController
public class TransactionController {
	
	@RequestMapping(value = "/transaction/get/last", method = RequestMethod.POST)
	public String registerUser() {
		Date date  = new TransactionService().getLastDate();
		if (date == null) {
			return new Date(0L).toString();
		}
		return date.toString();
	}
}
