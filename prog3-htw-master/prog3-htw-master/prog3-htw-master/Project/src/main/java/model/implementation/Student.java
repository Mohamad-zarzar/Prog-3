package model.implementation;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import model.interfaces.*;

@Entity
@Table(name = "Studenten")
public class Student implements Serializable, StudentInterface {

	private static final long serialVersionUID = 1L;

	private final static String UNGUELTIGE_MATRIKELNR = "Geben Sie eine gueltige Matrikelnummer an!";
	private static final String UNGUELTIGE_NAME = "Die Name darf nicht leer sein !";
	private static final String UNGUELTIGE_VORNAME = "Die Vorname darf nicht leer sein!";;
	private static final String UNGUELTIGE_NOTE = "Die Note soll zwischen 1.0 und 4.0 oder 5.0 sein oder geben Sie 0 an !";

	private final static int MATRIKEL_MIN = 1000000;
	private final static int MATRIKEL_MAX = 9999999;
	/*
	 * OLBERTZ Warum definiert ihr diese Konstanten mehrmals? Sie sind ja schon mal in Projekt definiert. Das macht man
	 * nicht. Dann lieber eine Klasse, die oeffentliche Konstanten enthaelt und auf die die anderen Klassen zugreifen
	 * koennen. Private Konstanten sind dann sinnvoll, wenn niemand anderes sie kennen muss. 
	 * 
	 * Wenn Sie diese Werte kapseln moechten, empfehle ich Ihnen eine andere Architektur: Schreiben Sie sich doch einfach
	 * Validiererklassen, die die ganze Valdierungslogik enthalten und auch die entsprechenden Werte kapseln. Ein Beispiel,
	 * das zeigt wie das bei mir aussehen wuerde, finden Sie in olbertz.NotenValidator.
	 */
	private final static double NOTE_MIN = 1.0;
	private final static double NOTE_MAX = 4.0;
	private final static double DURCHGEFALLEN = 5.0;
	private final static double NICHT_VERGEBEN = 0;

	@Id
	@Column(name = "Matrikelnummer", unique = true)
	private int matrikelnummer;

	@Column(name = "Vorname")
	private String vorname;

	@Column(name = "Name")
	private String name;

	@Column(name = "Note")
	private double note;

	@ManyToOne()
	@JoinColumn(name = "Projekt_id", nullable = false)
	private Projekt projekt;

	/**
	 * Konstruktor Student for ModelFactory
	 * 
	 * @param matrikelnummer
	 * @param name
	 * @param vorname
	 * @param note
	 */
	public Student(int matrikelnummer, String name, String vorname, double note) {
		setMatrikelnummer(matrikelnummer);
		setName(name);
		setVorname(vorname);
		setNote(note);
	}

	/**
	 * Konstruktor Student
	 * 
	 * @param matrikelnummer
	 * @param name
	 * @param vorname
	 */
	public Student(int matrikelnummer, String name, String vorname) {
		setMatrikelnummer(matrikelnummer);
		setName(name);
		setVorname(vorname);
	}

	public Student() {
	}

	public int getMatrikelnummer() {
		return matrikelnummer;
	}

	public String getVorname() {
		return vorname;
	}

	public String getName() {
		return name;
	}

	public double getNote() {
		return note;
	}

	public Projekt getProjekt() {
		return this.projekt;
	}

	public void setMatrikelnummer(int matrikelnummer) {
		if (matrikelnummer < MATRIKEL_MIN || matrikelnummer > MATRIKEL_MAX) {
			throw new IllegalArgumentException(UNGUELTIGE_MATRIKELNR);
		}

		this.matrikelnummer = matrikelnummer;
	}

	public void setName(String name) {
		// OLBERTZ Fuer so was schreibe ich mir immer ganz gerne Werkzeugmethoden. Ein Beispiel sehen Sie in olbertz.StringUtils.
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(UNGUELTIGE_NAME);
		}
		this.name = name;
	}

	public void setVorname(String vorname) {
		if (vorname.trim().isEmpty()) {
			throw new IllegalArgumentException(UNGUELTIGE_VORNAME);
		}
		this.vorname = vorname;
	}

	public void setNote(double note) {
		if ((note < NOTE_MIN || note > NOTE_MAX) && note != DURCHGEFALLEN && note != NICHT_VERGEBEN) {
			throw new IllegalArgumentException(UNGUELTIGE_NOTE);
		}
		this.note = note;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;

	}

	@Override
	public String toString() {
		return matrikelnummer + " , " + name + " , " + vorname;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(matrikelnummer);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(matrikelnummer, other.getMatrikelnummer());
	}

}
