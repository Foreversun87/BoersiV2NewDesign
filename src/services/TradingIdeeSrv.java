package services;

import javax.persistence.Query;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Aktie;
import model.Tradingidee;

public class TradingIdeeSrv implements Serializable {

    private EntityManagerFactory emf = null;

    public TradingIdeeSrv(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void anlegen(Tradingidee tradingIdee) throws Throwable {

        pruefen(tradingIdee);

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Tradingidee t = em.find(Tradingidee.class, tradingIdee.getId());
            if (t != null) {
                throw new PlausiException("Tradingidee " + t.getId() + " gibt es bereits!");
            }
            em.persist(tradingIdee);
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
    public Tradingidee aendern(Tradingidee t) throws Throwable {

        pruefen(t);

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Tradingidee d = em.find(Tradingidee.class, t.getId());
            if (d == null) {
                throw new PlausiException("Die Tradingidee " + t.getId() + " gibt es nicht!");
            } else {
                t = em.merge(t);
            }
            em.getTransaction().commit();
            return (t);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void loeschen(String id) throws Throwable {

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Tradingidee d = em.getReference(Tradingidee.class, id);

            em.remove(d);
            em.getTransaction().commit();

        } catch (EntityNotFoundException enfe) {
            throw new PlausiException("Das Depot kann nicht gelöscht werden, weil es nicht vorhanden ist!");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tradingidee> findByAktieId(Aktie aktieId) throws Throwable {

        EntityManager em = getEntityManager();
        List<Tradingidee> d = null;
        try {
            Query abfrage = em.createNamedQuery("Tradingidee.findByAktienId");
            abfrage.setParameter("aktienId", aktieId);
            d = abfrage.getResultList();
            return d;
        } catch (Exception e) {
            throw new PlausiException("Diese Aktie " + aktieId.getBez() + " wurde in der Datenbank nicht gefunden!");
        } finally {
            em.close();
        }
    }

    public Tradingidee find(String id) throws Throwable {

        EntityManager em = getEntityManager();

        try {
            Tradingidee d = em.find(Tradingidee.class, id);

            if (d == null) {
                throw new PlausiException("Tradingidee " + id + " in der Datenbank nicht gefunden!");
            } else {
                return d;
            }

        } finally {
            em.close();
        }
    }

    public List<Tradingidee> findAlle() throws Throwable {

        EntityManager em = getEntityManager();

        try {
            // Query abfrage = em.createQuery("Select sp from Sparte sp", Sparte.class);
            Query abfrage = em.createNamedQuery("Tradingidee.findAll");
            List<Tradingidee> TradingAlle = abfrage.getResultList();
            return TradingAlle;

        } catch (Exception ex) {
            throw new PlausiException(ex.getMessage());

        } finally {
            em.close();
        }
    }

    public void pruefen(Tradingidee d) throws PlausiException {

        ArrayList<String> meldungen = new ArrayList();

        if (d.getKursStoppnegativ() <= 0) {
            meldungen.add("Bitte einen positiven Wert eingeben!");
        }

        if (d.getKursStopppositiv() <= 0) {
            meldungen.add("Bitte einen positiven Wert eingeben!");
        }

        if (d.getAktKurs() <= 0) {
            meldungen.add("Bitte einen gültigen Kurs eingeben!");
        }
        if (d.getAktienId().equals(null)) {
            meldungen.add("Bitte eine Aktie auswählen");
        }
        if (d.getDepotID().equals(null)) {
            meldungen.add("Bitte ein Depot auswählen");
        }
        
        if(d.getAktienId() == null){
            meldungen.add("Bitte eine Aktie eingeben");
        }

        if (!meldungen.isEmpty()) {
            throw new PlausiException(meldungen);
        }

    }
}
