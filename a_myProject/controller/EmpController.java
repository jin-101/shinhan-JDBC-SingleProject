package a_myProject.controller;

import java.sql.Date;
import java.util.Scanner;

import a_myProject.model.AssetService;
import a_myProject.view.AssetView;
import a_myProject.vo.EmpVO;

public class EmpController {
	
	//logic
	public void emptotalSearch (AssetService eService) {
		AssetView.print(eService.viewEmp());
	}
	public void empIdSearch (Scanner sc, AssetService eService) {
		System.out.print("조회할 직원번호를 입력하세요. >> ");
		int empid = Integer.parseInt(sc.next());
		AssetView.print(eService.selectById(empid));
	}
	public void empInsertLogic (Scanner sc, AssetService eService) {
		EmpVO emp = makeEmp(sc);
		AssetView.print(eService.empInsert(emp));
	}
	public void empUpdateLogic (Scanner sc, AssetService eService) {
		System.out.print("수정할 직원번호를 입력하세요. >> ");
		int empId = Integer.parseInt(sc.next());
		AssetView.print(eService.selectById(empId));
		EmpVO emp = updateEmp(sc, empId);
		System.out.print("수정을 계속 진행할 것인가요? (Y/N) ");
		String yn = sc.next();
		if(yn.equals("Y") || yn.equals("y")) AssetView.print(eService.empUpdate(emp));
	}
	public void empDeleteLogic (Scanner sc, AssetService eService) {
		System.out.print("삭제할 직원번호를 입력하세요. >> ");
		int empId = Integer.parseInt(sc.next());
		EmpVO vo = eService.selectById(empId);
		AssetView.print(vo);
		if(vo != null) {
			System.out.print("삭제를 계속 진행할 것인가요? (Y/N) ");
			String yn = sc.next();
			if(yn.equals("Y") || yn.equals("y")) AssetView.print(eService.empDelete(empId));
		}
	}
	
	
	//select
	public String showEmpMenu(Scanner sc) {
		System.out.println();
		System.out.println("======================================");
		System.out.println("1) 직원조회 | 2) 직원내역 변경 | back. 돌아가기");
		System.out.println("======================================");
		System.out.print("작업을 선택하세요. >> ");
		String type = sc.next();
		return type;
	}
	
	public String showEmpSearch(Scanner sc) {
		System.out.println();
		System.out.println("===========================================");
		System.out.println("① 전체조회 | ② 직원번호로 직원정보조회 | back. 돌아가기");
		System.out.println("===========================================");
		System.out.print("작업을 선택하세요. >> ");
		String subType = sc.next();
		return subType;
	}
	
	public String showEmpChange(Scanner sc) {
		System.out.println();
		System.out.println("===================================================");
		System.out.println("① 신규직원등록 | ② 직원정보수정 | ③ 직원정보삭제 | back. 돌아가기");
		System.out.println("===================================================");
		System.out.print("작업을 선택하세요. >> ");
		String subType = sc.next();
		return subType;
	}
	
	
	
	//직원추가 입력받기
	private EmpVO makeEmp(Scanner sc) {
		System.out.print("1.신규직원의 성씨를 입력하세요. >> ");
		String firstName =  sc.next();
		System.out.print("2.신규직원의 이름을 입력하세요. >> ");
		String lastName =  sc.next();
		System.out.print("3.신규직원의 이메일을 입력하세요. >> ");
		String email =  sc.next();
		System.out.print("4.신규직원의 휴대폰 번호를 입력하세요. >> ");
		String phoneNumber =  sc.next();
		System.out.println("5.신규직원의 입사일을 입력하세요.");
		System.out.print("[ YYYY / MM / DD ] >> ");
		String sdate = sc.next();
		Date hireDate =  DateUtil.convertToDate(sdate);
		System.out.println("6.신규직원의 직급번호를 입력하세요.");
		System.out.print("[ S(사원) | A_MGR(대리) | MGR(과장) | DG_MGR(차장) | G_MGR(부장) | MD(이사) | P(대표이사) ] >> ");
		String jobId =  sc.next();
		System.out.print("7.신규직원의 급여를 입력하세요. >> ");
		int salary =  Integer.parseInt(sc.next());
		System.out.println("8.신규직원의 팀(파트)번호를 입력하세요.");
		System.out.print("[1000 | 2000_1,2 | 3000_1,2,3 | 4000 | 5000_1,2,3 | 6000 | 7000 | 8000 | 9000] >> ");
		int partNo = Integer.parseInt(sc.next());
		System.out.println("9.신규직원의 직책번호를 입력하세요.");
		System.out.print("[100(팀원) | 200(파트장) | 300(팀장) | 400(실장) | 500(사장) ] >> ");
		int positionNo = Integer.parseInt(sc.next());
		
		EmpVO emp = new EmpVO(); //하나로 묶어서 다닌다 -> VO다
		emp.setFirst_name(firstName);
		emp.setLast_name(lastName);
		emp.setEmail(email);
		emp.setPhone_number(phoneNumber);
		emp.setHire_date(hireDate);
		emp.setJobs_JOB_ID(jobId);
		emp.setSalary(salary);
		emp.setSubpart_PART_NO(partNo);
		emp.setPosition_POSITION_ID(positionNo);
		return emp;
	}
	
	//직원정보 수정 입력 받기
	private EmpVO updateEmp(Scanner sc, int employeeId) {
		System.out.print("1.직원의 수정할 이메일을 입력하세요. >> ");
		String email = sc.next();
		System.out.print("2.직원의 수정할 휴대폰 번호를 입력하세요. >> ");
		String phoneNo = sc.next();
		System.out.println("3.직원의 수정할 입사일을 입력하세요.");
		System.out.print("[ YYYY / MM / DD ] >> ");
		String sdate = sc.next();
		Date hireDate = DateUtil.convertToDate(sdate);
		System.out.println("4.직원의 수정할 직급을 입력하세요.");
		System.out.print("[ S(사원) | A_MGR(대리) | MGR(과장) | DG_MGR(차장) | G_MGR(부장) | MD(이사) | P(대표이사) ] >> ");
		String jobId =  sc.next();
		System.out.print("5.직원의 수정할 급여를 입력하세요. >> ");
		int salary = Integer.parseInt(sc.next());
		System.out.println("6.직원의 수정할 팀(파트)번호를 입력하세요.");
		System.out.print("[1000 | 2000_1,2 | 3000_1,2,3 | 4000 | 5000_1,2,3 | 6000 | 7000 | 8000 | 9000] >> ");
		int partNo = Integer.parseInt(sc.next());
		System.out.println("7.직원의 수정할 직책번호를 입력하세요.");
		System.out.print("[100(팀원) | 200(파트장) | 300(팀장) | 400(실장) | 500(사장) ] >> ");
		int positionNo = Integer.parseInt(sc.next());
		
		EmpVO emp = new EmpVO(); //하나로 묶어서 다닌다 -> VO다
		emp.setEmployee_id(employeeId);
		emp.setEmail(email);
		emp.setPhone_number(phoneNo);
		emp.setHire_date(hireDate);
		emp.setJobs_JOB_ID(jobId);
		emp.setSalary(salary);
		emp.setSubpart_PART_NO(partNo);
		emp.setPosition_POSITION_ID(positionNo);
		return emp;
	}
	
}
