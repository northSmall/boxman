package gk.game.work;

import java.awt.event.KeyListener;

public interface IMyPanel extends KeyListener {

    /**
     * when press key,init the panel
     * @param gameNum
     */
    void initPanel(int gameNum);

    /**
     * when move up
     */
    void moveUp();
    void backup(int flag);
    /**
     * when move down
     */
    void moveDown();
    void backDown(int flag);
    /**
     * when move left
     */
    void leftMove();
    void backLeft(int flag);
    /**
     * when move right
     */
    void rightMove();
    void backRight(int flag);
    boolean isWin();
}
