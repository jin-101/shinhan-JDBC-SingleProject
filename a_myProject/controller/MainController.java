package a_myProject.controller;

import java.util.Scanner;


import a_myProject.model.AccountService;
import a_myProject.model.AssetService;
import a_myProject.view.AccountView;

public class MainController {

	private static Scanner sc = new Scanner(System.in);
	private static AccountService loginService = new AccountService();
	private static AssetService eService = new AssetService();
	
	//첫 선택 메뉴
	private static String getMenu() {
		System.out.println();
		System.out.println("===============================");
		System.out.println("1.장비업무 | 2.직원업무 | exit. 종료");
		System.out.println("===============================");
		System.out.print("작업을 선택하세요. >> ");
		String choice = sc.next();
		return choice;
	}
	
	//login logic
	public static boolean userLogin(Scanner sc) {
		System.out.println("--------------------------");
		System.out.println("---- Asset Management ----");
		System.out.println("--------------------------");
		System.out.print("자산관리자 ID : >> ");
		String id = sc.next();
		System.out.print("자산관리자 PW : >> ");
		String pw = sc.next();
		int result = AccountView.print(loginService.userLogin(id, pw));
		return result == 1 ? true: false;
	}
	
	//asset program logic
	public static void init() {
		while(true){
			EqController eqContorl = new EqController();
			EmpController empControl = new EmpController();
			String choice = getMenu();
			if(choice.equals("exit")) break;
			switch (choice) {
				case "1" : {
					while(true){
					String type = eqContorl.showEqMenu(sc);
					if(type.equals("back")) break;
						switch (type) {
							case "1": //대여가능 장비조회
								while(true) {
									String subType = eqContorl.showEqNonUseMenu(sc);
									if(subType.equals("back")) break;
									switch(subType) {
										case "1" : eqContorl.rentPossibleTotal(eService); break;
										case "2" : eqContorl.rentTypeSearch (sc, eService); break;
										case "3" : eqContorl.rentModelSearch (sc, eService); break;
									}
								}
								break;
							case "2": //사용중인 장비조회
								while(true) {
									String subType = eqContorl.showEqUseMenu(sc);
									if(subType.equals("back")) break;
									switch(subType) {
										case "1" : eqContorl.empUseEqAllSearch (sc, eService); break;
										case "2" : eqContorl.empUseEqSearch (sc, eService); break;
										case "3" : eqContorl.eqUseEmpSearch (sc, eService); break;
									}
								}
								break;
							case "3":
								while(true) {
									String subType = eqContorl.showEqRentMenu(sc);
									if(subType.equals("back")) break;
									switch(subType) {
										case "1" : eqContorl.eqRentLogic(sc, eService); break;
										case "2" : eqContorl.eqReturnLogic(sc, eService); break;
									}
								}
								break;
							case "4":
								while(true) {
									String subType = eqContorl.showEqChange(sc);
									if(subType.equals("back")) break;
									switch(subType) {
										case "1" : eqContorl.eqInsertLogic(sc, eService); break;
										case "2" : eqContorl.eqUpdateLogic(sc, eService); break;
										case "3" : eqContorl.eqDeleteLogic(sc, eService); break;
									}
								}
								break;
						}
						
					}
					break;
				}
				case "2" : {
					while(true){
						String type = empControl.showEmpMenu(sc);
						if(type.equals("back")) break;
						switch (type) {
							case "1": // 전체 직원 조회
								while(true){
									String subType = empControl.showEmpSearch(sc);
									if(subType.equals("back")) break;
									switch (subType) {
										case "1" : empControl.emptotalSearch(eService); break;
										case "2" : empControl.empIdSearch(sc, eService); break;
									}
								}
								break;
							case "2": {
								while(true){
									String subType = empControl.showEmpChange(sc);
									if(subType.equals("back")) break;
									switch (subType) {
										case "1" : empControl.empInsertLogic(sc, eService); break;
										case "2" : empControl.empUpdateLogic(sc, eService); break;
										case "3" : empControl.empDeleteLogic(sc, eService); break;
									}
								}
								break;
							}
						}
					}
					break;
				}
			}
		}
		System.out.println("종료! 수고하셨습니다.");
		sc.close();	
	}
	
	public static void main(String[] args) {
		boolean isLogin = false;
		while(!isLogin) {
			isLogin = userLogin (sc); 
			if(isLogin) init();
		}
	}
}
