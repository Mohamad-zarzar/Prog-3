package service.implementation;

import java.util.List;


import dao.HibernateUtil;
import model.implementation.Modul;
import model.implementation.Thema;

public class TopicService {

	
	/**
	 * ein neues Thema hinzufuegen.
	 * 
	 * @param thema
	 * @author Feras
	 */
	public static void saveTopic(Thema thema) {
		HibernateUtil.saveTopic(thema);

	}
	
	/**
	 * ein Thema aktualisieren.
	 * 
	 * @param thema
	 * @author Feras
	 */
	public static void updateTopic(Thema thema) {
		HibernateUtil.updateTopic(thema);
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
		return HibernateUtil.getTopic(module, tpoic);

	}

	/**
	 * Gibt alle Themen zurueck , die in dem Modul uebergebenen sind.
	 * 
	 * @param module
	 * @return List<Thema>
	 * @author Feras
	 */
	public static List<Thema> getAllTopics(Modul module) {
		return HibernateUtil.getAllTopics(module);

	}

	/**
	 * das Thema loeschen, ohne eine andere Entitaet zu beeinflussen.
	 * 
	 * @param topic
	 * @author Feras
	 */
	public static void deleteTopic(Thema topic) {
		HibernateUtil.deleteTopic(topic);
	}
	
	
}
