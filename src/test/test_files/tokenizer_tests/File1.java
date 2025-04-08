/*
 * File1.java - test file with one class
 * comment
 */

package testpackage;

import java.util.Deque;
import java.lang.Integer;

// comment line
// coment line
// comment line

class MainClass {

	public int mainClassMethod1(int a, int b){
		return a + b;
	}

	public void mainClassMethod2 (int a, int b, char c) {
		//comment
		System.out.println("aaaa");
	}

	public String randomMethodStringReturn(int a, int b)   {
		/* multi line coment
		to include here */
		int c = a + b;
		return "222";
	}

	public void randomMethodVoid__2_ (int a, int b) {
		System.out.println("222");
	}

// comment in class scope
}

class InnerClass {
	public int innerClassMethod1 (int a, int b){
		return a+b;
	}

	public String innerClassMethod2 (String a, String b){
		return a+b;
	}

	public boolean innerClassMethod3 (String a, String b){
		return a.equals(b);
	}
}
