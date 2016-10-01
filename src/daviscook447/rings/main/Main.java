package daviscook447.rings.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import daviscook447.rings.core.Ring;
import daviscook447.rings.core.RingDrawer;
import daviscook447.rings.gen.dcook.RingLayerGenerator;

public class Main {

	public static final int DEFAULT_WIDTHPX = 1920; // width of rendered image
	public static final int DEFAULT_HEIGHTPX = 1080; // height of rendered image
	
	public static final String IMAGE_FORMAT = "png";
	public static final String DEFAULT_OUT_FILE_PATH = "radial_gradients_2." + IMAGE_FORMAT;
	
	public static final Color BACKGROUND_COLOR = new Color(255,255,255);//new Color(30,30,30);//new Color(255,255,255);
	
	public static void writeBufferedImageToFile(BufferedImage image, File file) throws IOException {
		// use ImageIO to write it
		ImageIO.write(image, IMAGE_FORMAT, file);
	}
	
	// main program handles command line arguments, generating the hex grids, coloring them, and saving the image
	public static void main(String[] args) {
		// command line args and defaults
		int widthPX = DEFAULT_WIDTHPX,
				heightPX = DEFAULT_HEIGHTPX;
		String outFilePath = DEFAULT_OUT_FILE_PATH;
		if (args.length>=1) {
			widthPX = Integer.parseInt(args[0]);
		}
		if (args.length>=2) {
			heightPX = Integer.parseInt(args[1]);
		}
		if (args.length>=3) {
			outFilePath = args[3];
		}
		// make a ring
		Ring ring = new Ring(0.0f,100.0f,(float)Math.PI,25.0f,new Color(0,0,255,255),0); // blue
		Ring ring2 = new Ring((float)Math.PI,100.0f,(float)Math.PI,25.0f,new Color(255,0,0,255),0); // red
		Ring ring3  = new Ring((float)Math.PI/2,100.0f,(float)Math.PI,25.0f,new Color(0,255,0,255),-1); // green
		ArrayList<Ring> rings = new ArrayList<Ring>();
		rings.add(ring);
		rings.add(ring2);
		rings.add(ring3);
		RingLayerGenerator ringLayerGenerator = new RingLayerGenerator();
		rings = ringLayerGenerator.generateLayerWithNoRemovals(0.0f, 100.0f, 25.0f, new Color(0,255,255,200), 0, 3, 0.5f);
		rings.addAll(ringLayerGenerator.generateLayerWithNoRemovals(0.0f, 60.0f, 25.0f, new Color(255,0,0,200), 0, 3, 0.5f));
		rings.addAll(ringLayerGenerator.generateLayerWithNoRemovals((float)Math.PI, 30.0f, 200.0f, new Color(255,255,0,200), 0, 3, 0.2f));
		rings.addAll(ringLayerGenerator.generateLayerWithNoRemovals((float)Math.PI, 215.0f, 30.0f, new Color(255,128,0,200), -1, 1, 1.0f));
		RingDrawer ringDrawer = new RingDrawer(BACKGROUND_COLOR);
		BufferedImage image = ringDrawer.drawRingsToBuffer(widthPX, heightPX, BufferedImage.TYPE_INT_ARGB, rings);
		// save the image as a file
		File outFile = new File(outFilePath);
		try {
			writeBufferedImageToFile(image, outFile);
		} catch (IOException e) {
			System.out.println("Could not write to the output file! An IOException occurred!");
			e.printStackTrace();
		}
	}
	
}
