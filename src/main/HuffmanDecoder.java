package main;


	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Scanner;


	public class HuffmanDecoder {


		public static void Decodificador () throws IOException {
			long start = System.currentTimeMillis();
			long duration = 0;
			
			FileReader inputStream = null;
			String fileName = "src/dados/codes.txt";
			String fileTextName = "src/dados/compressed.txt";
			FileOutputStream outputStream = new FileOutputStream(new File("C:src/dados/uncompressed.txt"));
		
			
			try {
					inputStream = new FileReader(fileTextName);
					FileReader inputStreamCode = new FileReader(fileName);
					
					int c;
					int b;
					List<Character> chars = new ArrayList<Character>();
					List<Character> codeChar = new ArrayList<Character>();
					
					// read characters into a list of characters
					while ((c = inputStream.read()) != -1) {
						chars.add((char)c);
					}
					inputStream.close();
					
					while ((b = inputStreamCode.read()) != -1) {
						codeChar.add((char)b);
					}
					inputStreamCode.close();
					
					//Construct a tree to decode with
					
					
					
		
			} finally {}
			
			System.out.println("O Arquivo foi descomprimido");
		
		}
		
		

	}


