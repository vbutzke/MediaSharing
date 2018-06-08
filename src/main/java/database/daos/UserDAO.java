package database.daos;

public class UserDAO extends AbstractDAO {
	
	public UserDAO() {
		super.dao = super.getDm().getUsers();
	}
	
}
