package service.implementation;


import java.util.List;
import java.util.Map;


import dao.HibernateUtil;

public class StatisticsService {

	/**
	 * Gibt eine Liste mit den eindeutigen Namen aller Themen zurueck.
	 * 
	 * @return List<Thema>
	 * @author Feras
	 */
	public static List<String> getDistinctTopics() {
		return HibernateUtil.getDistinctTopics();
	}
	
	
	/**
	 * Gibt eine <String, Long> HashMap zuruckk, mit String fur den Themennamen und
	 * Long fuer die Haeufigkeit
	 * 
	 * @return HashMap <String,Long>
	 * @author Feras
	 */
	public static Map<String, Long> getTopicsFrequency() {

		return HibernateUtil.getTopicsFrequency();
	}

	/**
	 * Gibt eine <Double, Long> HashMap zuruckk, mit Double fur den Noten und Long
	 * fuer die Haeufigkeit der Note.
	 * 
	 * @return
	 * @author Feras
	 */
	public static Map<Double,Long> getGradesFrequency(){
		return HibernateUtil.getGradesFrequency();
	}
	
	
}
