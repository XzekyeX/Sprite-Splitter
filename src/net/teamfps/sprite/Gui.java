package net.teamfps.sprite;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea ta;
	private JScrollPane scrollPane;
	private JButton btnSaveImage;
	private JTabbedPane tabbedPane;
	private JFileChooser fc;
	private Sprite selected;
	private JSpinner sw;
	private JSpinner sh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setTitle("Sprite Splitter V.0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		btnSaveImage = new JButton("Save Image");
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		ta = new JTextArea();
		scrollPane = new JScrollPane();
		fc = new JFileChooser();

		btnSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected != null) {
					int w = (int) sw.getValue();
					int h = (int) sh.getValue();
					if (w >= 2 && h >= 2) {
						Sprite[] sprites = Sprite.SplitSprite(selected, w, h);
						for (int i = 0; i < sprites.length; i++) {
							println("new Sprite has been created: " + sprites[i].getPath() + ".png");
						}
						for (int i = 0; i < sprites.length; i++) {
							sprites[i].WriteImage("res");
							println("new Sprite has been writed: " + sprites[i].getPath() + ".png");
						}
					} else {
						println("Please set width and height first!");
					}
				} else {
					println("Please select image first!");
				}
			}
		});
		btnSaveImage.setBounds(332, 335, 130, 23);
		contentPane.add(btnSaveImage);

		tabbedPane.setBounds(0, 0, 462, 324);
		contentPane.add(tabbedPane);

		ta.setEditable(false);
		scrollPane.setViewportView(ta);
		ta.setColumns(10);

		tabbedPane.addTab("Info", null, scrollPane, null);
		tabbedPane.addTab("Search", null, fc, null);

		String dir = "" + System.getProperty("user.dir");
		fc.setCurrentDirectory(new File(dir));
		sw = new JSpinner();
		sw.setModel(new SpinnerNumberModel(8, 8, 256, 1));
		sw.setBounds(182, 336, 40, 20);
		contentPane.add(sw);
		sh = new JSpinner();
		sh.setModel(new SpinnerNumberModel(8, 8, 256, 1));
		sh.setBounds(282, 336, 40, 20);
		contentPane.add(sh);

		JLabel lblSubSprite = new JLabel("New Sprites Size");
		lblSubSprite.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubSprite.setBounds(10, 339, 112, 14);
		contentPane.add(lblSubSprite);

		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setBounds(132, 339, 40, 14);
		contentPane.add(lblWidth);

		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(232, 339, 40, 14);
		contentPane.add(lblHeight);

		fc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().contains("ApproveSelection")) {
					setSelected(fc.getSelectedFile());
				}
			}
		});
	}

	protected void println(String str) {
		tabbedPane.setSelectedIndex(0);
		ta.append(str + "\n");
		System.out.println(str);
	}

	private void setSelected(File f) {
		if (f != null) {
			String name = f.getName().toUpperCase();
			if (name.contains(".PNG")) {
				if ((name.substring(name.length() - 4, name.length()).equals(".PNG"))) {
					this.selected = new Sprite(f);
					this.sw.setModel(new SpinnerNumberModel(0, 0, selected.getWidth(), 2));
					this.sh.setModel(new SpinnerNumberModel(0, 0, selected.getHeight(), 2));
					println("new Sprite has been created: " + selected);
				} else {
					System.err.println("Wrong File Type!");
				}
			} else {
				System.err.println("File Not Contains PNG!");
			}
		}
	}
}
