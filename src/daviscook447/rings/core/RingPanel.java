package daviscook447.rings.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class RingPanel extends JPanel {

	public static final Color RING_BACKGROUND_COLOR = new Color(255, 255, 255);
	
	public static final RingDrawer RING_DRAWER = new RingDrawer(RING_BACKGROUND_COLOR);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8603366025847256676L;
	private ArrayList<Ring> rings;
	private int width, height, padding;
	
	public RingPanel(ArrayList<Ring> rings, int padding) {
		this.rings = rings;
		this.padding = padding;
		Collections.sort(rings, (ring0, ring1) -> (int) ring0.compareToBySize(ring1)); // first sort by radius
		float maxSize = rings.get(rings.size() - 1).radius() + rings.get(rings.size() - 1).thickness();
		width = height = 2 * (int) (maxSize + padding);
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public void setRingList(ArrayList<Ring> rings) {
		this.rings = rings;
		Collections.sort(rings, (ring0, ring1) -> (int) ring0.compareToBySize(ring1)); // first sort by radius
		float maxSize = rings.get(rings.size() - 1).radius() + rings.get(rings.size() - 1).thickness();
		width = height = 2 * (int) (maxSize + padding);
		this.setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		RING_DRAWER.drawRings(g2D, width, height, rings);
	}
	
}
