package cn;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CalendarPanel extends JPanel implements ItemListener {
	
	private static final long serialVersionUID = 1L;
	
	JPanel p1, p2;
	JButton btn_today;
	JComboBox<ComboItem<Integer, String>> month;
	JComboBox<Integer> year;
	String weekdays[] = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	String months[] = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" };
	
	class ComboItem<K, V> {
		private K key;
		private V value;
		
		public ComboItem(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return this.value.toString();
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
	}
	
	class TodayAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Calendar cal = Calendar.getInstance();
			drawCalendar(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
		}		
	}

	public CalendarPanel(String title) {
		super();
		p1 = new JPanel();
		setVisible(true);
		// p1.setSize(350, 30);
		// p1.setLayout(new FlowLayout());
		month = new JComboBox<ComboItem<Integer, String>>();
		for (int i = 0; i < months.length; i++) {
			month.addItem(new ComboItem<Integer, String>(i, months[i]));
		}
		month.addItemListener(this);
		year = new JComboBox<Integer>();
		for (int i = 1980; i <= 2099; i++) {
			year.addItem(i);
		}
		year.addItemListener(this);
		
		btn_today = new JButton(new TodayAction());
		btn_today.setText("Today");
		
		p1.add(month);
		p1.add(year);
		p1.add(btn_today);
		
		p2 = new JPanel();
		p2.setLayout(new GridLayout(0, 7, 5, 5));
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		drawCalendar(cal.get(Calendar.MONTH), (cal.get(Calendar.YEAR)));
		
		new Integer(cal.get(Calendar.YEAR));
		year.setSelectedItem(cal.get(Calendar.YEAR));		
		month.setSelectedIndex(cal.get(Calendar.MONTH));
		
		this.setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		setVisible(true);
		setSize(230, 220);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			drawCalendar(((ComboItem<Integer, String>) month.getSelectedItem()).getKey(),
					(Integer) year.getSelectedItem());
		}
	}

	public void drawCalendar(int inputMonth, int inputYear) {
		p2.removeAll();
		for (int i = 0; i < weekdays.length; i++) {
			JLabel label = new JLabel(weekdays[i]);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			p2.add(label);
		}
		
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		
		Calendar cal = new GregorianCalendar(inputYear, inputMonth, 1);
		int noOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int day = 1;
		int i = 1;
		while (day <= noOfDaysInMonth) {
			while (day==1 && i < cal.get(Calendar.DAY_OF_WEEK)) {
				JLabel label = new JLabel("");
				p2.add(label);
				i++;
			}
			JLabel label = new JLabel(String.valueOf(day));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			
			if (	inputYear==today.get(Calendar.YEAR) && 
					inputMonth==today.get(Calendar.MONTH) && 
					day==today.get(Calendar.DAY_OF_MONTH)) {
				Font font = label.getFont();
				Font boldFont = new Font(font.getFontName(), Font.BOLD, 14);
				label.setFont(boldFont);
			}
			p2.add(label);
			
			
			
			day++;
		}
		p2.validate();
	}
}