package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelGreyToBlack;
import haw.Ausleihe.ActionPanels.ActionPanelLoeschen;
import haw.Ausleihe.ActionPanels.ActionPanelToGrey;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Models.SanktionsListeModel;
import Models.SanktionsListeModel.SanktionsListeModelType;
import Models.UserModel;
import Models.UserModel.UserModelType;
import Tools.Access;
import dao.BasicDao;
import dao.SanktionsListeDao;

public class GraueListe extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();
	private static final String KENNUNG_PARAMETER = "kennung";
	private static final String VORNAME_PARAMETER = "vorname";
	private static final String NACHNAME_PARAMETER = "nachname";

	public GraueListe(final PageParameters parameters) {
		super(parameters);
					
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		final SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		List<SanktionsListe> graueListe = sanktionsListeDao.getAllGraueListe();
		
		setTableHeadLineGreyList(graueListe.isEmpty());
		showErrorMessage(graueListe.isEmpty());
		 
		ListDataProvider<SanktionsListe> listDtaProvider = new ListDataProvider<SanktionsListe>(graueListe);
		 DataView<SanktionsListe> dataView = new DataView<SanktionsListe>("rows", listDtaProvider) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override  
			 protected void populateItem(Item<SanktionsListe> item) {
	                SanktionsListe graueListe = item.getModelObject();
	                item.add(new Label("kennung",graueListe.getUserID().getKennung()));
	                item.add(new Label("vorname",graueListe.getUserID().getFirstName()));
	                item.add(new Label("nachname",graueListe.getUserID().getLastName()));
	                item.add(new MultiLineLabel("begruendung",graueListe.getComments()));
	                ActionPanelLoeschen panelLoescheGrey = new ActionPanelLoeschen("loeschen",new SanktionsListeModel(new Model<SanktionsListe>(item.getModelObject()),SanktionsListeModelType.SANKTIONSLISTE));
	                item.add(panelLoescheGrey);
	                ActionPanelGreyToBlack panelGreytoBlack = new ActionPanelGreyToBlack("toBlack", new SanktionsListeModel(new Model<SanktionsListe>(item.getModelObject()),SanktionsListeModelType.SANKTIONSLISTE));
	                item.add(panelGreytoBlack);
	                
	                if(access.isLoggedin()){
					     if(!access.hasAccessTo("edit_Greylist")){
					    	 panelLoescheGrey.setVisible(false);
					     }
					     if(!access.hasAccessTo("edit_Blacklist")){
					    	 panelGreytoBlack.setVisible(false);
					     }
				     }
	            }
	        };
	        dataView.setItemsPerPage(25);
	        add(dataView);
    }
	
	private void showErrorMessage(boolean isEmpty) {
		Label error = new Label("error",  Model.of("Es befindet sich kein Nutzer auf der grauen Liste."));
		if(!isEmpty){
			error.setVisible(false);
		}
		add(error);		
	}
	
	private void setTableHeadLineGreyList (Boolean isEmpty){
		Model<String> kennungModel = Model.of("HAW-Kennung");
		Model<String> vornameMdoel = Model.of("Vorname");
		Model<String> nachnameModel = Model.of("Nachname");
		Model<String> begruendungModel = Model.of("Begruendung");
		Model<String> loeschenMdoel = Model.of("Löschen");
		Model<String> toBlackModel = Model.of("Zur schwarzen Liste");
		
		Label kennungHeadGrey = new Label("kennungHeadGrey", kennungModel);
		Label vornameHeadGrey = new Label("vornameHeadGrey", vornameMdoel);
		Label nachnameHeadGrey = new Label("nachnameHeadGrey", nachnameModel);
		Label begruendungHeadGrey = new Label("begruendungHeadGrey",begruendungModel);
		Label loeschenHeadGrey = new Label("loeschenHeadGrey", loeschenMdoel);
		Label blackListHeadGrey = new Label("blackListHeadGrey", toBlackModel);
		
		if(isEmpty){
			kennungHeadGrey.setVisible(false);
			vornameHeadGrey.setVisible(false);
			nachnameHeadGrey.setVisible(false);
			begruendungHeadGrey.setVisible(false);
			loeschenHeadGrey.setVisible(false);
			blackListHeadGrey.setVisible(false);
		}else if(!access.hasAccessTo("edit_Greylist") ||!access.hasAccessTo("is_admin") || !access.hasAccessTo("manage_own_lab")){
			loeschenHeadGrey.setVisible(false);
	    }else if(!access.hasAccessTo("edit_Blacklist") ||!access.hasAccessTo("is_admin") || !access.hasAccessTo("manage_own_lab")){
	    	blackListHeadGrey.setVisible(false);
	    }
		add(kennungHeadGrey);
		add(vornameHeadGrey);
		add(nachnameHeadGrey);
		add(begruendungHeadGrey);
		add(loeschenHeadGrey);
		add(blackListHeadGrey);
	}
	
	private List<User> checkIfUserAlreadyOnList(List<User> users){
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		List<SanktionsListe> sanktionsListe = sanktionsListeDao.getAllWithUsers();
		
		if(!sanktionsListe.isEmpty()){
			for(SanktionsListe liste: sanktionsListe){
				User user = liste.getUserID();		
				for(User u: users){
					if(user.getKennung() != null && u.getKennung() != null){
						if(user.getKennung().equals(u.getKennung())){
							users.remove(u);
							if(users.isEmpty()){
								break;
							}
						}
					}else{
						return null;
					}
				}
			}	
		}
		return users;
	}
}