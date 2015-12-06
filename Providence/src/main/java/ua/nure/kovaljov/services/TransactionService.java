package ua.nure.kovaljov.services;

import java.util.Date;
import java.util.List;

import ua.nure.kovaljov.database.dao.impl.TransactionDAOImpl;
import ua.nure.kovaljov.model.TransactionModel;

public class TransactionService {
	public Date getLastDate() {
		return new TransactionDAOImpl().getLatestDate();
	}
	
	public void insertTransactionModels(List<TransactionModel> models) {
		new TransactionDAOImpl().insertTransactions(models);
	}
}
