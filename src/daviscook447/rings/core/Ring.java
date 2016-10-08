package daviscook447.rings.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class Ring implements Comparable<Ring> {

	public static final float POINTS_PER_UNIT_LENGTH = 1.0f / 10.0f; // 1pt per 5px
	
	private float angle, radius, angleDelta, thickness;
	private Color color;
	private int priority;
	
	static class Point {
		public float x, y;
		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * Creates a ring
	 * @param angle the angle that the ring starts at
	 * @param radius the inner radius for the ring
	 * @param angleDelta the change in angle, e.g. the angle plus this gets the end angle
	 * @param thickness the thickness of the ring, e.g the radius plus this gets the outer radius
	 * @param x the center x
	 * @param y the center y
	 * @param color the color of the ring
	 * @param priority the priority at which this ring is drawn - higher priority is drawn later, i.e. on top of lower priorities
	 */
	public Ring(float angle, float radius, float angleDelta, float thickness, Color color, int priority) {
		this.angle = angle;
		this.radius = radius;
		this.angleDelta = angleDelta;
		this.thickness = thickness;
		this.color = color;
		this.priority = priority;
	}
	
	/**
	 * Gets the priority at which this ring should be drawn
	 * @return the priority at which this ring should be drawn
	 */
	public int priority() {
		return this.priority;
	}
	
	/**
	 * Gets the radius of this ring
	 * @return the radius of this ring
	 */
	public float radius() {
		return this.radius;
	}

	/**
	 * Gets the thickness of this ring
	 * @return the thickness of this ring
	 */
	public float thickness() {
		return this.thickness;
	}
	
	
	public void drawRing(Graphics2D g2D, float centerX, float centerY, Color drawColor) {
		float farCircumference = angleDelta*(radius+thickness);
		float nearCircumference = angleDelta*radius;
		int farPoints = (int) Math.ceil(farCircumference * POINTS_PER_UNIT_LENGTH);
		int nearPoints = (int) Math.ceil(nearCircumference * POINTS_PER_UNIT_LENGTH);
		Path2D path = new Path2D.Float();
		for (int i = 0; i <= farPoints; i++) {
			float currentAngle = angle + i / (float) farPoints * angleDelta;
			float currentX = centerX + (float) Math.cos(currentAngle)*(radius+thickness);
			float currentY = centerY + (float) Math.sin(currentAngle)*(radius+thickness);
			if (i==0) {
				path.moveTo(currentX, currentY);
			} else {
				path.lineTo(currentX, currentY);
			}
		}
		for (int i = nearPoints; i >= 0; i--) {
			float currentAngle = angle + i / (float) nearPoints * angleDelta;
			float currentX = centerX + (float) Math.cos(currentAngle)*(radius);
			float currentY = centerY + (float) Math.sin(currentAngle)*(radius);
			path.lineTo(currentX, currentY);
		}
		g2D.setColor(drawColor);
		g2D.fill(path);
	}
	
	/**
	 * Draws a ring to a buffer
	 * @param g2D the graphics context being used to draw the ring
	 * @param centerX the x coordinate of the center point for the ring
	 * @param centerY the y coordinate of the center point for the ring
	 */
	public void drawRing(Graphics2D g2D, float centerX, float centerY) {
		drawRing(g2D, centerX, centerY, this.color);
	}

	@Override
	public int compareTo(Ring other) {
		return (this.priority() - other.priority());
	}
	
	public int compareToByPriority(Ring other) {
		return compareTo(other);
	}
	
	public float compareToByRadius(Ring other) {
		return (this.radius() - other.radius());
	}
	
	public float compareToBySize(Ring other) {
		return ((this.radius() + this.thickness()) - (other.radius() + other.thickness()));
	}
	
}
