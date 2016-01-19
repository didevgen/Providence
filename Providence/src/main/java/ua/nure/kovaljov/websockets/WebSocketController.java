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
		WSContainer.webSessions.add(session);
	}

	public void removeSession(Session session) {
		WSContainer.webSessions.remove(session);
	}

	@OnOpen
	public void start(Session session) {
		try {
			addSession(session);
			if (!WSContainer.desktopSessions.isEmpty()) {
				session.getBasicRemote().sendText("connected");
			}
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
	}

	@OnError
	public void onError(Throwable t, Session session) throws Throwable {
		WSContainer.webSessions.remove(session);
	}

	

}