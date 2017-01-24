package fi.softala.jee.demo.d08.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.softala.jee.demo.d08.bean.Ostos;
import fi.softala.jee.demo.d08.dao.DAOPoikkeus;
import fi.softala.jee.demo.d08.dao.HenkiloDAO;

/**
 * Servlet implementation class HenkiloServlet
 */
@WebServlet("/ostokset")
public class OstosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OstosServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ostaja = request.getParameter("ostaja");

		String summa = request.getParameter("summa");
		Date nykyhetki = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String formatDate = formatter.format(nykyhetki);

		Ostos ostos = new Ostos(ostaja, formatDate, summa);

		try {
			HenkiloDAO hDao = new HenkiloDAO();
			hDao.lisaa(ostos);
		} catch (DAOPoikkeus e) {
			throw new ServletException(e);
		}

		response.sendRedirect("ostokset"); // redirect doGet
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Ostos> ostokset;

		try {
			// tietokannasta henkilöt
			HenkiloDAO hDao = new HenkiloDAO();
			ostokset = hDao.haeKaikki();
		} catch (DAOPoikkeus e) {
			throw new ServletException(e);
		}

		// requestiin talteen
		request.setAttribute("ostokset", ostokset);

		// jsp hoitaa muotoilun
		request.getRequestDispatcher("index.jsp").forward(request, response);

	}

}
