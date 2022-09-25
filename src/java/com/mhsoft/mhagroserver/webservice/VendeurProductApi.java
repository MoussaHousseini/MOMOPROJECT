/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.webservice;

import com.mhsoft.mhagroserver.entities.Product;
import com.mhsoft.mhagroserver.gerer.GererProduct;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Moussa Housseini
 */
@Path("vendeurproductapi")
@Stateless
public class VendeurProductApi {
    
   @EJB
   private GererProduct gererProduct;
   
   @Context
   UriInfo info ;
   
   
   
   @GET
   @Path("test")
   @Produces(MediaType.TEXT_PLAIN)
   public String test(){
       return "gdgdgdgd";
   }
   
   
   
   
   @GET
   @Path("{startingFrom}/{pageSize}/{vendeurID}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getProduitBySousCategories(@PathParam("startingFrom") int startingFrom,@PathParam("pageSize")int pageSize,@PathParam("vendeurID")String vendeurID){
        try{            
            JSONObject response = new JSONObject();
            long totalRecord = gererProduct.nombreProductParVendeurId(Long.parseLong(vendeurID));            
            List<Product> list = new ArrayList();
            list = gererProduct.listeProductParVendeurId(startingFrom, pageSize,Long.parseLong(vendeurID));            
            JSONArray products = getProduits(list);
            response.put("products",products);
            response.put("totalRecord",totalRecord);
            response.put("pageSize",pageSize);
            double pages = (double)totalRecord / (double) pageSize ;
            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize ;
            response.put("noOfPages",Math.ceil(pages));
            response.put("pageRatio",(int)Math.floor(pageOn) + "/" + (int)Math.ceil(pages));
            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "vendeurproductapi/" ;
            if(totalRecord > (startingFrom + pageSize - 1)){
                response.put("nextPage",baseUri + (startingFrom + pageSize) + "/" + pageSize + "/"+vendeurID);
            }else{
                response.put("nextPage","nil");
            }
            response.put("currentPage",info.getAbsolutePath().toString());
            if(startingFrom <= 1){
                response.put("prevPage","nil");
            }else if (startingFrom - pageSize <= 0){
                response.put("prevPage",baseUri + "1/" + pageSize + "/"+vendeurID);
            }else {
                response.put("prevPage",baseUri + (startingFrom - pageSize)+ "/" + pageSize + "/"+vendeurID);
            }
            return Response.ok(response.toString()).build();
        }catch(Exception e){
                   System.out.println(e);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
     
   //Generate JSONARRAY BASE ON PRIMEFACE JSON LIBRARY
   public JSONArray getProduits(List<Product> products) throws JSONException{
        JSONObject product ;
        JSONArray productArray = new JSONArray();
        for (Product p : products){
            product = new JSONObject();
            product.put("id",p.getId());
            product.put("titre_image_produit",p.getTitre_image_produit());
            product.put("intitule",p.getIntitule());
            product.put("prix_unitaire",p.getPrix_unitaire());
            product.put("unite_de_vente",p.getUnite_de_vente());
            product.put("categorie",p.getCategorie());
            product.put("quantite_stock_produit",p.getQuantite_stock_produit());
            product.put("description",p.getDescription());
            product.put("dateCreation",p.getDate_creation());
            product.put("dateUpdated",p.getDate_derniere_miseajour());
            product.put("publier",p.getPublier());
            productArray.put(product);
        }
        return productArray ;
    } 
}
