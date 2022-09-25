/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.webservice;

import com.mhsoft.mhagroserver.entities.Vendeur;
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
@Path("vendeurregistrationapi")
@Stateless
public class VendeurRegistrationApi {

    @EJB
    private GererVendeur gererVendeur;
 
   @POST
   @Path("registration")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response enregisterVendeur(JsonObject jo){
       JSONObject response = new JSONObject();
       try{
               String intitule = (String)jo.getString("intitule");
               String telephone =(String)jo.getString("telephone");
               String motdepasse = (String)jo.getString("motdepasse");
               String pays = (String)jo.getString("pays");
               String region = (String)jo.getString("region");
               String departement = (String)jo.getString("departement");
               String ville = (String)jo.getString("ville");
               if(gererVendeur.rechercheparTelephone(telephone)>0){
                   response.put("etat","exist");
                   response.put("description","Ce numero de telephone existe deja. Veuillez en choisir un autre.");
               }else{
                   Date currentTime = Calendar.getInstance().getTime();
                   Vendeur vendeur = new Vendeur();
                   vendeur.setIntitule(intitule);
                   vendeur.setTelephone(telephone);
                   vendeur.setPassword(MD5Util.generateMD5(motdepasse));
                   vendeur.setPays(pays);
                   vendeur.setRegion(region);
                   vendeur.setDepartement(departement);
                   vendeur.setVille(ville);
                   vendeur.setEtat_abonnement(true);
                   vendeur.setDate_creation(currentTime);
                   vendeur.setDate_derniere_miseajour(currentTime);
                   gererVendeur.Nouveau(vendeur);
                   response.put("etat","succes");
                   response.put("description","Votre compte a ete cree avec succes");

               }
           }catch(Exception e){
               response.put("code","erreur");
               response.put("description","Une erreur s'est produite");
        }
      
       return Response.ok(response.toString()).build();
    
       
   }
   
   
    
}
