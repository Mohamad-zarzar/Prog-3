package Factories;

import model.interfaces.*;

import java.time.LocalDate;
import java.time.LocalTime;

import model.implementation.*;

/**
 * Klasse zur zentralen Erzeugung der Modellobjekte
 * @author Christian
 *
 */
public class ModelFactory {

    private static ModelFactory factory;

    /**
     * erzeugt ein Neues Studentenobjekt
     * @param matrikelnummer
     * @param name
     * @param vorname
     * @param note
     * @return Student
     */
    public StudentInterface newStudent(int matrikelnummer,String name, String vorname,double note) {
        return new Student(matrikelnummer, name, vorname, note);
    }
    
    public SprintInterface newSprint (int idSprints,String sprintBeschreibung,String notizen,LocalDate sprintAnfang, LocalDate sprintEnde) {
    	return new Sprint(idSprints, sprintBeschreibung, notizen, sprintAnfang, sprintEnde);
    }

    public UrlInterface newURL(String url, Projekt projekt) {
    	return new URLs(url, projekt);
    }
    
    public ProjektInterface newProjekt(String name, String notizen, byte semester, boolean isActive, Modul modul) {
    	return new Projekt(name, notizen, semester, isActive, modul);
    }
    
    public ThemaInterface newThema (String name, String notiz, int id) {
		return new Thema(name, notiz, id);
	}
    
    public TerminInterface newTermin (long id, String notiz, LocalTime zeit) {
    	return new Termin(id,notiz,zeit);
    }
    
    public ModulInterface newModul ( String name,String semester, String image) {
    	return new Modul(name,semester,image);
    }
    
    public ModulInterface newModul ( String name,String semester) {
    	return new Modul(name,semester);
    }

    public static ModelFactory getInstance() {
        if(factory==null) {
            factory = new ModelFactory();
        }
        return factory;
    }
}
