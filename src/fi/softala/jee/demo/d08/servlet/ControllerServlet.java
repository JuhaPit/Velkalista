package fi.softala.jee.demo.d08.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.softala.jee.demo.d08.bean.Ostos;
import fi.softala.jee.demo.d08.dao.DAOPoikkeus;
import fi.softala.jee.demo.d08.dao.HenkiloDAO;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id = "";
	String ostaja = "";
	String pvm = "";
	String summa = "";
	double osuus = 0;
	String maksanutPrint = "";
	String maksamattaPrint = "";
	String ekaNimi = "";
	String tokaNimi = "";
	String kolmasNimi = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		id = request.getParameter("param");
		Ostos ostos;
		String henkilot = "";

		try {
			HenkiloDAO hDao = new HenkiloDAO();
			ostos = hDao.hae(id);
			maksanutPrint = hDao.haeMaksanut(id);
			maksamattaPrint = hDao.haeMaksamatta(id);
			henkilot = hDao.haeHenkilot(id);
		} catch (DAOPoikkeus e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}

		ostaja = ostos.getOstaja();
		pvm = ostos.getPvm();
		summa = ostos.getSumma();
		
		ekaNimi = henkilot.substring(0,4);
		tokaNimi = henkilot.substring(5,9);
		kolmasNimi = henkilot.substring(10,14);

		double summaLukuna = Double.parseDouble(summa);
		osuus = summaLukuna / 4;

		PrintWriter wout = response.getWriter();
		wout.println("<html><body>");
		wout.println("<h1>" + ostaja + "n ostos" + " " + pvm + "</h1>");
		wout.println("<p>Ostoksen summa: " + summa + "</p>");
		wout.println("<p>Osuus per henkilö: " + osuus + "</p>");
		wout.println("<form action=\"ControllerServlet\" method=\"post\">");
		wout.println("<p>Maksanut: " + maksanutPrint + "</p>");
		wout.println("<p>KUITTAA MAKSANEEKSI</p>");
		wout.println("<input type=\"radio\" name=\"nimi\" value=" + ekaNimi + ">" + ekaNimi + "<br>");
		wout.println("<input type=\"radio\" name=\"nimi\" value=" + tokaNimi +">" + tokaNimi + "<br>");
		wout.println("<input type=\"radio\" name=\"nimi\" value=" + kolmasNimi +">" + kolmasNimi + "<br>");
		wout.println("<br>");
		wout.println("<input type=\"submit\" name=\"action\" value=\"Kuittaa\"> ");
		wout.println("</form>");
		wout.println("<a href=\"ostokset\">Palaa etusivulle</a>");
		wout.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String nimi = request.getParameter("nimi");
		
		if (maksanutPrint.equals("Ei kukaan.")){
			
			maksanutPrint = nimi + " ";
		}
		
		else {
			
			maksanutPrint = maksanutPrint + nimi + " ";
		}
		
		try {
			HenkiloDAO hDao = new HenkiloDAO();
			hDao.paivitaMaksanut(id, maksanutPrint);
		} catch (DAOPoikkeus e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}
		
		
		
		
		PrintWriter wout = response.getWriter();
		wout.println("<html><body>");
		wout.println("<h1>" + ostaja + "n ostos" + " " + pvm + "</h1>");
		wout.println("<p>Ostoksen summa: " + summa + "</p>");
		wout.println("<p>Osuus per henkilö: " + osuus + "</p>");
		wout.println("<form action=\"ControllerServlet\" method=\"post\">");
		wout.println("<p>Maksanut: " + maksanutPrint + "</p>");
		
		if (maksanutPrint.length() == 15){
			
			wout.println("<p>Kaikki ovat maksaneet velkansa. Poista ostotapahtuma listalta ja siirry etusivulle <a href=\"PoistoServlet?param=" + id + "\">tästä</a></p>");
		}
		
		
		wout.println("<p>KUITTAA MAKSANEEKSI</p>");
		wout.println("<input type=\"radio\" name=\"nimi\" value=" + ekaNimi + ">" + ekaNimi + "<br>");
		wout.println("<input type=\"radio\" name=\"nimi\" value=" + tokaNimi + ">" + tokaNimi + "<br>");
		wout.println("<input type=\"radio\" name=\"nimi\" value=" + kolmasNimi + ">" + kolmasNimi + "<br>");
		wout.println("<br>");
		wout.println("<input type=\"submit\" name=\"action\" value=\"Kuittaa\"> ");
		wout.println("</form>");
		wout.println("<a href=\"ostokset\">Palaa etusivulle</a>");
		wout.println("</body></html>");
	}

}
