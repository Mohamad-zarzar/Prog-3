package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 
 * @author Feras
 *
 */
// OLBERTZ Wofuer steht M im Bezeichner?
public class MDateTime {

	/*
	 * OLBERTZ Und wie im ersten Semester schon gesagt: Eine Methode tut etwas, also sollte ihr Name ein
	 * TU-Wort = Verb enthalten. Sinnvoll waere ein Name wie determineDifferenceBetweenTodayAnd.
	 * 
	 * Sie haben generell nicht dokumentiert. Gerade bei solchen Methoden tut die fehlende Dokumentation
	 * besonders weh. Wenn ich den Aufruf der Methode sehe, habe ich nicht die geringste Ahnung, was
	 * in dem zurueckgegebenen String steht. Also muss ich mir die Muehe machen und mir die Methode
	 * genau ansehen, obwohl mich die Implementierung vielleicht gar nicht interessiert und ich auch 
	 * nicht die Zeit dafuer habe. Hier muss ganz ganz dringend die Rueckgabe dokumentiert werden. Das
	 * waere das mindeste.
	 */
	public static String diffrenceBetweenTodayAnd(LocalDate date, LocalTime time) {

		String result = "";
		
		if(date.isBefore(LocalDate.now()) || date.equals(LocalDate.now()) && time.isBefore(LocalTime.now())) 
			return result;
		
		
		LocalDate targetDate = date;
		LocalTime targetTime = time;

		LocalDate todayDate = LocalDate.now();
		LocalTime todayTime = LocalTime.now();

		Instant targetInstant = targetDate.atTime(targetTime).atZone(ZoneId.systemDefault()).toInstant();
		Instant todayInstant = todayDate.atTime(todayTime).atZone(ZoneId.systemDefault()).toInstant();

		Date date1 = Date.from(targetInstant);
		Date date2 = Date.from(todayInstant);

		long diff = date1.getTime() - date2.getTime();
		// OLBERTZ Auch das hier alles in Konstanten.
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		if (diffDays == 0) {
			if(diffHours == 0)
				result +="remaining time: "+  diffMinutes + "  minutes" ;
			
			else
				result +="remaining time: "+   diffHours + " hours, " + diffMinutes + "  minutes" ;
			
		}else {
			result +="remaining time: "+  diffDays + " days, "
										+ diffHours + " hours, "
										+ diffMinutes + " minutes";
		}

		return result;

	}
}
