/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagro;

import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.Layout;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Moussa Housseini
 */
public class ClientProductInfiniteContainer {
    private static final String WEBSERVICE_URL =  "MHAgroServer/api/clientproductapi";

   public Container getProduct(String ip ,EncodedImage placeholder,String resource){
        InfiniteContainer ic = new InfiniteContainer(10) {
            Double totalRecord ;
            List items;
            String nextURL =ip+WEBSERVICE_URL+"/"+resource;            
            
            @Override
            public Component[] fetchComponents(int index, int amount) {
                // pull to refresh resets the position
                if(index == 0) {
                    nextURL = ip+WEBSERVICE_URL+"/"+resource;
                }
                // downloaded all the content from the webservice
                
                
                if(nextURL.equals("nil")) {
                    return null;
                }
                
                ConnectionRequest req = new ConnectionRequest(nextURL) {
                    @Override
                    protected void readResponse(InputStream input) throws IOException {
                        items = null;
                        JSONParser parser = new JSONParser();
                        Map response = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
                        items = (List)response.get("products");
                        nextURL = (String)response.get("nextPage");  
                        totalRecord = (Double)response.get("totalRecord");
                    }

                    @Override
                    protected void handleException(Exception err) {
                        Log.e(err);
                        Display.getInstance().callSerially(() -> {
                            ToastBar.showErrorMessage("An error occured while connecting to the server: " + err);
                        });
                    }

                    @Override
                    protected void handleErrorResponseCode(int code, String message) {
                        Display.getInstance().callSerially(() -> {
                            ToastBar.showErrorMessage("Error code from the server: " + code + "\n" + message);
                        });
                    }
                    
                };
                req.setPost(false);
                NetworkManager.getInstance().addToQueueAndWait(req);
                
                if(items == null) {
                    return null;
                }
                
                Component[] result ;
                if(totalRecord.equals(0.0)){
                    result = new Component[1];
                    result[0]= new Label("Aucun resultat trouve");
                }else{
                    result = new Component[items.size()];
                for(int iter = 0 ; iter < result.length ; iter++) {
                    Map<String, Object> m = (Map<String, Object>)items.get(iter);
                    
                    String titre_image_produit = (String)m.get("titre_image_produit");
                    Double productId = (Double)m.get("id");
                    String intitule = (String)m.get("intitule");
                    String prix_unitaire = (String)m.get("prix_unitaire");
                    String unite_de_vente = (String)m.get("unite_de_vente");               
                    String categorie = (String)m.get("categorie");
                    String quantite_stock_produit = (String)m.get("quantite_stock_produit");
                    String description = (String)m.get("description");
                    String publier = (String)m.get("publier");
                    String pvendeurid = (String)m.get("pvendeurid");
                    String pvendeurintitule = (String)m.get("pvendeurintitule");
                    String pvendeurtelephone = (String)m.get("pvendeurtelephone");
                    String pvendeurpays = (String)m.get("pvendeurpays");
                    String pvendeurregion = (String)m.get("pvendeurregion");
                    String pvendeurdepartement = (String)m.get("pvendeurdepartement");
                    String pvendeurville = (String)m.get("pvendeurville");
                             
                    ProductCommander productcommander = new ProductCommander(productId.longValue(),titre_image_produit,intitule,prix_unitaire,
                                                                             unite_de_vente,categorie,quantite_stock_produit,description,publier,pvendeurid,
                                                                             pvendeurintitule,pvendeurtelephone,pvendeurpays,pvendeurregion,pvendeurdepartement,
                                                                             pvendeurville);
                    ClientProductItemRender clientproductitemRender = new ClientProductItemRender();
                    result[iter] =  clientproductitemRender.renderItem(ip,placeholder,productcommander) ;
                }
                Layout l = getLayout();
                if(l instanceof GridLayout) {
                    int cmps = getComponentCount() - 1 + result.length;
                    int extra = 0;
                    if(cmps % 3 != 0) {
                        extra = 1;
                    }
                    setLayout(new GridLayout(cmps / 3 + extra, 3));
                }
                    
                }
                
                return result;
            }
        };  
        return ic;
    }
    
}
