package daviscook447.rings.gen.dcook;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import daviscook447.rings.art.ColorPalette;
import daviscook447.rings.core.Ring;

public class RandomRingLayerGenerator {

	private int width, height;
	
	private Random rng;
	private ColorPalette cp;
	private RingLayerGenerator rlg;
	
	public static final int NUMBER_OF_COLORS = 24;
	
	public RandomRingLayerGenerator(int width, int height) {
		rng = new Random();
		this.width = width;
		this.height = height;
		rlg = new RingLayerGenerator();
		cp = new ColorPalette(Color.RED, Color.GREEN, NUMBER_OF_COLORS);
	}
	
	private static float smallerOf(float f0, float f1) {
		if (f0 < f1) {
			return f0;
		}
		return f1;
	}
	
	public float randomInRange(float min, float max) {
		return rng.nextFloat() * (max - min) + min;
	}
	
	public static final float MIN_ANGLE = 0.0f;
	public static final float MAX_ANGLE = 2 * (float) Math.PI;
	
	public float randomAngle() {
		return randomInRange(MIN_ANGLE, MAX_ANGLE);
	}
	
	public static final float MIN_RADIUS = 0.0f;
	
	public float randomRadius() {
		return randomInRange(MIN_RADIUS, 0.5f * smallerOf(width, height));
	}
	
	public static final float MIN_THICKNESS = 2.0f;
	
	public float randomThickness(float radius) {
		return randomInRange(MIN_THICKNESS, 0.5f * smallerOf(width - 2*radius, height - 2*radius));
	}
	
	public Color randomColor() {
		//return cp.getPalette()[rng.nextInt(NUMBER_OF_COLORS)];
		return new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
	}
	
	public static final float MIN_COUNT = 1;
	public static final float MAX_COUNT = 12;
	public static final float MAX_COUNT_PER_RADII = 0.15f;

	public int randomCount(float radius) {
		return(int) randomInRange(MIN_COUNT, MAX_COUNT); //MAX_COUNT_PER_RADII * radius);
	}
	
	public static final float MIN_PERCENT_FULL = 0.2f;
	public static final float MAX_PERCENT_FULL = 1.0f;
	
	public float randomPercentFull() {
		return randomInRange(MIN_PERCENT_FULL, MAX_PERCENT_FULL);
	}
	
	public ArrayList<Ring> generateRandom() {
		float angle = randomAngle();
		float radius = randomRadius();
		float thickness = randomThickness(radius);
		Color color = randomColor();
		int priority = 0;
		int count = randomCount(radius);
		boolean[] removals = new boolean[count];
		float percentFull = randomPercentFull();
		return rlg.generateLayerWithSpecificRemovals(angle, radius, thickness, color, priority, count, removals, percentFull);
	}
	
	public ArrayList<Ring> generateRandomWithRemovals() {
		float angle = randomAngle();
		float radius = randomRadius();
		float thickness = randomThickness(radius);
		Color color = randomColor();
		int priority = 0;
		int count = randomCount(radius);
		boolean[] removals = new boolean[count];
		for (int i = 0; i < count; i++) {
			removals[i] = rng.nextBoolean();
		}
		float percentFull = randomPercentFull();
		return rlg.generateLayerWithSpecificRemovals(angle, radius, thickness, color, priority, count, removals, percentFull);
	}
	
}
