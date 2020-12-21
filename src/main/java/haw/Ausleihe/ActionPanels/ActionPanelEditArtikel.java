package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.produktEdit;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.Artikel;
import Models.ArtikelModel;

public class ActionPanelEditArtikel extends Panel {
	private static final long serialVersionUID = 1L;

	public ActionPanelEditArtikel(String id,IModel<ArtikelModel> artikelModel) {
		super(id,artikelModel);
		
		add(new Link<Object>(id){
			private static final long serialVersionUID = 1L;
			@Override
            public void onClick(){
            	Artikel artikel = (Artikel) ActionPanelEditArtikel.this.getDefaultModelObject();

            	PageParameters pageParameters = new PageParameters();
            	pageParameters.add("artikelID", artikel.getId());

            	this.setResponsePage(produktEdit.class, pageParameters);
            }
        });
	}

}
