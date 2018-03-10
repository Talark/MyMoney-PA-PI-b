package com.jcg;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

//comment ...
public class SwingMVCDemo {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void createAndShowGUI() throws Exception {
		JFrame jframe = new TabbedView();
		jframe.pack();
		jframe.setVisible(true);
	}
}
