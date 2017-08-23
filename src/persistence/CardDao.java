package persistence;

import entity.Card;

public class CardDao extends Dao<Card> {

	public CardDao() {
		super(new Card());
	}
	
	public void dropTable() {
		session = HibernateUtil.getSessionFactory().openSession();
		session.createSQLQuery("DROP TABLE Card").executeUpdate();
		session.close();
	}

}
