package gk.game.work;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4014055194619198629L;
	JButton btnRestart = new JButton("restart");
	JButton btnBack = new JButton("back one step");
	JButton btnPre = new JButton("previous level");
	JButton btnnext = new JButton("next level");
	JButton btnchoose = new JButton("choose");
	JButton btnFirst = new JButton("the first level");
	JButton btnLast = new JButton("the last level");
	MyPanel panel;

	public MyFrame() {
		super("homework of boxman");
		this.initFrame();
	}

	/**
	 * initialize the frame
	 */
	private void initFrame(){
		setSize(800, 800);//sets the frame's width and height
		setVisible(true);
		setResizable(false);//unable to drag the frame
		setLocation(500, 20);//sets the frame's axis on the screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cont = getContentPane();
		cont.setLayout(null);
		cont.setBackground(Color.white);
		add(btnRestart);
		add(btnPre);
		add(btnnext);
		add(btnchoose);
		add(btnFirst);
		add(btnLast);
		add(btnBack);
		setJButtionSize();//sets the buttons' style
		panel = new MyPanel();
		add(panel);
		panel.initPanel(panel.level);
		panel.requestFocus();
		validate();
	}
	/**
	 * sets the buttons' style
	 */
	private void setJButtionSize(){
		btnRestart.setBounds(660, 100, 120, 30);
		btnRestart.addActionListener(this);
		btnBack.setBounds(660, 150, 120, 30);
		btnBack.addActionListener(this);
		btnFirst.setBounds(660, 200, 120, 30);
		btnFirst.addActionListener(this);
		btnPre.setBounds(660, 250, 120, 30);
		btnPre.addActionListener(this);
		btnnext.setBounds(660, 300, 120, 30);
		btnnext.addActionListener(this);
		btnLast.setBounds(660, 350, 120, 30);
		btnLast.addActionListener(this);
		btnchoose.setBounds(660, 400, 120, 30);
		btnchoose.addActionListener(this);
	}
	/**
	 * invoked when the buttons are pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRestart) {//restart
			panel.initPanel(panel.level);
			panel.requestFocus();
			panel.remove();
		} else if (e.getSource() == btnPre) {//press the previous level
			panel.level--;
			if (panel.level < 1) {
				panel.level++;
				JOptionPane.showMessageDialog(this, "This level is the first level!");
				panel.requestFocus();
			} else {
				panel.initPanel(panel.level);
				panel.requestFocus();
			}
			panel.remove();
		} else if (e.getSource() == btnnext) {//press the next level
			panel.level++;
			if (panel.level > panel.getMaxLevel()) {
				panel.level--;
				JOptionPane.showMessageDialog(this, "This level is the last level!");
				panel.requestFocus();
			} else {
				panel.initPanel(panel.level);
				panel.requestFocus();
			}
			panel.remove();
		}else if (e.getSource() == btnchoose) {
			String lel = JOptionPane.showInputDialog(this, "Please input the level num：(1~5)");
			//when choose cancel
			if (lel == null || "".equals(lel)) {
				panel.requestFocus();
			} else {
				panel.level = Integer.parseInt(lel);
				panel.remove();
				if (panel.level > panel.getMaxLevel() || panel.level < 1) {
					JOptionPane.showMessageDialog(this, "This level is not exits!");
					panel.requestFocus();
				} else {
					panel.initPanel(panel.level);
					panel.requestFocus();
				}				
			}
		}else if (e.getSource() == btnFirst) {
			panel.level = 1;
			panel.initPanel(panel.level);
			panel.requestFocus();
			panel.remove();
		} else if (e.getSource() == btnLast) {
			panel.level = panel.getMaxLevel();
			panel.initPanel(panel.level);
			panel.requestFocus();
			panel.remove();
		}  else if (e.getSource() == btnBack) {
			if (panel.isStackEmpty())
				JOptionPane.showMessageDialog(this, "You have not moved!");
			else {
				switch (panel.back()) {
					case 10:
						panel.backup(10);
						break;
					case 11:
						panel.backup(11);
						break;
					case 20:
						panel.backDown(20);
						break;
					case 21:
						panel.backDown(21);
						break;
					case 30:
						panel.backLeft(30);
						break;
					case 31:
						panel.backLeft(31);
						break;
					case 40:
						panel.backRight(40);
						break;
					case 41:
						panel.backRight(41);
						break;
				}
			}
			panel.requestFocus();
		}
	}

}