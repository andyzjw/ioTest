package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputUtil {
	private static final BufferedReader KEYBOARD_INPUT = new BufferedReader(new InputStreamReader(System.in));

	private InputUtil(){}
	
	public static String getString(String prompt) {
		boolean flag = true; // ���ݽ��ձ��
		String str = null;
		while(flag) {
			System.out.println(prompt);

			try {
				str = KEYBOARD_INPUT.readLine();
				if(str == null || "".equals(str)) {
					System.out.println("�������ݴ��󣬸����ݲ�����Ϊ��");
					
				}else {
					flag = false;
				}
			} catch (Exception e) {
 				e.printStackTrace();
                System.out.println("����������� �������ݲ�����Ϊ�գ�");

			}
			
		}
		return str;
	}
}

class Test{
	public static void main(String[] args) throws IOException {
 	 
 	}
}