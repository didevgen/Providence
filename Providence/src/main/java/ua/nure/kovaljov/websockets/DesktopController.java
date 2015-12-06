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

import com.google.gson.Gson;

import ua.nure.kovaljov.entity.Transaction;
import ua.nure.kovaljov.model.TransactionModel;
import ua.nure.kovaljov.services.TransactionService;
import ua.nure.kovaljov.websockets.container.WSContainer;
import ua.nure.kovaljov.websockets.service.WSBridge;

@ServerEndpoint(value = "/desktop")
public class DesktopController {


	public void addSession(Session session) {
		System.out.println("session added");
		WSContainer.desktopSessions.add(session);
	}

	public void removeSession(Session session) {
		WSContainer.desktopSessions.remove(session);
		System.out.println("session removed");
	}

	@OnOpen
	public void start(Session session) {
		try {
			addSession(session);
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
		System.out.println("desktopController");
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
//		WSBridge bridge = new WSBridge();
//		bridge.transferToClient(new Gson().toJson(model));
	}

	@OnError
	public void onError(Throwable t, Session session) throws Throwable {
		System.out.println(t.getMessage());
	}

	

}
