package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.BerechtigungSetzen;


import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.User;

public class ActionPanelBerechtigungSetzen extends Panel {
	private static final long serialVersionUID = 1L;

	public ActionPanelBerechtigungSetzen(String id, IModel<User> userModel) {
		super(id, userModel);
		add(new Link<Object>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				User user = (User) ActionPanelBerechtigungSetzen.this.getDefaultModelObject();
				PageParameters parameters = new PageParameters();
				parameters.add("UserID", user.getId());
				
				this.setResponsePage(BerechtigungSetzen.class, parameters);	
			}
		});

	}

}
