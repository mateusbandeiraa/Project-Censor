package entity;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import persistence.CardDao;

public class Card implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer cardId;
	private String cardWord;
	private String[] censoredWords = new String[5];

	public Card(Integer cardId, String cardWord, String[] censoredWords) {
		super();
		this.cardId = cardId;
		this.cardWord = cardWord;
		this.censoredWords = censoredWords;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", cardWord=" + cardWord + ", censoredWords=" + Arrays.toString(censoredWords)
				+ "]";
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public String getCardWord() {
		return cardWord;
	}

	public void setCardWord(String cardWord) {
		this.cardWord = cardWord;
	}

	public String[] getCensoredWords() {
		return censoredWords;
	}

	public void setCensoredWords(String[] censoredWords) {
		this.censoredWords = censoredWords;
	}

	public static void main(String[] args) throws Exception {
		CardDao cd = new CardDao();
		ArrayList<Card> cards = cd.selectAll();
		int i = new SecureRandom().nextInt(cards.size()-1);
		System.out.println(cards.get(i));
	}

}
