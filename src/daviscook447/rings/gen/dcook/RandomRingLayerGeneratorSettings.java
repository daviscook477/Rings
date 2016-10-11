package daviscook447.rings.gen.dcook;

import java.util.Random;

public class RandomRingLayerGeneratorSettings {

	private static float smallerOf(float f0, float f1) {
		if (f0 < f1) {
			return f0;
		}
		return f1;
	}
	
	public static final RandomRingLayerGeneratorSettings getDefaults(int widthPX, int heightPX) {
		return new RandomRingLayerGeneratorSettings(
			0.0f,
			0.5f * smallerOf(widthPX, heightPX),
			2.0f,
			1,
			12,
			0.2f,
			1.0f,
			"ring"
			);
	}
	
	public static final Random rng = new Random();
	
	public float minRadius, maxRadius, minThickness;
	public int minCount, maxCount;
	public float minPercentFull, maxPercentFull;
	public String filePath;
	
	public RandomRingLayerGeneratorSettings(float minRadius, float maxRadius, float minThickness, int minCount, int maxCount, float minPercentFull, float maxPercentFull, String filePath) {
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.minThickness = minThickness;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.minPercentFull = minPercentFull;
		this.maxPercentFull = maxPercentFull;
		this.filePath = filePath;
	}
	
}
