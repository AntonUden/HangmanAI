package se.zeeraa.HangManAI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HangManAI {
	// I suck at naming variables and functions
	public JFrame f = new JFrame();
	public JPanelWithBackground p = new JPanelWithBackground(null);
	public JTextField[] letterInput;
	public JTextField usedLetters = new JTextField();
	public JButton checkWords = new JButton("Check");
	public JButton reloadWords = new JButton("Reload words");
	public WordList wl = new WordList();
	public JLabel wordLabel = new JLabel("? words left");
	public JLabel recomendedGuess = new JLabel("Recomended guess: ?");
	public JTextArea wordsTextArea = new JTextArea();
	public JScrollPane sp = new JScrollPane(wordsTextArea);
	public int wordLength = 0;

	// called when the program is started
	public static void main(String[] args) {
		System.out.println("Starting");
		// Show settings when program is started
		new HangManAI().init();
	}

	// Show settings
	public void init() {
		System.out.println("Showing settings");
		JFrame fr = new JFrame();
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		JButton selectFile = new JButton("Select file");
		JSpinner numberInput = new JSpinner(new SpinnerNumberModel(1, 1, 35, 1));

		JPanelWithBackground pa = new JPanelWithBackground(getClass().getResourceAsStream("/bg.jpg"));
		JFileChooser jfc = new JFileChooser();

		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));

		numberInput.setMaximumSize(new Dimension(60, 20));

		selectFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc.showOpenDialog(null);
			}
		});

		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (jfc.getSelectedFile() != null) {
					if (!jfc.getSelectedFile().isDirectory()) {
						start(Integer.parseInt(numberInput.getValue() + ""), jfc.getSelectedFile());
						fr.setVisible(false);
						fr.dispose();
						return;
					}
				}
				System.err.println("Invalid file selected");
			}
		});

		cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		numberInput.setAlignmentX(Component.CENTER_ALIGNMENT);
		pa.setBackground(Color.BLACK);
		pa.add(new JLabel(" "));
		pa.add(new JLabel(" "));
		pa.setLayout(new BoxLayout(pa, BoxLayout.Y_AXIS));
		pa.add(numberInput);
		pa.add(new JLabel(" "));
		pa.add(selectFile);
		pa.add(new JLabel(" "));
		pa.add(ok);
		pa.add(new JLabel(" "));
		pa.add(cancel);
		pa.setAlignmentX(Component.CENTER_ALIGNMENT);

		fr.add(pa);
		fr.pack();
		fr.setMinimumSize(new Dimension(fr.getSize().width, fr.getSize().height));
		fr.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
		fr.setSize(new Dimension(fr.getWidth() + 200, fr.getHeight() + 200));
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setMaximumSize(new Dimension(1920, 1200));
		fr.setLocationRelativeTo(null);
		fr.setResizable(true);
		fr.setVisible(true);
	}

	// Start program
	public void start(int letters, File file) {
		System.out.println("Starting main program");
		p = new JPanelWithBackground(getClass().getResourceAsStream("/bg.jpg"));
		wordLength = letters;
		if (!wl.load(file)) {
			System.err.println("Cant load file at " + file.getAbsolutePath());
			System.exit(0);
		}
		JPanel wordInputPanel = new JPanel();
		letterInput = new JTextField[letters];
		wordInputPanel.setLayout(new BoxLayout(wordInputPanel, BoxLayout.X_AXIS));
		for (int i = 0; i < letterInput.length; i++) {
			letterInput[i] = new JTextField("", 1);
			letterInput[i].setMaximumSize(new Dimension(15, 30));
			letterInput[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			wordInputPanel.add(letterInput[i]);
			if (i + 1 < letterInput.length) {
				JLabel ls = new JLabel(" : ");
				ls.setAlignmentX(Component.CENTER_ALIGNMENT);
				wordInputPanel.add(ls);
			}
		}
		wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		wordLabel.setForeground(Color.WHITE);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		reloadWords.setAlignmentX(Component.CENTER_ALIGNMENT);
		reloadWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wl.clear();
				wl.load(file);
				updateWordList();
			}
		});

		recomendedGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
		recomendedGuess.setForeground(Color.WHITE);

		usedLetters.setMaximumSize(new Dimension(200, 20));
		usedLetters.setMinimumSize(new Dimension(80, 20));
		usedLetters.setAlignmentX(Component.CENTER_ALIGNMENT);

		checkWords.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateWordList();
			}
		});

		sp.setAlignmentX(Component.CENTER_ALIGNMENT);
		sp.setMaximumSize(new Dimension(800, 400));
		wordsTextArea.setMaximumSize(new Dimension(800, 400));

		wordInputPanel.setBackground(Color.BLACK);

		wordInputPanel.setLayout(new BoxLayout(wordInputPanel, BoxLayout.X_AXIS));
		p.setBackground(Color.BLACK);
		p.add(new JLabel(""));
		p.add(wordLabel);
		p.add(new JLabel(" "));
		p.add(recomendedGuess);
		p.add(new JLabel(" "));
		JLabel jl2 = new JLabel("Word");
		jl2.setAlignmentX(Component.CENTER_ALIGNMENT);
		jl2.setForeground(Color.WHITE);
		p.add(jl2);
		p.add(wordInputPanel);
		p.add(new JLabel(" "));
		JLabel jl1 = new JLabel("Used letters");
		jl1.setAlignmentX(Component.CENTER_ALIGNMENT);
		jl1.setForeground(Color.WHITE);
		p.add(jl1);
		p.add(usedLetters);
		p.add(new JLabel(" "));
		p.add(checkWords);
		p.add(new JLabel(" "));
		p.add(reloadWords);
		p.add(new JLabel(" "));
		p.add(sp);

		f.add(p);
		f.pack();
		f.setMinimumSize(new Dimension(f.getSize().width, f.getSize().height));
		f.setMaximumSize(new Dimension(1920, 1200));
		f.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
		f.setSize(new Dimension(f.getSize().width + 400, f.getSize().height + 200));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		updateWordList();
	}

	// The name says it all
	public char getBestGuess() {
		String guessedLetters = usedLetters.getText();
		for (int i = 0; i < letterInput.length; i++) {
			guessedLetters += letterInput[i].getText();
		}
		guessedLetters = guessedLetters.toLowerCase();
		System.out.println(guessedLetters);

		Map<Character, Integer> letters = wl.getLettersUsedInMostWords2();
		char bestC = "a".toCharArray()[0];
		int bestI = -1;

		for (Map.Entry<Character, Integer> entry : letters.entrySet()) {
			Character key = entry.getKey();
			int value = entry.getValue();

			if (value > bestI) {
				if (!guessedLetters.contains(key + "")) {
					bestC = key;
					bestI = value;
				}
			}
		}
		return bestC;
	}

	// Removes words that are too large, small or contains the wrong letters
	public void updateWordList() {
		String text = "";
		wl.filterLength(wordLength);
		for (int i = 0; i < letterInput.length; i++) {
			if (letterInput[i].getText().equalsIgnoreCase("") || letterInput[i].getText().length() > 1
					|| letterInput[i].getText().length() == 0) {
				letterInput[i].setText("");
			} else {
				wl.filterLetterAt(i, letterInput[i].getText().toCharArray()[0]);
			}
		}
		for (int j = 0; j < wl.getWords().size(); j++) {
			text += wl.getWords().get(j) + "\n";
		}

		for (int k = 0; k < usedLetters.getText().toCharArray().length; k++) {
			wl.removeWordsThatContains(usedLetters.getText().toCharArray()[k]);
		}
		char bestGuess = getBestGuess();

		recomendedGuess.setText("Recomended guess: " + bestGuess);

		wordsTextArea.setText(text);
		if (wl.getWords().size() == 1) {
			wordLabel.setText(wl.getWords().size() + " Word left");
		} else {
			wordLabel.setText(wl.getWords().size() + " Words left");
		}
	}
}