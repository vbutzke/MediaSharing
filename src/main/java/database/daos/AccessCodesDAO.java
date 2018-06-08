package database.daos;

public class AccessCodesDAO extends AbstractDAO {

	public AccessCodesDAO() {
		super.dao = super.getDm().getAccessCodes();
	}
	
}