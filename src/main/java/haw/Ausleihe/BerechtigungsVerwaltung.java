package haw.Ausleihe;


import haw.Ausleihe.ActionPanels.ActionPanelBerechtigungDetails;
import haw.Ausleihe.ActionPanels.ActionPanelBerechtigungEditieren;
import haw.Ausleihe.ActionPanels.ActionPanelBerechtigungsGruppeLoeschen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import Database.Entities.Berechtigung;
import Database.Entities.UserGroups;
import Models.UserGroupModel;
import Models.UserGroupModel.UserGroupsType;
import dao.BerechtigungDao;
import dao.UserGroupsDao;

public class BerechtigungsVerwaltung extends WebPage{
	private static final long serialVersionUID = 1L;
	private static boolean isBearbeiten = true;
	private static UserGroups userGroup ;
	private static boolean isNeu = false;
	
	
	final TextField<String> gruppeNamen = new TextField<String>("gruppeNamen", Model.of(""));
	
	final CheckBox chkProdukteSehen = new CheckBox("chkProdukteSehen",Model.of(Boolean.FALSE));
	final CheckBox chkReservieren = new CheckBox("chkReservieren",Model.of(Boolean.FALSE));		
	final CheckBox chkBuchungBestaetigen= new CheckBox("chkBuchungBestaetigen",Model.of(Boolean.FALSE));
	final CheckBox chkAusleihen = new CheckBox("chkAusleihen",Model.of(Boolean.FALSE));
	
	final CheckBox chkRuecknahme = new CheckBox("chkRuecknahme",Model.of(Boolean.FALSE));
	final CheckBox chkArtikelEditieren = new CheckBox("chkArtikelEditieren",Model.of(Boolean.FALSE));		
	final CheckBox chkArtikelLoeschen= new CheckBox("chkArtikelLoeschen",Model.of(Boolean.FALSE));
	final CheckBox chkArtikelErstellen = new CheckBox("chkArtikelErstellen",Model.of(Boolean.FALSE));
	
	final CheckBox chkGraueListeSehen = new CheckBox("chkGraueListeSehen",Model.of(Boolean.FALSE));
	final CheckBox chkGraueListeBearbeiten = new CheckBox("chkGraueListeBearbeiten",Model.of(Boolean.FALSE));		
	final CheckBox chkSchwarzeListeSehen= new CheckBox("chkSchwarzeListeSehen",Model.of(Boolean.FALSE));
	final CheckBox chkSchwarzeListeBearbeiten = new CheckBox("chkSchwarzeListeBearbeiten",Model.of(Boolean.FALSE));
	
	final CheckBox chkEigenesLabor= new CheckBox("chkEigenesLabor",Model.of(Boolean.FALSE));
	final CheckBox chkAdmin = new CheckBox("chkAdmin",Model.of(Boolean.FALSE));

	public BerechtigungsVerwaltung(PageParameters parameters){
		super(parameters);
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		StringValue isBearbeitenSV = parameters.get("isBearbeiten");
		if(!isBearbeitenSV.isNull()){
			isBearbeiten = isBearbeitenSV.toBoolean();
		}
		
		createFormNeueBerechtigungsGruppe();
		createTableBerechtigungen();
		
		userGroup = loadUserGroup(parameters);
		if(userGroup != null){
			loadUserGroupInForm(userGroup);
			
		}else{
			isNeu = true;
			isBearbeiten = false;
		}
		
		showError(parameters.get("isSuccess"));
	}		
	
	private void showError(StringValue stringValue) {
		Label error = new Label("error", Model.of("Beim Löschen passierte leider ein Fehler! Wahrscheinlich ist die Berechtigungsgruppe noch in Verwendung und kann daher nicht gelöscht werden."));
		add(error);
		if(stringValue.isNull()){
			error.setVisible(false);
		}
	}

	private void loadUserGroupInForm(UserGroups userGroup) {
		List<Berechtigung> berechtigungen = userGroup.getBerechtigung();
		boolean isEmpty = berechtigungen.isEmpty();
		int size = berechtigungen.size();
		System.out.println("empty: " + isEmpty + "; size: " + size);
		gruppeNamen.setModelObject(userGroup.getName());
		
		for(Berechtigung b: berechtigungen){
			String accesModule = b.getAccessModule();
			if(accesModule.equals("view_Products")){
				chkProdukteSehen.setModelObject(Boolean.TRUE);
			}else if(accesModule.equals("reserve_products")){
				chkReservieren.setModelObject(Boolean.TRUE);
			}else if(accesModule.equals("book_reserved")){
				chkBuchungBestaetigen.setModelObject(Boolean.TRUE);
			}else if(accesModule.equals("lending")){
				chkAusleihen.setModelObject(Boolean.TRUE);
			}
			
			else if(b.getAccessModule().equals("return_items")){
				chkRuecknahme.setModelObject(Boolean.TRUE);
			}else if(b.getAccessModule().equals("article_edit")){
				chkArtikelEditieren.setModelObject(Boolean.TRUE);
			}else if(b.getAccessModule().equals("article_remove")){
				chkArtikelLoeschen.setModelObject(Boolean.TRUE);
			}else if(b.getAccessModule().equals("article_create")){
				chkArtikelErstellen.setModelObject(Boolean.TRUE);
			}
			
			else if(b.getAccessModule().equals("view_Greylist")){
				chkGraueListeSehen.setModelObject(Boolean.TRUE);
			}else if(b.getAccessModule().equals("edit_Greylist")){
				chkGraueListeBearbeiten.setModelObject(Boolean.TRUE);
			}else if(b.getAccessModule().equals("view_Blacklist")){
				chkSchwarzeListeSehen.setModelObject(Boolean.TRUE);
			}else if(b.getAccessModule().equals("edit_Blacklist")){
				chkSchwarzeListeBearbeiten.setModelObject(Boolean.TRUE);
			}
			else if(b.getAccessModule().equals("manage_own_lab")){
				chkEigenesLabor.setModelObject(Boolean.TRUE);
			}else if(b.getAccessModule().equals("is_admin")){
				chkAdmin.setModelObject(Boolean.TRUE);
			}		
		}		
	}

	private UserGroups loadUserGroup(PageParameters parameters) {
		StringValue idSV = parameters.get("userGroupId");
		
		if(!idSV.isEmpty()){
			long userGroupId = idSV.toLong();
			UserGroupsDao userGroupsDao = new UserGroupsDao();
			return userGroupsDao.findUserGroupsWithBerechtigungById(userGroupId);
		}
		return null;	
	}

	public void createFormNeueBerechtigungsGruppe(){	
		Form<?> formNeueBerechtigung = new Form<Void>("formNeueBerechtigung") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				String inputName = gruppeNamen.getModelObject();
				
				boolean inputProdukteSehen = chkProdukteSehen.getModelObject();
				boolean inputReservierien = chkReservieren.getModelObject();
				boolean inputBuchungBestaetigen = chkBuchungBestaetigen.getModelObject();
				boolean inputAusleihen = chkAusleihen.getModelObject();
				
				boolean inputRuecknahme = chkRuecknahme.getModelObject();
				boolean inputArtikelEditieren = chkArtikelEditieren.getModelObject();
				boolean inputArtikelLoeschen = chkArtikelLoeschen.getModelObject();
				boolean inputArtikelErstellen = chkArtikelErstellen.getModelObject();
 
				boolean inputGraueListeSehen = chkGraueListeSehen.getModelObject();
				boolean inputGraueListeBearbeiten = chkGraueListeBearbeiten.getModelObject();
				boolean inputSchwarzeListeSehen = chkSchwarzeListeSehen.getModelObject();
				boolean inputSchwarzeListeBearbeiten = chkSchwarzeListeBearbeiten.getModelObject();
				
				boolean inputEigenesLabor = chkEigenesLabor.getModelObject();
				boolean inputAdmin = chkAdmin.getModelObject();
				
				BerechtigungDao berechtigungsDao = new BerechtigungDao();
				List<Berechtigung> berechtigungen = new ArrayList<Berechtigung>();
				if(inputProdukteSehen)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "view_Products"));
				if(inputReservierien)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "reserve_products"));
				if(inputBuchungBestaetigen)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "book_reserved"));
				if(inputAusleihen)					
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "lending"));
				
				if(inputRuecknahme)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "return_items"));
				if(inputArtikelEditieren)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "article_edit"));
				if(inputArtikelLoeschen)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "article_remove"));
				if(inputArtikelErstellen)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "article_create"));
				
				if(inputGraueListeSehen)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "view_Greylist"));
				if(inputGraueListeBearbeiten)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "edit_Greylist"));
				if(inputSchwarzeListeSehen)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "view_Blacklist"));
				if(inputSchwarzeListeBearbeiten)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "edit_Blacklist"));
				
				if(inputEigenesLabor)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "manage_own_lab"));
				if(inputAdmin)
					berechtigungen.add(berechtigungsDao.getUniqueByField("accessModule", "is_admin"));
							
				UserGroupsDao userGroupsDao = new UserGroupsDao();
				if(isBearbeiten){
					userGroup.setBerechtigung(berechtigungen);
					userGroup.setModified(new Date());
					userGroupsDao.edit(userGroup);				
				}else if(isNeu){
					UserGroups userGroup = new UserGroups();
					userGroup.setName(inputName);
					userGroup.setBerechtigung(berechtigungen);
					userGroup.setCreated(new Date());
					userGroupsDao.create(userGroup);
				}
				setResponsePage(BerechtigungsVerwaltung.class);
			}
		};
		add(formNeueBerechtigung);
		formNeueBerechtigung.add(gruppeNamen);
		
		formNeueBerechtigung.add(chkProdukteSehen);
		formNeueBerechtigung.add(chkReservieren);
		formNeueBerechtigung.add(chkBuchungBestaetigen);
		formNeueBerechtigung.add(chkAusleihen);
		
		formNeueBerechtigung.add(chkRuecknahme);
		formNeueBerechtigung.add(chkArtikelEditieren);
		formNeueBerechtigung.add(chkArtikelLoeschen);
		formNeueBerechtigung.add(chkArtikelErstellen);
		
		formNeueBerechtigung.add(chkGraueListeSehen);
		formNeueBerechtigung.add(chkGraueListeBearbeiten);
		formNeueBerechtigung.add(chkSchwarzeListeSehen);
		formNeueBerechtigung.add(chkSchwarzeListeBearbeiten);
		
		formNeueBerechtigung.add(chkEigenesLabor);
		formNeueBerechtigung.add(chkAdmin);
		
		if(!isBearbeiten){
			formNeueBerechtigung.setEnabled(false);
		}
	}
	
	private void createTableBerechtigungen(){
		UserGroupsDao userGroupsDao = new UserGroupsDao();
		List<UserGroups> userGroups = userGroupsDao.getAll();
		
		setTableHeadUserGroups(userGroups.isEmpty());
		
		ListDataProvider<UserGroups> listDtaProvider = new ListDataProvider<UserGroups>(userGroups);
		 DataView<UserGroups> dataView = new DataView<UserGroups>("rows", listDtaProvider) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override  
			 protected void populateItem(Item<UserGroups> item) {
	                UserGroups userGroup = item.getModelObject();
	                item.add(new Label("id",userGroup.getId()));
	                item.add(new Label("name",userGroup.getName()));
	                ActionPanelBerechtigungsGruppeLoeschen panelLoeschen = new ActionPanelBerechtigungsGruppeLoeschen("loeschen", new UserGroupModel(new Model<UserGroups>(item.getModelObject()),UserGroupsType.USER_GROUP));
	                item.add(panelLoeschen);
	                ActionPanelBerechtigungEditieren panelBearbeiten = new ActionPanelBerechtigungEditieren("bearbeiten", new UserGroupModel(new Model<UserGroups>(item.getModelObject()),UserGroupsType.USER_GROUP));
	                item.add(panelBearbeiten);
	                ActionPanelBerechtigungDetails panelDetails= new ActionPanelBerechtigungDetails("details", new UserGroupModel(new Model<UserGroups>(item.getModelObject()),UserGroupsType.USER_GROUP));
	                item.add(panelDetails);              
	            }
	        };
	        dataView.setItemsPerPage(25);
	        add(dataView);
	}

	private void setTableHeadUserGroups(boolean isEmpty) {
		Label idHeadUserGroup = new Label("idHeadUserGroup", Model.of("ID"));
		Label nameHeadUserGroup = new Label("nameHeadUserGroup", Model.of("Name"));
		Label loeschenUserGroup = new Label("loeschenUserGroup", Model.of("Löschen"));
		Label bearbeitenUserGroup = new Label("bearbeitenUserGroup", Model.of("Bearbeiten"));
		Label detailsUserGroup = new Label("detailsUserGroup", Model.of("Details"));
		
		if(isEmpty){
			idHeadUserGroup.setVisible(false);
			nameHeadUserGroup.setVisible(false);
			loeschenUserGroup.setVisible(false);
			bearbeitenUserGroup.setVisible(false);
			detailsUserGroup.setVisible(false);
		}		
		add(idHeadUserGroup);
		add(nameHeadUserGroup);
		add(loeschenUserGroup);
		add(bearbeitenUserGroup);
		add(detailsUserGroup);
	}
}
