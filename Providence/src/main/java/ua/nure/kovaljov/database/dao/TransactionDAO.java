package ua.nure.kovaljov.database.dao;

import java.util.Date;
import java.util.List;

import ua.nure.kovaljov.model.TransactionModel;

public interface TransactionDAO {
	Date getLatestDate();
	void insertTransactions(List<TransactionModel> transactions);
}

