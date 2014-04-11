package cn;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

public class CnContent extends Observable {
	private static CnContent instance = null;
	
	private Calendar cal;
	private String bodyText;
	
	private CnContent() {
		this.cal = Calendar.getInstance();
		this.cal.setTime(new Date());
	}
	
	public static synchronized CnContent getInstance() {
		if (instance == null) {
			instance = new CnContent();
		}
		return instance;
	}
		
	public String getBodyText() {
		return bodyText;
	}
	
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	
	public Calendar getCal() {
		return cal;
	}
	
	public void setCal(Calendar cal) {
		this.cal = cal;
		this.setChanged();
		this.notifyObservers();
	}

	public void setCal(int year, int month, int date) {
		this.cal.set(year, month, date);
		this.setChanged();
		this.notifyObservers();
	}
}
