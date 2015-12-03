package ua.nure.kovaljov.websockets.container;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

public class WSContainer {
	public static final Set<Session> desktopSessions = new HashSet<Session>();
	public static  final Set<Session> webSessions = new HashSet<Session>();
	
	public static void sendToWebSession(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException ex) {
			WSContainer.webSessions.remove(session);
		}
	}

	public static void sendToAllWebConnectedSessions(String message) {
		for (Session session : WSContainer.webSessions) {
			sendToWebSession(session, message);
		}
	}
	
	public static  void sendToDestopSession(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException ex) {
			WSContainer.desktopSessions.remove(session);
		}
	}

	public static  void sendToAllDesktopConnectedSessions(String message) {
		for (Session session : WSContainer.desktopSessions) {
			sendToDestopSession(session, message);
		}
	}
}
