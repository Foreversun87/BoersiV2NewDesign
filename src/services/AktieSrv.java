package services;

import javax.persistence.Query;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Aktie;
import model.Tradingidee;

public class AktieSrv implements Serializable {

    private EntityManagerFactory emf = null;
    private boolean exist = false;

    public AktieSrv(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void anlegen(Aktie aktie) throws Throwable {

        pruefen(aktie);

        if (aktie.getTradingideeList() == null) {
            aktie.setTradingideeList(new ArrayList<Tradingidee>());
        }

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Aktie a = em.find(Aktie.class, 1);
            if (a != null) {
                throw new PlausiException("Aktie " + aktie.getId() + " gibt es bereits!");
            }

            em.persist(aktie);
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
    public Aktie aendern(Aktie aktie) throws Throwable {

        pruefen(aktie);

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Aktie a = em.find(Aktie.class, aktie.getId());
            if (a == null) {
                throw new PlausiException("Das Aktie " + aktie.getId() + " gibt es nicht!");
            } else {
                aktie = em.merge(aktie);
            }

            em.getTransaction().commit();
            return (aktie);
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

            Aktie aktie = em.getReference(Aktie.class, id);

            List<String> meldungen = null;

            List<Tradingidee> tradingIdeenAktuell = aktie.getTradingideeList();

            for (Tradingidee ts : tradingIdeenAktuell) {
                if (meldungen == null) {
                    meldungen = new ArrayList<String>();
                    meldungen.add("Folgende Tradingideen sind aktuell der Aktie " + aktie.getId() + " zugeordnet:");
                }

                meldungen.add(String.valueOf(ts.getId()));
            }

            if (meldungen != null) {
                meldungen.add("Die Aktie " + aktie.getId() + " kann daher nicht gelöscht werden!");
                throw new PlausiException(meldungen);
            }

            em.remove(aktie);
            em.getTransaction().commit();

        } catch (EntityNotFoundException enfe) {
            throw new PlausiException("Die Aktie kann nicht gelöscht werden, weil sie nicht vorhanden ist!");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Aktie findByWkn(String id) throws Throwable {

        EntityManager em = getEntityManager();

        try {
            Aktie a = null;
            Query abfrage = em.createNamedQuery("Aktie.findByWkn");
            abfrage.setParameter("wkn", id);
            a = (Aktie) abfrage.getSingleResult();
            return a;
        } catch (Exception e) {
            throw new PlausiException("Ohjee Aktie gibt es als WKN nicht");
        } finally {
            em.close();
        }
    }

    public Aktie findByBezeichnung(String id) throws Throwable {

        EntityManager em = getEntityManager();

        try {

            Aktie a = null;

            //a = findByWkn(id);
            if (a == null) {
                Query abfrage = em.createNamedQuery("Aktie.findByBez");
                abfrage.setParameter("bez", id);
                a = (Aktie) abfrage.getSingleResult();
            }            
            return a;
        } catch (Exception e) {
            throw new RuntimeException("Aktie " + id + " gibt es nicht");
        } finally {
            em.close();
        }
    }

    public Aktie find(int id) throws Throwable {

        EntityManager em = getEntityManager();

        try {
            Aktie a = em.find(Aktie.class, id);

            if (a == null) {
                throw new PlausiException("Aktie " + id + " in der Datenbank nicht gefunden!");
            } else {
                return a;
            }

        } finally {
            em.close();
        }
    }

    public List<Aktie> findAlle() throws Throwable {

        EntityManager em = getEntityManager();

        try {
            // Query abfrage = em.createQuery("Select sp from Sparte sp", Sparte.class);
            Query abfrage = em.createNamedQuery("Aktie.findAll");
            List<Aktie> aktieAlle = abfrage.getResultList();
            return aktieAlle;

        } catch (Exception ex) {
            throw new PlausiException(ex.getMessage());

        } finally {
            em.close();
        }
    }

    public void pruefen(Aktie a) throws PlausiException {

        ArrayList<String> meldungen = new ArrayList();

        if (a.getBez().isEmpty()) {
            meldungen.add("Bitte eine Bezeichnung eingeben!");
        }

        if (a.getWkn().isEmpty()) {
            meldungen.add("Bitte eine WKN eingeben!");
        }

        if (!meldungen.isEmpty()) {
            throw new PlausiException(meldungen);
        }

    }
}
