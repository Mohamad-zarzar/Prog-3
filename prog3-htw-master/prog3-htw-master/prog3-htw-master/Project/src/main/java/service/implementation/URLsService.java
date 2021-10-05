package service.implementation;

import java.util.List;


import dao.HibernateUtil;
import model.implementation.Projekt;
import model.implementation.URLs;

public class URLsService {

	
	/**
	 * einen neune URL hinzufuegen
	 * 
	 * @param url
	 * @author Feras
	 */
	
	public static void addURL(URLs url) {

		HibernateUtil.addURL(url);

	}
	

	/**
	 * Gibt eine Liste mit allen URLs, die zu dem uebergegebenen Projekt 
	 * gehoeren.
	 * 
	 * @param project
	 * @return List<URLs>
	 * @author Feras
	 */
	public static List<URLs> getURLs(Projekt project) {


		return HibernateUtil.getURLs(project);
	}

	
	/**
	 * 
	 * einen URL aktualisieren.
	 * 
	 * @param url
	 * @author Feras
	 */
	
	public static void updateURL(URLs url) {

		HibernateUtil.updateURL(url);

	}


	/**
	 * einen URL loeschen
	 * 
	 * @param url
	 * @author Feras
	 */
	public static void deleteURL(URLs url) {
		HibernateUtil.deleteURL(url);
	}
}
