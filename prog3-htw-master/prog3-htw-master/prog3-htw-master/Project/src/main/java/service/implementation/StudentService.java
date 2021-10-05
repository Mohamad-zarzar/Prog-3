package service.implementation;

import java.util.List;


import dao.HibernateUtil;
import model.implementation.Projekt;
import model.implementation.Student;
import model.interfaces.StudentInterface;

public class StudentService {

	
	/**
	 * Uebergibt die Werte an die Datenbank zum hinzufuegen.
	 * 
	 * @return true, wenn erfolgreich eingefuegt
	 */
	public static boolean addStudent(StudentInterface student) {

		if (!exists(student.getMatrikelnummer())) {
			HibernateUtil.addStudent(student);
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Uebergibt die Werte an die Datenbank zum hinzufuegen.
	 * 
	 * @return true, wenn erfolgreich eingefuegt
	 */
	public static StudentInterface getStudent(int matrikelnummer) {

		if(exists(matrikelnummer)) {
			StudentInterface student = HibernateUtil.getStudent(matrikelnummer);
			return student;
		}
		
		return null;
	}
	
	
	/**
	 *
	 * 
	 * @return Liste aller Studenten.
	 */
	public static List<StudentInterface>  getStudents() {

	
		List<StudentInterface> student = HibernateUtil.getStudents();
		return student;
	}
	public static void deleteStudent(Student student) {
		
		HibernateUtil.deleteStudent(student);
		
	}
	

	/**
	 *
	 * 
	 * @return Liste aller Studenten eines Projektes.
	 */
	public static List<Student>  getStudents(Projekt project) {

	
		List<Student> student = HibernateUtil.getStudents(project);
		return student;
	}
	
	/**
	 * Ueberpruft,ob die Matrikelnummer in der Datenbank vorhanden ist.
	 * @param matrikelnummer
	 * @return
	 */
	public static boolean exists(int matrikelnummer) {
		
		return HibernateUtil.exists(matrikelnummer);
	}
	
	

}
