package fi.softala.jee.demo.d08.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fi.softala.jee.demo.d08.dao.DAOPoikkeus;
import fi.softala.jee.demo.d08.dao.HenkiloDAO;

/**
 * Servlet implementation class PoistoServlet
 */
@WebServlet("/PoistoServlet")
public class PoistoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PoistoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("param");
		
		try {
			HenkiloDAO hDao = new HenkiloDAO();
			hDao.poista(id);
		} catch (DAOPoikkeus e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}
		
		response.sendRedirect("ostokset");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
