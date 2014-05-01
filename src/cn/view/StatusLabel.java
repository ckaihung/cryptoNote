package cn.view;

import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import cn.model.CnContent;

public class StatusLabel extends JLabel implements Observer {
	public StatusLabel() {
		super();
		addListener();
	}
	
	public StatusLabel(String text) {
		super(text);
		addListener();
	}
	
	private void addListener() {
		CnContent content = CnContent.getInstance();
		content.addObserver((Observer) this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		CnContent content = CnContent.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("Y/M/d");
		String dateStr = formatter.format(content.getCal().getTime());
		this.setText(dateStr);
	}
}
