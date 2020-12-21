package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.BerechtigungSetzen;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.User;
import Database.Entities.UserGroups;
import dao.UserDao;
import dao.UserGroupsDao;


public class ActionPanelNeueBerechtigungUser extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelNeueBerechtigungUser(String id,final long userId, final long userGroupId) {
		super(id);

		add(new Link<Object>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				UserDao userDao = new UserDao();
				User user = userDao.findUserWithUserGroupsById(userId);
				List<UserGroups> userGroupsOfUser = user.getMemberOfGroups();
				
				UserGroupsDao userGroupsDao = new UserGroupsDao();
				UserGroups userGroup = userGroupsDao.findById(userGroupId);
				userGroupsOfUser.add(userGroup);
				
				user.setMemberOfGroups(userGroupsOfUser);
				userDao.edit(user);

				PageParameters parameters = new PageParameters();
				parameters.add("UserID", user.getId());
				
				this.setResponsePage(BerechtigungSetzen.class, parameters);
			}
		});
	}
}
