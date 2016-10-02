package daviscook447.rings.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import daviscook447.rings.core.Ring;
import daviscook447.rings.core.RingDrawer;
import daviscook447.rings.gen.dcook.RandomRingLayerGenerator;
import daviscook447.rings.gen.dcook.RingLayerGenerator;

public class Main {

	public static final int DEFAULT_WIDTHPX = 600; // width of rendered image
	public static final int DEFAULT_HEIGHTPX = 600; // height of rendered image
	
	public static final String IMAGE_FORMAT = "png";
	public static final String DEFAULT_OUT_FILE_PATH = "radial_gradients." + IMAGE_FORMAT;
	
	public static final Color BACKGROUND_COLOR = new Color(255,255,255);//new Color(30,30,30);//new Color(255,255,255);
	
	public static void writeBufferedImageToFile(BufferedImage image, File file) throws IOException {
		// use ImageIO to write it
		ImageIO.write(image, IMAGE_FORMAT, file);
	}
	
	public static void generateRandom(int widthPX, int heightPX, int numberOfLayers, String filepath) {
		RandomRingLayerGenerator rrlg = new RandomRingLayerGenerator(widthPX, heightPX);
		ArrayList<Ring> rings = new ArrayList<Ring>();
		RingDrawer ringDrawer = new RingDrawer(BACKGROUND_COLOR);
		for (int i = 0; i < numberOfLayers; i++) {
			rings.addAll(rrlg.generateRandomWithRemovals());
		}
		BufferedImage image = ringDrawer.drawRingsToBuffer(widthPX, heightPX, BufferedImage.TYPE_INT_ARGB, rings);
		// save the image as a file
		File outFile = new File(filepath);
		try {
			writeBufferedImageToFile(image, outFile);
		} catch (IOException e) {
			System.out.println("Could not write to the output file! An IOException occurred!");
			e.printStackTrace();
		}
	}
	
	// main program handles command line arguments, generating the hex grids, coloring them, and saving the image
	public static void main(String[] args) {
		// command line args and defaults
		int widthPX = DEFAULT_WIDTHPX,
				heightPX = DEFAULT_HEIGHTPX;
		if (args.length>=1) {
			widthPX = Integer.parseInt(args[0]);
		}
		if (args.length>=2) {
			heightPX = Integer.parseInt(args[1]);
		}
		Random rng = new Random();
		for (int i = 0; i < 10; i++) {
			generateRandom(widthPX, heightPX, rng.nextInt(10)+3, "radial_gradients_norm__stuff_"+i+".png");
			System.out.println("PWNED " + i + " NOOBS!");
		}
	}
	
}
