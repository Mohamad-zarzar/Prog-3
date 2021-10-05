package model.implementation;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import model.interfaces.*;

@Entity
@Table(name = "Projektthema")
public class Thema implements ThemaInterface {

	private final static String NAME_LEER = "Das Thema braucht einen Namen";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Beschreibung")
	private String beschreibung;

	@Column(name = "Notiz")
	private String notiz;

	@OneToMany(mappedBy = "thema")
	private Set<Projekt> projectsList = new HashSet<>();

	@ManyToMany(mappedBy = "themen", fetch = FetchType.LAZY)
	private Set<Modul> modules = new HashSet<>();

	/*
	 * OLBERTZ Was ist denn das fuer ein Parametername: beschreString? Warum nennen
	 * Sie das Ding nicht einfach beschreibung, wie es sich gehort? 
	 */
	public Thema(String name, String beschreString) {
		setName(name);
		setBeschreibung(beschreString);

	}

	public Thema(String name, String notiz, int id) {
		setName(name);
		setNotiz(notiz);
		setId(id);
	}
	public Thema() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException(NAME_LEER);
		}
		this.name = name;
	}
	
	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	
	public Set<Modul> getModules() {
		return modules;
	}
	public void setModules(Set<Modul> modules) {
		this.modules = modules;
	}



	public Set<Projekt> getProjectsList() {
		return projectsList;
	}

	public void setProjectsList(Set<Projekt> projectsList) {
		this.projectsList = projectsList;
	}

	public static String getNameLeer() {
		return NAME_LEER;
	}

	

	public void addModule(Modul module) {
		this.modules.add(module);
		module.addThema(this);
	}

	public void removeModule(Modul module) {
		this.modules.remove(module);
		module.removeThema(this);
	}


	public void addProjekt(Projekt project) {
		projectsList.add(project);
		project.setThema(this);
	}

	public void removeProjekt(Projekt project) {
		projectsList.remove(project);
		project.setThema(null);
	}

	public String toString() {
		return id + " : " + name;
	}
//	
//	 @Override
//	    public int hashCode() {
//	        return Objects.hashCode(id);
//	    }
//	 
//	 @Override
//	    public boolean equals(Object obj) {
//	        if (this == obj)
//	            return true;
//	        if (obj == null)
//	            return false;
//	        if (getClass() != obj.getClass())
//	            return false;
//	        Thema other = (Thema) obj;
//	        return Objects.equals(id, other.getId());
//	    }

}
