/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.webservice;

import com.mhsoft.mhagroserver.entities.Vendeur;
import com.mhsoft.mhagroserver.gerer.GererVendeur;
import com.mhsoft.mhagroserver.util.MD5Util;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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
@Path("vendeurloginapi")
@Stateless
public class VendeurLoginApi {
    
   @EJB
   private GererVendeur gererVendeur;
 
   @POST
   @Path("login")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response loginVendeur(JsonObject jo){
       JSONObject response = new JSONObject();
       try{
            String telephone =(String)jo.getString("telephone");
            String motdepasse = (String)jo.getString("motdepasse");
            Query query = gererVendeur.rechercheparTelephoneMotdepasse();
            query.setParameter("telephone",telephone);
            query.setParameter("password",MD5Util.generateMD5(motdepasse) );
            
        try{
               Vendeur vendeur = (Vendeur)query.getSingleResult();
               if(vendeur.getEtat_abonnement()){
                   response.put("etat","succes");
                   response.put("description","loger avec succes");
                   response.put("id",""+vendeur.getId());
                   response.put("intitule",vendeur.getIntitule());
                   response.put("telephone",vendeur.getTelephone());
                   response.put("pays",vendeur.getPays());
                   response.put("region",vendeur.getRegion());
                   response.put("departement",vendeur.getDepartement());
                   response.put("ville",vendeur.getVille()); 
               }else{
                   response.put("etat","desactive");
                   response.put("description","Votre compte a ete desactive");
                   
               }
               
               
            }catch (NoResultException e){
                 String loginError = "Verifier votre numero de telephone ou votre mot de passe";
                 response.put("etat","loginError");
                 response.put("description",loginError);
        }}catch(Exception e){
               response.put("etat","erreur");
               response.put("description","Une erreur s'est produite");
        }
               
       return Response.ok(response.toString()).build();
    
       
   }
    
}
