package model.implementation;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.CascadeType;

import model.interfaces.*;

/**
 * Klasse Projekt @ Rajoub am 12.02
 */

@Entity
@Table(name = "Projekt")
public class Projekt implements Serializable, ProjektInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * OLBERTZ Strings bitte anstaendig internationalisieren.
	 */
	private final static String UNGUELTIGER_NAME = "Der Projektname darf nicht leer sein";
	private final static String UNGUELTIGE_NOTIZEN = "Die Notizen darf nicht leer sein";
	private final static String UNGUELTIGE_SEMESTER_ANZAHL = "Die Semesteranzahl befindet sich zwischen 1 und 7";
	private final static String VORHANDENE_ID = "Die ID ist schon vorhanden";
	private final static String ID_NICHT_POSITIV = "Die ID muss positiv sein";
	private static final String UNGUELTIGE_NOTE = "Die Note soll zwischen 1.0 und 4.0 oder 5.0 oder 0 sein !";
	
	private final static double NOTE_MIN = 1.0;
	private final static double NOTE_MAX = 4.0;
	private final static double DURCHGEFALLEN = 5.0;
	private final static double NICHT_VERGEBEN = 0.0;
	private final static byte SEMESTER_MAX = 7;
	private final static byte SEMESTER_MIN = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Notizen")
	private String notizen;

	@Column(name = "Semester")
	private byte semester;

	@Column(name = "MainUrl")
	private String mainUrl;

	@Column(name = "isActive")
	private boolean isActive;
	
	@Column(name = "Note")
	private Double note ;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Projektthema_id", nullable = true, updatable = true)
	private Thema thema;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Projektmodul_id", nullable = false, updatable = true)
	private Modul modul;

	@OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)

	private Set<Student> students = new HashSet<>();

	@OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
	private Set<Sprint> sprints = new HashSet<>();

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Set<URLs> urls = new HashSet<>();

	/**
	 * Konstruktor Projekt
	 * 
	 * @param name
	 * @param notizen
	 * @param semester
	 * @param isActive
	 * 
	 * @author Rajoub
	 */
	public Projekt( String name, String notizen, byte semester, boolean isActive, Modul modul) {
		setName(name);
		setNotizen(notizen);
		setSemester(semester);
		setIsActive(isActive);
		setId(id);
		setModul(modul);
	}

	public Projekt() {}



	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNotizen() {
		return notizen;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public byte getSemester() {
		return semester;
	}

	public boolean isActive() {
		return isActive;
	}

	public Modul getModul() {
		return this.modul;
	}

	public Thema getThema() {
		return thema;
	}

	public Set<URLs> getUrls() {
		return urls;
	}

	public Set<Student> getStudents() {
		return students;
	}
	
	public Double getNote() {
		return note;
	}
	

	public void setNote(Double note) {
		if ((note < NOTE_MIN || note > NOTE_MAX)&& note != DURCHGEFALLEN && note != NICHT_VERGEBEN) {
			throw new IllegalArgumentException(UNGUELTIGE_NOTE);
		}
		this.note = note;
	}


	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(UNGUELTIGER_NAME);
		}
		this.name = name;
	}

	public void setNotizen(String notizen) {
	
		this.notizen = notizen;
	}

	public void setSemester(byte semester) {
		if (semester > SEMESTER_MAX || semester < SEMESTER_MIN) {
			throw new IllegalArgumentException(UNGUELTIGE_SEMESTER_ANZAHL);
		}
		this.semester = semester;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public void setModul(Modul modul) {
		this.modul = modul;

	}

	public void setThema(Thema thema) {
		this.thema = thema;
	}

	public void setIsActive(boolean isActive) {
		if (isActive) {
			this.isActive = true;
		} else {
			this.isActive = false;
		}
	}

	public void setUrls(Set<URLs> urls) {
		this.urls = urls;
	}

	public void setModul(ModulInterface modul) {
		this.modul = (Modul) modul;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public void addUrl(URLs url) {
		this.urls.add(url);
		url.setProject(this);
	}

	
	
	
	
	
	public void removeUrl(URLs url) {
		this.urls.remove(url);
		url.setProject(null);
	}

	public void addSprint(Sprint sprint) {
		this.sprints.add(sprint);
		sprint.setProjekt(this);
	}

	public void removeSprint(Sprint sprint) {
		this.sprints.remove(sprint);
		sprint.setProjekt(null);
	}

	public void addStudent(Student student) {
		students.add(student);
		student.setProjekt(this);
	}

	public void removeStudent(Student student) {
		students.remove(student);
		student.setProjekt(null);
	}

	public void removeThema(Thema thema) {
		thema.removeProjekt(this);
		this.setThema(null);
	}

	public void removeModule(Modul module) {
		module.removeProjekt(this);
		this.setModul(null);
	}

	public String toString() {
		return id + " , " + name + " , " + name + " , " + semester + " , " + notizen + " , ";
	}

}
