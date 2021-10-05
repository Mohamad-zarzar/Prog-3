package facades;

import java.util.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.implementation.*;
import model.interfaces.*;
import service.implementation.*;

public class ServiceFacade {
/*
 * OLBERTZ Diese Fassadenklasse ist nicht schlecht, aber Sie ziehen den Code durch Leerzeilen sehr in die
 * Laenge. Das kann man machen, aber ich finde es nicht guenstig. Letztendlich ist es allerdings wohl auch
 * eine Sache des eigenen Stils. 
 */
	
	
	/***********************************************************
	 * 
	 * 						Statistiken
	 * 
	 * @author Feras
	 ***********************************************************/
	
	/**
	 * Gibt eine Liste mit den eindeutigen Namen aller Themen zurueck.
	 * 
	 * @return List<Thema>
	 * @author Feras
	 */
	public static List<String> getDistinctTopics() {
		return StatisticsService.getDistinctTopics();
	}
	
	
	/**
	 * Gibt eine <String, Long> HashMap zuruckk, mit String fur den Themennamen und
	 * Long fuer die Haeufigkeit
	 * 
	 * @return HashMap <String,Long>
	 * @author Feras
	 */
	public static Map<String, Long> getTopicsFrequency() {

	return StatisticsService.getTopicsFrequency();
	}

	/**
	 * Gibt eine <Double, Long> HashMap zuruckk, mit Double fur den Noten und Long
	 * fuer die Haeufigkeit der Note.
	 * 
	 * @return
	 * @author Feras
	 */
	public static Map<Double,Long> getGradesFrequency(){
		
		return StatisticsService.getGradesFrequency();
	}
	
	

	/***********************************************************
	 * 
	 *						 Modul
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * ein Modul speichern oder aktualisieren .
	 * 
	 * @param modul
	 * @author Feras
	 */
	public static void addModule(ModulInterface modul) {

		ModuleService.addModule(modul);

	}

	/**
	 * Gibt alle Module zurueck.
	 * 
	 * @return List<Modul>
	 * @author Feras
	 */
	public static List<Modul> getAllModules() {
		return ModuleService.getAllModules();

	}

	/**
	 * ein bestimmtes Modul loeschen.
	 * 
	 * @param module
	 * @author Feras
	 */
	public static void deleteModule(Modul module) {
		ModuleService.deleteModule(module);
	}

	public static void updateModule(ModulInterface modul) {

		ModuleService.updateModule(modul);

	}

	/**
	 * 
	 * @param toBeUpdated
	 * @param name
	 * @author Feras
	 */
	public static void updateModule(ModulInterface toBeUpdated, String name) { // TODO
		ModuleService.updateModule(toBeUpdated, name);
	}

	public static void updateModule(ModulInterface toBeUpdated, Thema topic) { // TODO
		ModuleService.updateModule(toBeUpdated, topic);
	}

	
	/***********************************************************
	 * 
	 * 						Projekt
	 * 
	 * @author Feras
	 ***********************************************************/

	public static void saveORUpdate(Projekt project) {

		ProjectService.saveORUpdate(project);

	}

	public static void deleteProject(Projekt project) {

		ProjectService.deleteProject(project);
	}

	public static Projekt getProject(Projekt project) {

		return ProjectService.getProject(project);

	}

	public static List<Projekt> getAllProjects(Modul module) {

		List<Projekt> modules = ProjectService.getAllProjects(module);
		return modules;
	}



	/***********************************************************
	 * 
	 * 						Thema
	 * 
	 * @author Feras
	 ***********************************************************/
	
	/**
	 * Gibt alle Themen zurueck , die in dem Modul uebergebenen sind.
	 * 
	 * @param module
	 * @return List<Thema>
	 * @author Feras
	 */
	public static List<Thema> getAllTopics(Modul module) {
		return TopicService.getAllTopics(module);

	}

	/**
	 * Gibt ein bestimmtes Thema zurueck.
	 * 
	 * @param module Das Modul, zu dem das Thema gehört.
	 * @param tpoic
	 * @return Thema
	 * @author Feras
	 */
	public static Thema getTopic(Modul module, Thema tpoic) {
		return TopicService.getTopic(module, tpoic);

	}

	public static void saveTopic(Thema thema) {
		TopicService.saveTopic(thema);

	}

	public static void updateTopic(Thema thema) {
		TopicService.updateTopic(thema);
	}

	/**
	 * das Thema loeschen, ohne eine andere Entitaet zu beeinflussen.
	 * 
	 * @param topic
	 * @author Feras
	 */
	public static void deleteTopic(Thema topic) {
		TopicService.deleteTopic(topic);
	}
	
	/***********************************************************
	 * 
	 *						 Sprint
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * Gibt eine Liste mit allen Sprints eines bestimmten Projektes.
	 * 
	 * @param project
	 * @return Liste
	 * @author Feras
	 */
	public static List<Sprint> getAllSprints(Projekt project) {

		return SprintService.getAllSprints(project);
	}
	
	public static List<Sprint> getSortedSprints(Projekt project) {

		return SprintService.getSortedSprints(project);
	}

	public static Integer getSprintsNumber(Projekt project) {

		return SprintService.getSprintsNumber(project);

	}

	public static void saveSprint(Sprint sprint) {

		SprintService.saveSprint(sprint);

	}

	public static void updateSprint(Sprint sprint) {

		SprintService.updateSprint(sprint);

	}

	public static void deleteSprint(Sprint sprint) {

		SprintService.deleteSprint(sprint);
	}


	/***********************************************************
	 * 
	 * 						URL
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<URLs> getURLs(Projekt project) {


		return URLsService.getURLs(project);
	}

	public static void addURL(URLs url) {

		URLsService.addURL(url);

	}
	
	
	public static void updateURL(URLs toBeUpdatedURL) {
		URLsService.updateURL(toBeUpdatedURL);
		
	}

	public static void deleteURL(URLs url) {
		URLsService.deleteURL(url);
	}
	

	/***********************************************************
	 * 
	 * 							Termin
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<Termin> getAllReminders() {
		
		ObservableList<Termin> allReminders = FXCollections.observableList(ReminderService.getAllReminders());
		return allReminders;

	}

	public static Termin getReminder(Termin rem) {

		return ReminderService.getReminder(rem);
	}


	public static void addReminder(Termin reminder) {
		ReminderService.addReminder(reminder);

	}

	public static void updateReminder(Termin oldRem) {
		ReminderService.updateReminder(oldRem);
	}

	public static void updateReminderStatus(Termin oldRem) {
		ReminderService.updateReminderStatus(oldRem);
	}


	public static void deleteReminder(Termin reminder) {
		ReminderService.deleteReminder(reminder);
	}
	

	

	/***********************************************************
	 * 
	 * 							Student
	 * 
	 * 
	 ***********************************************************/

	/**
	 * Erfragt von der Datenbank alle Studenten, die aktuell an irgendeinem Projekt
	 * teilnehmen
	 * 
	 * @return Liste der Studenten
	 */
	public static List<StudentInterface> getStudents() {
		return StudentService.getStudents();
	}

	/**
	 * Erfragt von der Datenbank einen Student.
	 * 
	 * @return Student
	 */
	public static StudentInterface getStudent(int matrikelnummer) {

		return StudentService.getStudent(matrikelnummer);
	}
	
	public static boolean exists(StudentInterface student) {
		return StudentService.exists(student.getMatrikelnummer());
	}

	/**
	 * Erfragt von der Datenbank alle Studenten, die aktuell an einem bestimmten
	 * Projekt teilnehmen
	 * 
	 * @return Liste der Studenten
	 * @author Feras
	 */
	public static List<Student> getStudents(Projekt project) {

		List<Student> students = StudentService.getStudents(project);
		return students;
	}

	public static void deleteStudent(Student student) {

		 StudentService.deleteStudent(student);
	}

	/**
	 * Fuegt in der Datenbank einen neuen Studenten hinzu
	 * 
	 * @return true, wenn erfolgreich eingefuegt
	 */
	public static boolean addStudent(StudentInterface student) {
		return StudentService.addStudent(student);
	}


	

}
