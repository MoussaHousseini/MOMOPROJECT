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
public class VendeurProductInfiniteContainer {
    private static final String WEBSERVICE_URL =  "MHAgroServer/api/vendeurproductapi/0/10";

   public Container getProduct(String ip ,String vendeurID,EncodedImage placeholder,Vendeur vendeur){
        InfiniteContainer ic = new InfiniteContainer(10) {
            List items;
            String nextURL =ip+WEBSERVICE_URL+"/"+vendeurID;            
            
            @Override
            public Component[] fetchComponents(int index, int amount) {
                // pull to refresh resets the position
                if(index == 0) {
                    nextURL = ip+WEBSERVICE_URL+"/"+vendeurID;
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
                
                
                Component[] result = new Component[items.size()];
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

                    VendeurProduct vendeurproduct = new VendeurProduct(productId.longValue(),titre_image_produit,intitule,prix_unitaire,unite_de_vente,categorie,quantite_stock_produit,description,publier);
                    VendeurProductItemRender vendeurproductitemRender = new VendeurProductItemRender();
                    result[iter] =  vendeurproductitemRender.renderItem(ip,placeholder,vendeurproduct,vendeur) ;
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
                return result;
            }
        };  
        return ic;
    }
    
}
