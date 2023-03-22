package a_myProject.view;

import java.util.List;

public class AssetView {
	public static <T> void print(List<T> li) {
		if(li.isEmpty()) {
			System.out.println("조건에 맞는 정보가 존재하지 않습니다.");
		}else {
			System.out.println("=========================================상세보기=====================================");
			for(T e : li) {
				System.out.println(e);
			}
		}
	}
	
	public static <T> void print(T e) {
		if(e == null) {
			System.out.println("조건에 맞는 정보가 존재하지 않습니다.");
		}else {
			System.out.println("=========================================상세보기=====================================");
			System.out.println(e);
		}
	}
	
	public static void print(String message) {
		System.out.println("[알림]" + message);
	}
}
