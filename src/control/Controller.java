package control;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Card;
import persistence.CardDao;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String ref;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ref = request.getHeader("referer");
		System.out.println(ref);
		ref = ref.split("\\?")[0];
		String cmd = request.getParameter("cmd");
		switch (cmd) {
		case "gravar":
			gravar(request, response);
		case "apagar":
			apagar(request, response);
		case "editar":
			editar(request, response);
		}
	}

	protected void gravar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cardWord = request.getParameter("cardWord");
		String[] censuredWords = { request.getParameter("censuredWord0"), request.getParameter("censuredWord1"),
				request.getParameter("censuredWord2"), request.getParameter("censuredWord3"),
				request.getParameter("censuredWord4") };
		Card c = new Card(null, cardWord, censuredWords);
		String msg;
		try {
			new CardDao().create(c);
			msg = "Gravação efetuada com sucesso!";
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Erro! " + ex.getLocalizedMessage();
		}

		response.sendRedirect(ref + "?writeMsg=" + URLEncoder.encode(msg, "UTF-8"));
	}

	protected void editar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer cardId = Integer.decode(request.getParameter("editCardId"));
		String cardWord = request.getParameter("editCardWord");
		String[] censuredWords = { request.getParameter("editCensuredWord0"), request.getParameter("editCensuredWord1"),
				request.getParameter("editCensuredWord2"), request.getParameter("editCensuredWord3"),
				request.getParameter("editCensuredWord4") };
		Card c = new Card(cardId, cardWord, censuredWords);
		String msg;
		try {
			new CardDao().update(c);
			msg = "Edição efetuada com sucesso!";
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Erro! " + ex.getLocalizedMessage();
		}

		response.sendRedirect(ref + "?editMsg=" + URLEncoder.encode(msg, "UTF-8"));
	}

	protected void apagar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cardIdString = request.getParameter("cardId");
		try {
			new CardDao().delete(Integer.decode(cardIdString));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
