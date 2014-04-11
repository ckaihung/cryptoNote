package cn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

public class MainWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnOption;
	private JMenuItem mntmLogin;
	private JMenuItem mntmResetNotebook;
	private JMenuItem mntmAbout;
	private JPanel leftPanel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JMenu mnFile;
	private JMenuItem mntmSave;
	private JMenuItem mntmQuit;
	private JPanel optionPanel;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	class StatusLabel extends JLabel implements Observer {
		@Override
		public void update(Observable arg0, Object arg1) {
			CnContent content = CnContent.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("Y/M/d");
			String dateStr = formatter.format(content.getCal().getTime());
			this.setText(dateStr);
		}
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		initialize();
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		mntmQuit = new JMenuItem("Quit");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnFile.add(mntmQuit);
		
		mnOption = new JMenu("Option");
		menuBar.add(mnOption);
		
		mntmLogin = new JMenuItem("Login");
		mnOption.add(mntmLogin);
		
		mntmResetNotebook = new JMenuItem("Reset Notebook");
		mnOption.add(mntmResetNotebook);
		
		mntmAbout = new JMenuItem("About");
		mnOption.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel calendarPanel = new CalendarPanel();
		leftPanel.add(calendarPanel, BorderLayout.NORTH);
		
		contentPane.add(leftPanel, BorderLayout.WEST);
		
		optionPanel = new JPanel();
		leftPanel.add(optionPanel);
		
		lblStatus = new StatusLabel();
		lblStatus.setText(" ");
		Border topLine = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY);
		lblStatus.setBorder(topLine);
		leftPanel.add(lblStatus, BorderLayout.SOUTH);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane = new JScrollPane(textArea);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		// add listener
		CnContent content = CnContent.getInstance();
		content.addObserver((Observer) lblStatus);
	}
	
	public void setStatus(String str) {
		this.lblStatus.setText(str);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CnContent content = CnContent.getInstance();
		Date date = content.getCal().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("Y/M/d");
		formatter.format(date);		
	}

}
