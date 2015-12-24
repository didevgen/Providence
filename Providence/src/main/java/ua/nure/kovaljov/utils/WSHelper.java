package ua.nure.kovaljov.utils;

import java.util.Date;
import java.util.List;

import ua.nure.kovaljov.model.TransactionModel;

public class WSHelper {
	public static Date getMinDate(List<TransactionModel> models) {
		Date date = new Date(System.currentTimeMillis());
		for (TransactionModel model : models) {
			if (date.compareTo(model.getTime())>0) {
				date = model.getTime();
				
			}
		}
		return date;
	}
}
