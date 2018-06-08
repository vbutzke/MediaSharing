package database.daos;

public class InstitutionDAO extends AbstractDAO {

	public InstitutionDAO() {
		super.dao = super.getDm().getInstitutions();
	}
	
}
