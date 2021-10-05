package daotest;

import Factories.ModelFactory;
import facades.ServiceFacade;
import model.interfaces.StudentInterface;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HibernateTester {

	private static final String UNGUELTIGE_ANGABE = "Geben Sie eine gueltige Option an!";
	private static final int STUDENTEN = 1;
	private static final int ENDE = 0;

	private Scanner input;
	// -------------------Konstanten vom Student--------------//
	private static final int FUNKTIONEN_VON_STUDENT = 100;
	private static final int ADD_STUDENT = 1;
	private static final int GET_STUDENT = 2;
	private static final int GET_STUDENTS = 3;

	private boolean breakRunFunctions;
	private int startFunktionen = -1;

	// -------------------Konstuktur-------------------------//
	public HibernateTester() {
		input = new Scanner(System.in);
	}

	// -------------------Main------------------------------//
	public static void main(String[] args) {

		// Warnungen unterdruecken
		Logger.getRootLogger().setLevel(Level.OFF);

		new HibernateTester().start();
	}


	//--------------------Start-----------------------------//
	public void start() {

		while (startFunktionen != ENDE && !breakRunFunctions) {
			try {
				if (startFunktionen == FUNKTIONEN_VON_STUDENT) {
					startStudentInnereFunktionen();
				} else {
					startFunktionen = einleseFunktion();
					waehleModeTypAus(startFunktionen);
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				this.start();
			} catch (InputMismatchException e) {
				System.out.println(UNGUELTIGE_ANGABE);
				input.nextLine();
				this.start();
			}
		}

	}

	//-------------------StudentRelevant-------------------//

	/**
	 * Fuegt einen Student in der Datenbank hinzu.
	 */
	private void addStudent() {

		StudentInterface student = studentEinlesen();
		ServiceFacade.addStudent(student);
	}

	private StudentInterface getStudent() {

		int matrikelnummer = matrikelNrEinlesen();
		return ServiceFacade.getStudent(matrikelnummer) ;
	}

	private List<StudentInterface> getStudents(){
		return ServiceFacade.getStudents();
	}

	/**
	 *
	 * @return die Nummer der ausgewaehlten Methode.
	 */
	private int studentEinleseFunktion() {
		int funktion;
		System.out.println("Waehlen Sie eine Funktion aus !");

		System.out.println(
				ADD_STUDENT +  ":Add Student\n" +
						GET_STUDENT +  ":Get Student\n" +
						GET_STUDENTS + ":Get all Students\n"
		);
		funktion = input.nextInt();
		input.nextLine();
		return funktion;
	}

	/**
	 * Liest die MatrikNr,name,vorname und Note von einem Student ein und gibt ein
	 * Student mit diesen Werten zurueck.
	 *
	 * @return StudentInterface
	 */
	private StudentInterface studentEinlesen() {
		int matrikelnummer;
		String name;
		String vorname;
		double note;

		System.out.print("Matrikelnummer: ");
		matrikelnummer = input.nextInt();

		System.out.print("Name: ");
		input.nextLine();
		vorname = input.nextLine();

		System.out.print("Vorname: ");
		name = input.nextLine();

		System.out.print("Note: ");
		note = input.nextDouble();
		input.nextLine();

		ModelFactory factory = ModelFactory.getInstance();

		return factory.newStudent(matrikelnummer, name, vorname, note);
	}
	//----------------------------Ende_StudentRelevant--------------------------------//

	/**
	 * Zeigt alle vorhandenen ModelTypen an,um einen davon auszuwaehlen.
	 *
	 * @return
	 */
	private int einleseFunktion() {
		int funktion;
		System.out.print(
				STUDENTEN + ": Studenten \n" +
						ENDE 	  +	": beenden ->\n "
		);

		funktion = input.nextInt();
		input.nextLine();
		return funktion;
	}


	private int matrikelNrEinlesen() {
		int matrikelnummer;


		System.out.print("Matrikelnummer: ");
		matrikelnummer = input.nextInt();
		input.nextLine();
		return matrikelnummer;
	}

	/**
	 * Zeigt die Funktionen eines ModelTypes an ( z.b Funktionen von : Student,Projekt ..)
	 *
	 * @param funktion
	 */

	private void waehleModeTypAus(int funktion) {

		switch (funktion) {

			case STUDENTEN:
				startStudentInnereFunktionen();
				break;
			case ENDE:
				breakRunFunctions = true;
				System.out.println("Das Programm ist beendet..");
				break;
			default:
				System.out.println("Falsche Eingabe!");
				break;
		}
	}

	/**
	 * Zeigt alle Funktionen von Student.
	 */
	private void startStudentInnereFunktionen() {

		int funktion = studentEinleseFunktion();

		switch (funktion) {
			case ADD_STUDENT:
				addStudent();
				break;

			case GET_STUDENT:
				StudentInterface student = getStudent();
				if(student != null)
					System.out.println(student);
				break;

			case GET_STUDENTS:
				List<StudentInterface> list =  getStudents();
				list.forEach(System.out::println);
				break;

			default:
				startFunktionen = FUNKTIONEN_VON_STUDENT;
				throw new IllegalArgumentException("Falsche Eingabe!");

		}

	}



}
