/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagro;

import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Moussa Housseini
 */
public class ClientProductItemRender {
    
    private String productImagePath  ;
    private String action ;

    
    public ClientProductItemRender(){
    }
    
     Component renderItem(String ip,EncodedImage placeholder,ProductCommander productcommander){
         BorderLayout bl = new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
         Container container = new Container(bl);
         container.setUIID("ItemRenderContainer");

         Label name = new Label(productcommander.getIntitule());
         URLImage photo = URLImage.createToStorage(placeholder,productcommander.getTitre_image_produit(),ip+"MHAgroServer/ImageServletResize?id="+productcommander.getId()+"&width="+Display.getInstance().getDisplayWidth()+"&height="+Display.getInstance().getDisplayWidth() / 4 * 3, URLImage.RESIZE_SCALE_TO_FILL);
         Label imagelabel = new Label(photo);
         Label priceLabel = new Label(productcommander.getPrix_unitaire()+"FCFA/"+productcommander.getUnite_de_vente(),"ItemRenderPriceLabel");
         
         Style s = UIManager.getInstance().getComponentStyle("Title");
         
         FontImage editIcon = FontImage.createMaterial(FontImage.MATERIAL_EDIT, s);
         FontImage deleteIcon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, s);
         
//         Button btnEdit = new Button("Modifier",editIcon);
//         btnEdit.setUIID("ItemRenderButton");
//         btnEdit.addActionListener(e -> {
//             
//                  
//                    });
         
//         Button btnDelete = new Button("Supprimer",deleteIcon);
//         btnDelete.setUIID("ItemRenderButton");
//         btnDelete.addActionListener(e -> {
//                    if( Dialog.show("Info", "Voulez-vous supprimer","OK", "Cancel")){
//                        
//                    }
//                    });
           
//         Container southContainer = new Container(new GridLayout(1,2));
   Container southContainer = new Container(new GridLayout(1,1));
   
    Button btnCommande = new Button("Commander");
//         btnCommande.setUIID("ItemRenderButton");
         btnCommande.addActionListener(e -> {
               
             commanderDialog(ip,productcommander,btnCommande);
                  
                    });

//         southContainer.add(btnEdit);
//         southContainer.add(btnDelete);
southContainer.add(btnCommande);
         
        Container box = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Button title = new Button(productcommander.getIntitule());
        title.setUIID("ItemRenderTitleLabel");
//        Label highlights = new Label(product.getTitle());
        TextArea details = new TextArea(productcommander.getDescription());
        details.setUIID("ItemRenderDescriptionLabel");
//        highlights.setUIID("DishBody");
        
        box.addComponent(title);
        box.addComponent(LayeredLayout.encloseIn(imagelabel,BorderLayout.south(priceLabel)));
        
        box.setLeadComponent(title);
        
        title.addActionListener((e) -> {
            if(details.getParent() != null) {
//                box.removeComponent(highlights);
                     box.removeComponent(details);
            } else {
                
//                box.addComponent(highlights);
                box.addComponent(details);

            }
            container.getParent().animateLayout(300);
        });
        
         
         container.add(BorderLayout.CENTER,box);
         container.add(BorderLayout.SOUTH,southContainer);
         return container ; 
     }
     
     public void commanderDialog(String ip,ProductCommander productcommander,Button button){
                Container containe = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Label intituleproduit = new Label(productcommander.getIntitule(),"ProductCreateLabel");
                Label priceLabel = new Label(productcommander.getPrix_unitaire()+"FCFA/"+productcommander.getUnite_de_vente(),"ProductCreateLabel");

                TextField nomprenom = new TextField();
                nomprenom.setHint("Nom et Prenom");
                
                TextField telephone = new TextField();
                telephone.setHint("Telephone");
                telephone.setConstraint(TextArea.PHONENUMBER);


                TextField adresse = new TextField();
                adresse.setHint("Adresse");

                TextField quantite = new TextField();
                quantite.setHint("Quantite a commander");
                

                Button bn = new Button("Enregistrer");
                
                bn.addActionListener(e -> {
                           JSONObject jo = new JSONObject();
                           String nomprenomn = nomprenom.getText();
                           
                        try {
                               jo.put("nomprenom",nomprenom.getText());
                               jo.put("telephone",telephone.getText());
                               jo.put("adresse",adresse.getText());
                               jo.put("quantitecommander",quantite.getText());
                               jo.put("id",""+productcommander.getId());
                               jo.put("intitule",productcommander.getIntitule());
                               jo.put("prixunitaire",productcommander.getPrix_unitaire());
                               jo.put("unitevente",productcommander.getUnite_de_vente());
                               jo.put("categorie",productcommander.getCategorie());
                               jo.put("quantitestockproduit",productcommander.getQuantite_stock_produit());
                               jo.put("description",productcommander.getDescription());
                               jo.put("publier",productcommander.getPublier());
                               jo.put("pvendeurid",productcommander.getPvendeurid());
                               jo.put("pvendeurintitule",productcommander.getPvendeurintitule());
                               jo.put("pvendeurtelephone",productcommander.getPvendeurtelephone());
                               jo.put("pvendeurpays",productcommander.getPvendeurpays());
                               jo.put("pvendeurregion",productcommander.getPvendeurregion());
                               jo.put("pvendeurdepartement",productcommander.getPvendeurdepartement());
                               jo.put("pvendeurville",productcommander.getPvendeurville());

                        
                               MHAgroClient maclient = new MHAgroClient(ip,"commandeapi/commander/");
                               Map map = maclient.registration(jo);
                               
                               String etat = (String) map.get("etat");
                               String description = (String) map.get("description");
                               
                               if(etat.equals("exist")){
                                  Dialog.show("Infos",description,"OK",null);
                                 }
                               if(etat.equals("erreur")){
                                  Dialog.show("Infos",description,"OK",null);
                                 }
                               if(etat.equals("succes")){
                                    Dialog.show("Infos",description,"OK",null);
                                    button.getComponentForm().showBack();
                                 }
                               
                             } catch (JSONException ex) {
                           } catch (IOException ex) {
                           }
                    
                    });
                containe.add(intituleproduit);
                containe.add(priceLabel);
                containe.add(nomprenom);
                containe.add(telephone);
                containe.add(adresse);
                containe.add(quantite);
                containe.add(bn);
                 
//                String pattern = "\\d{9}";
//                Validator validator = new Validator();
//                validator.setShowErrorMessageForFocusedComponent(true);
//                validator.setValidationFailureHighlightMode(Validator.HighlightMode.UIID);
//                validator.setErrorMessageUIID("ProductValidationLabel");
//                  
//                validator.addConstraint(nomprenom,new  LengthConstraint(1,"Ce champ est obligatoire")).
//                addConstraint(telephone,new LengthConstraint(1,"Ce champ est obligatoire")). 
//                addConstraint(telephone,new RegexConstraint(pattern, "Ce numero n'est pas valide")). 
//                addConstraint(quantite,new LengthConstraint(1,"Ce champ est obligatoire"));
//                validator.addSubmitButtons(bn);
                

                Dialog.show("Passer la commande",containe ,new Command("Annuler"));  
     }
}

