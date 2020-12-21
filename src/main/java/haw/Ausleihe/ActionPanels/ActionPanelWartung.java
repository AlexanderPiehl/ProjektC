package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.WartungHP;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.Artikel;
import Models.ArtikelModel;

public class ActionPanelWartung extends Panel {
	private static final long serialVersionUID = 1L;

	public ActionPanelWartung(String id,IModel<ArtikelModel> artikelModel) {
		super(id,artikelModel);
		
		add(new Link<Object>(id){
			private static final long serialVersionUID = 1L;
			@Override
            public void onClick(){
            	Artikel artikel = (Artikel) ActionPanelWartung.this.getDefaultModelObject();

            	PageParameters pageParameters = new PageParameters();
            	pageParameters.add("artikelID", artikel.getId());

            	this.setResponsePage(WartungHP.class, pageParameters);
            }
        });
	}

}
