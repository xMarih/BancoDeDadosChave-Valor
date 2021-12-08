package main;

/*
 * Code by Thang Tran
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/*
 * Please Read:
 * I checked the output of the codes.txt file and found a nearly exact match to the bit string as well as order.
 * I isolated what part of the code took so long to the Main code.  I also tested both my codeStr and codes.
 * I also changed the way I generated my bit strings (instead of doing it when making my tree) I am just traversing it after 
 * I am done making the tree.  You will notice unused methods that were part of that method.  Both methods produce the same output.   
 * I believe the extended length of run time might be due to my computer being slow but even if I am wrong 20 seconds isn't too bad.
 * The time it takes to compressed my pg2600 txt ("War and Peace" a much longer book) takes 26.2 seconds on my computer with a 56% 
 * compression ratio
 */

/*
 * This class will be the data structure used to implement Huffman encoding
 */
public class HuffmanCodingTree {

	/*
	 * headtree is the root of the whole tree
	 * count is the map that takes in a relative frequency
	 * temp and Codebuilder are stringbuilders for generating the bit strings
	 */
	public String codeStr;
	public List<HuffmanCode> codes;
	HashMap<Character, Integer> count;
	public HuffmanNode headtree;
	public StringBuilder codeBuilder;
	public StringBuilder temp;
	
	/*
	 * Constructor, calls construct to implement the construction
	 */
	public HuffmanCodingTree(List<Character> inputchars) {
		codes = new ArrayList<HuffmanCode>();
		construct(inputchars);
	}

	/*
	 * This will make the tree and from that tree initialize the correct codeStr and codes
	 */
	private void construct(List<Character> inputchars) {
		//get frequency count map
		HuffmanFrequencyCount frequency = new HuffmanFrequencyCount(inputchars);
		count = frequency.getFrequencyMap();
		
		//iterate through map to make nodes and
		//put nodes into priority queue
		PriorityQueue<HuffmanNode> nodequeue = makePriorityQueue(frequency.getFrequencyMap()); 
		
		
		
		//use priority queue to construct final tree until there is only 1
		while(nodequeue.size()>1) {
			HuffmanNode left = nodequeue.poll();
			HuffmanNode right = nodequeue.poll();
			//left.append("0");
			//right.append("1");
			HuffmanNode root = new HuffmanNode(0, null);
			root.pointLeft(left);
			root.pointRight(right);
			nodequeue.offer(root);
		}
		headtree = nodequeue.poll();
		
		//iterate through tree to make the string for codeStr
		//codeStr = constructCodeStr();
		constructCodeStr();
		temp = new StringBuilder();
		codeBuilder = new StringBuilder();
		constructCodeBuilder(headtree);
		codeStr = codeBuilder.toString();
		//System.out.print(codeStr);
		
		
		//scan through codeStr to make codes
//		Scanner scan = new Scanner(codeStr);
//		while(scan.hasNext()) {
//			if(scan.hasNext()) {
//				char temp = scan.next().charAt(0);
//				if(scan.hasNext()) {
//				Code code = new Code(temp, scan.next());
//				codes.add(code);
//				}
//			}
//		}
		
	}

	//Unused method for generating bit strings
	private String constructCodeStr() {
		return headtree.StringBuilder().toString();
	}

	//Gets the set of keys from map and iterates through set to put created nodes into priority queue
	private PriorityQueue<HuffmanNode> makePriorityQueue(
			HashMap<Character, Integer> frequencyMap) {
		PriorityQueue<HuffmanNode> out_queue = new PriorityQueue<HuffmanNode>();
		Set<Character> charSet = frequencyMap.keySet();
		for(Character ichar : charSet) {
			out_queue.offer(new HuffmanNode(frequencyMap.get(ichar), ichar));
		}
		
		return out_queue;
	}
	
	//Method to test out certain variables
	/*public void printThis() {
		
		System.out.println("Codes: " + codes.toString());
		System.out.println("CodeStr: " + codeStr);
		System.out.println("Hashmap" + count.toString());
		
	}*/
	
	//Current method for getting bit strings (note other method is in the node class itself and is unused)
	private void constructCodeBuilder(HuffmanNode input) {
		if(input.left == null && input.right == null) {
			codeBuilder.append("\n"+ input.getCharacter() +" " + temp.toString());
			codes.add(new HuffmanCode(input.getCharacter(), temp.toString()));
		}
		
		temp.append("0");
		if(input.left != null) {
			constructCodeBuilder(input.left);	
		}
		
		temp.deleteCharAt(temp.toString().length()-1);
		temp.append("1");
		
		if(input.right != null) {
		constructCodeBuilder(input.right);
		}
		temp.deleteCharAt(temp.toString().length()-1);
		
	}
}
