package haw.Ausleihe.ActionPanels;

import java.util.List;

import haw.Ausleihe.ReservierungBearbeiten;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import dao.ReservierungDao;
import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Models.ArtikelModel;

public class ActionPanelLoeschenArtikelReservierung extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelLoeschenArtikelReservierung(String id, IModel<ArtikelModel> artikelModel, final Long reservierungID) {
		super(id, artikelModel);
		
		add(new Link<Object>(id){
			private static final long serialVersionUID = 1L;
			@Override
            public void onClick(){
				ReservierungDao resDao = new ReservierungDao();
				Reservierung res = resDao.findReservierungWithArtikelById(reservierungID);
				List<Artikel> resArtikels = res.getArtikel();
            	Artikel artikel = (Artikel) ActionPanelLoeschenArtikelReservierung.this.getDefaultModelObject();
            	
            	for(Artikel a: resArtikels){
            		System.out.println("a.getId: " + a.getId() + " artikel.getId: " + artikel.getId());
            		if(a.getId() == artikel.getId()){
            			System.out.println("Löschen " + artikel.getId());
            			resArtikels.remove(a);
            			break;
            		}
            	}
            	res.setArtikel(resArtikels);
            	resDao.edit(res);
            	
            	PageParameters pageParameters = new PageParameters();
            	pageParameters.add("reservierungID", reservierungID);
            	pageParameters.add("bearbeiten", true);

            	this.setResponsePage(ReservierungBearbeiten.class, pageParameters);
            }
        });

	}
}
