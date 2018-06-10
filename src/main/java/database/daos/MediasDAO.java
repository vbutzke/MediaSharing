package database.daos;

import database.DatabaseManagement;

public class MediasDAO extends AbstractDAO {

	public MediasDAO(DatabaseManagement dm) {
		super(dm);
		super.dao = super.getDm().getAllMedias();
	}
	
}
