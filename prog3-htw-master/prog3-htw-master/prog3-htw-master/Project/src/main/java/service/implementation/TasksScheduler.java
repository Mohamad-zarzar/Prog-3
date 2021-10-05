package service.implementation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;

import facades.ServiceFacade;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

import model.implementation.Termin;

/**
 * Dies kann verwendet werden, um eine Erinnerung zu planen, die zu einem
 * bestimmten Zeitpunkt in der Zukunft angezeigt werden soll. 
 * 
 * @author Feras
 *
 */
public class TasksScheduler extends TimerTask {

	// Praedikate, die angeben, wann die Erinnerung angezeigt werden soll.
	private final static Predicate<Termin> TODAY = m -> m.getDate().equals(LocalDate.now().toString());
	private final static Predicate<Termin> TIME_NOW = m -> m.getTime().toString().equals(formatTime(LocalTime.now()));
	private final static Predicate<Termin> TIME_BEFORE_ONE_HOUR =  m -> m.getTime().toString()
																		.equals(formatTime(LocalTime.now().minusHours(1)));
	private final static Predicate<Termin> TIME_BEFORE_THREE_HOURS =  m -> m.getTime().toString()
																		.equals(formatTime(LocalTime.now().minusHours(3)));
	
	
	

	public TasksScheduler() {}

	@Override
	public void run() {

		List<Termin> toDo = getTasks();

		showNotifications(toDo);

	}

	private List<Termin> getTasks() {
		return ServiceFacade.getAllReminders()
							.stream()
							.filter(TODAY.and(TIME_NOW) 
									.or(TODAY.and(TIME_BEFORE_ONE_HOUR))
									.or(TODAY.and(TIME_BEFORE_THREE_HOURS)))
							.collect(Collectors.toList());
	}

	private void showNotifications(List<Termin> toDo) {

		for (Termin r : toDo) {

			Platform.runLater(() -> createNotitfication(r));

		}
		

	}

	private void createNotitfication(Termin reminder) {
		Image img = new Image("/img/bell.png");

		
		Notification notification = Notifications.INFORMATION;
		TrayNotification tray = new TrayNotification();
		
		tray.setNotification(notification);
		tray.setTitle(formatTitle(reminder));
		tray.setMessage(formatMessage(reminder));
		tray.setRectangleFill(Paint.valueOf("#BCB0A7"));
		tray.setAnimation(Animations.SLIDE);
		tray.setImage(img);
		tray.showAndWait();

		new Sound().playSound("/sounds/ping.wav");

	}

	private String formatTitle(Termin reminder) {

		String result = "";

		/*
		 * OLBERTZ Hier schwirren einige Magic Numbers durch die Gegend: 25, 20. Haben die eine Bedeutung?
		 * Im ersten Semester haben wir ja schon gesagt, dass Magic Numbers vermieden und durch Konstanten
		 * mit ordentlichem Namen ersetzt werden sollten.
		 */
		if (reminder.getTitle().length() > 25)
			/* 
			 * OLBERTZ Das hier waere schicker mit einem Formatierungsstring, wie Sie ihn im ersten Semester
			 * kennengelernt haben. Ich zeige das mal an einem Beispiel. Die Konstante TITLE_STRING
			 * gehoert natuerlich nach oben in die Attributliste, aber der Lesbarkeit
			 * halber habe ich das jetzt mal hier zusammengefasst.
			 */
			//final String TITLE_STRING = "<%s> at: %s";
			//final String title = reminder.getTitle().substring(0, 20);
			//final String time = reminder.getTime();
			//result = String.format(TITLE_STRING, title, time);
			result = "<" + reminder.getTitle().substring(0, 20) + "...>" + "at: " + reminder.getTime();
		else
			result = "<" + reminder.getTitle() + ">" + "at: " + reminder.getTime();

		return result;

	}

	private String formatMessage(Termin reminder) {

		String result = "";

		if (reminder.getComment().length() > 50)
			result = reminder.getComment().substring(0, 50) + "...";
		else
			result = reminder.getComment();

		return result;

	}

	/*
	 * OLBERTZ Wundervolle statische Methode fuer eine Werkzeugklasse namens DateTimeUtils.
	 * So was kann man immer brauchen. Auch in anderen Programmen. Dann muesste nur der Name
	 * noch etwas aussagekraeftiger werden, vielleicht formatTimeInHoursAndMinutes.  
	 */
	private static String formatTime(LocalTime time) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		return time.format(dtf);
	}

}
