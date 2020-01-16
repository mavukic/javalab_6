
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.JOptionPane;



public class UserConfig {
	private static final String propertiesFile = "C:\\Users\\mvuki\\eclipse-workspace\\Lab5\\chat.properties";
	private static final String hostPropertieName = "host";
	private static final String portPropertieName = "port";
	private static final String userPropertieName = "user";

	private static String host;
	private static int port;
	private static String korisnik;

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		UserConfig.host = host;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		UserConfig.port = port;
	}

	public static String getKorisnik() {
		return korisnik;
	}

	public static void setKorisnik(String korisnik) {
		UserConfig.korisnik = korisnik;
	}

	static {
		loadParams();
	}

	// spremanje i citanje podataka u/iz datoteku

	public static void loadParams() {
		Properties props = new Properties();
		InputStream is = null;
		// Najprije pokusavamo ucitati iz lokalnog direktorija

		try {
			File f = new File(propertiesFile);
			is = new FileInputStream(f);

		} catch (Exception e) {
			e.printStackTrace();
			is = null;
		}
		try {
			// pokusavaju se ucitati parametri
			props.load(is);
		} catch (Exception e) {
		}

		// prvi parametar: naziv datoteke
		// drugi parametar: ako nije nadenaa vrijednost onda se vraca drugi parametar
		host = props.getProperty(hostPropertieName, "192.168.0.1");
		port = new Integer(props.getProperty(portPropertieName, "8080"));
		korisnik = props.getProperty(userPropertieName, "anonymous");

	}

	public static void saveParamChange() {
		try {
			Properties props = new Properties();
			props.setProperty(hostPropertieName, host);
			props.setProperty(portPropertieName, "" + port);
			props.setProperty(userPropertieName, korisnik);
			File f = new File(propertiesFile);
			OutputStream out = new FileOutputStream(f);
			props.store(out, "Komentar");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
