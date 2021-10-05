package service.implementation;

import java.util.List;



import dao.HibernateUtil;
import model.implementation.Modul;
import model.implementation.Projekt;


public class ProjectService {
	
	/**
	 * ein projekt hinzufuegen oder aktualisieren.
	 * 
	 * @param project
	 * @author Feras
	 */
	public static void saveORUpdate(Projekt project) {

		HibernateUtil.saveORUpdate(project);

	}
	

	/**
	 * ein projekt loeschen.
	 * 
	 * @param project
	 * @author Feras
	 */
	public static void deleteProject(Projekt project) {

		HibernateUtil.deleteProject(project);

	}
	
	/**
	 * Gibt ein bestimmtes Projekt zuruck
	 *  
	 * @param project
	 * @return Project
	 * @author Feras
	 */
	public static Projekt getProject(Projekt project) {
		
		return HibernateUtil.getProject(project);

	}


	/**
	 * 
	 * Gibt alle Projekte zuruck,die zu dem uebergegebenen Modul gehoeren.
	 * 
	 * @param module
	 * @return List<Projekt>
	 * @author Feras
	 */
	public static List<Projekt> getAllProjects(Modul module) {

		List<Projekt> modules = HibernateUtil.getAllProjects(module);
		return modules;
	}




}
