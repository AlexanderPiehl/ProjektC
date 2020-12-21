package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelLoeschen;

import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

import Database.Entities.SanktionsListe;
import Models.SanktionsListeModel;
import Models.SanktionsListeModel.SanktionsListeModelType;
import Tools.Access;
import dao.SanktionsListeDao;

public class SchwarzeListe extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();

	public SchwarzeListe(final PageParameters parameters) {
		super(parameters);

		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		List<SanktionsListe> schwarzeListe = sanktionsListeDao.getAllSchwarzeListe();
		
		setTableHeadLineBlackList(schwarzeListe.isEmpty());
		showErrorMessage(schwarzeListe.isEmpty());
		
		ListDataProvider<SanktionsListe> listDtaProvider = new ListDataProvider<SanktionsListe>(schwarzeListe);
		 DataView<SanktionsListe> dataView = new DataView<SanktionsListe>("rows", listDtaProvider) {
	         @Override  
			 protected void populateItem(Item<SanktionsListe> item) {
	                SanktionsListe schwarzeListe = item.getModelObject();
	                item.add(new Label("kennung",schwarzeListe.getUserID().getKennung()));
	                item.add(new Label("vorname",schwarzeListe.getUserID().getFirstName()));
	                item.add(new Label("nachname",schwarzeListe.getUserID().getLastName()));
	                item.add(new MultiLineLabel("begruendung",schwarzeListe.getComments()));
	                ActionPanelLoeschen panelLoeschen = new ActionPanelLoeschen("loeschen",new SanktionsListeModel(new Model<SanktionsListe>(item.getModelObject()),SanktionsListeModelType.SANKTIONSLISTE));
	                item.add(panelLoeschen);
	                if(access.isLoggedin()){
					     if(!access.hasAccessTo("edit_Blacklist") ||!access.hasAccessTo("is_admin") || !access.hasAccessTo("manage_own_lab")){
					    	 panelLoeschen.setVisible(false);
					     }
	                }
	            }
	        };
	        dataView.setItemsPerPage(25);
	        add(dataView);

    }
	
	private void showErrorMessage(boolean isEmpty) {
		Label error = new Label("error",  Model.of("Es befindet sich kein Nutzer auf der schwarzen Liste."));
		if(!isEmpty){
			error.setVisible(false);
		}
		add(error);		
	}
	
	private void setTableHeadLineBlackList (Boolean isEmpty){
		Model<String> kennungModel = Model.of("HAW-Kennung");
		Model<String> vornameMdoel = Model.of("Vorname");
		Model<String> nachnameModel = Model.of("Nachname");
		Model<String> begruendungModel = Model.of("Begruendung");
		Model<String> loeschenMdoel = Model.of("Löschen");
		
		Label kennungHeadBlack = new Label("kennungHeadBlack", kennungModel);
		Label vornameHeadBlack = new Label("vornameHeadBlack", vornameMdoel);
		Label nachnameHeadBlack = new Label("nachnameHeadBlack", nachnameModel);
		Label begruendungHeadBlack = new Label("begruendungHeadBlack",begruendungModel);
		Label loeschenHeadBlack = new Label("loeschenHeadBlack", loeschenMdoel);
		
		if(isEmpty){
			kennungHeadBlack.setVisible(false);
			vornameHeadBlack.setVisible(false);
			nachnameHeadBlack.setVisible(false);
			begruendungHeadBlack.setVisible(false);
			loeschenHeadBlack.setVisible(false);
		}else if(!access.hasAccessTo("edit_Blacklist") ||!access.hasAccessTo("is_admin") || !access.hasAccessTo("manage_own_lab")){
			loeschenHeadBlack.setVisible(false);
	     }
		add(kennungHeadBlack);
		add(vornameHeadBlack);
		add(nachnameHeadBlack);
		add(begruendungHeadBlack);
		add(loeschenHeadBlack);
	}
}
