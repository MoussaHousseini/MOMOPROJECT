/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.gerer;
import com.mhsoft.mhagroserver.entities.Commande;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author mh
 */
@Stateless
public class GererCommande extends Gerer {
    
    public void Nouveau (Commande commande){
        em.persist(commande);
    }
    
    public Commande Recherche(Commande commande){
        return em.find(Commande.class,commande.getId());
    }
    
    public Commande RechercheId(Object id) {
        return em.find(Commande.class,id);
    }
    
    public Commande RechercheId(Long id) {
        return em.find(Commande.class,id);
    }
    public void Supprimer(Commande commande){
        Commande recherche = Recherche(commande);
        if (recherche != null)em.remove(recherche);
    }
    
    public void Miseajour(Commande commande){
        Commande recherche = Recherche(commande);
        if(recherche!=null) em.merge(commande);
    }
    public List<Commande> liste(int depart) {
         Query requête = em.createNamedQuery("Commande.findAll");
         requête.setMaxResults(10);
         requête.setFirstResult(depart);
         return requête.getResultList();
    }
    public List<Commande> listeAll() {
         Query requête = em.createNamedQuery("Commande.findAll");
         return requête.getResultList();
    }
    
    public List<Commande> listeAll(int startingFrom,int pageSize) {
         Query requête = em.createNamedQuery("Commande.findAll");
         requête.setMaxResults(pageSize);
         requête.setFirstResult(startingFrom);
         return requête.getResultList();
     }
       
    public long nombre() {
        Query requête = em.createNamedQuery("Commande.nombre");
        return (Long) requête.getSingleResult();
    }
    
   
}
