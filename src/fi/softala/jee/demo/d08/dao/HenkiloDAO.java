package fi.softala.jee.demo.d08.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fi.softala.jee.demo.d08.bean.Ostos;

public class HenkiloDAO {

	/**
	 * Konstruktori lataa tietokantayhteyden ajurin
	 */
	public HenkiloDAO() throws DAOPoikkeus {
		try {
			Class.forName(
					DBConnectionProperties.getInstance().getProperty("driver"))
					.newInstance();
		} catch (Exception e) {
			throw new DAOPoikkeus("Tietokannan ajuria ei kyetty lataamaan.", e);
		}
	}

	/**
	 * Avaa tietokantayhteyden
	 * 
	 * @return avatun tietokantayhteyden
	 * @throws Exception
	 *             Mikäli yhteyden avaaminen ei onnistu
	 */
	private Connection avaaYhteys() throws DAOPoikkeus {

		try {
			return DriverManager.getConnection(DBConnectionProperties
					.getInstance().getProperty("url"), DBConnectionProperties
					.getInstance().getProperty("username"),
					DBConnectionProperties.getInstance()
							.getProperty("password"));
		} catch (Exception e) {
			throw new DAOPoikkeus("Tietokantayhteyden avaaminen epäonnistui", e);
		}
	}

	/**
	 * Sulkee tietokantayhteyden
	 * 
	 * @param yhteys
	 *            Suljettava yhteys
	 */
	private void suljeYhteys(Connection yhteys) throws DAOPoikkeus {
		try {
			if (yhteys != null && !yhteys.isClosed())
				yhteys.close();
		} catch (Exception e) {
			throw new DAOPoikkeus(
					"Tietokantayhteys ei jostain syystä suostu menemään kiinni.",
					e);
		}
	}

	/**
	 * Hakee kaikki henkilöt kannasta
	 * 
	 * @return listallinen henkilöitä
	 * @throws DAOPoikkeus
	 */

	public Ostos hae(String id) throws DAOPoikkeus {

		Connection yhteys = avaaYhteys();

		Ostos ostos = new Ostos();

		try {

			String sql = "select id, ostaja, pvm, summa from ostokset where id="
					+ id;
			Statement haku = yhteys.createStatement();
			ResultSet tulokset = haku.executeQuery(sql);

			while (tulokset.next()) {
				int haettuId = tulokset.getInt("id");
				String ostaja = tulokset.getString("ostaja");
				String pvm = tulokset.getString("pvm");
				String summa = tulokset.getString("summa");

				ostos = new Ostos(haettuId, ostaja, pvm, summa);

			}

		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Tietokantahaku aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

		System.out.println("HAETTIIN TIETOKANNASTA OSTOKSET: "
				+ ostos.toString());

		return ostos;

	}

	public List<Ostos> haeKaikki() throws DAOPoikkeus {

		ArrayList<Ostos> ostokset = new ArrayList<Ostos>();

		// avataan yhteys
		Connection yhteys = avaaYhteys();

		try {

			// suoritetaan haku
			String sql = "select id, ostaja, pvm, summa from ostokset";
			Statement haku = yhteys.createStatement();
			ResultSet tulokset = haku.executeQuery(sql);

			// käydään hakutulokset läpi
			while (tulokset.next()) {
				int id = tulokset.getInt("id");
				String ostaja = tulokset.getString("ostaja");
				String pvm = tulokset.getString("pvm");
				String summa = tulokset.getString("summa");

				// lisätään henkilö listaan
				Ostos ostos = new Ostos(id, ostaja, pvm, summa);
				ostokset.add(ostos);
			}

		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Tietokantahaku aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

		System.out.println("HAETTIIN TIETOKANNASTA OSTOKSET: "
				+ ostokset.toString());

		return ostokset;
	}

	/**
	 * Lisää henkilön tietokantaan
	 * 
	 * @param h
	 *            Lisättävän henkilön tiedot
	 */
	public void lisaa(Ostos ostos) throws DAOPoikkeus {

		String ostaja = ostos.getOstaja();

		String maksamatta = "";
		String henkilot = "";

		if (ostaja.equals("Juha")) {

			maksamatta = "Joni Sami Niko";
			henkilot = "Joni Sami Niko";
		}

		else if (ostaja.equals("Joni")) {

			maksamatta = "Juha Sami Niko";
			henkilot = "Juha Sami Niko";
		}

		else if (ostaja.equals("Sami")) {

			maksamatta = "Joni Juha Niko";
			henkilot = "Joni Juha Niko";
		}

		else if (ostaja.equals("Niko")) {

			maksamatta = "Joni Sami Juha";
			henkilot = "Joni Sami Juha";
		}

		// avataan yhteys
		Connection yhteys = avaaYhteys();

		try {

			// suoritetaan haku

			// alustetaan sql-lause
			String sql = "insert into ostokset(ostaja, pvm, summa, maksanut, maksamatta, henkilot) values(?,?,?,?,?,?)";
			PreparedStatement lause = yhteys.prepareStatement(sql);

			// täytetään puuttuvat tiedot
			lause.setString(1, ostos.getOstaja());
			lause.setString(2, ostos.getPvm());
			lause.setString(3, ostos.getSumma());
			lause.setString(4, "Ei kukaan.");
			lause.setString(5, maksamatta);
			lause.setString(6, henkilot);

			// suoritetaan lause
			lause.executeUpdate();
			System.out.println("LISÄTTIIN OSTOS TIETOKANTAAN: " + ostos);
		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Ostoksen lisäämisyritys aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

	}

	public void poista(String id) throws DAOPoikkeus {

		// avataan yhteys
		Connection yhteys = avaaYhteys();

		try {

			// suoritetaan haku

			// alustetaan sql-lause
			String sql = "delete from ostokset where id=?";
			PreparedStatement lause = yhteys.prepareStatement(sql);

			// täytetään puuttuvat tiedot
			lause.setString(1, id);

			// suoritetaan lause
			lause.executeUpdate();
			System.out.println("POISTETTIIN OSTOS TIETOKANNASTA, ID: " + id);
		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Ostoksen poistoyritys aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

	}

	public String haeMaksanut(String id) throws DAOPoikkeus {

		Connection yhteys = avaaYhteys();

		String maksanut = "";

		try {

			String sql = "select maksanut from ostokset where id=" + id;
			Statement haku = yhteys.createStatement();
			ResultSet tulokset = haku.executeQuery(sql);

			while (tulokset.next()) {

				maksanut = tulokset.getString("maksanut");
			}

		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Tietokantahaku aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

		return maksanut;
	}

	public String haeMaksamatta(String id) throws DAOPoikkeus {

		Connection yhteys = avaaYhteys();

		String maksamatta = "";

		try {

			String sql = "select maksamatta from ostokset where id=" + id;
			Statement haku = yhteys.createStatement();
			ResultSet tulokset = haku.executeQuery(sql);

			while (tulokset.next()) {

				maksamatta = tulokset.getString("maksamatta");
			}

		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Tietokantahaku aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

		return maksamatta;
	}

	public String haeHenkilot(String id) throws DAOPoikkeus {

		Connection yhteys = avaaYhteys();

		String henkilot = "";

		try {

			String sql = "select henkilot from ostokset where id=" + id;
			Statement haku = yhteys.createStatement();
			ResultSet tulokset = haku.executeQuery(sql);

			while (tulokset.next()) {

				henkilot = tulokset.getString("henkilot");
			}

		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Tietokantahaku aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

		return henkilot;
	}

	public void paivitaMaksanut(String id, String maksanutPrint) throws DAOPoikkeus {

		Connection yhteys = avaaYhteys();

		try {

			// suoritetaan haku

			// alustetaan sql-lause
			String sql = "update ostokset set maksanut=? where id=" + id;
			PreparedStatement lause = yhteys.prepareStatement(sql);

			// täytetään puuttuvat tiedot
			lause.setString(1, maksanutPrint);
			
			// suoritetaan lause
			lause.executeUpdate();
			System.out.println("LISÄTTIIN OSTOS TIETOKANTAAN: " + id);
		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			throw new DAOPoikkeus("Ostoksen lisäämisyritys aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}

	}

}
