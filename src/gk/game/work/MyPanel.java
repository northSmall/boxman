package gk.game.work;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements IMyPanel{
	private static final long serialVersionUID = 1L;
	int maxLevel = 2;
	int[][] map, mapFlag;
	int manX, manY;
	Image[] myImage;
	/* the map on the Panel */
	MapBackground mapBackground;
	/* used when back a step */
	MapBackground mapBack;
	int len = 30;
	public int level = 1;
	/* used when back a step.Use stack data structure. */
	Stack<Integer> stack = new Stack<Integer>();

	public MyPanel() {
		setBounds(15, 50, 600, 600);
		setBackground(Color.white);
		addKeyListener(this);
		myImage = new Image[10];
		for (int i = 0; i < 10; i++) {
			myImage[i] = Toolkit.getDefaultToolkit().getImage("images\\" + i + ".gif");
		}

		setVisible(true);
	}

	/**
	 * repaint the panel
	 * @param gameNum game's level
	 */
	public void initPanel(int gameNum) {
		mapBackground = new MapBackground(gameNum);
		mapBack = new MapBackground(gameNum);
		map = mapBackground.getMap();
		manX = mapBackground.getManX();
		manY = mapBackground.getManY();
		mapFlag = mapBack.getMap();
		repaint();
	}

	int getMaxLevel() {
		return maxLevel;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++) {
				g.drawImage(myImage[map[j][i]],
						i * len,
						j * len,
						this);
			}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.drawString("Level  " + String.valueOf(level), 240, 40);
	}

	/**
	 * Listen the keyboards,and judge whether isWin or not.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			moveUp();
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			moveDown();
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			leftMove();
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightMove();
		
		if (isWin()) {
			if (level == maxLevel) {//if this is the last level
				JOptionPane.showMessageDialog(this, "Congratulations,you have passed the last level!");
			} else {
				String message = "Congratulations，you have passed the level " + level + "!\n whether get into the next level or not？";
				int choice = 0;
				choice = JOptionPane.showConfirmDialog(null, message, "passed the level", JOptionPane.YES_NO_OPTION);
				if (choice == 1)//press yes
					System.exit(0);
				else if (choice == 0) {//press no
					level++;
					this.initPanel(level);
				}
			}
			stack.removeAllElements();
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public boolean isStackEmpty() {
		return stack.isEmpty();
	}

	public int back() {
		return (Integer) stack.pop();
	}

	public void remove() {
		stack.removeAllElements();
	}

	public void moveUp() {
		if (map[manY - 1][manX] == 2 || map[manY - 1][manX] == 4) {//ground or aim
			judgePic();
			map[manY - 1][manX] = 8;
			initPanelUpDown(10, 0);
		} else if (map[manY - 1][manX] == 3) {//Top one is box
			if (map[manY - 2][manX] == 4) {//Top two is aim
				judgePic();
				map[manY - 1][manX] = 8;//man's up picture
				map[manY - 2][manX] = 9;//success picture
				initPanelUpDown(11, 0);
			} else if (map[manY - 2][manX] == 2) {//ground
				judgePic();
				map[manY - 1][manX] = 8;
				map[manY - 2][manX] = 3;
				initPanelUpDown(11, 0);
			} else {
				map[manY][manX] = 8;
				repaint();
			}
		} else if (map[manY - 1][manX] == 9) {//Top one is success picture
			if (map[manY - 2][manX] == 4) {
				judgePic();
				map[manY - 1][manX] = 8;
				map[manY - 2][manX] = 9;
				initPanelUpDown(11, 0);
			} else if (map[manY - 2][manX] == 2) {//when the man push the box from red box
				judgePic();
				map[manY - 1][manX] = 8;
				map[manY - 2][manX] = 3;
				initPanelUpDown(11, 0);
			} else {
				map[manY][manX] = 8;
				repaint();
			}
		}
		if (map[manY - 1][manX] == 1) {
			map[manY][manX] = 8;
			repaint();
		}
	}

	public void backup(int flag) {
		int n = flag;
		if (n == 10) {
			judgePic();
		} else if (n == 11) {
			if (mapFlag[manY][manX] == 4 || mapFlag[manY][manX] == 9) {
				map[manY][manX] = 9;
			} else
				map[manY][manX] = 3;
			if (mapFlag[manY - 1][manX] == 4 || mapFlag[manY - 1][manX] == 9) {
				map[manY - 1][manX] = 4;
			} else
				map[manY - 1][manX] = 2;
		}
		map[manY + 1][manX] = 8;
		repaint();
		manY++;
	}

	public void moveDown() {
		if (map[manY + 1][manX] == 2 || map[manY + 1][manX] == 4) {
			judgePic();
			map[manY + 1][manX] = 5;
			initPanelUpDown(20, 1);
		}
		if (map[manY + 1][manX] == 3) {
			if (map[manY + 2][manX] == 4) {
				judgePic();
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 9;
				initPanelUpDown(21, 1);
			} else if (map[manY + 2][manX] == 2) {
				judgePic();
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 3;
				initPanelUpDown(21, 1);
			} else {
				map[manY][manX] = 5;
				repaint();
			}
		}
		if (map[manY + 1][manX] == 9) {
			if (map[manY + 2][manX] == 4) {
				judgePic();
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 9;
				initPanelUpDown(21, 1);
			} else if (map[manY + 2][manX] == 2) {
				judgePic();
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 3;
				initPanelUpDown(21, 1);
			} else {
				map[manY][manX] = 5;
				repaint();
			}
		}
		if (map[manY + 1][manX] == 1) {
			map[manY][manX] = 5;
			repaint();
		}
	}

	public void backDown(int flag) {
		int n = flag;
		if (n == 20) {
			judgePic();
		} else if (n == 21) {
			if (mapFlag[manY][manX] == 4 || mapFlag[manY][manX] == 9)
				map[manY][manX] = 9;
			else
				map[manY][manX] = 3;
			if (mapFlag[manY + 1][manX] == 4 || mapFlag[manY + 1][manX] == 9)
				map[manY + 1][manX] = 4;
			else
				map[manY + 1][manX] = 2;
		}
		map[manY - 1][manX] = 5;
		repaint();
		manY--;
	}

	public void leftMove() {
		if (map[manY][manX - 1] == 2 || map[manY][manX - 1] == 4) {
			judgePic();
			map[manY][manX - 1] = 6;
			initPanelLeftRight(30, 0);
		}
		if (map[manY][manX - 1] == 3) {
			if (map[manY][manX - 2] == 4) {
				judgePic();
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 9;
				initPanelLeftRight(31, 0);
			} else if (map[manY][manX - 2] == 2) {
				judgePic();
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 3;
				initPanelLeftRight(31, 0);
			} else {
				map[manY][manX] = 6;
				repaint();
			}
		}
		if (map[manY][manX - 1] == 9) {
			if (map[manY][manX - 2] == 4) {
				judgePic();
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 9;
				initPanelLeftRight(31, 0);
			} else if (map[manY][manX - 2] == 2) {
				judgePic();
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 3;
				initPanelLeftRight(31, 0);
			} else {
				map[manY][manX] = 6;
				repaint();
			}
		}
		if (map[manY][manX - 1] == 1) {
			map[manY][manX] = 6;
			repaint();
		}
	}

	public void backLeft(int flag) {
		int n = flag;
		if (n == 30) {
			judgePic();
		} else if (n == 31) {
			if (mapFlag[manY][manX] == 4 || mapFlag[manY][manX] == 9) {
				map[manY][manX] = 9;
			} else
				map[manY][manX] = 3;
			if (mapFlag[manY][manX - 1] == 4 || mapFlag[manY][manX - 1] == 9) {
				map[manY][manX - 1] = 4;
			} else
				map[manY][manX - 1] = 2;
		}
		map[manY][manX + 1] = 6;
		repaint();
		manX++;
	}

	public void rightMove() {
		if (map[manY][manX + 1] == 2 || map[manY][manX + 1] == 4) {
			judgePic();
			map[manY][manX + 1] = 7;
			initPanelLeftRight(40, 1);
		}
		if (map[manY][manX + 1] == 3) {
			if (map[manY][manX + 2] == 4) {
				if (mapFlag[manY][manX] == 4)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 9;
				initPanelLeftRight(41, 1);
			} else if (map[manY][manX + 2] == 2) {
				if (mapFlag[manY][manX] == 4)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 3;
				initPanelLeftRight(41, 1);
			} else {
				map[manY][manX] = 7;
				repaint();
			}
		}
		if (map[manY][manX + 1] == 9) {
			if (map[manY][manX + 2] == 4) {
				judgePic();
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 9;
				initPanelLeftRight(41, 1);
			} else if (map[manY][manX + 2] == 2) {
				judgePic();
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 3;
				initPanelLeftRight(41, 1);
			} else {
				map[manY][manX] = 7;
				repaint();
			}
		}
		if (map[manY][manX + 1] == 1) {
			map[manY][manX] = 7;
			repaint();
		}
	}

	public void backRight(int flag) {
		int n = flag;
		if (n == 40) {
			judgePic();
		} else if (n == 41) {
			if (mapFlag[manY][manX] == 4 || mapFlag[manY][manX] == 9) {
				map[manY][manX] = 9;
			} else
				map[manY][manX] = 3;
			if (mapFlag[manY][manX + 1] == 4 || mapFlag[manY][manX + 1] == 9) {
				map[manY][manX + 1] = 4;
			} else
				map[manY][manX + 1] = 2;
		}
		map[manY][manX - 1] = 7;
		repaint();
		manX--;
	}

	public boolean isWin() {
		boolean num = false;
		out : for (int i = 0; i < 20; i++)
				for (int j = 0; j < 20; j++) {
					if (mapFlag[i][j] == 4 || mapFlag[i][j] == 9)
						if (map[i][j] == 9)
							num = true;
						else {
							num = false;
							break out;
						}
				}
		return num;
	}
	
	/**
	 * when move the man up or down,initialize the panel.
	 * @param stackNum
	 * @param flag
	 */
	private void initPanelUpDown(int stackNum, int flag){
		repaint();
		if (flag > 0)
			manY++;
		else manY--;
		stack.push(stackNum);
	}

	/**
	 * when move the man left or right,initialize the panel.
	 * @param stackNum
	 * @param flag
	 */
	private void initPanelLeftRight(int stackNum, int flag){
		this.repaint();
		if (flag > 0)
			manX++;
		else manX--;
		stack.push(stackNum);
	}
	
	/**
	 * when the man is moved,the picture of the previous axis.
	 */
	private void judgePic() {
		//aim or red box
		if (mapFlag[manY][manX] == 4 || mapFlag[manY][manX] == 9)
            map[manY][manX] = 4;//sets aim picture
        else
            map[manY][manX] = 2;//sets groud picture
	}
}
