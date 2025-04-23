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

	private int number;
	private String name;

	public int mainClassMethod1(int a, int b){
		return a + b;
	}

	public void mainClassMethod2 (int a, int b, char c) {
		//comment
		System.out.println("aaaa");
	}

	public String randomMethodStringReturn(int a, int b) throws Exception {
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

//some more comments
//some more comments
//
//
 /*
  *multiline comms
  multiline comms
  * //
  */

class InnerClass {
	public int innerClassMethod1 (int a, int b) throws Something{
		return a+b;
	}

	public String innerClassMethod2 (String a, String b) throws SomethingElse{
		return a+b;
	}

	public boolean innerClassMethod3 (String a, String b){
		return a.equals(b);
	}
}
