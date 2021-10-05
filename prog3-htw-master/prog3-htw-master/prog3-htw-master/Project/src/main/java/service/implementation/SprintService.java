package service.implementation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dao.HibernateUtil;
import model.implementation.Projekt;
import model.implementation.Sprint;

public class SprintService {

	/**
	 * Gibt eine Liste mit allen Sprints eines bestimmten Projektes.
	 * 
	 * @param project
	 * @return Liste
	 * @author Feras
	 */
	public static List<Sprint> getAllSprints(Projekt project) {

		return HibernateUtil.getAllSprints(project);
	}

	/**
	 * 
	 * Gibt die aktuelle Anzahl der Sprints in einem Projekt zuruck
	 * 
	 * @param project
	 * @return Anzahl der Sprints in dem Projekt
	 * @author Feras
	 *
	 */
	public static Integer getSprintsNumber(Projekt project) {

		return HibernateUtil.getSprintsNumber(project);

	}

	/**
	 * 
	 * ein Sprint abspeichern.
	 * 
	 * @param project
	 * @return Anzahl der Sprints in dem Projekt
	 * @author Feras
	 *
	 */
	public static void saveSprint(Sprint sprint) {

		HibernateUtil.saveSprint(sprint);

	}

	/**
	 * ein Spirnt aktualisieren.
	 * 
	 * @param sprint
	 * @author Feras
	 */
	public static void updateSprint(Sprint sprint) {

		HibernateUtil.updateSprint(sprint);

	}

	/**
	 * ein Sprint loeschen
	 * 
	 * @param sprint
	 * @author Feras
	 */
	public static void deleteSprint(Sprint sprint) {

		HibernateUtil.deleteSprint(sprint);
	}

	
	/**
	 * Sprints nach dem Anfangsdatum sortieren.
	 * 
	 * @param project
	 * @return List<Sprint>
	 * @author Feras
	 */
	public static List<Sprint> getSortedSprints(Projekt project) {

		List<Sprint> allSprints = getAllSprints(project);

		Collections.sort(allSprints, new Comparator<Sprint>() {
			public int compare(Sprint s1, Sprint s2) {
				if (s1.getSprintAnfang() == null || s2.getSprintAnfang() == null)
					return 0;
				return s1.getSprintAnfang().compareTo(s2.getSprintAnfang());
			}
		});

		int i = 1;
		for (Sprint sprint : allSprints) {
			sprint.setSprintNumber(i++);
			updateSprint(sprint);
		}

		return allSprints;
	}

}
