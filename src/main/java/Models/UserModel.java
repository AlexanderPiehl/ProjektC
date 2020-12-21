package Models;

import org.apache.wicket.model.IModel;

import Database.Entities.User;

public class UserModel implements IModel{
	private static final long serialVersionUID = 1L;
	private IModel<User> userModel;
	private UserModelType type;
	
	public enum UserModelType{
		USER,USER_ID,USER_KENNUNG,USER_VORNAME,USER_NACHNAME
	}
	
	public UserModel(IModel<User> userModel, UserModelType type){
		this.userModel = userModel;
		this.type = type;
	}
	
	@Override
	public void detach() {
		userModel.detach();	
	}

	@Override
	public Object getObject() {
		User user = (User) userModel.getObject();
		
		switch(type){
		case USER:
			return user;
		case USER_ID:
			return user.getId();
		case USER_KENNUNG:
			return user.getKennung();
		case USER_VORNAME:
			return user.getFirstName();
		case USER_NACHNAME:
			return user.getLastName();				
		}
		throw new UnsupportedOperationException("invalid UserModelType = "
                + type.name());	
	}

	@Override
	public void setObject(Object object) {
		// TODO Auto-generated method stub
		
	}

}
