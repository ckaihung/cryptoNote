package controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import cn.model.CnContent;

public class DateAction extends AbstractAction {
	private int year;
	private int month;
	private int date;
	
	/**
	 * @param year
	 * @param month 0-Jan, 1-Feb, ..., 11-Dec
	 * @param date
	 */
	public DateAction(int year, int month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		CnContent content = CnContent.getInstance();
		content.setCal(year, month, date);
	}

}
