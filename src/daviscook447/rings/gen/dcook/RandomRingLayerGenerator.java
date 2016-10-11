package daviscook447.rings.gen.dcook;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import daviscook447.rings.art.ColorPalette;
import daviscook447.rings.core.Ring;

public class RandomRingLayerGenerator {

	private Random rng;
	private ColorPalette cp;
	private RingLayerGenerator rlg;
	private RandomRingLayerGeneratorSettings settings;
	
	public static final int NUMBER_OF_COLORS = 24;
	
	public RandomRingLayerGenerator(RandomRingLayerGeneratorSettings settings) {
		rng = new Random();
		rlg = new RingLayerGenerator();
		cp = new ColorPalette(Color.RED, Color.GREEN, NUMBER_OF_COLORS);
		this.settings = settings;
	}
	
	public float randomInRange(float min, float max) {
		return rng.nextFloat() * (max - min) + min;
	}
	
	public static final float MIN_ANGLE = 0.0f;
	public static final float MAX_ANGLE = 2 * (float) Math.PI;
	
	public float randomAngle() {
		return randomInRange(MIN_ANGLE, MAX_ANGLE);
	}
	
	public float randomRadius() {
		return randomInRange(settings.minRadius, settings.maxRadius);
	}
	
	public float randomThickness(float radius) {
		return randomInRange(settings.minThickness, settings.maxRadius - radius);
	}
	
	public Color randomColor() {
		//return cp.getPalette()[rng.nextInt(NUMBER_OF_COLORS)];
		return new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
	}
	
	public static final float MAX_COUNT_PER_RADII = 0.15f;

	public int randomCount(float radius) {
		return(int) randomInRange(settings.minCount, settings.maxCount); //MAX_COUNT_PER_RADII * radius);
	}

	public float randomPercentFull() {
		return randomInRange(settings.minPercentFull, settings.maxPercentFull);
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
