package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.HomePage;
import haw.Ausleihe.MySession;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import Database.Entities.Artikel;
import Models.ArtikelModel;

public class ActionPanelLeihe extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelLeihe(String id, IModel<ArtikelModel> type) {
		super(id, type);
		

		add(new Link<Object>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				Artikel artikel = (Artikel) ActionPanelLeihe.this.getDefaultModelObject();
				//System.out.println("obj: " + artikel);
				
				MySession.get().setArtikelID(artikel);
				//System.out.println(artikel.getId());
				
				this.setResponsePage(HomePage.class);
			}
		});
	}
	
	
}
