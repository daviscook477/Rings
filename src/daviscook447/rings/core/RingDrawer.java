package daviscook447.rings.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class RingDrawer {

	private Color backgroundColor;
	
	public RingDrawer(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public BufferedImage drawRingsToBuffer(int width, int height, int imageType, ArrayList<Ring> rings) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = (Graphics2D) image.getGraphics();
		// set background
		g2D.setColor(backgroundColor);
		g2D.fillRect(0, 0, width, height);
		// draw rings
		Collections.sort(rings, (ring0, ring1) -> ring0.compareTo(ring1)); // first sort by priority
		for (Ring ring : rings) {
			ring.drawToBuffer(image, width/2, height/2);
		}
		return image;
	}
	
	
}
