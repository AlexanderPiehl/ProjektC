package dao;


import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Database.HibernateHelper;
import Database.Entities.Berechtigung;
import Database.Entities.UserGroups;

public class UserGroupsDao extends BasicDao<UserGroups>{
	
	public UserGroupsDao() {
		super(UserGroups.class);
	}
	
	@SuppressWarnings("unchecked")
	public UserGroups findBerechtigungWithUserById(long id){
		Session session = HibernateHelper.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		IdentifierLoadAccess ila = session.byId(UserGroups.class);
		UserGroups userGroups = ila != null ? (UserGroups) ila.getReference(id) : null;
		if(userGroups != null){
			userGroups = initializeAndUnproxy(userGroups);
		}

			Hibernate.initialize(userGroups.getUser());
		
		transaction.commit();
		
		return userGroups;
	}
	
	@SuppressWarnings("unchecked")
	public UserGroups findUserGroupsWithBerechtigungById(long id){
		Session session = HibernateHelper.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		IdentifierLoadAccess ila = session.byId(UserGroups.class);
		UserGroups userGroups = ila != null ? (UserGroups) ila.getReference(id) : null;
		if(userGroups != null){
			userGroups = initializeAndUnproxy(userGroups);
		}

			Hibernate.initialize(userGroups.getBerechtigung());
		
		transaction.commit();
		
		return userGroups;
	}
	
	
}
