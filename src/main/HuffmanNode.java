package main;

/*
 * This is the node class that would be the tree
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
	
	/*
	 * Fields
	 * weight = sum of weights of all nodes below it or the weight initially assigned
	 * Character = the Character is holds
	 * right and left are the leafs
	 * bit is a StringBuilder that is part of the 2nd method for generating the bit strings as you make the tree
	 */
	int weight;
	Character character;
	HuffmanNode right;
	HuffmanNode left;
	StringBuilder bit;
	
	
	/*
	 * Constructs a node with right and left as null, note inputChar will used as a null
	 */
	public HuffmanNode(Integer input_weight, Character inputChar) {
		weight = input_weight.intValue();
		character = inputChar;
		bit = new StringBuilder();
		right = null;
		left = null;
	}
	
	//sets right/left to the argument and adds the weight of that input node
	public void pointRight(HuffmanNode inputNode) {
		right = inputNode;
		weight += inputNode.weight;
	}
	public void pointLeft(HuffmanNode inputNode) {
		left = inputNode;
		weight += inputNode.weight;
	}
	
	//Character setter
	public void setCharacter(char inputChar) {
		character = new Character(inputChar);
	}
	
	/*
	 * Original method for generating inputBits, nodes would have a variable that would be changed whenever two nodes are united by a root
	 */
	public void append(String inputBit) {
		if(right == null && left == null) {
			bit.append(inputBit);
		}
		
		if(left != null) {
			left.append(inputBit);
		}
		
		if(right != null) {
			right.append(inputBit);
		}
	}
	
	/*
	 * Builds the string for the tree
	 */
	public StringBuilder StringBuilder() {
		StringBuilder sb = new StringBuilder();
		build(sb);
		return sb;	
	}
	
	/*
	 * Recursive function for making the tree
	 */
	public void build(StringBuilder input) {
		if(right == null && left == null) {
			
			input.append(this.getCharacter() + " " + this.getBit() + "\n");
			
		}
		
		if(left != null) {
			left.build(input);
		}
		
		if(right != null) {
			right.build(input);
		}
	}
	
	


	/*
	 * Compares o to this,  -1 if this is less in weight then o etc
	 */
	@Override
	public int compareTo(HuffmanNode o) {
		int output = 0;
		
		if(this.weight < o.getWeight()) {
			output = -1;
		} else if(this.weight > o.getWeight()){
			output = 1;
		}
		
		return output;
	}
	
	//Accessors
	public int getWeight() {
		return weight;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public String getBit() {
		return bit.reverse().toString();
	}
	
	//For testing purposes only
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nWeight:" + weight);
		sb.append(" Character:" + character);
		sb.append(" Bit:" + bit);
		sb.append(left);
		sb.append(right);
		return sb.toString();
	}

}
