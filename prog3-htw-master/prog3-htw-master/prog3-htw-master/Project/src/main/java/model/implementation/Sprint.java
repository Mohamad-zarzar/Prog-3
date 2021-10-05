package model.implementation;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

import model.interfaces.*;

/**
 * Klasse Sprint
 */

@Entity
/*
 * OLBERTZ Nach Konvention werden Tabellen im Singular benannt und nicht im Plural, also in diesem
 * Fall wuerde die Tabelle dann Sprint heissen.
 */
@Table(name = "Sprints")
public class Sprint implements Serializable, SprintInterface {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id", unique = true)
	private long id;

	@Column(name ="SprintNumber",nullable = true)
	private int sprintNumber;
	
	
	@Column(name = "SprintBeschreibung")
	private String sprintBeschreibung;
	
	@Column(name = "Notizen")
	private String notizen;


	@Column(name = "SprintAnfang")
	private LocalDate sprintAnfang;

	@Column(name = "SprintEnde")
	private LocalDate sprintEnde;

	@ManyToOne()
	@JoinColumn(name = "Projekt_id", nullable = false)
	private Projekt projekt;

	/**
	 * Konstruktor Sprint
	 * 
	 * @param idSprints
	 * @param sprintBeschreibung
	 * @param notizen
	 * @param sprintAnfang
	 * @param sprintEnde
	 */

	public Sprint(int id, String sprintBeschreibung, String notizen, LocalDate sprintAnfang,
			LocalDate sprintEnde) {
		setId(id);
		setSprintBeschreibung(sprintBeschreibung);
		setNotizen(notizen);
		setSprintAnfang(sprintAnfang);
		setSprintEnde(sprintEnde);
	}

	public Sprint() {	}


	public long getId() {
		return id;
	}

	public String getSprintBeschreibung() {
		return sprintBeschreibung;
	}

	public String getNotizen() {
		return notizen;
	}

	public LocalDate getSprintAnfang() {
		return sprintAnfang;
	}

	public LocalDate getSprintEnde() {
		return sprintEnde;
	}

	@Override
	public Projekt getProjekt() {
		return this.projekt;
	}
	
	public int getSprintNumber() {
		return sprintNumber;
	}

	


	public void setId(long id) {
		this.id = id;
	}
	
	public void setSprintNumber(int sprintNumber) {
		this.sprintNumber = sprintNumber;
	}

	public void setSprintBeschreibung(String sprintBeschreibung) {
		this.sprintBeschreibung = sprintBeschreibung;
	}

	public void setNotizen(String notizen) {
		this.notizen = notizen;
	}

	public void setSprintAnfang(LocalDate sprintAnfang) {
		this.sprintAnfang = sprintAnfang;
	}

	public void setSprintEnde(LocalDate sprintEnde) {
		this.sprintEnde = sprintEnde;
	}

	@Override
	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}

	@Override
	public String toString() {
		return id + " , " + sprintBeschreibung + " , " + notizen + " , " + sprintAnfang + " , " + sprintEnde;
	}


}
