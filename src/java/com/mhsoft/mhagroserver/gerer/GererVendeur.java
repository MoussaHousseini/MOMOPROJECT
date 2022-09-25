/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.gerer;

import com.mhsoft.mhagroserver.entities.Vendeur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author mh
 */
@Stateless
public class GererVendeur extends Gerer {
    
    public void Nouveau (Vendeur vendeur){
        em.persist(vendeur);
    }
    
    public Vendeur Recherche(Vendeur vendeur){
        return em.find(Vendeur.class,vendeur.getId());
    }
    
    public Vendeur RechercheId(Object id) {
        return em.find(Vendeur.class,id);
    }
    
    public Vendeur RechercheId(Long id) {
        return em.find(Vendeur.class,id);
    }
    public void Supprimer(Vendeur vendeur){
        Vendeur recherche = Recherche(vendeur);
        if (recherche != null)em.remove(recherche);
    }
    
    public void Miseajour(Vendeur vendeur){
        Vendeur recherche = Recherche(vendeur);
        if(recherche!=null) em.merge(vendeur);
    }
    public List<Vendeur> liste(int depart) {
         Query requête = em.createNamedQuery("Vendeur.findAll");
         requête.setMaxResults(10);
         requête.setFirstResult(depart);
         return requête.getResultList();
    }
    public List<Vendeur> listeAll() {
         Query requête = em.createNamedQuery("Vendeur.findAll");
         return requête.getResultList();
    }
    
    public List<Vendeur> listeAll(int startingFrom,int pageSize) {
         Query requête = em.createNamedQuery("Vendeur.findAll");
         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
       
    public long nombre() {
        Query requête = em.createNamedQuery("Vendeur.nombre");
        return (Long) requête.getSingleResult();
    }
    
   public int rechercheparTelephone(String telephone){
        Query requête = em.createNamedQuery("Vendeur.telephone");
        requête.setParameter("telephone",telephone);
        return requête.getResultList().size();
    }
   
   public Query  rechercheparTelephoneMotdepasse(){
        Query requête = em.createNamedQuery("Vendeur.telephone_password",Vendeur.class);
        return requête ;
    }
}
