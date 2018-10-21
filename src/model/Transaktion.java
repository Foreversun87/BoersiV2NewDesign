/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ratte
 */
@Entity
@Table(name = "transaktion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaktion.findAll", query = "SELECT t FROM Transaktion t")
    , @NamedQuery(name = "Transaktion.findById", query = "SELECT t FROM Transaktion t WHERE t.id = :id")
    , @NamedQuery(name = "Transaktion.findByStueckzahl", query = "SELECT t FROM Transaktion t WHERE t.stueckzahl = :stueckzahl")
    , @NamedQuery(name = "Transaktion.findByKursVerkauf", query = "SELECT t FROM Transaktion t WHERE t.kursVerkauf = :kursVerkauf")
    , @NamedQuery(name = "Transaktion.findByOcoDatum", query = "SELECT t FROM Transaktion t WHERE t.ocoDatum = :ocoDatum")
    , @NamedQuery(name = "Transaktion.findByVerkaufdatum", query = "SELECT t FROM Transaktion t WHERE t.verkaufdatum = :verkaufdatum")
    , @NamedQuery(name = "Transaktion.findByKaufdatum", query = "SELECT t FROM Transaktion t WHERE t.kaufdatum = :kaufdatum")
    , @NamedQuery(name = "Transaktion.findByNotiz", query = "SELECT t FROM Transaktion t WHERE t.notiz = :notiz")})
public class Transaktion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Stueckzahl")
    private Double stueckzahl;
    @Column(name = "KursVerkauf")
    private Double kursVerkauf;
    @Column(name = "OcoDatum")
    @Temporal(TemporalType.DATE)
    private Date ocoDatum;
    @Column(name = "Verkaufdatum")
    @Temporal(TemporalType.DATE)
    private Date verkaufdatum;
    @Column(name = "Kaufdatum")
    @Temporal(TemporalType.DATE)
    private Date kaufdatum;
    @Column(name = "Notiz")
    private String notiz;
    @JoinColumn(name = "AktieId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Aktie aktieId;
    @JoinColumn(name = "TradingId", referencedColumnName = "Id")
    @OneToOne(optional = false)
    private Tradingidee tradingId;

    public Transaktion() {
    }

    public Transaktion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getStueckzahl() {
        return stueckzahl;
    }

    public void setStueckzahl(Double stueckzahl) {
        this.stueckzahl = stueckzahl;
    }

    public Double getKursVerkauf() {
        return kursVerkauf;
    }

    public void setKursVerkauf(Double kursVerkauf) {
        this.kursVerkauf = kursVerkauf;
    }

    public Date getOcoDatum() {
        return ocoDatum;
    }

    public void setOcoDatum(Date ocoDatum) {
        this.ocoDatum = ocoDatum;
    }

    public Date getVerkaufdatum() {
        return verkaufdatum;
    }

    public void setVerkaufdatum(Date verkaufdatum) {
        this.verkaufdatum = verkaufdatum;
    }

    public Date getKaufdatum() {
        return kaufdatum;
    }

    public void setKaufdatum(Date kaufdatum) {
        this.kaufdatum = kaufdatum;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public Aktie getAktieId() {
        return aktieId;
    }

    public void setAktieId(Aktie aktieId) {
        this.aktieId = aktieId;
    }

    public Tradingidee getTradingId() {
        return tradingId;
    }

    public void setTradingId(Tradingidee tradingId) {
        this.tradingId = tradingId;
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
        if (!(object instanceof Transaktion)) {
            return false;
        }
        Transaktion other = (Transaktion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Transaktion[ id=" + id + " ]";
    }
    
}
