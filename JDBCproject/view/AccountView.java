package JDBCproject.view;

public class AccountView {
	public static int print(int result) {
		String printText = "";
		switch (result) {
			case 1 : printText = "로그인 성공"; break;
			case 0 : printText = "비밀번호가 올바르지 않습니다."; break;
			case -1 : printText = "아이디가 올바르지 않습니다."; break;
			case -2 : printText = "알 수 없는 오류가 발생하였습니다."; break;
		}
		System.out.println("[알림]" + printText);
		return result;
	}
}
