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
import javax.servlet.http.HttpSession;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import entity.Card;
import persistence.CardDao;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String ADMIN_USERNAME = System.getenv("ADM_USER");
	private final String ADMIN_PASSWORD = System.getenv("ADM_PASS");
	private String ref;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ref = request.getHeader("referer");
		ref = ref.split("\\?")[0];
		System.out.println(ref);
		String cmd = request.getParameter("cmd");
		System.out.println(cmd);
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
		case "login":
			login(request, response);
			break;
		case "logout":
			logout(request, response);
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
			msg = "Gravação efetuada com sucesso!";
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
				cd.dropTable();

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
			msg = "Edição efetuada com sucesso!";
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
			int id = Integer.decode(cardIdString);
			new CardDao().delete(new Card(id, null, null));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(new Date() + ": apagar() & ID = " + cardIdString);
	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String inputUsername = request.getParameter("username");
		String inputPassword = request.getParameter("password");
		
		if(inputUsername.equals(ADMIN_USERNAME) && inputPassword.equals(ADMIN_PASSWORD)) 
			session.setAttribute("userAuthorized", "true");
		
		
		System.out.println(new Date() + ": login()");
		String redirect;
		if(ref.contains("/index.jsp")) {
		redirect = ref.split("/index.jsp")[0];
		} else {
			redirect = ref.substring(0, ref.lastIndexOf("/"));
		}
		redirect += "/adm/adminTools.jsp";
		response.sendRedirect(redirect);
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("userAuthorized", "false");
		
		System.out.println(new Date() + ": logout()");
		String redirect = ref.split("/adm/adminTools.jsp")[0] + "/index.jsp";
		response.sendRedirect(redirect);
	}

}
