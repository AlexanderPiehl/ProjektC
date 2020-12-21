package Models;

import org.apache.wicket.model.IModel;

import Database.Entities.UserGroups;

public class UserGroupModel implements IModel{
	private static final long serialVersionUID = 1L;
	private IModel<UserGroups> userGroupModel;
	private UserGroupsType type;
	
	public enum UserGroupsType{
		USER_GROUP
	}

	public UserGroupModel(IModel<UserGroups> userGroupModel, UserGroupsType type){
		this.userGroupModel = userGroupModel;
		this.type = type;
	}
	
	@Override
	public void detach() {
		userGroupModel.detach();	
	}

	@Override
	public Object getObject() {
		UserGroups userGroup = (UserGroups) userGroupModel.getObject();
		
		switch(type){
		case USER_GROUP:
			return userGroup;			
		}
		throw new UnsupportedOperationException("invalid UserModelType = "
                + type.name());	
	}

	@Override
	public void setObject(Object object) {		
	}

}
