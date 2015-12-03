package ua.nure.kovaljov.websockets.service;

import ua.nure.kovaljov.websockets.container.WSContainer;

public class WSBridge {
	
	public void transferToClient(String message) {
		System.out.println("bridge");
		WSContainer.sendToAllWebConnectedSessions(message);
	}
}
