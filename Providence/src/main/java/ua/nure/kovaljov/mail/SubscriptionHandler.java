package ua.nure.kovaljov.mail;

import java.util.List;
import java.util.Set;

import ua.nure.kovaljov.entity.dbentity.History;
import ua.nure.kovaljov.entity.dbentity.User;

public class SubscriptionHandler {
	
	public void handleSubscribers(List<History> history) {
		handlePersonSubscribers(history);
		handleGroupSubscribers(history);
		handleRoomSubscribers(history);
	}
	
	private void handlePersonSubscribers(List<History> history) {
		for(History item : history ) {
			Set<User> users = item.getPerson().getSubscribers();
			for (User user : users) {
				StringBuilder builder = new StringBuilder();
				String s = item.getRoom().getDoorState().equals("entrance") ? 
						" has appeared in room number " : " left room number ";
				builder.append("Send message to ").append(user.getLogin()).append(", that ")
				.append(item.getPerson().getPersonName()).append(s)
				.append(item.getRoom().getRoomName()).append(" at ").append(item.getTime());
				System.out.println(builder.toString());
			}
		}
	}

	private void handleRoomSubscribers(List<History> history) {
		for(History item : history ) {
			Set<User> users = item.getRoom().getSubscribers();
			for (User user : users) {
				StringBuilder builder = new StringBuilder();
				String s = item.getRoom().getDoorState().equals("entrance") ? 
						" has appeared in room number " : " left room number ";
				builder.append("Send message to ").append(user.getLogin()).append(", that ")
				.append(item.getPerson().getPersonName()).append(s)
				.append(item.getRoom().getRoomName()).append(" at ").append(item.getTime());
				System.out.println(builder.toString());
			}
		}
	}

	private void handleGroupSubscribers(List<History> history) {

	}
}
