package entity;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name="Card")
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column
	private Integer cardId;
	@Column(unique=true)
	private String cardWord;
	private String[] censoredWords = new String[5];
	@Column
	private String censoredWordsJSON;

	public Card(Integer cardId, String cardWord, String[] censoredWords) {
		super();
		this.cardId = cardId;
		this.cardWord = cardWord;
		this.censoredWords = censoredWords;
	}

	public Card() {

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
		this.censoredWordsJSON = gilson().toJson(this.censoredWords);
		this.censoredWords = censoredWords;
	}
	
	public String getCensoredWordsJSON() {
		this.censoredWordsJSON = gilson().toJson(this.censoredWords);
		return this.censoredWordsJSON;
	}
	
	public void setCensoredWordsJSON(String json) {
		this.censoredWordsJSON = json;
		this.censoredWords = gilson().fromJson(json, String[].class);
	}
	
	private Gson gilson() {
		return new GsonBuilder().create();
	}

}
