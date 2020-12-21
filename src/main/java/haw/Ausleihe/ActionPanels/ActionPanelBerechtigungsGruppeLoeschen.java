package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.BerechtigungsVerwaltung;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import dao.UserGroupsDao;
import Database.Entities.UserGroups;
import Models.UserGroupModel;

public class ActionPanelBerechtigungsGruppeLoeschen extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelBerechtigungsGruppeLoeschen(String id, IModel<UserGroupModel> userGroupModel) {
		super(id, userGroupModel);
		
		Link<Object> link = new Link<Object>(id){
			 private static final long serialVersionUID = 1L;
			 
			 @Override
			 public void onClick(){
				 UserGroups userGroup = (UserGroups) ActionPanelBerechtigungsGruppeLoeschen.this.getDefaultModelObject();
				 UserGroupsDao userGroupsDao = new UserGroupsDao();
				 boolean isgeloescht = userGroupsDao.delete(userGroup);
				 
				 PageParameters parameters = new PageParameters();
				 parameters.add("isSuccess", isgeloescht);
        	
			    this.setResponsePage(BerechtigungsVerwaltung.class, parameters);
			 }
		 };
		 add(link);
	}
}
