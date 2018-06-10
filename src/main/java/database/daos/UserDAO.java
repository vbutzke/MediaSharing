package database.daos;

import database.DatabaseManagement;

public class UserDAO extends AbstractDAO {
	
	public UserDAO(DatabaseManagement dm) {
		super(dm);
		super.dao = super.getDm().getUsers();
	}
	
}
