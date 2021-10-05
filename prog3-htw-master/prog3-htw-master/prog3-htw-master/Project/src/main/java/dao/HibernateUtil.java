package dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.implementation.Modul;
import model.implementation.Projekt;
import model.implementation.Sprint;
import model.implementation.Student;
import model.implementation.Termin;
import model.implementation.Thema;
import model.implementation.URLs;
import model.interfaces.ModulInterface;
import model.interfaces.StudentInterface;

/*
 * OLBERTZ Architekturtipp: Sie haben sich dafuer entschieden, saemtliche Datenbankzugriffsmethoden in eine
 * einzige Klasse zu schreiben. Das endet natuerlich sehr schnell in einer schlicht gigantischen Klasse,
 * die Sie niemals mehr mit endlichem Aufwand aendern oder erweitern koennen, weil Sie den Ueberblick
 * verloren haben. Damit der Ueberblick nicht ganz verloren geht, haben Sie jeweils Kommentare als
 * Begrenzungen eingefuegt. Aber das ist keine Loesung auf Dauer.  
 * 
 * Am besten machen Sie es so wie ich es gezeigt habe: Goennen sie jedem Themebereich eine eigene
 * Datenbankzugriffsklasse. Dann finden Sie sich sehr schnell zurecht und sehen dann noch sehr schnell,
 * welche Methoden bereits implementiert wurden. Das hier herauszufinden, kann laengere Zeit dauern. 
 * Wenn Sie es dann noch mit Interfaces machen, wie ich es in der Vorlesung gezeigt habe, haben Sie eine
 * noch bessere Uebersicht und dazu noch erheblich mehr Flexibilitaet.
 */

public class HibernateUtil {

	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URLs.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Modul.class).addAnnotatedClass(Thema.class)
			.addAnnotatedClass(Projekt.class).addAnnotatedClass(Termin.class).buildSessionFactory();

	
	

	
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
    /*
     * OLBERTZ Lassen Sie am besten die Namen aller Datenbankzugriffsmethoden mit find beginnen
     * und nicht mit get. get sollte reserviert sein fuer richtige Getter, also lesende Zugriffe auf Attribute einer
     * Klasse. Diese Methode wuerde also besser heissen: findDistinctTopics(). Das ist eine Konvention, die wir
     * in der Vorlesung besprochen haben und die allgemein als guter Stil betrachtet wird.  
     */

	public static List<String> getDistinctTopics() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<String> distinctTopics = null;
		try {
			tx = session.beginTransaction();

			Query<String> query = session.createQuery("SELECT DISTINCT name FROM Thema", String.class);

			distinctTopics = query.list();

			tx.commit();
			return distinctTopics;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return distinctTopics;

	}
	
	
	/**
	 * Gibt eine <String, Long> HashMap zuruckk, mit String fur den Themennamen und
	 * Long fuer die Haeufigkeit
	 * 
	 * @return HashMap <String,Long>
	 * @author Feras
	 */
	public static Map<String, Long> getTopicsFrequency() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<String,Long> topicsFrequency = new LinkedHashMap<>() ;
		try {
			tx = session.beginTransaction();

			String HQL = "SELECT t.name, count(*) "+ 
						 "FROM Thema t  join t.projectsList pl "+ 
						 "group by t.name "+ 
						 "order by count(*) DESC";
							
			
			Query<Object[]> query = session.createQuery(HQL, Object[].class);
			List<Object[]> list = query.list();
			for (Object[] objects : list) {
				String topicName=(String)objects[0];
				Long frequency = (Long )objects[1];
				topicsFrequency.put(topicName, frequency);
			}

			tx.commit();
			return topicsFrequency;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return topicsFrequency;
	}

	/**
	 * Gibt eine <Double, Long> HashMap zuruckk, mit Double fur den Noten und Long
	 * fuer die Haeufigkeit der Note.
	 * 
	 * @return
	 * @author Feras
	 */
	public static Map<Double,Long> getGradesFrequency(){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<Double,Long> gradesFrequency = new LinkedHashMap<>() ;
		try {
			tx = session.beginTransaction();

			String HQL = "SELECT  note, count(*) "+ 
						 "FROM Projekt "+ 
						 "where note is not null "+ 
						 "group by note "+ 
						 "order by count(*) DESC";
							
			
			Query<Object[]> query = session.createQuery(HQL, Object[].class);
			List<Object[]> list = query.list();
			for (Object[] objects : list) {
				Double grade=(Double)objects[0];
				Long frequency = (Long )objects[1];
				gradesFrequency.put(grade, frequency);
			}

			tx.commit();
			return gradesFrequency;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return gradesFrequency;
	}
	
	
	
	
	/***********************************************************
	 * 
	 * 						Projekt
	 * 
	 * @author Feras
	 ***********************************************************/
	/**
	 * Das uebergebene Projekt Speichern oder aktualisieren .
	 * 
	 * @param project
	 * @author Feras
	 */
	/*
	 * OLBERTZ Kein grosses R. Richtig ware saveOrUpdate. Das ist nicht nur Haarspalterei, sondern
	 * hat einen Grund. Wenn das R gross geschrieben ist, denkt man beim Lesen erst einmal an eine
	 * Abkuerzung und erkennt das Wort nicht direkt als Or. Und schon sind wertvolle Sekunden fuer 
	 * unnoetiges Ueberlegen verstrichen. Also achten Sie auch auf solche Kleinigkeiten.
	 */
	public static void saveORUpdate(Projekt project) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(project);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	public static void updateProject(Projekt project) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(project);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	/**
	 * Projekt loeschen.
	 * 
	 * @param project
	 * @author Feras
	 */
	public static void deleteProject(Projekt project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Projekt toBeDeleted = session.load(Projekt.class, project.getId());
			Thema thema = toBeDeleted.getThema();
			Modul module = toBeDeleted.getModul();
			// dieses Projekt vom Modul und dem Thema, zu dem es gehört trennen.
			module.removeProjekt(toBeDeleted);
			if (thema != null)
				thema.removeProjekt(toBeDeleted);

			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	
	public static Projekt getProject(Projekt project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Projekt projectFromDB = null;
		try {
			tx = session.beginTransaction();
			projectFromDB = session.load(Projekt.class, project.getId());
			tx.commit();

			return projectFromDB;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return projectFromDB;

	}

	/**
	 * Gibt alle Projekte von einem bestimmten Modul zurueck.
	 * 
	 * @param module
	 * @return List<Projekt>
	 * @author Feras
	 */
	public static List<Projekt> getAllProjects(Modul module) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Projekt> modules = null;
		try {
			tx = session.beginTransaction();

			Query<Projekt> query = session.createQuery(
					"FROM Projekt p " + 
				    "WHERE p.modul.id = :Value ",
					Projekt.class);
			query.setParameter("Value", module.getId());
			modules = query.list();

			tx.commit();
			return modules;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return modules;

	}
	
	/***********************************************************
	 * 
	 * 						Modul
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

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(modul);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	public static void updateModule(ModulInterface modul) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(modul);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	/**
	 * Gibt alle Module zurueck.
	 * 
	 * @return List<Modul>
	 * @author Feras
	 */
	public static List<Modul> getAllModules() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Modul> modules = null;
		try {
			tx = session.beginTransaction();
			modules = session.createQuery("FROM Modul", Modul.class).list();
			tx.commit();
			return modules;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return modules;

	}
	
	/**
	 * Gibt den Modul mit dem uebergegebenen Namen zurueck.
	 * 
	 * @return List<Modul>
	 * @author Feras
	 */
	public static Modul getModule(String name) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Modul> modules = null;
		Modul lastModuleWithSameName = null;
		try {
			tx = session.beginTransaction();
			Query<Modul> query = session.createQuery("FROM Modul m "+ 
										 "WHERE m.name = :VALUE ", Modul.class);
			
			query.setParameter("VALUE", name);
			modules = query.list();
			tx.commit();
			
			if(modules.size() > 0)
				lastModuleWithSameName = modules.get(modules.size()-1);
			
			return lastModuleWithSameName;
				
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lastModuleWithSameName;

	}


	/**
	 * ein bestimmtes Modul loeschen.
	 * 
	 * @param module
	 * @author Feras
	 */
	public static void deleteModule(Modul module) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Modul toBeDeleted = session.load(Modul.class, module.getId());
			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param toBeUpdated
	 * @param name
	 * @author Feras
	 */
	public static void updateModule(ModulInterface toBeUpdated, String name) { // TODO
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ModulInterface module = (Modul) session.get(Modul.class, toBeUpdated.getId());
			module.setName(name);
			session.update(module);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/*
	 * OLBERTZ Wieso programmieren Sie hier in dieser Methode einmal gegen ein Interface und einmal
	 * gegen eine Implementierung? Wenn schon, dann sollten Sie beide mal mit einem Interface arbeiten.
	 */
	public static void updateModule(ModulInterface toBeUpdated, Thema topic) { // TODO
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Modul module = (Modul) session.get(Modul.class, toBeUpdated.getId());

			module.addThema(topic);

			session.update(module);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/***********************************************************
	 * 
	 * 						Thema
	 * 
	 * @author Feras
	 ***********************************************************/
	
	public static void updateTopic(Thema thema) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(thema);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	public static void saveTopic(Thema thema) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(thema);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

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
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Thema topic = null;
		try {
			tx = session.beginTransaction();

			Query<Thema> query = session.createQuery(
					// Select t -> es gibt ein Objekt von Thema zurueck.
					"SELECT t " +
					"FROM Modul m JOIN m.themen t " +
				    "WHERE m.id = :MODULE_ID AND t.id = :TOPIC_ID",
					Thema.class);

			query.setParameter("MODULE_ID", module.getId());
			query.setParameter("TOPIC_ID", tpoic.getId());

			topic = query.getSingleResult();

			tx.commit();
			return topic;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return topic;

	}

	/**
	 * Gibt alle Themen zurueck , die in dem Modul  sind.
	 * 
	 * @param module
	 * @return List<Thema>
	 * @author Feras
	 */
	public static List<Thema> getAllTopics(Modul module) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Thema> topics = null;
		try {
			tx = session.beginTransaction();

			Query<Thema> query = session.createQuery(
					// Select t -> it returns an object of Thema.
					"SELECT t "+ 
					"FROM Modul m JOIN m.themen t "+
					"where m.id = :Value ", Thema.class);

			query.setParameter("Value", module.getId());
			topics = query.list();

			tx.commit();
			return topics;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return topics;

	}

	/**
	 * das Thema loeschen, ohne eine andere Entitaet zu beeinflussen.
	 * 
	 * @param topic
	 * @author Feras
	 */
	public static void deleteTopic(Thema topic) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Thema toBeDeleted = session.load(Thema.class, topic.getId());

			// das Thema von allen Modulen und Projekten, zu denen es gehört,Trennen .
			Set<Modul> modules = toBeDeleted.getModules();
//			Set<Projekt> projects = toBeDeleted.getProjectsList();

			for (Modul modul : modules) {
				List<Thema> topics = modul.getThemen();
				// toBeDeleted.removeModule(modul);
				topics.remove(toBeDeleted);
			}

//			for (Projekt project : projects) {
//				project.removeThema(toBeDeleted);
//			}

			session.flush();

			// loesche es aus der DB
			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}
	}

	/***********************************************************
	 * 
	 * 						Sprint
	 * 
	 * @author Feras
	 ***********************************************************/

	public static void saveSprint(Sprint sprint) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(sprint);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	public static void updateSprint(Sprint sprint) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(sprint);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	public static List<Sprint> getAllSprints(Projekt project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Sprint> sprints = null;
		try {
			tx = session.beginTransaction();

			Query<Sprint> query = session.createQuery(
					// Select t -> it returns an object of Sprint.
					"SELECT s "+ 
					"FROM Projekt p JOIN p.sprints s "+
					"WHERE p.id = :Value ", Sprint.class);

			query.setParameter("Value", project.getId());
			sprints = query.list();

			tx.commit();
			return sprints;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return sprints;

	}

	public static Integer getSprintsNumber(Projekt project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer numberOfSprints = null;
		try {
			tx = session.beginTransaction();

			Query<Sprint> query = session.createQuery(
					// Select t -> it returns an object of Sprint.
					"SELECT s "+
					"FROM Projekt p JOIN p.sprints s "+
					"WHERE p.id = :Value ", Sprint.class);

			query.setParameter("Value", project.getId());
			numberOfSprints = query.list().size();

			tx.commit();
			return numberOfSprints;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return numberOfSprints;

	}

	
	public static void deleteSprint(Sprint sprint) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Sprint toBeDeleted = session.load(Sprint.class, sprint.getId());
			Projekt project = toBeDeleted.getProjekt();
			// dieses Sprint vom Projekt , zu dem es gehört trennen.
			project.removeSprint(toBeDeleted);

			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/***********************************************************
	 * 
	 * 						URL
	 * 
	 * @author Feras
	 ***********************************************************/
	public static void addURL(URLs url) {

		Session session = sessionFactory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(url);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}
	
	public static void updateURL(URLs url) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(url);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	public static List<URLs> getURLs(Projekt project) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<URLs> urls = null;
		try {
			tx = session.beginTransaction();

			Query<URLs> query = session.createQuery(
					// Select u -> Es gibt ein Objekt des Linkes zurueck
					"Select u " 
				 + "From Projekt p JOIN p.urls u " 
				 + "where p.id = :Value ", URLs.class);

			query.setParameter("Value", project.getId());
			urls = query.list();

			tx.commit();

			return urls;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return urls;
	}

	public static void deleteURL(URLs url) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			URLs toBeDeleted = session.load(URLs.class, url.getId());
			Projekt project = toBeDeleted.getProject();
			// einen Link vom Projekt , zu dem er gehört trennen
			project.removeUrl(toBeDeleted);

			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/***********************************************************
	 * 
	 * 							Termin
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<Termin> getAllReminders() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Termin> reminders = null;
		try {
			tx = session.beginTransaction();
			// Wir verwenden den Namen der Entitaetsklasse und nicht den Namen der Tabelle in
			// der Datenbank, indem wir Daten abrufen.
			reminders = session.createQuery("FROM Termin", Termin.class).list();

			tx.commit();

			return reminders;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return reminders;

	}

	public static Termin getReminder(Termin rem) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Termin reminder = null;
		try {
			tx = session.beginTransaction();
			reminder = session.load(Termin.class, rem.getId());
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return reminder;

	}


	public static void addReminder(Termin reminder) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(reminder);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void updateReminder(Termin oldRem) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Termin reminderFromDb = session.load(Termin.class, oldRem.getId());
			reminderFromDb.setTitle(oldRem.getTitle());
			reminderFromDb.setComment(oldRem.getComment());
			reminderFromDb.setDate(String.valueOf(oldRem.getDate())); 
			reminderFromDb.setTime(oldRem.getTime());
			reminderFromDb.setFinished(oldRem.isFinished());

			session.update(reminderFromDb);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void updateReminderStatus(Termin oldRem) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Termin reminderFromDB = session.load(Termin.class, oldRem.getId());

			reminderFromDB.setFinished(oldRem.isFinished());

			session.update(reminderFromDB);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}


	public static void deleteReminder(Termin reminder) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Termin toBeDeleted = session.load(Termin.class, reminder.getId());
			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
	/***********************************************************
	 * 
	 * 							Student
	 * 
	 * 
	 ***********************************************************/
	
	public static void addStudent(StudentInterface student) {

		Session session = sessionFactory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(student);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	public static void updateStudent(StudentInterface student_) {

		Session session = sessionFactory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student studentFromDb = session.load(Student.class, student_.getMatrikelnummer());
			studentFromDb.setMatrikelnummer(student_.getMatrikelnummer());
			studentFromDb.setName(student_.getName());
			studentFromDb.setVorname(student_.getVorname());
			studentFromDb.setNote(student_.getNote());
			studentFromDb.setProjekt(student_.getProjekt());


			session.saveOrUpdate(studentFromDb);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	/**
	 * Gibt den Student mit der gegebenen MatrikelNr zurueck.
	 * 
	 * @param matrikelnummer
	 * @return Student
	 */
	public static StudentInterface getStudent(int matrikelnummer) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		StudentInterface student = null;
		try {
			tx = session.beginTransaction();
			student = session.load(Student.class, matrikelnummer);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return student;

	}

	/**
	 * Gibt List mit allen Studenten zurueck.
	 * 
	 * @return liste mit allen Studenten
	 */
	public static List<StudentInterface> getStudents() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<StudentInterface> students = null;
		try {
			tx = session.beginTransaction();
			students = session.createQuery("FROM Student", StudentInterface.class).list();
			tx.commit();

			return students;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return students;
	}

	/**
	 * Gibt eine Liste mit allen Studenten eines bestimmten Projekts zuruck.
	 * 
	 * @return liste mit allen Studenten eines bestimmten Projekts
	 * @author Feras
	 */
	public static List<Student> getStudents(Projekt project) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Student> students = null;
		try {
			tx = session.beginTransaction();

			Query<Student> query = session.createQuery(
					// Select s -> Es gibt ein Objekt des Studenten zurueck..
					"SELECT s " +
					"FROM Projekt p JOIN p.students s " + 
					"WHERE p.id = :Value ", Student.class);

			query.setParameter("Value", project.getId());
			students = query.list();

			tx.commit();

			return students;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return students;
	}

	/**
	 * Student von einem zugehörigen Projekt trennen und dann aus der DB loeschen.
	 * 
	 * @param student
	 * @author Feras
	 */
	public static void deleteStudent(Student student) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student toBeDeleted = session.load(Student.class, student.getMatrikelnummer());
			Projekt project = toBeDeleted.getProjekt();
			// den Student vom Projekt , zu dem er gehört trennen
			project.removeStudent(toBeDeleted);

			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param matrikelnummer
	 * @return True,ob die Matrikelnummer in der Datenbank gefunden wurde
	 */
	public static Boolean exists(int matrikelnummer) {
		Session session = sessionFactory.openSession();
		try {

			Query<Integer> query = session.createQuery(
					"SELECT s.matrikelnummer " + "FROM Student s " + "WHERE s.matrikelnummer = :VALUE", Integer.class);
			query.setParameter("VALUE", matrikelnummer);

			return (query.uniqueResult() != null);
		} finally {
			session.close();
		}

	}
	
	

}
