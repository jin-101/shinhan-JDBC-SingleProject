package JDBCproject.model;

import java.util.List;

import JDBCproject.vo.EmpVO;
import JDBCproject.vo.EquiVO;
	
public class AssetService {
	AssetDAO assetDao = new AssetDAO();
	
	//장비관련
	//1-1-1 대여가능장비 조회
	public List<EquiVO> rentPossible() {
		return assetDao.rentPossible();
	}
	//1-1-2 유형별 대여가능 장비 조회
	public List<EquiVO> eqTypeSearch(String typeId) {
		return assetDao.eqTypeSearch(typeId);
	}
	//1-1-3 모델별 대여가능 장비 조회
	public List<EquiVO> rentModelSearch(String model) {
		return assetDao.rentModelSearch(model);
	}
	//1-2-1 사용중인 장비 모두 조회
	public List<EquiVO> empUseEqAllSearch() {
		return assetDao.empUseEqAllSearch();
	}
	//1-2-2 사용자별 사용중인 장비 조회
	public List<EquiVO> userEqSearch(int userId) {
		return assetDao.userEqSearch(userId);
	}
	//1-2-3 장비ID별 현재 사용자 조회
	public List<EmpVO> equiIdSearch(int eqId) {
		return assetDao.equiIdSearch(eqId);
	}
	//1-3-1 장비대여
	public String eqRental(int equipmentId, int employeeId) {
		int result = assetDao.eqRental(equipmentId,employeeId);
		return result > 0 ? "대여성공" : "대여실패";
	}
	//1-3-2 장비반납
	public String eqRetrun(int eqId) {
		int result = assetDao.eqRetrun(eqId);
		return result > 0 ? "반납처리중" : "반납처리중" ;
	}
	public String rentInfoDelete(int eqId) {
		int result = assetDao.rentInfoDelete(eqId);
		return result > 0 ? "반납완료" : "반납실패";
	}
	//1-4 장비등록
	public String eqInsert(EquiVO eq) {
		int result = assetDao.eqInsert(eq);
		return result > 0 ? "입력성공" : "입력실패";
	}
	//1-5 장비내역 수정
	public String eqUpdate(EquiVO eq) {
		int result = assetDao.eqUpdate(eq);
		return result > 0 ? "업데이트 성공" : "업데이트 실패";
	}
	public EquiVO selectByEqId(int eqId) {
		return assetDao.selectByEqId(eqId);
	}
	//1-6 장비삭제
	public String eqDelete(int eqId) {
		int result = assetDao.eqDelete(eqId);
		return result > 0 ? "삭제 성공" : "삭제 실패";
	}
	
	
	//직원관련
	//2-1 전체 직원조회
	public List<EmpVO> viewEmp() {
		return assetDao.viewEmp();
	}
	//2-2 직원ID로 직원정보조회
	public EmpVO selectById(int empid) {
		return assetDao.selectById(empid);
	}
	//2-3 신규직원등록(insert)
	public String empInsert(EmpVO emp) {
		int result = assetDao.empInsert(emp);
		return result > 0 ? "입력성공" : "입력실패";
	}
	//2-4 직원정보수정(Update)
	public String empUpdate(EmpVO emp) {
		int result = assetDao.empUpdate(emp);
		return result > 0 ? "업데이트 성공" : "업데이트 실패";
	}
	//2-5 1건의 직원을 삭제하기(delete)
	public String empDelete(int empId) {
		int result = assetDao.empDelete(empId);
		return result > 0 ? "삭제 성공" : "삭제 실패";
	}
	
}
