package JDBCproject.controller;

import java.sql.Date;
import java.util.Scanner;

import JDBCproject.model.AssetService;
import JDBCproject.view.AssetView;
import JDBCproject.vo.EquiVO;

public class EqController {
	
	//logic
	public void rentPossibleTotal (AssetService eService) {
		AssetView.print(eService.rentPossible());
	}
	public void rentTypeSearch (Scanner sc, AssetService eService) {
		System.out.print("조회할 장비유형을 선택하세요. [ PC | MO | TA | PH | PE ] >> ");
		String typeId =  sc.next();
		AssetView.print(eService.eqTypeSearch(typeId));
	}
	public void rentModelSearch (Scanner sc, AssetService eService) {
		System.out.print("조회할 장비모델을 입력하세요. >> ");
		sc.nextLine();
		String model =  sc.nextLine();
		AssetView.print(eService.rentModelSearch(model));
	}
	public void empUseEqAllSearch(Scanner sc, AssetService eService) {
		AssetView.print(eService.empUseEqAllSearch());
	}
	public void empUseEqSearch (Scanner sc, AssetService eService) {
		System.out.print("직원번호를 입력하세요. >> ");
		int userId = Integer.parseInt(sc.next());
		AssetView.print(eService.userEqSearch(userId));
	}
	public void eqUseEmpSearch (Scanner sc, AssetService eService) {
		System.out.print("장비번호를 입력하세요. >> ");
		int eqId =  Integer.parseInt(sc.next());
		AssetView.print(eService.equiIdSearch(eqId));
	}
	public void eqRentLogic (Scanner sc, AssetService eService) {
		System.out.print("1.장비번호를 입력하세요. >> ");
		int equipmentId = Integer.parseInt(sc.next());
		System.out.print("2.대여 할 직원번호를 입력하세요. >> ");
		int employeeId = Integer.parseInt(sc.next());
		System.out.print("장비대여를 계속 진행할 것인가요? (Y/N) >> ");
		String yn = sc.next();
		if(yn.equals("Y") || yn.equals("y")) AssetView.print(eService.eqRental(equipmentId,employeeId));
	}
	public void eqReturnLogic (Scanner sc, AssetService eService) {
		System.out.print("1.장비번호를 입력하세요. >> ");
		int eqId =  Integer.parseInt(sc.next());
		System.out.print("장비반납을 계속 진행할 것인가요? (Y/N) >> ");
		String yn = sc.next();
		if(yn.equals("Y") || yn.equals("y")) {
			AssetView.print(eService.eqRetrun(eqId));
			AssetView.print(eService.rentInfoDelete(eqId));
		}
	}
	public void eqInsertLogic (Scanner sc, AssetService eService) {
		EquiVO eq = makeEq(sc);
		AssetView.print(eService.eqInsert(eq));
	}
	public void eqUpdateLogic (Scanner sc, AssetService eService) {
		System.out.print("수정할 장비번호를 입력하세요. >> ");
		int eqId = Integer.parseInt(sc.next());
		AssetView.print(eService.selectByEqId(eqId));
		EquiVO eq = updateEq(sc, eqId);
		System.out.print("수정을 계속 진행할 것인가요? (Y/N) ");
		String yn = sc.next();
		if(yn.equals("Y") || yn.equals("y")) AssetView.print(eService.eqUpdate(eq));
	}
	public void eqDeleteLogic (Scanner sc, AssetService eService) {
		System.out.print("삭제할 장비번호를 입력하세요. >> ");
		int eqId = Integer.parseInt(sc.next());
		EquiVO vo = eService.selectByEqId(eqId);
		AssetView.print(vo);
		if(vo != null) {
			System.out.print("삭제를 계속 진행할 것인가요? (Y/N) ");
			String yn = sc.next();
			if(yn.equals("Y") || yn.equals("y")) AssetView.print(eService.eqDelete(eqId));	
		}
	}
	
	
	//select
	public String showEqMenu(Scanner sc) {
		System.out.println();
		System.out.println("=================================================================================");
		System.out.println("1) 대여가능 장비조회 | 2) 사용중인 장비조회 | 3) 장비대여 및 반납 | 4) 장비내역 변경 | back. 돌아가기");
		System.out.println("=================================================================================");
		System.out.print("작업을 선택하세요. >> ");
		String type = sc.next();
		return type;
	}
	public String showEqNonUseMenu(Scanner sc) {
		System.out.println();
		System.out.println("===========================================================");
		System.out.println("① 전체조회 | ② 대여장비 유형별조회 | ③ 대여장비 모델별조회 | back. 돌아가기");
		System.out.println("===========================================================");
		System.out.print("작업을 선택하세요. >> ");
		String subType = sc.next();
		return subType;
	}
	public String showEqUseMenu(Scanner sc) {
		System.out.println();
		System.out.println("===============================================================================");
		System.out.println("① 사용중인 장비 전체조회 | ② 직원별 사용중인 장비 조회 | ③ 장비ID별 현재 사용자 조회 | back. 돌아가기");
		System.out.println("===============================================================================");
		System.out.print("작업을 선택하세요. >> ");
		String subType = sc.next();
		return subType;
	}
	public String showEqRentMenu(Scanner sc) {
		System.out.println();
		System.out.println("=================================");
		System.out.println("① 장비대여 | ② 장비반납 | back. 돌아가기");
		System.out.println("=================================");
		System.out.print("작업을 선택하세요. >> ");
		String subType = sc.next();
		return subType;
	}
	public String showEqChange(Scanner sc) {
		System.out.println();
		System.out.println("===============================================");
		System.out.println("① 장비등록 | ② 장비정보 수정 | ③ 장비삭제 | back. 돌아가기");
		System.out.println("===============================================");
		System.out.print("작업을 선택하세요. >> ");
		String subType = sc.next();
		return subType;
	}
	
	
	//장비추가 입력받기
	private EquiVO makeEq(Scanner sc) {
		System.out.println("1.신규장비의 유형을 입력하세요.");
		System.out.print("[ PC(Desktop,Notebook) | TA(Tablet) | MO(Monitor) | PH(Phone) | PE(SmartPen) ] >> ");
		String typeId =  sc.next();
		System.out.println("2.신규장비의 제조회사 코드를 입력하세요.");
		System.out.print("[ SA(Samsung) | AP(Apple) | LG(LG) | DE(Dell) | LE(Lenovo) | WA(Wacom) | HP(HP) ] >> ");
		String companyId =  sc.next();
		System.out.print("3.신규장비의 모델명을 입력하세요. >> ");
		String model =  sc.next();
		System.out.print("4.신규장비의 일련번호를 입력하세요. >> ");
		String serialNo =  sc.next();
		System.out.println("5.신규장비의 구매일을 입력하세요.");
		System.out.print("[ YYYY / MM / DD ] >> ");
		String pdate = sc.next();
		Date purchaseDate =  DateUtil.convertToDate(pdate);
		System.out.print("6.신규장비의 가격을 입력하세요. >> ");
		int price = Integer.parseInt(sc.next());
		
		EquiVO eq = new EquiVO(); //하나로 묶어서 다닌다 -> VO다
		eq.setEquipmentstype_TYPE_ID(typeId);
		eq.setEquipmentscompany_CO_ID(companyId);
		eq.setMODEL(model);
		eq.setSERIAL_NO(serialNo);
		eq.setPURCHASE_DATE(purchaseDate);
		eq.setPRICE(price);
		return eq;
	}
	//장비정보 수정 입력받기 
	private EquiVO updateEq(Scanner sc, int equipmentID) {
		System.out.print("1.장비의 수정할 모델명을 입력하세요. >> ");
		String model =  sc.next();
		System.out.print("2.장비의 수정할 일련번호를 입력하세요. >> ");
		String serialNo =  sc.next();
		System.out.println("3.장비의 수정할 구매일을 입력하세요.");
		System.out.print("[ YYYY / MM / DD ] >> ");
		String pdate = sc.next();
		Date purchaseDate =  DateUtil.convertToDate(pdate);
		System.out.print("4.장비의 수정할 가격을 입력하세요. >> ");
		int price = Integer.parseInt(sc.next());
		EquiVO eq = new EquiVO(); //하나로 묶어서 다닌다 -> VO다
		eq.setEquipment_id(equipmentID);
		eq.setMODEL(model);
		eq.setSERIAL_NO(serialNo);
		eq.setPURCHASE_DATE(purchaseDate);
		eq.setPRICE(price);
		return eq;
	}
}
