package a_myProject.model;

public class AccountService {
	AccountDAO accDao = new AccountDAO();
	
	public int userLogin(String id, String pw) {
		return accDao.userLogin(id,pw); 
	}
}
