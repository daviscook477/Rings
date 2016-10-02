package daviscook447.rings.art;

import java.awt.Color;

public class ColorPalette {

	private Color[] colors;
	
	public ColorPalette(Color c0, Color c1, int numColors) {
		if (numColors < 2) {
			throw new IllegalArgumentException("numColors must be >= 2");
		}
		colors = new Color[numColors];
		for (int i = 0; i < numColors; i++) {
			float percent0 = ((float) i) / (numColors - 1);
			float percent1 = ((float) (numColors - 1 - i)) / (numColors - 1);
			System.out.println(percent0 + ":" + percent1);
			colors[i] = new Color((int)(c0.getRed()*percent0+c1.getRed()*percent1),
					(int)(c0.getGreen()*percent0+c1.getGreen()*percent1),
					(int)(c0.getBlue()*percent0+c1.getBlue()*percent1));
		}
	}
	
	public Color[] getPalette() {
		return colors;
	}
	
	public static void main(String[] args) {
		ColorPalette test = new ColorPalette(Color.RED, Color.GREEN, 5);
		Color[] palette = test.getPalette();
		for (int i = 0; i < palette.length; i++) {
			System.out.println(palette[i]);
		}
	}
	
}
