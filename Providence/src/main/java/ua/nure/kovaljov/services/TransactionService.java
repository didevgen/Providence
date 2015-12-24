package ua.nure.kovaljov.services;

import java.util.Date;
import java.util.List;

import ua.nure.kovaljov.database.dao.impl.TransactionDAOImpl;
import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.model.TransactionModel;

public class TransactionService {
	public Date getLastDate() {
		return new TransactionDAOImpl().getLatestDate();
	}
	
	public void insertTransactionModels(List<TransactionModel> models) {
		new TransactionDAOImpl().insertTransactions(models);
	}
	
	public List<History> getAllHistory() {
		return new TransactionDAOImpl().getAllHistory();
	}
	
	public List<History> getLastMonthHistory() {
		return new TransactionDAOImpl().getMonthHistory();
	}
	
	public List<History> getUserHistory(long cardNumber){
		return new TransactionDAOImpl().getHistoryByCardNumber(cardNumber);
	}
	public List<History> getLatestHistory(Date date){
		return new TransactionDAOImpl().getHistoryGreaterThanDate(date);
	}
}
