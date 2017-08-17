package persistence;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Card;

public class CardDao extends Dao {
	Gson gilson = new GsonBuilder().create();

	public void create(Card c) throws Exception {
		open();
		stmt = con.prepareStatement("INSERT INTO card(cardWord, censuredWords) VALUES(?,?)");
		stmt.setString(1, c.getCardWord());

		stmt.setString(2, gilson.toJson(c.getCensoredWords()));

		stmt.execute();
		stmt.close();
		close();
	}

	public Card select(int index) throws Exception {
		open();
		stmt = con.prepareStatement("SELECT * FROM card WHERE cardId = ?");
		stmt.setInt(1, index);
		rs = stmt.executeQuery();
		Card c = null;
		if (rs.next()) {
			c = new Card(rs.getInt(1), rs.getString(2), gilson.fromJson(rs.getString(3), String[].class));
		}
		close();
		return c;
	}

	public ArrayList<Card> selectAll() throws Exception {
		open();
		stmt = con.prepareStatement("SELECT * FROM card");
		rs = stmt.executeQuery();
		ArrayList<Card> cards = new ArrayList<Card>();
		while (rs.next()) {
			Card c = new Card(rs.getInt(1), rs.getString(2), gilson.fromJson(rs.getString(3), String[].class));
			cards.add(c);
		}
		close();
		System.out.println(cards);
		return cards;
	}

	public void delete(int index) throws Exception {
		open();
		stmt = con.prepareStatement("DELETE FROM Card WHERE cardId = ?");
		stmt.setInt(1, index);
		stmt.execute();
		close();
	}

	public void sql(String statement) throws Exception {
		open();
		stmt = con.prepareStatement(statement);
		stmt.execute();
		close();
	}

	public void update(Card c) throws Exception {
		open();
		stmt = con.prepareStatement("UPDATE card SET cardWord = ?, censuredWords = ? WHERE cardId = ?");
		stmt.setString(1, c.getCardWord());
		stmt.setString(2, gilson.toJson(c.getCensoredWords()));
		stmt.setInt(3, c.getCardId());
		stmt.execute();
		close();
	}

	public static void main(String[] args) {
		try {
			CardDao cd = new CardDao();
			System.out.println(cd.gilson.toJson(cd.selectAll()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
