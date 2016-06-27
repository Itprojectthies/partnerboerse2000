package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;

import de.superteam2000.gwt.client.ClientsideSettings;

/**
 *
 * Die Klasse DBConnection verwaltet die Verbindung zu einer relationalen Datenbank. Dabei wird auf
 * den jdbc-Treiber zur�ckgegriffen, der eine Verbindung von JAVA zu verschiedenen Datenbank-Typen
 * realisiert.
 *
 * @see AehnlichkeitsMapper
 * @see AuswahlMapper,
 * @see InfoMapper
 * @see KontaktsperreMapper
 * @see MerkzettelMapper
 * @see ProfilMapper
 * @see SuchprofilMapper
 *
 * @author Benjamin Henn
 *
 */
public class DBConnection {
  /**
   * Von der Klasse DBConnection kann nur eine Instanz erzeugt werden. Sie erfüllt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ Connection, die die einzige Instanz der Klasse darstellt.
   *
   */
  private static Connection con = null;

  private static Properties info = new Properties();

  /**
   * Zwei verschiedene URL, da für das Testen eine lokale Datenbank verwendet wurde und für die
   * Ausf�hrung jene von Google Cloud SQl.
   */


  private static String googleUrl =
      "jdbc:google:mysql://partnerboerse2000:partnerboerse2000-db/partnerboerse2000?user=root&password=test";
  private static String localUrl =
      "jdbc:mysql://127.0.0.1:3306/partnerboerse2000?user=root&password=";

  /**
   * Statische Methode, die genau eine Instanz der DBConnection erzeugt und die
   * Verbindungs-Informationen der Datenbank-Verbindung in dieser speichert. Eine weitere Instanz zu
   * erzeugen ist nicht m�glich, da gepr�ft wird, ob w�hrend der Laufzeit schon eine Instanz
   * existiert.
   *
   *
   * @return Das DBConnection-Objekt con.
   */

  public static Connection connection() {
    Logger logger = ClientsideSettings.getLogger();
    // Wenn es bisher keine Conncetion zur DB gab, oder die bestehende abgelaufen ist


    if ((con == null) || !conIsValid(con, 2)) {


      String url = null;
      try {
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

          logger.info("Google Cloud MySQL-DB");
          // Load the class that provides the new
          // "jdbc:google:mysql://" prefix.
          Class.forName("com.mysql.jdbc.GoogleDriver");
          url = googleUrl;
        } else {
          // Local MySQL instance to use during development.
          Class.forName("com.mysql.jdbc.Driver");
          url = localUrl;
          logger.info("Lokale MySQL-DB");
        }
        /*
         * Dann erst kann uns der DriverManager eine Verbindung mit den oben in der Variable url
         * angegebenen Verbindungsinformationen aufbauen.
         *
         * Diese Verbindung wird dann in der statischen Variable con abgespeichert und fortan
         * verwendet.
         */

        info.put("autoReconnect", "true");
        info.put("connectTimeout", "360000");
        con = DriverManager.getConnection(url, info);

        logger.info("MySQL-Verbindung erfolgreich");
      } catch (Exception e) {
        con = null;
        e.printStackTrace();
        logger.info("MySQL-Verbindung fehlgeschlagen");
        ClientsideSettings.getLogger()
            .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
      }
    }

    // Zurückgegeben der Verbindung
    return con;
  }


  protected static boolean conIsValid(Connection con, int timeout) {

    try {
      if (!con.isValid(timeout)) {
        con.close();
        return false;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error. Cannot check connection status!", e);
    }

    return true;
  }


}
