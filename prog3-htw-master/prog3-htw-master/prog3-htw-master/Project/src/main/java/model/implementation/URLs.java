package model.implementation;

import java.io.Serializable;
import model.interfaces.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * Klasse URLs
 *
 */
/*
 * OLBERTZ Die Objekte dieser Klasse enthalten ja nur eine URL. Somit ist der Name im Plural
 * sehr missverstaendlich. Bei diesem Namen erwarte ich, dass die Klasse eine Liste mit 
 * mehreren URLs kapselt. Und dass Tabellennamen nach Konvention sowieso nur im Plural stehen,
 * habe ich ja schon geschrieben.
 */
@Entity
@Table(name = "URLs")
public class URLs implements Serializable, UrlInterface {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name ="Name")
    private String name;
  
	@Column(name ="URL", unique = true)
    private String url;


    @ManyToOne()
    @JoinColumn(name="Projekt_id", nullable=false)
    private Projekt project;


    /**
     * Konstruktor URL
     * @param url
     * @param projekt_id
     * @param projekt_Projektthema_id
     */

    public URLs(String url,Projekt project){
        setUrl(url);
        setProject(project);
    }




    
  	public URLs() {}





	public String getName() {
  		return name;
  	}

  	public void setName(String name) {
  		this.name = name;
  	}

    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
    public String getUrl(){
        return url;
    }

    public Projekt getProject(){
        return project;
    }



    public void setUrl(String url){
        this.url = url;
    }

    public void setProject(Projekt project){
        this.project = project;
    }


    @Override
    public String toString() {
        return url + " , " +project   ;
    }

}
