package de.superteam2000.gwt.server.db;


	 /**
	 * Klasse, die die Aufgabe erfüllt, die Objekte einer persistenten Klasse auf die Datenbank abzubilden und dort zu speichern.
	 * Die zu speichernden Objekte werden de-materialisiert und zu gewinnende Objekte aus der Datenbank entsprechend materialisiert. Dies wird
	 * als indirektes Mapping bezeichnet. Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende Methoden zur Suche, zum Speichern, Löschen und 
	 * Modifizieren von Objekten.
	 * @see AuswahlMapper
	 * @see BeschreibungsMapper
	 * @see DBConnection
	 * @see InfoMapper
	 * @see KontaktsperreMapper
	 * @see MerkzettelMapper
	 * @see ProfilMapper
	 * @see SuchprofilMapper

 	 * @author 
 	 */
public class AehnlichkeitsmassMapper {

	/**
	 * Von der Klasse AehnlichkeitsMapper kann nur eine Instanz erzeugt werden. Sie erfüllt die Singleton-Eigenschaft.
	 * Dies geschieht mittels eines private default-Konstruktors und genau einer statischen Variablen vom 
	 * Typ AuswahlMapper, die die einzige Instanz der Klasse darstellt.
	 *
	 * 
	 */
	private static AehnlichkeitsmassMapper aehnlichkeitsmassMapper = null;

	/**
	 * Durch den Modifier "private" geschützter Konstruktor, der verhindert das weiter Instanzen der Klasse erzeugt werden können
	 *  
	 */
	protected AehnlichkeitsmassMapper() {
	}

	/**
	 * Von der Klasse AuswahlMapper kann nur eine Instanz erzeugt werden. Sie erfüllt die Singleton-Eigenschaft.
	 * Dies geschieht mittels eines private default-Konstruktors und genau einer statischen Variablen vom 
	 * Typ AuswahlMapper, die die einzige Instanz der Klasse darstellt.
	 */
	
	
	public static AehnlichkeitsmassMapper aehnlichkeitsmassMapper() {
		if (aehnlichkeitsmassMapper == null) {
			aehnlichkeitsmassMapper = new AehnlichkeitsmassMapper();
		}

		return aehnlichkeitsmassMapper;
	}


}
