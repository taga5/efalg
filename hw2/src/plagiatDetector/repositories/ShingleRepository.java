package plagiatDetector.repositories;

import plagiatDetector.util.Shingle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository for storage of all Shingles.
 */
public class ShingleRepository {
	
	private ArrayList<Shingle> shingles;
	private Map<Shingle, Integer> shingleIds; // in addition to the ArrayList that stores the shingle, this Map allows quick lookup for shingles.
	
	public ShingleRepository() {
		shingles = new ArrayList<>();
		shingleIds = new HashMap<>();
	}
	
	public int add(Shingle shingle) {
		if (shingleIds.containsKey(shingle)) return shingleIds.get(shingle);
		int currentIndex = shingles.size();
		shingles.add(shingle);
		shingleIds.put(shingle, currentIndex);
		return currentIndex;
	}
	
	public boolean contains(Shingle shingle) {
		return shingleIds.containsKey(shingle);
	}
	
	public Shingle findByIndex(int index) {
		return shingles.get(index);
	}
	
	public int getSize() {
		return shingles.size();
	}
}
