package services;

import controls.ProgModus;
import javax.persistence.Query;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.*;
import javafx.scene.control.RadioButton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Tradingidee;
import model.Transaktion;

public class TransaktionSrv implements Serializable {

    private EntityManagerFactory emf = null;

    public TransaktionSrv(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void anlegen(Transaktion transaktion) throws Throwable {

        pruefen(transaktion);

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Transaktion t = em.find(Transaktion.class, 1);
            if (t != null) {
                throw new PlausiException("Transaktion " + t.getId() + " gibt es bereits!");
            }

            em.persist(transaktion);
            em.getTransaction().commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Transaktion aendern(Transaktion transaktion) throws Throwable {

        pruefen(transaktion);

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Transaktion t = em.find(Transaktion.class, transaktion.getId());
            if (t == null) {
                throw new PlausiException("Die Transaktion " + t.getId() + " gibt es nicht!");
            } else {
                transaktion = em.merge(transaktion);
            }
            em.getTransaction().commit();
            return (transaktion);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void loeschen(int id) throws Throwable {
        Tradingidee tradingIdeeAktuell = null;
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Transaktion transaktion = em.getReference(Transaktion.class, id);
            tradingIdeeAktuell = transaktion.getTradingId();
            List<String> meldungen = null;
            if (!tradingIdeeAktuell.equals(null)) {
                meldungen = new ArrayList<String>();
                meldungen.add("Folgende Tradingidee ist aktuell der Transaktion " + transaktion.getId() + " zugeordnet: " + tradingIdeeAktuell);
            }
            if (meldungen != null) {
                meldungen.add("Die Transaktion " + transaktion.getId() + " kann daher nicht gelöscht werden!");
                throw new PlausiException(meldungen);
            }
            em.remove(transaktion);
            em.getTransaction().commit();

        } catch (EntityNotFoundException enfe) {
            throw new PlausiException("Das Depot kann nicht gelöscht werden, weil es nicht vorhanden ist!");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Transaktion find(int id) throws Throwable {

        EntityManager em = getEntityManager();

        try {
            Transaktion t = em.find(Transaktion.class, id);

            if (t == null) {
                throw new PlausiException("Transaktion " + id + " in der Datenbank nicht gefunden!");
            } else {
                return t;
            }

        } finally {
            em.close();
        }
    }

    public List<Transaktion> findAlle() throws Throwable {

        EntityManager em = getEntityManager();

        try {
            // Query abfrage = em.createQuery("Select sp from Sparte sp", Sparte.class);
            Query abfrage = em.createNamedQuery("Transaktion.findAll");
            List<Transaktion> transaktionAlle = abfrage.getResultList();
            return transaktionAlle;

        } catch (Exception ex) {
            throw new PlausiException(ex.getMessage());

        } finally {
            em.close();
        }
    }

    public void pruefen(Transaktion t) throws PlausiException {

        ArrayList<String> meldungen = new ArrayList();

        if (ProgModus.IS_SELECTED_RB) {

            //Plausiprüfung bei konkreter Tradingidee
            if (t.getKaufdatum() == null || t.getOcoDatum() == null || t.getKaufdatum() != null && t.getOcoDatum() == null || t.getOcoDatum().before(t.getKaufdatum())) {

                if (t.getKaufdatum() == null) {
                    meldungen.add("Bitte ein Kauf-Datum eingeben");
                }

                if (t.getOcoDatum() == null) {
                    meldungen.add("Bei einer konkreten Tradingidee bitte ein OCO-Datum eingeben");
                }
                if (t.getKaufdatum() != null && t.getOcoDatum() != null) {
                    if (t.getOcoDatum().before(t.getKaufdatum())) {
                        meldungen.add("Bitte ein OCO-Datum nach dem Kaufdatum eingeben");
                    }
                }
            }
        }

        if (ProgModus.IS_COMPLETED) {
            //Plausiprüfung bei Abschluss einer konkreter Tradingidee
            if ((t.getKursVerkauf() != null && t.getVerkaufdatum() == null) || (t.getKursVerkauf() == null && t.getVerkaufdatum() != null) || t.getVerkaufdatum().before(t.getKaufdatum())) {

                if (t.getVerkaufdatum() == null) {
                    meldungen.add("Bitte ein Verkaufsdatum eingeben");
                }
                if (t.getKaufdatum().after(t.getVerkaufdatum())) {
                    meldungen.add("Bitte das Verkaufsdatum nach Kaufdatum justieren");
                }
                if (t.getKursVerkauf() == null) {
                    meldungen.add("Bitte einen Verkaufskurs eingeben");
                }
            }
        }

        if (!meldungen.isEmpty()) {
            throw new PlausiException(meldungen);
        }

    }
}
