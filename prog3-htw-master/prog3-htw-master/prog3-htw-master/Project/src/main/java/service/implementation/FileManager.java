package service.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Klasse, um Dateien auf dem ausführenden PC im Dateisystem zu bewegen
 * @author Christian Hartmann
 *
 */
public class FileManager {
	
	private static final String MISSING_FILE = "Datei existiert nicht";
	private static final String	CAN_NOT_READ = "Die Datei kann nicht gelesen werden";
	
	/**
	 * Erstellt eine Kopie einer Datei an der gewünschten Stelle
	 * Achtung!: Wenn schon eine Datei mit identischem Names existiert, wird dieser gelöscht
	 * @param filePath Pfad zur Originaldatei
	 * @param destination Ordner, in dem die Kopie gespeichert werden soll
	 * @param name Name, den die Kopie haben soll (Ohne Dateiendung)
	 * @return Pfad zur Kopie
	 * @throws IOException
	 */
	public static String copy (String filePath, String destination, String name){
	File f = new File(filePath);
	if (!f.exists()) {
		throw new IllegalArgumentException(MISSING_FILE);
	}
	if (!f.canRead()) {
		throw new IllegalArgumentException(CAN_NOT_READ);
	}
		String weg = destination + name + getFileFormat(filePath);
		File inF = new File(filePath);
        File outF = new File(weg);
        if (outF.exists()){
        	outF.delete();
        }
        try {
        outF.createNewFile();
	}
        catch (IOException e) {//Kann nicht ausgelöst werden
        	
        }
        copyFile(inF, outF);
    return weg;
	}
	
	/**
	 * Erstellt eine Kopie einer Datei an der gewünschten Stelle
	 * @param filePath Pfad zur Originaldatei
	 * @param destination Ordner, in dem die Kopie gespeichert werden soll
	 * @return Pfad zur Kopie
	 * @throws IOException
	 */
	public static String copy (String filePath, String destination){
		String [] parts = filePath.split("/");
		String name = parts[parts.length-1];
		name = name.split("\\.")[0];
		return copy(filePath, destination, name);
	}
	
	/**
	 * Schreibt die Daten der Originaldatei in die Kopie
	 * @param in Originaldatei
	 * @param out Kopie
	 */
	/*
	 * OLBERTZ Gute Methode. Aber ich wuerde hier sogar noch einen Schritt weitergehen und diese Methode
	 * in eine Klasse FileUtils stecken. Das ist dann der Werkzeugkasten, den ich in der Vorlesung erwaehnt habe
	 * und den man in jedes andere Programm mitnehemn kann. Eine Methode, die Dateien kopiert, kann man 
	 * immer mal brauchen. 
	 */
	private static void copyFile(File in, File out){
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        /*
         * OLBERTZ Verwenden Sie am besten try-with-Resources wie ich es im Kapitel Exceptions gezeigt
         * habe. Gerade bei der Dateiverarbeitung ist das Gold wert. 
         */
        try {
            inChannel = new FileInputStream(in).getChannel();
            outChannel = new FileOutputStream(out).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
        	// OLBERTZ Wenn wir beim Thema vernuenftige Ausnamebehandlung sind ....
        	System.out.println("meeep");
        } 
        finally {
            try {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            } catch (IOException e) {
            	
            }
            }
        }
	
	/**
	 * Bestimmt die Dateiendung
	 * @param path Dateipfad
	 * @return Dateiendung (mit Punkt)
	 */
	private static String getFileFormat (String path) {
		String [] parts = path.split("\\.");
		return "." + parts[parts.length -1];
	}
}
