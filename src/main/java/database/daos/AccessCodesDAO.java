package database.daos;

import database.DatabaseManagement;

public class AccessCodesDAO extends AbstractDAO {

	public AccessCodesDAO(DatabaseManagement dm) {
		super(dm);
		super.dao = super.getDm().getAccessCodes();
	}
	
}