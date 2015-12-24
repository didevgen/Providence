package ua.nure.kovaljov.database.dao;

import java.util.Date;
import java.util.List;

import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.model.TransactionModel;

public interface TransactionDAO {
	Date getLatestDate();

	void insertTransactions(List<TransactionModel> transactions);

	List<History> getAllHistory();
	
	List<History> getMonthHistory();
	
	List<History> getHistoryByCardNumber(long cardNumber);
	
	List<History> getHistoryGreaterThanDate(Date date);
}
