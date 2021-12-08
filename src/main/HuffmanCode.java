package main;

/*
 * Code by Dr. Marriot
 */

public class HuffmanCode {
	char c;
	String bits;
	public HuffmanCode(char cha, String b){
		c = cha;
		bits = b;
	}
	
	public String toString(){
		return "("+c+", "+bits+")";
	}
}
