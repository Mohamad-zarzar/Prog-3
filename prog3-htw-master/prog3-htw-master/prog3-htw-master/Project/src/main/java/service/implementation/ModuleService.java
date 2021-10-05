package service.implementation;

import java.util.List;


import dao.HibernateUtil;
import model.implementation.Modul;
import model.implementation.Thema;
import model.interfaces.ModulInterface;

public class ModuleService {



	/**
	 * ein Modul speichern oder aktualisieren .
	 * 
	 * @param modul
	 * @author Feras
	 */
	public static void addModule(ModulInterface modul) {

		HibernateUtil.addModule(modul);

	}

	/**
	 * Gibt alle Module zurueck.
	 * 
	 * @return List<Modul>
	 * @author Feras
	 */
	public static List<Modul> getAllModules() {
		return HibernateUtil.getAllModules();

	}

	/**
	 * ein bestimmtes Modul loeschen.
	 * 
	 * @param module
	 * @author Feras
	 */
	public static void deleteModule(Modul module) {
		HibernateUtil.deleteModule(module);
	}

	public static void updateModule(ModulInterface modul) {

		HibernateUtil.updateModule(modul);

	}

	/**
	 * 
	 * @param toBeUpdated
	 * @param name
	 * @author Feras
	 */
	public static void updateModule(ModulInterface toBeUpdated, String name) { // TODO
		HibernateUtil.updateModule(toBeUpdated, name);
	}

	public static void updateModule(ModulInterface toBeUpdated, Thema topic) { // TODO
		HibernateUtil.updateModule(toBeUpdated, topic);
	}

}
