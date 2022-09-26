
package com.mhsoft.mhagro;

/**
 *
 * @author Moussa Housseini 
 */
public class VendeurProduct {
    
    private Long   Id ;
    private String titre_image_produit ;
    private String intitule ;
    private String prix_unitaire ;
    private String unite_de_vente ;
    private String categorie ;
    private String quantite_stock_produit ;
    private String description ;
    private String publier ;
    
    public VendeurProduct(){}

    public VendeurProduct(Long Id, String titre_image_produit, String intitule, String prix_unitaire, String unite_de_vente, String categorie, String quantite_stock_produit, String description, String publier) {
        this.Id = Id;
        this.titre_image_produit = titre_image_produit;
        this.intitule = intitule;
        this.prix_unitaire = prix_unitaire;
        this.unite_de_vente = unite_de_vente;
        this.categorie = categorie;
        this.quantite_stock_produit = quantite_stock_produit;
        this.description = description;
        this.publier = publier;
    }

    
    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
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

    
    
    
   
}
