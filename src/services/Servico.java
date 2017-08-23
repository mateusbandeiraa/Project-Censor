package services;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Card;
import persistence.CardDao;

@Path("/card")
public class Servico {
	Gson gilson = new GsonBuilder().create();

	@GET
	@Path("getCards")
	@Produces("application/json")
	public String getCards() {
		System.out.println(new Date() + ": getCards()");
		String output = null;
		try {
			ArrayList<Card> cards = new ArrayList<>(new CardDao().selectAll());
			output = gilson.toJson(cards);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return output;
	}

	@GET
	@Path("getCard/{id}")
	@Produces("application/json")
	public String getCard(@PathParam("id") String id) {
		System.out.println(new Date() + ": getCard() & ID = " + id);
		String output = null;
		try {
			Card c = new CardDao().select(Integer.decode(id));
			output = gilson.toJson(c);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return output;
	}

	// @GET
	// @Path("randomCard")
	// @Produces("application/json")
	// public String randomCard() {
	// Card output = null;
	// try {
	// ArrayList<Card> cards = new CardDao().selectAll();
	// System.out.println(new CardDao().select(2));
	// int i = new SecureRandom().nextInt(cards.size() - 1);
	// output = cards.get(i);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// System.out.println(gilson.toJson(output));
	// return gilson.toJson(output);
	// }

	/*
	 * @POST
	 * 
	 * @Path("importCards/")
	 * 
	 * @Produces("text/plain") public String importCard(@HeaderParam("json")
	 * String json) throws Exception{ Type type = new
	 * TypeToken<ArrayList<Card>>(){}.getType(); ArrayList<Card> cards =
	 * gilson.fromJson(json, type); CardDao cd = new CardDao(); cd.
	 * sql("CREATE TABLE card (cardId INTEGER IDENTITY PRIMARY KEY, cardWord VARCHAR(256), censuredWords VARCHAR(256));"
	 * ); for (Card c : cards) { try { cd.create(c); } catch (Exception ex) {
	 * ex.printStackTrace(); return "Erro: " + ex.getLocalizedMessage(); } }
	 * return "Sucesso";
	 * 
	 * }
	 */

	public static void main(String[] args) throws Exception {
	}
}