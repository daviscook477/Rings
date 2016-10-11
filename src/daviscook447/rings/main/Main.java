package daviscook447.rings.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import daviscook447.rings.core.Ring;
import daviscook447.rings.core.RingDrawer;
import daviscook447.rings.core.RingPanel;
import daviscook447.rings.gen.dcook.RandomRingLayerGenerator;
import daviscook447.rings.gen.dcook.RandomRingLayerGeneratorSettings;

public class Main {

	public static final int DEFAULT_WIDTHPX = 800; // width of rendered image
	public static final int DEFAULT_HEIGHTPX = 800; // height of rendered image
	
	public static final String IMAGE_FORMAT = "png";
	public static final String DEFAULT_OUT_FILE_PATH = "radial_gradients." + IMAGE_FORMAT;
	
	public static final Color BACKGROUND_COLOR = new Color(255,255,255);//new Color(30,30,30);//new Color(255,255,255);
	
	public static void writeBufferedImageToFile(BufferedImage image, File file) throws IOException {
		// use ImageIO to write it
		ImageIO.write(image, IMAGE_FORMAT, file);
	}
	
	public static void generateRandom(int widthPX, int heightPX, int numberOfLayers, String filepath, RandomRingLayerGeneratorSettings settings) {
		RandomRingLayerGenerator rrlg = new RandomRingLayerGenerator(settings);
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
	
	public static final int PADDING = 20;
	
	public static final ArrayList<Ring> getRandomRings(int widthPX, int heightPX, RandomRingLayerGeneratorSettings settings) {
		Random rng = new Random();
		RandomRingLayerGenerator rrlg = new RandomRingLayerGenerator(settings);
		ArrayList<Ring> rings = new ArrayList<Ring>();
		for (int i = 0; i < rng.nextInt(10)+3; i++) {
			rings.addAll(rrlg.generateRandomWithRemovals());
		}
		return rings;
	}
	
	public static JButton buildChangeButton(ArrayList<Ring> rings, int widthPX, int heightPX, RingPanel ringPanel, RandomRingLayerGeneratorSettings settings) {
		JButton jButton = new JButton("Change Rings");
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rings.removeAll(rings);
				rings.addAll(getRandomRings(widthPX, heightPX, settings));
				ringPanel.setRingList(rings, false);
				ringPanel.repaint();
			}
			
		});
		return jButton;
	}
	public static JButton buildSaveButton(ArrayList<Ring> rings, int widthPX, int heightPX, RandomRingLayerGeneratorSettings settings) {
		JButton jButton = new JButton("Save Rings");
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RingDrawer ringDrawer = new RingDrawer(BACKGROUND_COLOR);
				BufferedImage image = ringDrawer.drawRingsToBuffer(widthPX, heightPX, BufferedImage.TYPE_INT_ARGB, rings);
				File outFile = new File(settings.filePath + ".png");
				try {
					writeBufferedImageToFile(image, outFile);
				} catch (IOException e) {
					System.out.println("Could not write to the output file! An IOException occurred!");
					e.printStackTrace();
				}
			}
			
		});
		return jButton;
	}
	
	public static float randomInRange(float min, float max) {
		Random rng = new Random();
		return rng.nextFloat() * (max - min) + min;
	}
	
	private static float smallerOf(float f0, float f1) {
		if (f0 < f1) {
			return f0;
		}
		return f1;
	}
	
	public static JButton buildRandomizeSettingsButton(int widthPX, int heightPX, RandomRingLayerGeneratorSettings settings) {
		JButton jButton = new JButton("Randomize Settings");
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				settings.maxRadius = randomInRange(settings.minRadius, 0.5f * smallerOf(widthPX, heightPX));
				
			}
			
		});
		return jButton;
	}
	
	public static float clamp(float a, float b, float c) {
		if (a < b) {
			return b;
		}
		if (a > c) {
			return c;
		}
		return a;
	}
	
	public static JTextField buildRadiusSettingsField(int widthPX, int heightPX, RandomRingLayerGeneratorSettings settings) {
		JTextField textField = new JTextField(3);
		
		textField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changeSettings();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changeSettings();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  changeSettings();
			  }
			  
			  public void changeSettings() {
				  try {
					  settings.maxRadius = clamp(Float.parseFloat(textField.getText()), 0.0f, 0.5f * smallerOf(widthPX, heightPX));
				  } catch (Exception e ) {
					  JOptionPane.showMessageDialog(null,
			                    "Invalid Radius!", "Error Message",
			                    JOptionPane.ERROR_MESSAGE);
				  }
				  
			  }
			});
		
		return textField;
	}
	
	public static JPanel buildOptionsPanel(ArrayList<Ring> rings, int widthPX, int heightPX, RandomRingLayerGeneratorSettings settings) {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());
		jPanel.add(buildRandomizeSettingsButton(widthPX, heightPX, settings));
		jPanel.add(new JLabel("Radius: "));
		jPanel.add(buildRadiusSettingsField(widthPX, heightPX, settings));
		return jPanel;
	}
	
	public static JTextField buildSaveLocationTextField(RandomRingLayerGeneratorSettings settings) {
		JTextField textField = new JTextField();
		
		textField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changeSettings();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changeSettings();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  changeSettings();
			  }
			  
			  public void changeSettings() {
				  try {
					  settings.filePath = textField.getText();
				  } catch (Exception e ) {
					  JOptionPane.showMessageDialog(null,
			                    "Invalid Radius!", "Error Message",
			                    JOptionPane.ERROR_MESSAGE);
				  }
				  
			  }
			});
		
		return textField;
	}
	
	public static JFrame buildJFrame(RingPanel ringPanel, ArrayList<Ring> rings, int widthPX, int heightPX, RandomRingLayerGeneratorSettings settings) {
		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.add(ringPanel,BorderLayout.CENTER);
		JPanel leftSidePanel = new JPanel();
		leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));
		leftSidePanel.add(buildSaveLocationTextField(settings));
		leftSidePanel.add(buildSaveButton(rings, widthPX, heightPX, settings));
		leftSidePanel.add(buildChangeButton(rings, widthPX, heightPX, ringPanel, settings));
		jFrame.add(leftSidePanel, BorderLayout.WEST);
		jFrame.add(buildOptionsPanel(rings, widthPX, heightPX, settings), BorderLayout.NORTH);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setTitle("Ring Generator");
		return jFrame;
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
		RandomRingLayerGeneratorSettings settings = RandomRingLayerGeneratorSettings.getDefaults(widthPX, heightPX);
		ArrayList<Ring> rings = getRandomRings(widthPX, heightPX, settings);
		RingPanel ringPanel = new RingPanel(rings, widthPX, heightPX);
		@SuppressWarnings("unused")
		JFrame jFrame = buildJFrame(ringPanel, rings, widthPX, heightPX, settings);
	}
	
}
