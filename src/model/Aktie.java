/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ratte
 */
@Entity
@Table(name = "aktie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aktie.findAll", query = "SELECT a FROM Aktie a")
    , @NamedQuery(name = "Aktie.findById", query = "SELECT a FROM Aktie a WHERE a.id = :id")
    , @NamedQuery(name = "Aktie.findByBez", query = "SELECT a FROM Aktie a WHERE a.bez = :bez")
    , @NamedQuery(name = "Aktie.findByWkn", query = "SELECT a FROM Aktie a WHERE a.wkn = :wkn")})
public class Aktie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Bez")
    private String bez;
    @Basic(optional = false)
    @Column(name = "Wkn")
    private String wkn;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aktienId")
    private List<Tradingidee> tradingideeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aktieId")
    private List<Transaktion> transaktionList;

    public Aktie() {
    }

    public Aktie(Integer id) {
        this.id = id;
    }

    public Aktie(Integer id, String bez, String wkn) {
        this.id = id;
        this.bez = bez;
        this.wkn = wkn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBez() {
        return bez;
    }

    public void setBez(String bez) {
        this.bez = bez;
    }

    public String getWkn() {
        return wkn;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    @XmlTransient
    public List<Tradingidee> getTradingideeList() {
        return tradingideeList;
    }

    public void setTradingideeList(List<Tradingidee> tradingideeList) {
        this.tradingideeList = tradingideeList;
    }

    @XmlTransient
    public List<Transaktion> getTransaktionList() {
        return transaktionList;
    }

    public void setTransaktionList(List<Transaktion> transaktionList) {
        this.transaktionList = transaktionList;
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
        if (!(object instanceof Aktie)) {
            return false;
        }
        Aktie other = (Aktie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Aktie[ id=" + id + " ]";
    }
    
}
