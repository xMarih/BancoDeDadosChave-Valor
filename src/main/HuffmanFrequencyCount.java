package main;

import java.util.HashMap;
import java.util.List;

/*
 * This is the class that will return a map 
 */
public class HuffmanFrequencyCount {
	
	/*
	 * The map to be returned
	 */
	static HashMap<Character, Integer> count;
	
	/*
	 * Constructs the map with the input of list of BigChars
	 */
	public HuffmanFrequencyCount(List<Character> inputChars) {
		count = new HashMap<Character, Integer>();
		counstructMap(inputChars);
	}

	/*
	 * Method for construction
	 */
	private void counstructMap(List<Character> inputChars) {

		for(int i=0; i<inputChars.size(); i+=1) {
			if(count.containsKey(inputChars.get(i)))  {
				count.put(inputChars.get(i), count.get(inputChars.get(i)) + 1);
			} else{
				count.put(inputChars.get(i), 1);
			}
		}
	}
	
	/*
	 * To return the map
	 */
	public HashMap<Character, Integer> getFrequencyMap() {
		return count;
	}
	
	/*
	 * This is for testing purposes
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return count.toString();
	}

}
