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
@Table(name = "depot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Depot.findAll", query = "SELECT d FROM Depot d")
    , @NamedQuery(name = "Depot.findById", query = "SELECT d FROM Depot d WHERE d.id = :id")
    , @NamedQuery(name = "Depot.findByKapital", query = "SELECT d FROM Depot d WHERE d.kapital = :kapital")
    , @NamedQuery(name = "Depot.findByRisikogesamt", query = "SELECT d FROM Depot d WHERE d.risikogesamt = :risikogesamt")
    , @NamedQuery(name = "Depot.findByRiskikoeinzel", query = "SELECT d FROM Depot d WHERE d.riskikoeinzel = :riskikoeinzel")
    , @NamedQuery(name = "Depot.findByUser", query = "SELECT d FROM Depot d WHERE d.user = :user")
    , @NamedQuery(name = "Depot.findByPasswort", query = "SELECT d FROM Depot d WHERE d.passwort = :passwort")})
public class Depot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Kapital")
    private double kapital;
    @Basic(optional = false)
    @Column(name = "Risikogesamt")
    private double risikogesamt;
    @Basic(optional = false)
    @Column(name = "Riskikoeinzel")
    private double riskikoeinzel;
    @Basic(optional = false)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @Column(name = "passwort")
    private String passwort;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depotID")
    private List<Tradingidee> tradingideeList;

    public Depot() {
    }

    public Depot(Integer id) {
        this.id = id;
    }

    public Depot(Integer id, double kapital, double risikogesamt, double riskikoeinzel, String user, String passwort) {
        this.id = id;
        this.kapital = kapital;
        this.risikogesamt = risikogesamt;
        this.riskikoeinzel = riskikoeinzel;
        this.user = user;
        this.passwort = passwort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getKapital() {
        return kapital;
    }

    public void setKapital(double kapital) {
        this.kapital = kapital;
    }

    public double getRisikogesamt() {
        return risikogesamt;
    }

    public void setRisikogesamt(double risikogesamt) {
        this.risikogesamt = risikogesamt;
    }

    public double getRiskikoeinzel() {
        return riskikoeinzel;
    }

    public void setRiskikoeinzel(double riskikoeinzel) {
        this.riskikoeinzel = riskikoeinzel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @XmlTransient
    public List<Tradingidee> getTradingideeList() {
        return tradingideeList;
    }

    public void setTradingideeList(List<Tradingidee> tradingideeList) {
        this.tradingideeList = tradingideeList;
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
        if (!(object instanceof Depot)) {
            return false;
        }
        Depot other = (Depot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Depot[ id=" + id + " ]";
    }
    
}
