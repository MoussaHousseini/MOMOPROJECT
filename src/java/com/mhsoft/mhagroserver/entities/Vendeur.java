/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mh
 */
@Entity
@XmlRootElement
@Table(name = "VENDEUR")
@NamedQueries({
    @NamedQuery(name = "Vendeur.findAll", query = "SELECT v FROM Vendeur v"),
    @NamedQuery(name = "Vendeur.telephone", query = "SELECT v FROM Vendeur v WHERE v.telephone = :telephone"),
    @NamedQuery(name = "Vendeur.telephone_password", query = "SELECT v FROM Vendeur v WHERE v.telephone =:telephone AND v.password = :password"),
    @NamedQuery(name="Vendeur.nombre", query="SELECT COUNT(v) FROM Vendeur v")})

public class Vendeur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String intitule ;
    private String telephone ;
    private String password ;
    private String pays ;
    private String region ;
    private String departement ;
    private String ville;
    private Boolean etat_abonnement ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_creation ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_derniere_miseajour ;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendeur")
    private List<Product> listesProduct;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }
    
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Boolean getEtat_abonnement() {
        return etat_abonnement;
    }

    public void setEtat_abonnement(Boolean etat_abonnement) {
        this.etat_abonnement = etat_abonnement;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Date getDate_derniere_miseajour() {
        return date_derniere_miseajour;
    }

    public void setDate_derniere_miseajour(Date date_derniere_miseajour) {
        this.date_derniere_miseajour = date_derniere_miseajour;
    }

    public List<Product> getListesProduct() {
        return listesProduct;
    }

    public void setListesProduct(List<Product> listesProduct) {
        this.listesProduct = listesProduct;
    }
    public void ajouterProduct(Product product){
        this.listesProduct.add(product);
    }
    
    public void retirerProduct(Product product){
        this.listesProduct.remove(product);
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
        if (!(object instanceof Vendeur)) {
            return false;
        }
        Vendeur other = (Vendeur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mhsoft.mhagroserver.entities.Vendeur[ id=" + id + " ]";
    }
    
}
