/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ratte
 */
@Entity
@Table(name = "tradingidee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tradingidee.findAll", query = "SELECT t FROM Tradingidee t")
    , @NamedQuery(name = "Tradingidee.findByAktienId", query = "SELECT t FROM Tradingidee t WHERE t.aktienId = :aktienId")
    , @NamedQuery(name = "Tradingidee.findById", query = "SELECT t FROM Tradingidee t WHERE t.id = :id")
    , @NamedQuery(name = "Tradingidee.findByKursStoppnegativ", query = "SELECT t FROM Tradingidee t WHERE t.kursStoppnegativ = :kursStoppnegativ")
    , @NamedQuery(name = "Tradingidee.findByKursStopppositiv", query = "SELECT t FROM Tradingidee t WHERE t.kursStopppositiv = :kursStopppositiv")
    , @NamedQuery(name = "Tradingidee.findByAktKurs", query = "SELECT t FROM Tradingidee t WHERE t.aktKurs = :aktKurs")})
public class Tradingidee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Basic(optional = false)
    @Column(name = "KursStoppnegativ")
    private double kursStoppnegativ;
    @Basic(optional = false)
    @Column(name = "KursStopppositiv")
    private double kursStopppositiv;
    @Basic(optional = false)
    @Column(name = "AktKurs")
    private double aktKurs;
    @JoinColumn(name = "AktienId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Aktie aktienId;
    @JoinColumn(name = "DepotID", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Depot depotID;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tradingId")
    private Transaktion transaktion;

    public Tradingidee() {
    }

    public Tradingidee(String id) {
        this.id = id;
    }

    public Tradingidee(String id, double kursStoppnegativ, double kursStopppositiv, double aktKurs) {
        this.id = id;
        this.kursStoppnegativ = kursStoppnegativ;
        this.kursStopppositiv = kursStopppositiv;
        this.aktKurs = aktKurs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getKursStoppnegativ() {
        return kursStoppnegativ;
    }

    public void setKursStoppnegativ(double kursStoppnegativ) {
        this.kursStoppnegativ = kursStoppnegativ;
    }

    public double getKursStopppositiv() {
        return kursStopppositiv;
    }

    public void setKursStopppositiv(double kursStopppositiv) {
        this.kursStopppositiv = kursStopppositiv;
    }

    public double getAktKurs() {
        return aktKurs;
    }

    public void setAktKurs(double aktKurs) {
        this.aktKurs = aktKurs;
    }

    public Aktie getAktienId() {
        return aktienId;
    }

    public void setAktienId(Aktie aktienId) {
        this.aktienId = aktienId;
    }

    public Depot getDepotID() {
        return depotID;
    }

    public void setDepotID(Depot depotID) {
        this.depotID = depotID;
    }

    public Transaktion getTransaktion() {
        return transaktion;
    }

    public void setTransaktion(Transaktion transaktion) {
        this.transaktion = transaktion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tradingidee)) {
            return false;
        }
        Tradingidee other = (Tradingidee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Tradingidee[ id=" + id + " ]";
    }
    
}
