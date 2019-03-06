package logic;

import board.Board;
import bot.BotState;

import java.awt.*;

public class Logic {

    public static void doMove(Board board, int moveCol, int pId) {
        if (moveCol < board.getWidth() && moveCol >= 0) {
            for (int row = board.getHeight() - 1; row >= 0; row--) {
                Point p = new Point(row, moveCol);
                if (board.getFieldAt(p).equals(Board.EMPTY_FIELD)) {
                    board.setFieldAt(p, String.valueOf(pId));
                    return;
                }
            }
        } else {
            System.err.println("Invalid Move");
        }
    }

    public static void undoMove(Board board, int moveCol) {
        if (moveCol < board.getWidth() && moveCol >= 0) {
            for (int row = 0; row < board.getHeight(); row++) {
                Point p = new Point(row, moveCol);
                if (!board.getFieldAt(p).equals(Board.EMPTY_FIELD)) {
                    board.setFieldAt(p, Board.EMPTY_FIELD);
                    return;
                }
            }
        } else {
            System.err.println("Invalid Move");
        }
    }

    public static boolean isWinning(Board board, int inARow, int pId) {
        String c = String.valueOf(pId);

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (c.equals(board.getFieldAt(new Point(i, j)))) {
                    //vertical
                    int x = 0;
                    while (x < inARow && c.equals(board.getFieldAt(new Point(i + x, j)))) {
                        x++;
                        if (!(i + x < board.getHeight()))
                            break;
                    }
                    if (x == inARow) return true;

                    //horizontal
                    x = 0;
                    while (x < inARow && c.equals(board.getFieldAt(new Point(i, j + x)))) {
                        x++;
                        if (!(j + x < board.getWidth()))
                            break;
                    }
                    if (x == inARow) return true;

                    //diagonal
                    x = 0;
                    while (x < inARow && c.equals(board.getFieldAt(new Point(i + x, j + x)))) {
                        x++;
                        if (!(i + x < board.getHeight() && j + x < board.getWidth()))
                            break;
                    }
                    if (x == inARow) return true;

                    x = 0;
                    while (x < inARow && c.equals(board.getFieldAt(new Point(i + x, j - x)))) {
                        x++;
                        if (!(i + x < board.getHeight() && j - x >= 0))
                            break;
                    }
                    if(x == inARow) return true;
                }
            }
        }
        return false;
    }
}
