package haw.Ausleihe.ActionPanels;

import java.util.List;

import haw.Ausleihe.BerechtigungSetzen;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import dao.UserDao;
import Database.Entities.User;
import Database.Entities.UserGroups;

public class ActionPanelBerechtigungUserLoeschen extends Panel {
	private static final long serialVersionUID = 1L;

	public ActionPanelBerechtigungUserLoeschen(String id,final long userId, final long userGroupId) {
		super(id);

		add(new Link<Object>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				UserDao userDao = new UserDao();
				User user = userDao.findUserWithUserGroupsById(userId);
				List<UserGroups> userGroupsOfUser = user.getMemberOfGroups();
				
				for(UserGroups uG: userGroupsOfUser){
					if(uG.getId() == userGroupId){
						userGroupsOfUser.remove(uG);
						break;
					}
				}
				user.setMemberOfGroups(userGroupsOfUser);
				userDao.edit(user);

				PageParameters parameters = new PageParameters();
				parameters.add("UserID", user.getId());
				
				this.setResponsePage(BerechtigungSetzen.class, parameters);
			}
		});
	}
}
