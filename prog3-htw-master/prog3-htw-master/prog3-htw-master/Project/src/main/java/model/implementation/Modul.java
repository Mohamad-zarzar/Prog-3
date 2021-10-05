package model.implementation;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import model.interfaces.*;

@Entity
@Table(name = "Modul")
public class Modul implements ModulInterface {
	/*
	 * OLBERTZ Es wird empfohlen, die Annotationen immer an die Getter zu schreiben, nicht an die Attribute.
	 * Denn wenn die Annotationen an den Attributen stehen, darf der PersistenceProvider mittels Reflections
	 * unter Umgehung der Setter darauf zugreifen. Das ist u.U. nicht so schoen. Daher gilt es gemeinhin als
	 * guter Stil, diese Annotationen nur an die Getter zu schreiben. 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private long id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Semester")
	private String semester;
	@Column(name = "Notiz")
	private String notiz;
	@Column(name = "Image")
	private String image;
	/*
	 * OLBERTZ Generell wird empfohlen, solche Initialisierungen im Konstruktor vorzunehmen
	 * und nicht in der Attributliste. Und Sie haben ja auch einen wunderschoenen Konstruktor.
	 * Warum nicht diesen auch dafuer nutzen? 
	 */
	@OneToMany(mappedBy = "modul", cascade = CascadeType.ALL)
	private List<Projekt> projects = new LinkedList<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "modul_thema", joinColumns = { @JoinColumn(name = "modul_id") }, inverseJoinColumns = {
			@JoinColumn(name = "projektthema_id") })
	private List<Thema> themen = new LinkedList<>();

	public Modul(String name, String semester, String image) {

		setName(name);
		setSemester(semester);
		setImage(image);
	}

	public Modul(String name, String semester) {
		this(name, semester, "");
	}

	public Modul() {
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<Projekt> getProjects() {
		return projects;
	}

	public String getNotiz() {
		return notiz;
	}

	public String getSemester() {
		return semester;
	}

	public String getImage() {
		return this.image;
	}

	public List<Thema> getThemen() {
		return themen;
	}

	public void setProjects(List<Projekt> projects) {
		this.projects = projects;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setImage(String imagePath) {
		this.image = imagePath;
	}

	public void setThemen(List<Thema> themen) {
		this.themen = themen;
	}

	public void addProjekt(Projekt projekt) {
		this.projects.add(projekt);
		projekt.setModul(this);
	}

	public void removeProjekt(Projekt projekt) {
		this.projects.remove(projekt);
		projekt.setModul(null);
	}

	public void addThema(Thema thema) {
		this.themen.add(thema);
		thema.getModules().add(this);
	}

	public void removeThema(Thema thema) {
		this.themen.remove(thema);
		thema.getModules().remove(this);
	}

	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Modul other = (Modul) obj;
		return Objects.equals(id, other.getId());
	}

}
