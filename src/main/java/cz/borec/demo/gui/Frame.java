package cz.borec.demo.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class Frame extends JFrame {
	
	JPanel panelTable = new JPanel();
	JPanel panelActions = new JPanel();
	JTable table = new JTable();
	JLabel label = new JLabel();
    JFormattedTextField textField = new JFormattedTextField();

	@Override
	protected void frameInit() {
		super.frameInit();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setTitle("Tabulka se zvýraznìnými prvoèísly");
	}

	private void setUIComponents() {
		setLayout(new BorderLayout());
		panelTable.setLayout(new BorderLayout());
		panelTable.add(table, BorderLayout.CENTER);
		add(panelTable, BorderLayout.CENTER);
		label.setText("Èísla od:");
	    textField.setValue(new Integer(10000));
		panelActions.add(label);
		panelActions.add(textField);
		add(panelActions, BorderLayout.SOUTH);

	}

	public Frame() throws HeadlessException {
		super();
        setUIComponents();
	}

	private static final long serialVersionUID = 1L;

}
