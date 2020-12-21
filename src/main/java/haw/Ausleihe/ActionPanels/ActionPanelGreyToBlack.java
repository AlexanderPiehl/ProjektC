package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.SanktionslisteNeu;
import haw.Ausleihe.GraueListe;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.SanktionsListe;
import Models.SanktionsListeModel;
import dao.SanktionsListeDao;
import dco.SanktionsListeDco;

public class ActionPanelGreyToBlack extends Panel{

	private static final long serialVersionUID = 1L;

	public ActionPanelGreyToBlack(String id, IModel<SanktionsListeModel> sanktionsListeModel) {
		super(id, sanktionsListeModel);
		add(new Link<Object>(id){
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick(){
            	SanktionsListe sanktionsListe = (SanktionsListe) ActionPanelGreyToBlack.this.getDefaultModelObject();
            	System.out.println(sanktionsListe.getUserID().getKennung());
            	PageParameters pageParameters = new PageParameters();
            	pageParameters.add("typ", "black");
            	pageParameters.add("userID", sanktionsListe.getUserID().getId());
            	pageParameters.add("sanktionsListeID", sanktionsListe.getId());
            	
//            	SanktionsListeDco sanktionsListeDco = new SanktionsListeDco();
//            	sanktionsListeDco.userFromGreyToBlack(sanktionsListe);
      	
            	this.setResponsePage(SanktionslisteNeu.class, pageParameters);
            }
        });
	}
}


