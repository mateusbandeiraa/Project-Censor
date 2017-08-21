package control;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
		ref = ref.split("\\?")[0];
		String cmd = request.getParameter("cmd");
		switch (cmd) {
		case "gravar":
			gravar(request, response);
			break;
		case "importarJson":
			importarJSON(request, response);
			break;
		case "apagar":
			apagar(request, response);
			break;
		case "editar":
			editar(request, response);
			break;
		}
	}

	protected void gravar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(new Date() + ": gravar()");
		String cardWord = request.getParameter("cardWord");
		String[] censuredWords = { request.getParameter("censuredWord0"), request.getParameter("censuredWord1"),
				request.getParameter("censuredWord2"), request.getParameter("censuredWord3"),
				request.getParameter("censuredWord4") };
		Card c = new Card(null, cardWord, censuredWords);
		String msg;
		try {
			new CardDao().create(c);
			msg = "Grava��o efetuada com sucesso!";
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Erro! " + ex.getLocalizedMessage();
		}
		System.out.println(new Date() + ": gravar() \n" + c);
		response.sendRedirect(ref + "?tab=create&writeMsg=" + URLEncoder.encode(msg, "UTF-8"));
	}

	protected void importarJSON(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(new Date() + ": importarJSON()");

		String json = request.getParameter("jsonTxt");
		Type tt = new TypeToken<ArrayList<Card>>() {
		}.getType();
		ArrayList<Card> cards = new GsonBuilder().create().fromJson(json, tt);

		String msg;
		try {
			CardDao cd = new CardDao();
			if (request.getParameterValues("jsonOverwrite") != null)
				cd.sql("DELETE FROM card");

			for (Card c : cards)
				cd.create(c);

			msg = "Importa��o efetuada com sucesso!";
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Erro! " + ex.getLocalizedMessage();
		}
		response.sendRedirect(ref + "?tab=importimportMsg=" + URLEncoder.encode(msg, "UTF-8"));
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
			msg = "Edi��o efetuada com sucesso!";
		} catch (Exception ex) {
			ex.printStackTrace();
			msg = "Erro! " + ex.getLocalizedMessage();
		}
		System.out.println(new Date() + ": editar() \n" + c);
		response.sendRedirect(ref + "?tab=edit&editMsg=" + URLEncoder.encode(msg, "UTF-8"));
	}

	protected void apagar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cardIdString = request.getParameter("cardId");
		try {
			new CardDao().delete(Integer.decode(cardIdString));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(new Date() + ": apagar() & ID = " + cardIdString);
	}

}
