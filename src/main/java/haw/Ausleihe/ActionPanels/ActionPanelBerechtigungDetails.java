package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.BerechtigungsVerwaltung;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.UserGroups;
import Models.UserGroupModel;

public class ActionPanelBerechtigungDetails extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelBerechtigungDetails(String id, IModel<UserGroupModel> userGroupModel) {
		super(id, userGroupModel);
		
		Link<Object> link = new Link<Object>(id){
			 private static final long serialVersionUID = 1L;
			 
			 @Override
			 public void onClick(){
				UserGroups userGroup = (UserGroups) ActionPanelBerechtigungDetails.this.getDefaultModelObject();
				PageParameters parameters = new PageParameters();
				parameters.set("isBearbeiten", Boolean.FALSE);
				parameters.set("userGroupId", userGroup.getId());
        	
			    this.setResponsePage(BerechtigungsVerwaltung.class, parameters);
			 }
		 };
		 add(link);
		 }
}
