package Util;

import java.util.Scanner;

public class InputManager {
	private static Scanner scan = new Scanner(System.in);

	public static String getStr(String msg) {
		System.out.printf("▶ %s 입력 : ", msg);
		return scan.next();
	}

	public static int getInt(String msg) {
		System.out.printf("▶ %s 입력 : ", msg);
		int sel = -1;
		try {
			sel = scan.nextInt();
		} catch (Exception e) {
			System.out.println("숫자를 입력하세요");
		}
		scan.nextLine();
		return sel;
	}

	public void closeScanner() {
		scan.close();
	}
}