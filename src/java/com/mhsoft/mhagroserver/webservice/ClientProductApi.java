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
@Path("clientproductapi")
@Stateless
public class ClientProductApi {
    
   @EJB
   private GererProduct gererProduct;
   
   @Context
   UriInfo info ;
   
   
   
   @GET
   @Path("test")
   @Produces(MediaType.TEXT_HTML)
   public String test(){
       return "<center><b>MHAgro client product api.</b></center>";
   }
   
   
   //Tout les produits
//   http://localhost:8080/MHAgroServer/api/clientproductapi/0/10/Oui
   @GET
   @Path("{startingFrom}/{pageSize}/{publier}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getProduitPublierVendeurActif(@PathParam("startingFrom") int startingFrom,@PathParam("pageSize")int pageSize,@PathParam("publier")String publier){
        try{            
            JSONObject response = new JSONObject();
            long totalRecord = gererProduct.nombreProductPublierVendeurActif(publier, true);            
            List<Product> list = new ArrayList();
            list = gererProduct.listeProductPublierVendeurActif(startingFrom, pageSize, publier, true);            
            JSONArray products = getProduits(list);
            response.put("products",products);
            response.put("totalRecord",totalRecord);
            response.put("pageSize",pageSize);
            double pages = (double)totalRecord / (double) pageSize ;
            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize ;
            response.put("noOfPages",Math.ceil(pages));
            response.put("pageRatio",(int)Math.floor(pageOn) + "/" + (int)Math.ceil(pages));
            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "clientproductapi/" ;
            if(totalRecord > (startingFrom + pageSize - 1)){
                response.put("nextPage",baseUri + (startingFrom + pageSize) + "/" + pageSize + "/"+publier);
            }else{
                response.put("nextPage","nil");
            }
            response.put("currentPage",info.getAbsolutePath().toString());
            if(startingFrom <= 1){
                response.put("prevPage","nil");
            }else if (startingFrom - pageSize <= 0){
                response.put("prevPage",baseUri + "1/" + pageSize + "/"+publier);
            }else {
                response.put("prevPage",baseUri + (startingFrom - pageSize)+ "/" + pageSize + "/"+publier);
            }
            return Response.ok(response.toString()).build();
        }catch(Exception e){
                   System.out.println(e);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
   
    //Recherche par categorie
   // http://localhost:8080/MHAgroServer/api/clientproductapi/parcategorie/0/10/Oui/Legumes
   @GET
   @Path("parcategorie/{startingFrom}/{pageSize}/{publier}/{categorie}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response rechercheparCategorieProduitPublierVendeurActif(@PathParam("startingFrom") int startingFrom,@PathParam("pageSize")int pageSize,@PathParam("publier")String publier,@PathParam("categorie")String categorie){
        try{            
            JSONObject response = new JSONObject();
            long totalRecord = gererProduct.nombreProductCategoriePublierVendeurActif(publier, true, categorie);            
            List<Product> list = new ArrayList();
            list = gererProduct.listeProductCategoriePublierVendeurActif(startingFrom, pageSize, publier, true, categorie);            
            JSONArray products = getProduits(list);
            response.put("products",products);
            response.put("totalRecord",totalRecord);
            response.put("pageSize",pageSize);
            double pages = (double)totalRecord / (double) pageSize ;
            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize ;
            response.put("noOfPages",Math.ceil(pages));
            response.put("pageRatio",(int)Math.floor(pageOn) + "/" + (int)Math.ceil(pages));
            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "clientproductapi/parcategorie/" ;
            if(totalRecord > (startingFrom + pageSize - 1)){
                response.put("nextPage",baseUri + (startingFrom + pageSize) + "/" + pageSize + "/"+publier + "/"+categorie);
            }else{
                response.put("nextPage","nil");
            }
            response.put("currentPage",info.getAbsolutePath().toString());
            if(startingFrom <= 1){
                response.put("prevPage","nil");
            }else if (startingFrom - pageSize <= 0){
                response.put("prevPage",baseUri + "1/" + pageSize + "/"+publier + "/"+categorie);
            }else {
                response.put("prevPage",baseUri + (startingFrom - pageSize)+ "/" + pageSize + "/"+publier + "/"+categorie);
            }
            return Response.ok(response.toString()).build();
        }catch(Exception e){
                   System.out.println(e);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
   
   //recherche par categorie ville
//   http://localhost:8080/MHAgroServer/api/clientproductapi/parcategorieville/0/10/Oui/Fruits/Banyo
   @GET
   @Path("parcategorieville/{startingFrom}/{pageSize}/{publier}/{categorie}/{ville}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response rechercheparCategorieVille(@PathParam("startingFrom") int startingFrom,
                                              @PathParam("pageSize")int pageSize,
                                              @PathParam("publier")String publier,
                                              @PathParam("categorie")String categorie,
                                              @PathParam("ville")String ville){
        
       try{            
            JSONObject response = new JSONObject();
            long totalRecord = gererProduct.nombreProductCategorieVille(publier,true,categorie,ville);            
            List<Product> list = new ArrayList();
            list = gererProduct.listeProductCategorieVille(startingFrom, pageSize, publier,true,categorie,ville);            
            JSONArray products = getProduits(list);
            response.put("products",products);
            response.put("totalRecord",totalRecord);
            response.put("pageSize",pageSize);
            double pages = (double)totalRecord / (double) pageSize ;
            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize ;
            response.put("noOfPages",Math.ceil(pages));
            response.put("pageRatio",(int)Math.floor(pageOn) + "/" + (int)Math.ceil(pages));
            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "clientproductapi/parcategorieville/" ;
            if(totalRecord > (startingFrom + pageSize - 1)){
                response.put("nextPage",baseUri + (startingFrom + pageSize) + "/" + pageSize + "/"+publier + "/"+categorie + "/"+ville);
            }else{
                response.put("nextPage","nil");
            }
            response.put("currentPage",info.getAbsolutePath().toString());
            if(startingFrom <= 1){
                response.put("prevPage","nil");
            }else if (startingFrom - pageSize <= 0){
                response.put("prevPage",baseUri + "1/" + pageSize + "/"+publier + "/"+categorie + "/"+ville);
            }else {
                response.put("prevPage",baseUri + (startingFrom - pageSize)+ "/" + pageSize + "/"+publier + "/"+categorie + "/"+ville);
            }
            return Response.ok(response.toString()).build();
        }catch(Exception e){
                   System.out.println(e);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
   
   
//   recherche par intitule produit
//   http://localhost:8080/MHAgroServer/api/clientproductapi/parintitule/0/10/Oui/anana/
   @GET
   @Path("parintitule/{startingFrom}/{pageSize}/{publier}/{intitule}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response rechercheIntile(@PathParam("startingFrom") int startingFrom,@PathParam("pageSize")int pageSize,@PathParam("publier")String publier,@PathParam("intitule")String intitule){
        try{            
            JSONObject response = new JSONObject();
            long totalRecord = gererProduct.nombreProductIntitule(publier, true, intitule);            
            List<Product> list = new ArrayList();
            list = gererProduct.listeProductIntitule(startingFrom, pageSize, publier, true, intitule);            
            JSONArray products = getProduits(list);
            response.put("products",products);
            response.put("totalRecord",totalRecord);
            response.put("pageSize",pageSize);
            double pages = (double)totalRecord / (double) pageSize ;
            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize ;
            response.put("noOfPages",Math.ceil(pages));
            response.put("pageRatio",(int)Math.floor(pageOn) + "/" + (int)Math.ceil(pages));
            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "clientproductapi/parintitule/" ;
            if(totalRecord > (startingFrom + pageSize - 1)){
                response.put("nextPage",baseUri + (startingFrom + pageSize) + "/" + pageSize + "/"+publier + "/"+intitule);
            }else{
                response.put("nextPage","nil");
            }
            response.put("currentPage",info.getAbsolutePath().toString());
            if(startingFrom <= 1){
                response.put("prevPage","nil");
            }else if (startingFrom - pageSize <= 0){
                response.put("prevPage",baseUri + "1/" + pageSize + "/"+publier + "/"+intitule);
            }else {
                response.put("prevPage",baseUri + (startingFrom - pageSize)+ "/" + pageSize + "/"+publier + "/"+intitule);
            }
            return Response.ok(response.toString()).build();
        }catch(Exception e){
                   System.out.println(e);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

//   recherche par ville vendeur
//   http://localhost:8080/MHAgroServer/api/clientproductapi/parintitule/0/10/Oui/banyo/
   @GET
   @Path("parville/{startingFrom}/{pageSize}/{publier}/{ville}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response rechercheVille(@PathParam("startingFrom") int startingFrom,@PathParam("pageSize")int pageSize,@PathParam("publier")String publier,@PathParam("ville")String ville){
        try{            
            JSONObject response = new JSONObject();
            long totalRecord = gererProduct.nombreProductVille(publier, true, ville);            
            List<Product> list = new ArrayList();
            list = gererProduct.listeProductVille(startingFrom, pageSize, publier, true, ville);            
            JSONArray products = getProduits(list);
            response.put("products",products);
            response.put("totalRecord",totalRecord);
            response.put("pageSize",pageSize);
            double pages = (double)totalRecord / (double) pageSize ;
            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize ;
            response.put("noOfPages",Math.ceil(pages));
            response.put("pageRatio",(int)Math.floor(pageOn) + "/" + (int)Math.ceil(pages));
            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "clientproductapi/parville/" ;
            if(totalRecord > (startingFrom + pageSize - 1)){
                response.put("nextPage",baseUri + (startingFrom + pageSize) + "/" + pageSize + "/"+publier + "/"+ville);
            }else{
                response.put("nextPage","nil");
            }
            response.put("currentPage",info.getAbsolutePath().toString());
            if(startingFrom <= 1){
                response.put("prevPage","nil");
            }else if (startingFrom - pageSize <= 0){
                response.put("prevPage",baseUri + "1/" + pageSize + "/"+publier + "/"+ville);
            }else {
                response.put("prevPage",baseUri + (startingFrom - pageSize)+ "/" + pageSize + "/"+publier + "/"+ville);
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
            product.put("pvendeurid",""+p.getVendeur().getId());
            product.put("pvendeurintitule",p.getVendeur().getIntitule());
            product.put("pvendeurtelephone",p.getVendeur().getTelephone());
            product.put("pvendeurpays",p.getVendeur().getPays());
            product.put("pvendeurregion",p.getVendeur().getRegion());
            product.put("pvendeurdepartement",p.getVendeur().getDepartement());
            product.put("pvendeurville",p.getVendeur().getVille());
            productArray.put(product);
        }
        return productArray ;
    } 
}
