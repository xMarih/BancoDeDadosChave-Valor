package main;

import java.io.*;
import java.util.*;

public class HuffmanCodificador {

	public static void Codificar () throws IOException {
		long start = System.currentTimeMillis();
		long duration = 0;
		
		FileReader inputStream = null;
		String fileName = "src/dados/BancoDeDados.txt";
		FileOutputStream outputStream = new FileOutputStream(new File("src/dados/compressed.txt"));
		FileOutputStream codeStream = new FileOutputStream(new File("src/dados/codes.txt"));
		
		try {
				inputStream = new FileReader(fileName);
				int c;
				List<Character> chars = new ArrayList<Character>();
				
				// lê os caracteres da lista de caracteres
				while ((c = inputStream.read()) != -1) {
					chars.add((char)c);
				}
				inputStream.close();

				// passa a lista de caracters para o construtor 
				
				HuffmanCodingTree ct = new HuffmanCodingTree(chars);
				//ct.printThis();
				
				
				//Para mostrar quanto tempo leva para fazer  parte do código
				//System.out.println("Time it took to make Tree: " + (System.currentTimeMillis() - start));
				
				//ct.printThis(); 
				// escrevendo arquivo code
				
				codeStream.write(ct.codeStr.getBytes());
				codeStream.close();
				
	
				
				List<String> codes = new ArrayList<String>();
				
				
				for(int i = 0; i < 256; i++){
					codes.add("");
				}
				
				for(int i = 0; i < ct.codes.size(); i++){
					codes.set((int)ct.codes.get(i).c, ct.codes.get(i).bits);
				}
			
				
				
				
				String buffer = "";
				long asciiCost = chars.size()*8;
				long compressedCost = 0;
				
				
				//System.out.println("Duration check " + (System.currentTimeMillis() - start));
				/*
				 * This part of the code until the next duration check comment takes 19 seconds on my computer
				 */
				
				for(int i = 0; i < chars.size(); i++){
					// inefficient since String += is O(n) where n is the length of the string
					buffer += codes.get((int)chars.get(i));;
					if(buffer.length() > 256){
						
						while(buffer.length() > 8){	
							int chr = Integer.parseInt(buffer.substring(0, 8),2);
							outputStream.write(chr);
							buffer = buffer.substring(8, buffer.length());
							compressedCost += 8;
						}
					}					
				}
				
				//System.out.println("Duration Check " + (System.currentTimeMillis() - start));
				
				compressedCost += buffer.length();
				while(buffer.length() > 8){
					int chr = Integer.parseInt(buffer.substring(0, 7),2);
					outputStream.write(chr);
					buffer = buffer.substring(8, buffer.length());
				}
				outputStream.close();
				
				duration = System.currentTimeMillis() - start;

				System.out.println("Tamanho do arquivo original: " + asciiCost/8 + " bytes");
				System.out.println("Tamanho do arquivo após a compressão: " + compressedCost/8 + " bytes");
				System.out.println("Porcentagem de compressão: " + compressedCost*100/asciiCost + "%");
				System.out.println("Tempo de Compressão: " + duration + " milliseconds");
		} finally {}
	
	}

}