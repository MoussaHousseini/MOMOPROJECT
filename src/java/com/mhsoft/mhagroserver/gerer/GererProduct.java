/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.gerer;

import com.mhsoft.mhagroserver.entities.Product;
import com.mhsoft.mhagroserver.entities.Vendeur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author mh
 */
@Stateless
public class GererProduct extends Gerer {
    
    public void Nouveau (Product product){
        em.persist(product);
    }
    
    public Product Recherche(Product product){
        return em.find(Product.class,product.getId());
    }
    
    public Product RechercheId(Object id) {
        return em.find(Product.class,id);
    }
    
    public Product RechercheId(Long id) {
        return em.find(Product.class,id);
    }
    public void Supprimer(Product product){
        Product recherche = Recherche(product);
        if (recherche != null)em.remove(recherche);
    }
    
    public void Miseajour(Product product){
        Product recherche = Recherche(product);
        if(recherche!=null) em.merge(product);
    }
    public List<Product> liste(int depart) {
         Query requête = em.createNamedQuery("Product.findAll");
         requête.setMaxResults(10);
         requête.setFirstResult(depart);
         return requête.getResultList();
    }
    public List<Product> listeAll() {
         Query requête = em.createNamedQuery("Product.findAll");
         return requête.getResultList();
    }
    
    public List<Product> listeAll(int startingFrom,int pageSize) {
         Query requête = em.createNamedQuery("Product.findAll");
         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
       
    public long nombre() {
        Query requête = em.createNamedQuery("Product.nombre");
        return (Long) requête.getSingleResult();
    }
    
     public List<Product> listeProductParVendeur(int startingFrom,int pageSize,String vendeur) {
         Query requête = em.createNamedQuery("Product.findByVendeur");
         requête.setParameter("vendeur",vendeur);
         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
    
    public long nombreProductParVendeur(String vendeur) {
        Query requête = em.createNamedQuery("Product.nombreByVendeur");
        requête.setParameter("vendeur",vendeur);
        return (Long) requête.getSingleResult();
    }
    
    public List<Product> listeProductParVendeurId(int startingFrom,int pageSize,Long id) {
         Query requête = em.createNamedQuery("Product.findByVendeurId");
         requête.setParameter("vendeurid",id);
         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
    
    public long nombreProductParVendeurId(Long id) {
        Query requête = em.createNamedQuery("Product.nombreByVendeurId");
        requête.setParameter("vendeurid",id);
        return (Long) requête.getSingleResult();
    }
    
    public List<Product> listeProductPublierVendeurActif(int startingFrom,int pageSize,String publier,boolean etat_abonnement) {
         Query requête = em.createNamedQuery("Product.findProductByProductPublierVendeurActif");
         requête.setParameter("publier",publier);
         requête.setParameter("etat_abonnement",etat_abonnement);
         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
    
    public long nombreProductPublierVendeurActif(String publier,boolean etat_abonnement) {
        Query requête = em.createNamedQuery("Product.nombreByProductPublierVendeurActif");
        requête.setParameter("publier",publier);
        requête.setParameter("etat_abonnement",etat_abonnement);
        return (Long) requête.getSingleResult();
    }
    
   public List<Product> listeProductCategoriePublierVendeurActif(int startingFrom,int pageSize,String publier,boolean etat_abonnement,String categorie) {
         Query requête = em.createNamedQuery("Product.findProductByCategorieProductPublierVendeurActif");
         requête.setParameter("publier",publier);
         requête.setParameter("etat_abonnement",etat_abonnement);
         requête.setParameter("categorie",categorie);
         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
    
    public long nombreProductCategoriePublierVendeurActif(String publier,boolean etat_abonnement,String categorie) {
        Query requête = em.createNamedQuery("Product.nombreProductByCategorieProductPublierVendeurActif");
        requête.setParameter("publier",publier);
        requête.setParameter("etat_abonnement",etat_abonnement);
        requête.setParameter("categorie",categorie);
        return (Long) requête.getSingleResult();
    } 
   
    public List<Product> listeProductCategorieVille(int startingFrom,int pageSize,String publier,boolean etat_abonnement,String categorie,String ville) {
         Query requête = em.createNamedQuery("Product.findProductByCategorieVille");
         requête.setParameter("publier",publier);
         requête.setParameter("etat_abonnement",etat_abonnement);
         requête.setParameter("categorie",categorie);
         requête.setParameter("ville",ville);

         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
    
    public long nombreProductCategorieVille(String publier,boolean etat_abonnement,String categorie,String ville) {
        Query requête = em.createNamedQuery("Product.nombreProductByCategorieVille");
        requête.setParameter("publier",publier);
        requête.setParameter("etat_abonnement",etat_abonnement);
        requête.setParameter("categorie",categorie);
        requête.setParameter("ville",ville);
        return (Long) requête.getSingleResult();
    } 
    
    public List<Product> listeProductIntitule(int startingFrom,int pageSize,String publier,boolean etat_abonnement,String intitule) {
         Query requête = em.createNamedQuery("Product.findProductIntitule");
         requête.setParameter("publier",publier);
         requête.setParameter("etat_abonnement",etat_abonnement);
         requête.setParameter("intitule",intitule+"%");

         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
    
    public long nombreProductIntitule(String publier,boolean etat_abonnement,String intitule) {
        Query requête = em.createNamedQuery("Product.nombreProductIntitule");
        requête.setParameter("publier",publier);
        requête.setParameter("etat_abonnement",etat_abonnement);
        requête.setParameter("intitule",intitule+"%");
        return (Long) requête.getSingleResult();
    } 
    
    public List<Product> listeProductVille(int startingFrom,int pageSize,String publier,boolean etat_abonnement,String ville) {
         Query requête = em.createNamedQuery("Product.findProductVille");
         requête.setParameter("publier",publier);
         requête.setParameter("etat_abonnement",etat_abonnement);
         requête.setParameter("ville",ville);

         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
    
    public long nombreProductVille(String publier,boolean etat_abonnement,String ville) {
        Query requête = em.createNamedQuery("Product.nombreProductVille");
        requête.setParameter("publier",publier);
        requête.setParameter("etat_abonnement",etat_abonnement);
        requête.setParameter("ville",ville);
        return (Long) requête.getSingleResult();
    } 
}
