package model;

import javax.persistence.*;

public class DbVerbindung {

	private static EntityManager em = null;
	private static EntityManagerFactory emf = null;

	private DbVerbindung() {
	}

	
	//Nutzlos?!
	public static EntityManager getEntityManager() {
		if (em == null) {
			em = getEmf().createEntityManager();
		}
		return em;
	}

	public static EntityManagerFactory getEmf() {
		if (emf == null)
			emf = Persistence.createEntityManagerFactory("BoersePU");
		return emf;
	}

	public static void close() {
		if (em != null)
			em.close();

		if (emf != null)
			emf.close();
	}

	public static void main(String[] args) {
		try {
			emf = Persistence.createEntityManagerFactory("BoersePU");
			System.out.println("Verbindung ok!");
		} catch (Exception e) {
			System.out.println("keine Verbindung!" + e.getMessage());
		}
	}
}