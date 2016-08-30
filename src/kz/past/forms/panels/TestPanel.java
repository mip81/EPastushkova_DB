package kz.past.forms.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestPanel {
 private JLabel lbl = new JLabel("Testing label");
 private JPanel panel = new JPanel();
 public JPanel getPanel(){
	 panel.add(lbl);
	 
	 return panel;
 }
}
