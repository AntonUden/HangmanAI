package se.zeeraa.HangManAI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPanelWithBackground extends JPanel {
	private static final long serialVersionUID = -5287601804511155791L;

	/*
	 * Found this code at
	 * https://stackoverflow.com/questions/1466240/how-to-set-an-image-as-a-
	 * background-for-frame-in-swing-gui-of-java and changed it to work without
	 * a background
	 */
	private Image backgroundImage;
	private boolean useBG = true;

	public JPanelWithBackground(InputStream inputstream) {
		try {
			backgroundImage = ImageIO.read(inputstream);
		} catch (Exception e) {
			useBG = false;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (useBG) {
			g.drawImage(backgroundImage, 0, 0, this);
		}
	}
}
