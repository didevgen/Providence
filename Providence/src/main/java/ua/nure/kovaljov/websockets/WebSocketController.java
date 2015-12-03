package ua.nure.kovaljov.websockets;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import ua.nure.kovaljov.websockets.container.WSContainer;

@ServerEndpoint(value = "/websocket")
public class WebSocketController {

	public void addSession(Session session) {
		System.out.println("session added");
		WSContainer.webSessions.add(session);
	}

	public void removeSession(Session session) {
		WSContainer.webSessions.remove(session);
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
		System.out.println("message");
	}

	@OnError
	public void onError(Throwable t, Session session) throws Throwable {
		System.out.println("error");
	}

	

}