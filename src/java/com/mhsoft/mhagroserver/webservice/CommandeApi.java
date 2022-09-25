/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.webservice;

import com.mhsoft.mhagroserver.entities.Commande;
import com.mhsoft.mhagroserver.entities.Vendeur;
import com.mhsoft.mhagroserver.gerer.GererCommande;
import com.mhsoft.mhagroserver.gerer.GererVendeur;
import com.mhsoft.mhagroserver.util.MD5Util;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.primefaces.json.JSONObject;

/**
 *
 * @author MH
 */
@Path("commandeapi")
@Stateless
public class CommandeApi {

    @EJB
    private GererCommande gererCommande;

   @POST
   @Path("commander")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response enregistrerCommande(JsonObject jo){
        
//         System.out.println(jo.toString());

         JSONObject response = new JSONObject();
         String nomprenom = (String)jo.getString("nomprenom");
         String telephone =(String)jo.getString("telephone");
         String adresse =(String)jo.getString("adresse");
         String quantitecommander =(String)jo.getString("quantitecommander");
         
         String pid =(String)jo.getString("id");
         String pintitule =(String)jo.getString("intitule");
         String pprixunitaire =(String)jo.getString("prixunitaire");
         String punitevente =(String)jo.getString("unitevente");
         String pcategorie =(String)jo.getString("categorie");
         String pquantitestock =(String)jo.getString("quantitestockproduit");
         String pdescription =(String)jo.getString("description");
         
         String pvendeurid =(String)jo.getString("pvendeurid");
         String pvendeurintitule =(String)jo.getString("pvendeurintitule");
         String pvendeurtelephone =(String)jo.getString("pvendeurtelephone");
         String pvendeurpays =(String)jo.getString("pvendeurpays");
         String pvendeurregion =(String)jo.getString("pvendeurregion");
         String pvendeurdepartement =(String)jo.getString("pvendeurdepartement");
         String pvendeurville =(String)jo.getString("pvendeurville");         
         
       try{
            Commande commande = new Commande();
           
            commande.setClient_nomprenom(nomprenom);
            commande.setClient_telephone(telephone);
            commande.setClient_adresse(adresse);
            commande.setClient_quantite_commande(quantitecommander);
            
            commande.setProduit_commander_id(pid);
            commande.setProduit_commander_intitule(pintitule);
            commande.setProduit_commander_prix_unitaire(pprixunitaire);
            commande.setProduit_commander_unite_de_vente(punitevente);
            commande.setProduit_commander_categorie(pcategorie);
            commande.setProduit_commander_quantitestock(pquantitestock);
            commande.setProduit_commander_description(pdescription);
            
            commande.setPvendeur_id(pvendeurid);
            commande.setPvendeur_intitule(pvendeurintitule);
            commande.setPvendeur_telephone(pvendeurtelephone);
            commande.setPvendeur_pays(pvendeurpays);
            commande.setPvendeur_region(pvendeurregion);
            commande.setPvendeur_departement(pvendeurdepartement);
            commande.setPvendeur_ville(pvendeurville);
            commande.setEtat_abonnement(Boolean.FALSE);
            
            Date currentTime = Calendar.getInstance().getTime();
            commande.setDate_creation(currentTime);
            commande.setDate_derniere_miseajour(currentTime);
            
             gererCommande.Nouveau(commande);
             response.put("etat","succes");
             response.put("description","Votre commande a ete enregistre avec succes");
           }catch(Exception e){
               response.put("code","erreur");
               response.put("description","Une erreur s'est produite");
        }
       return Response.ok(response.toString()).build();  
   }
   
   
    
}
