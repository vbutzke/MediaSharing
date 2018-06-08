package database.daos;

public class MediasDAO extends AbstractDAO {

	public MediasDAO() {
		super.dao = super.getDm().getAllMedias();
	}
	
}
