package Utils;

import java.util.Scanner;

public class InputManager {
	private static Scanner scan = new Scanner(System.in);

	public static int getInt(String msg) {
		System.out.print(msg);
		int sel = -1;
		try {
			sel = scan.nextInt();
		} catch (Exception e) {
			System.out.println("숫자를 입력하세요");
			scan.nextLine();
		}
		return sel;
	}

	public static String getStr(String msg) {
		System.out.println(msg);
		return scan.next();
	}

//	숫자 범위
	public static int selValue(int sel, int start, int end) {
		if (sel < start || sel > end) {
			return -1;
		}
		return sel;
	}

	public static void scanClose() {
		scan.close();
	}
}