package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.MySession;
import haw.Ausleihe.warenkorb;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import Database.Entities.Artikel;
import Models.ArtikelModel;

public class ActionPanelCartLoeschen extends Panel {
	private static final long serialVersionUID = 1L;

	public ActionPanelCartLoeschen(String id,IModel<ArtikelModel> artikelModel) {
		super(id,artikelModel);

		add(new Link<Object>(id){
			private static final long serialVersionUID = 1L;
			@Override
            public void onClick(){
            	Artikel artikel = (Artikel) ActionPanelCartLoeschen.this.getDefaultModelObject();
            	
            	MySession.get().removeFromCart(artikel);

            	this.setResponsePage(warenkorb.class);
            }
        });
	}

}
