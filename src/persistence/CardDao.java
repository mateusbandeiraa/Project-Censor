package persistence;

import entity.Card;

public class CardDao extends Dao<Card> {

	public CardDao() {
		super(new Card());
	}
	
	public void dropTable() {
		session = HibernateUtil.getSessionFactory().openSession();
		session.createSQLQuery("DELETE FROM Card").executeUpdate();
		session.close();
	}

}
