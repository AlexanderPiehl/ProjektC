package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.MySession;
import haw.Ausleihe.ReservierungBearbeiten;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import dao.ArtikelDao;
import dao.ReservierungDao;
import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Models.ArtikelModel;

public class ActionPanelArtikelHinzufuegen extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelArtikelHinzufuegen(String id, IModel<ArtikelModel> artikelModel, final Long reservierungsId) {
		super(id, artikelModel);

		add(new Link<Object>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				Artikel artikel = (Artikel) ActionPanelArtikelHinzufuegen.this.getDefaultModelObject();
				
				ReservierungDao reservierungDao = new ReservierungDao();
				Reservierung res = reservierungDao.findReservierungWithArtikelById(reservierungsId);
				
				ArtikelDao artikelDao = new ArtikelDao();
				boolean warning = true;
				if(artikelDao.okToLoan(res.getReservedFrom(), res.getReservedTo(), artikel.getId())){
					List<Artikel> resArtikel = res.getArtikel();
					resArtikel.add(artikel);
					res.setArtikel(resArtikel);
					reservierungDao.edit(res);
					warning = false;
				}
								
				MySession.get().setReservierungsId(0l);
				
				PageParameters pageParameters = new PageParameters();
				pageParameters.add("reservierungID", res.getId());
				pageParameters.add("bearbeiten", true);
				pageParameters.add("warning", warning);
        	
			    this.setResponsePage(ReservierungBearbeiten.class, pageParameters);
			}
		});
	}

}
