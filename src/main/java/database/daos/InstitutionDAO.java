package database.daos;

import database.DatabaseManagement;

public class InstitutionDAO extends AbstractDAO {

	public InstitutionDAO(DatabaseManagement dm) {
		super(dm);
		super.dao = super.getDm().getInstitutions();
	}
	
}
