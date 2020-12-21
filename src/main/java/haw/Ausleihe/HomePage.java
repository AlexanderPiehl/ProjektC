package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelArtikelHinzufuegen;
import haw.Ausleihe.ActionPanels.ActionPanelEditArtikel;
import haw.Ausleihe.ActionPanels.ActionPanelHistorie;
import haw.Ausleihe.ActionPanels.ActionPanelLeihe;
import haw.Ausleihe.ActionPanels.ActionPanelWartung;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Models.ArtikelModel;
import Models.ArtikelModel.ArtikelModelType;
import Tools.Access;
import dao.ArtikelDao;
import dao.ReservierungDao;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();
	private static long resId = 0;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		Label inCartHeadline = new Label("inCartHeadline", "In den Warenkorb");
		add(inCartHeadline);
		Label editArtikelHeadline = new Label("editArtikelHeadline", "Artikel Bearbeiten");
		add(editArtikelHeadline);
		Label wartungHeadline = new Label("wartungHeadline", "Wartung");
		add(wartungHeadline);
		Label historieHeadline = new Label("historieHeadline", "Verleihhistorie");
		add(historieHeadline);
		final Label addArtikel = new Label("addArtikel", "Artikel hinzufügen");
		add(addArtikel);
		
		if(access.isLoggedin()){
        	if(!access.hasAccessTo("lending")){
        		inCartHeadline.setVisible(false);
        	}
        	if(!access.hasAccessTo("article_edit")){
        		editArtikelHeadline.setVisibilityAllowed(false);
        		wartungHeadline.setVisibilityAllowed(false);
    			
        	}
		}else{
			inCartHeadline.setVisibilityAllowed(false);
			editArtikelHeadline.setVisibilityAllowed(false);
			wartungHeadline.setVisibilityAllowed(false);
			historieHeadline.setVisibilityAllowed(false);
		}
		
		ArtikelDao artikelDao = new ArtikelDao();
		List<Artikel> artikel = new ArrayList<Artikel>();
		
		if(MySession.get().isWirdGesucht()){
			artikel = MySession.get().getListSuche();
			MySession.get().setWirdGesucht(false);
		}else if(MySession.get().getReservierungsId() != null){
			resId = MySession.get().getReservierungsId();
			MySession.get().setReservierungsId(0l);
			if(resId > 0){
				ReservierungDao reservierungsDao = new ReservierungDao();
				Reservierung res = reservierungsDao.findById(resId);
				if(res != null){
					artikel = artikelDao.getFreeArtikel(res.getReservedFrom(), res.getReservedTo());
				}
			}else{
				artikel = artikelDao.getAll();
			}		
		} else {
			artikel = artikelDao.getAll();
		}
				
		ListDataProvider<Artikel> listDtaProvider = new ListDataProvider<Artikel>(artikel);

		 DataView<Artikel> dataView = new DataView<Artikel>("rows", listDtaProvider) {
			private static final long serialVersionUID = 1L;
			
			@Override  
			protected void populateItem(Item<Artikel> item) {
				Artikel artikel = item.getModelObject();
	            item.add(new Label("Hersteller",artikel.getHersteller()));
	            item.add(new Label("Art",artikel.getArt()));
	            item.add(new Label("Typ",artikel.getTyp()));
	            item.add(new Label("Kategorie",artikel.getKategorie()));
	            item.add(new Label("Verzeichnisbaum",artikel.getVerzeichnisbaum()));
	            item.add(new Label("Kaufdatum",artikel.getKaufdatum()));
	            item.add(new Label("Menge",artikel.getMenge()));
	            
	            item.add(new Label("name",artikel.getName()));
	            item.add(new Label("preis",artikel.getPreis()));
	            item.add(new Label("lieferant",artikel.getLieferant()));
	            item.add(new Label("volNr",artikel.getVolNr()));
	            item.add(new Label("bemerkung",artikel.getBemerkungen()));
	            item.add(new Label("zustand",artikel.getZustand()));
	            item.add(new Label("eltLeistung",artikel.getElektrischeLeistung()));
	            item.add(new Label("packmass",artikel.getPackmass()));
	            item.add(new Label("gewicht",artikel.getGewicht()));
	            item.add(new Label("leuchtmitteltyp",artikel.getLeuchtmitteltyp()));
	            item.add(new Label("akkutyp",artikel.getAkkutyp()));
	            item.add(new Label("batterietyp",artikel.getBatterientyp()));
	            item.add(new Label("batterienAnzahl",artikel.getBatterienAnzahl()));
	            item.add(new Label("speicherMedium",artikel.getSpeichermedium()));
	            item.add(new Label("serienNummer",artikel.getSeriennummer()));
	            item.add(new Label("verpackung",artikel.getVerpackung()));
	            
	            //alle anderen informationen die sie sehen wollen
	            ActionPanelLeihe panelLeiheArtikel = new ActionPanelLeihe ("Warenkorb",new ArtikelModel(new Model<Artikel>(item.getModelObject()), ArtikelModelType.ARTIKEL));
	            item.add(panelLeiheArtikel);	            
	            ActionPanelEditArtikel panelEditArtikel = new ActionPanelEditArtikel("editArtikel",new ArtikelModel(new Model<Artikel>(item.getModelObject()), ArtikelModelType.ARTIKEL));
	            item.add(panelEditArtikel);  
	            ActionPanelWartung panelWartung = new ActionPanelWartung("toWartung", new ArtikelModel(new Model<Artikel>(item.getModelObject()),ArtikelModelType.ARTIKEL));
	            item.add(panelWartung);            
	            ActionPanelHistorie panelHistorie = new ActionPanelHistorie("historie", false, artikel.getId());
	            item.add(panelHistorie);
	            
	            ActionPanelArtikelHinzufuegen panelArtikelAdd= new ActionPanelArtikelHinzufuegen("addArtikel", new ArtikelModel(new Model<Artikel>(item.getModelObject()), ArtikelModelType.ARTIKEL), resId);
	            item.add(panelArtikelAdd);
	            
	            if(access.isLoggedin()){            	
	            	if(!access.hasAccessTo("lending")){
	            		panelLeiheArtikel.setVisible(false);
	            	}
	            	if(!access.hasAccessTo("article_edit")){
	            		panelEditArtikel.setVisible(false);
	            		panelWartung.setVisible(false);
	            	
	            	}    			
	    		}else{
	    			panelEditArtikel.setVisible(false);
	    			panelWartung.setVisible(false);
	    			panelLeiheArtikel.setVisible(false);
	    			panelHistorie.setVisible(false);
	    		}
	            if(resId == 0l){
	            	panelArtikelAdd.setVisible(false);
	            	addArtikel.setVisible(false);
	            }	            
			}
	   };
	        dataView.setItemsPerPage(15);
	        add(dataView);
	      	 
	       add(new PagingNavigator("navigator", dataView));
    }	
}