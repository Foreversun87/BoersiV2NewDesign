package services;

import javax.persistence.Query;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Depot;
import model.Tradingidee;

public class DepotSrv implements Serializable {

    private EntityManagerFactory emf = null;

    public DepotSrv(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void anlegen(Depot depot) throws Throwable {

        pruefen(depot);

        if (depot.getTradingideeList() == null) {
            depot.setTradingideeList(new ArrayList<Tradingidee>());
        }

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

                
            Depot d = em.find(Depot.class, 1);
            if (d != null) {
                throw new PlausiException("Depot " + d.getId() + " gibt es bereits!");
            }

            em.persist(depot);
            em.getTransaction().commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /*
         try {
            em = getEntityManager();
            em.getTransaction().begin();

            Lager lg = em.find(Lager.class, lager.getId());
            if (lg != null) {
                throw new PlausiException("Lager " + lager.getId() + " gibt es bereits!");
            }

            em.persist(lager);
            em.getTransaction().commit();
     */
    public Depot aendern(Depot depot) throws Throwable {

        pruefen(depot);

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Depot d = em.find(Depot.class, depot.getId());
            if (d == null) {
                throw new PlausiException("Das Depot " + d.getId() + " gibt es nicht!");
            } else {
                depot = em.merge(depot);
            }

            em.getTransaction().commit();
            return (depot);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void loeschen(int id) throws Throwable {

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Depot depot = em.getReference(Depot.class, id);

            List<String> meldungen = null;

            List<Tradingidee> transaktionenAktuell = depot.getTradingideeList();

            for (Tradingidee ts : transaktionenAktuell) {
                if (meldungen == null) {
                    meldungen = new ArrayList<String>();
                    meldungen.add("Folgende Transaktionen sind aktuell dem Depot " + depot.getId() + " zugeordnet:");
                }

                meldungen.add(String.valueOf(ts.getId()));
            }

            if (meldungen != null) {
                meldungen.add("Das Depot " + depot.getId() + " kann daher nicht gel�scht werden!");
                throw new PlausiException(meldungen);
            }

            em.remove(depot);
            em.getTransaction().commit();

        } catch (EntityNotFoundException enfe) {
            throw new PlausiException("Das Depot kann nicht gelöscht werden, weil es nicht vorhanden ist!");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Depot findByUser(String id) throws Throwable {

        EntityManager em = getEntityManager();

        try {
            Depot d = null;
            Query abfrage = em.createNamedQuery("Depot.findByUser");
            abfrage.setParameter("user", id);
            d = (Depot) abfrage.getSingleResult();
            return d;
        } catch (Exception e) {
            throw new PlausiException("User " + id + " gibt es nicht");
        } finally {
            em.close();
        }
    }

    public Depot find(int id) throws Throwable {

        EntityManager em = getEntityManager();

        try {
            Depot d = em.find(Depot.class,
                     id);

            if (d == null) {
                throw new PlausiException("Depot " + id + " in der Datenbank nicht gefunden!");
            } else {
                return d;
            }

        } finally {
            em.close();
        }
    }

    public List<Depot> findAlle() throws Throwable {

        EntityManager em = getEntityManager();

        try {
            // Query abfrage = em.createQuery("Select sp from Sparte sp", Sparte.class);
            Query abfrage = em.createNamedQuery("Depot.findAll");
            List<Depot> depotAlle = abfrage.getResultList();
            return depotAlle;

        } catch (Exception ex) {
            throw new PlausiException(ex.getMessage());

        } finally {
            em.close();
        }
    }

    public void pruefen(Depot d) throws PlausiException {

        ArrayList<String> meldungen = new ArrayList();

        if (d.getKapital() <= 0) {
            meldungen.add("Bitte einen positiven Wert eingeben!");
        }

        if (d.getRisikogesamt() <= 0) {
            meldungen.add("Bitte ein Risiko eingeben!");
        }

        if (d.getRiskikoeinzel() <= 0) {
            meldungen.add("Bitte ein Risiko eingeben!");
        }
        if (d.getUser().isEmpty()) {
            meldungen.add("Bitte einen Namen eingeben!");
        }
        if (d.getPasswort().isEmpty()) {
            meldungen.add("Bitte ein Passwort eingeben!");
        }

        if (!meldungen.isEmpty()) {
            throw new PlausiException(meldungen);
        }

    }
}
