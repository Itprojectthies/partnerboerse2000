package de.superteam2000.gwt.server.db;


/**
 * Mapper-Klasse, die <code>Account</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @see CustomerMapper, TransactionMapper
 * @author Thies
 */
public class AehnlichkeitsmassMapper {

	/**
	 * Die Klasse AehnlichkeitsmassMapper wird nur einmal instantiiert. Man
	 * spricht hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see AehnlichkeitsmassMapper()
	 */
	private static AehnlichkeitsmassMapper aehnlichkeitsmassMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected AehnlichkeitsmassMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>AehnlichkeitsmassMapper.AehnlichkeitsmassMapper()</code>. Sie
	 * stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur
	 * eine einzige Instanz von <code>AehnlichkeitsmassMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> AehnlichkeitsmassMapper sollte nicht mittels
	 * <code>new</code> instantiiert werden, sondern stets durch Aufruf dieser
	 * statischen Methode.
	 * 
	 * @return DAS <code>AehnlichkeitsmassMapper</code>-Objekt.
	 * @see AehnlichkeitsmassMapper
	 */
	public static AehnlichkeitsmassMapper aehnlichkeitsmassMapper() {
		if (aehnlichkeitsmassMapper == null) {
			aehnlichkeitsmassMapper = new AehnlichkeitsmassMapper();
		}

		return aehnlichkeitsmassMapper;
	}


}
