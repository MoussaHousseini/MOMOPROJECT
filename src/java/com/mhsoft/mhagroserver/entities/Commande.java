/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MH
 */
@Entity
@XmlRootElement
@Table(name = "COMMANDE")
@NamedQueries({
    @NamedQuery(name = "Commande.findAll", query = "SELECT c FROM Commande c"),
    @NamedQuery(name="Commande.nombre", query="SELECT COUNT(c) FROM Commande c")})

public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String client_nomprenom;
    private String client_telephone ;
    private String client_adresse ;
    private String client_quantite_commande ;
    private String produit_commander_id ;
    private String produit_commander_intitule;
    private String produit_commander_prix_unitaire ;
    private String produit_commander_unite_de_vente;
    private String produit_commander_categorie ;
    private String produit_commander_quantitestock ;
    private String produit_commander_description;
    private String pvendeur_id ;
    private String pvendeur_intitule ;
    private String pvendeur_telephone ;
    private String pvendeur_pays ;
    private String pvendeur_region ;
    private String pvendeur_departement ;
    private String pvendeur_ville ;
    private Boolean etat_abonnement ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_creation ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_derniere_miseajour ;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient_nomprenom() {
        return client_nomprenom;
    }

    public void setClient_nomprenom(String client_nomprenom) {
        this.client_nomprenom = client_nomprenom;
    }

    public String getClient_telephone() {
        return client_telephone;
    }

    public void setClient_telephone(String client_telephone) {
        this.client_telephone = client_telephone;
    }

    public String getClient_adresse() {
        return client_adresse;
    }

    public void setClient_adresse(String client_adresse) {
        this.client_adresse = client_adresse;
    }

    public String getClient_quantite_commande() {
        return client_quantite_commande;
    }

    public void setClient_quantite_commande(String client_quantite_commande) {
        this.client_quantite_commande = client_quantite_commande;
    }

    public String getProduit_commander_id() {
        return produit_commander_id;
    }

    public void setProduit_commander_id(String produit_commander_id) {
        this.produit_commander_id = produit_commander_id;
    }

    public String getProduit_commander_intitule() {
        return produit_commander_intitule;
    }

    public void setProduit_commander_intitule(String produit_commander_intitule) {
        this.produit_commander_intitule = produit_commander_intitule;
    }

    public String getProduit_commander_prix_unitaire() {
        return produit_commander_prix_unitaire;
    }

    public void setProduit_commander_prix_unitaire(String produit_commander_prix_unitaire) {
        this.produit_commander_prix_unitaire = produit_commander_prix_unitaire;
    }

    public String getProduit_commander_unite_de_vente() {
        return produit_commander_unite_de_vente;
    }

    public void setProduit_commander_unite_de_vente(String produit_commander_unite_de_vente) {
        this.produit_commander_unite_de_vente = produit_commander_unite_de_vente;
    }

    public String getProduit_commander_categorie() {
        return produit_commander_categorie;
    }

    public void setProduit_commander_categorie(String produit_commander_categorie) {
        this.produit_commander_categorie = produit_commander_categorie;
    }

    public String getProduit_commander_quantitestock() {
        return produit_commander_quantitestock;
    }

    public void setProduit_commander_quantitestock(String produit_commander_quantitestock) {
        this.produit_commander_quantitestock = produit_commander_quantitestock;
    }

    public String getProduit_commander_description() {
        return produit_commander_description;
    }

    public void setProduit_commander_description(String produit_commander_description) {
        this.produit_commander_description = produit_commander_description;
    }

    public String getPvendeur_id() {
        return pvendeur_id;
    }

    public void setPvendeur_id(String pvendeur_id) {
        this.pvendeur_id = pvendeur_id;
    }

    public String getPvendeur_intitule() {
        return pvendeur_intitule;
    }

    public void setPvendeur_intitule(String pvendeur_intitule) {
        this.pvendeur_intitule = pvendeur_intitule;
    }

    public String getPvendeur_telephone() {
        return pvendeur_telephone;
    }

    public void setPvendeur_telephone(String pvendeur_telephone) {
        this.pvendeur_telephone = pvendeur_telephone;
    }
    
    

    public String getPvendeur_pays() {
        return pvendeur_pays;
    }

    public void setPvendeur_pays(String pvendeur_pays) {
        this.pvendeur_pays = pvendeur_pays;
    }

    public String getPvendeur_region() {
        return pvendeur_region;
    }

    public void setPvendeur_region(String pvendeur_region) {
        this.pvendeur_region = pvendeur_region;
    }

    public String getPvendeur_departement() {
        return pvendeur_departement;
    }

    public void setPvendeur_departement(String pvendeur_departement) {
        this.pvendeur_departement = pvendeur_departement;
    }

    public String getPvendeur_ville() {
        return pvendeur_ville;
    }

    public void setPvendeur_ville(String pvendeur_ville) {
        this.pvendeur_ville = pvendeur_ville;
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
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mhsoft.mhagroserver.entities.Commande[ id=" + id + " ]";
    }
    
}
