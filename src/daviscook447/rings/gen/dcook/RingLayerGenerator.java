package daviscook447.rings.gen.dcook;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import daviscook447.rings.core.Ring;

public class RingLayerGenerator {

	public static final float TWO_PI = (float) Math.PI * 2;
	
	public RingLayerGenerator() {}
	
	public ArrayList<Ring> generateLayerWithSomeNumberOfRemovals(float angle, float radius, float thickness, Color color, int priority,
			 int count, int countRemoved, float percentFull) {
		// randomly remove countRemoved slices
		boolean[] removals = new boolean[count];
		ArrayList<Integer> intsInRange = new ArrayList<Integer>(count);
		for (int i = 0; i < count; i++) {
			intsInRange.add(i);
		}
		Random rng = new Random();
		for (int i = 0; i < (count - countRemoved); i++) {
			intsInRange.remove(rng.nextInt(intsInRange.size()));
		}
		for (int i : intsInRange) {
			System.out.println("removing " + i);
			removals[i] = true;
		}
		 // use new boolean[count] b/c it auto initializes to false
		return generateLayerWithSpecificRemovals(angle, radius, thickness, color, priority, count, removals, percentFull);
	} 
	
	public ArrayList<Ring> generateLayerWithNoRemovals(float angle, float radius, float thickness, Color color, int priority,
			 int count, float percentFull) {
		 // use new boolean[count] b/c it auto initializes to false
		return generateLayerWithSpecificRemovals(angle, radius, thickness, color, priority, count, new boolean[count], percentFull);
	}
	
	public ArrayList<Ring> generateLayerWithSpecificRemovals(float angle, float radius, float thickness, Color color, int priority,
			 int count, boolean[] removals, float percentFull) {
		if (count != removals.length) {
			throw new IllegalArgumentException();
		}
		ArrayList<Ring> rings = new ArrayList<Ring>(count);
		float angleDelta = TWO_PI / count * percentFull;
		for (int i = 0; i < count; i++) {
			// only add a ring if it isn't supposed to be removed
			if (!removals[i]) {
				float currentAngle = angle + (TWO_PI / count * i) - (angleDelta / 2);
				rings.add(new Ring(currentAngle, radius, angleDelta, thickness, color, priority));
			}
		}
		return rings;
	}
	
}
