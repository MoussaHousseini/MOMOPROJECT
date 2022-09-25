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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mh
 */
@Entity
@XmlRootElement
@Table(name = "PRODUCT")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByVendeurId", query = "SELECT p FROM Product p WHERE p.vendeur.id = :vendeurid ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.findByVendeur", query = "SELECT p FROM Product p WHERE p.vendeur.intitule = :vendeur ORDER BY p.date_creation DESC"),
    
    @NamedQuery(name = "Product.findProductByProductPublierVendeurActif", query = "SELECT p FROM Product p WHERE p.publier = :publier AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.findProductByCategorieProductPublierVendeurActif", query = "SELECT p FROM Product p WHERE p.publier = :publier AND p.categorie = :categorie AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.findProductByCategorieVille", query = "SELECT p FROM Product p WHERE p.publier = :publier AND p.categorie = :categorie AND p.vendeur.ville = :ville  AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.findProductIntitule", query = "SELECT p FROM Product p WHERE p.publier = :publier AND p.intitule LIKE :intitule  AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.findProductVille", query = "SELECT p FROM Product p WHERE p.publier = :publier AND p.vendeur.ville = :ville  AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),

    
    @NamedQuery(name = "Product.nombreByVendeur", query = "SELECT COUNT(p) FROM Product p WHERE p.vendeur.intitule = :vendeur"),
    @NamedQuery(name = "Product.nombreByVendeurId", query = "SELECT COUNT(p) FROM Product p WHERE p.vendeur.id = :vendeurid"),
    @NamedQuery(name = "Product.nombreByProductPublierVendeurActif", query = "SELECT COUNT(p) FROM Product p WHERE p.publier = :publier AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.nombreProductByCategorieProductPublierVendeurActif", query = "SELECT COUNT(p) FROM Product p WHERE p.publier = :publier AND p.categorie = :categorie AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.nombreProductByCategorieVille", query = "SELECT  COUNT(p) FROM Product p WHERE p.publier = :publier AND p.categorie = :categorie AND p.vendeur.ville = :ville  AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.nombreProductIntitule", query = "SELECT COUNT(p) FROM Product p WHERE p.publier = :publier AND p.intitule LIKE :intitule  AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),
    @NamedQuery(name = "Product.nombreProductVille", query = "SELECT COUNT(p) FROM Product p WHERE p.publier = :publier AND p.vendeur.ville = :ville  AND p.vendeur.etat_abonnement = :etat_abonnement ORDER BY p.date_creation DESC"),

    @NamedQuery(name="Product.nombre", query="SELECT COUNT(p) FROM Product p")})

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Lob
    private byte[] image_binaire_produit ;
    private String titre_image_produit ;
    private String intitule ;
    private String prix_unitaire ;
    private String unite_de_vente ;
    private String categorie ;
    private String quantite_stock_produit ;
    @Lob
    private String description ;
    private String publier ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_creation ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_derniere_miseajour ;
    //Mise en relation produit et sous categorie
    @JoinColumn(name = "vendeur_id", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Vendeur vendeur ;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @XmlTransient
    public byte[] getImage_binaire_produit() {
        return image_binaire_produit;
    }

    public void setImage_binaire_produit(byte[] image_binaire_produit) {
        this.image_binaire_produit = image_binaire_produit;
    }

    public String getTitre_image_produit() {
        return titre_image_produit;
    }

    public void setTitre_image_produit(String titre_image_produit) {
        this.titre_image_produit = titre_image_produit;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(String prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public String getUnite_de_vente() {
        return unite_de_vente;
    }

    public void setUnite_de_vente(String unite_de_vente) {
        this.unite_de_vente = unite_de_vente;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getQuantite_stock_produit() {
        return quantite_stock_produit;
    }

    public void setQuantite_stock_produit(String quantite_stock_produit) {
        this.quantite_stock_produit = quantite_stock_produit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublier() {
        return publier;
    }

    public void setPublier(String publier) {
        this.publier = publier;
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

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mhsoft.mhagroserver.entities.Product[ id=" + id + " ]";
    }
    
}
