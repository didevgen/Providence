package ua.nure.kovaljov.websockets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import ua.nure.kovaljov.entity.Transaction;
import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.mail.SubscriptionHandler;
import ua.nure.kovaljov.model.TransactionModel;
import ua.nure.kovaljov.services.TransactionService;
import ua.nure.kovaljov.utils.WSHelper;
import ua.nure.kovaljov.websockets.container.WSContainer;
import ua.nure.kovaljov.websockets.service.WSBridge;

@ServerEndpoint(value = "/desktop")
public class DesktopController {
	private Logger log = LogManager.getLogger(DesktopController.class);
	
	public void addSession(Session session) {
		WSContainer.desktopSessions.add(session);
	}

	public void removeSession(Session session) {
		WSContainer.desktopSessions.remove(session);
	}

	@OnOpen
	public void start(Session session) {
		try {
			addSession(session);
			new WSBridge().transferToClient("connected");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@OnClose
	public void end(Session session) {
		removeSession(session);
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		if (message.equals("getLastDate")) {
			Date date  = new TransactionService().getLastDate();
			if (date == null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				WSContainer.sendToAllDesktopConnectedSessions(format.format(new Date(0L)));
				return;
			}
			WSContainer.sendToAllDesktopConnectedSessions(date.toString());
			return;
		}
		Transaction[] transactions = new Gson().fromJson(message, Transaction[].class);
		List<TransactionModel> model = TransactionModel.getModelFromTransaction(transactions);
		new TransactionService().insertTransactionModels(model);
		List<History> resultHistory = new TransactionService().getLatestHistory(WSHelper.getMinDate(model));
		SubscriptionHandler handler = new SubscriptionHandler();
		handler.handleSubscribers(resultHistory);
		WSBridge bridge = new WSBridge();
		bridge.transferToClient(new Gson().toJson(resultHistory));
	}

	@OnError
	public void onError(Throwable t, Session session) throws Throwable {
		log.error(t.getMessage());
		WSContainer.desktopSessions.clear();
		new WSBridge().transferToClient("disconnected");
		WSContainer.webSessions.clear();
	}

	

}
