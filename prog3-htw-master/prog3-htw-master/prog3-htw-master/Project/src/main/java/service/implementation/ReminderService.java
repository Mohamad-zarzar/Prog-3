package service.implementation;

import java.util.List;


import dao.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.implementation.Termin;

public class ReminderService {

	/***********************************************************
	 * 
	 * 							Termin
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<Termin> getAllReminders() {
		
		ObservableList<Termin> allReminders = FXCollections.observableList(HibernateUtil.getAllReminders());
		return allReminders;

	}

	public static Termin getReminder(Termin rem) {

		return HibernateUtil.getReminder(rem);
	}


	public static void addReminder(Termin reminder) {
		HibernateUtil.addReminder(reminder);

	}

	public static void updateReminder(Termin oldRem) {
		HibernateUtil.updateReminder(oldRem);
	}

	public static void updateReminderStatus(Termin oldRem) {
		HibernateUtil.updateReminderStatus(oldRem);
	}


	public static void deleteReminder(Termin reminder) {
		HibernateUtil.deleteReminder(reminder);
	}
	




	

	
}
