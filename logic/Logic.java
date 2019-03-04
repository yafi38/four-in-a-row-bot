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

    public static boolean isWinning(Board b, int inARow, int pId) {
        String pIdStr = String.valueOf(pId);

        /* Check for horizontal wins */
        for (int i = 0; i < b.getHeight(); i++) {
            for (int j = 0; j < b.getWidth() - inARow + 1; j++) {
                boolean win = true;
                for (int x = 0; x < inARow; x++) {
                    if (!pIdStr.equals(b.getFieldAt(new Point(i, j + x)))) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    //System.err.println("Horizontal");
                    return true;
                }
            }
        }


        /* Check for vertical wins */
        for (int i = 0; i < b.getHeight() - inARow + 1; i++) {
            for (int j = 0; j < b.getWidth(); j++) {
                boolean win = true;
                for (int x = 0; x < inARow; x++) {
                    if (!pIdStr.equals(b.getFieldAt(new Point(i + x, j)))) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    //System.err.println("Vertical");
                    return true;
                }
            }
        }


        /* Check for diagonal wins */
        for (int i = 0; i < b.getHeight() - inARow + 1; i++) {
            for (int j = 0; j < b.getWidth() - inARow + 1; j++) {
                boolean win = true;
                for (int x = 0; x < inARow; x++) {
                    if (!pIdStr.equals(b.getFieldAt(new Point(i + x, j + x)))) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    //System.err.println("Diagonal");
                    return true;
                }
            }
        }

        /* Check for anti diagonal wins */
        for (int i = b.getHeight() - 1; i >= inARow - 1; i--) {
            for (int j = 0; j < b.getWidth() - inARow + 1; j++) {
                boolean win = true;
                for (int x = 0; x < inARow; x++) {
                    if (!pIdStr.equals(b.getFieldAt(new Point(i - x, j + x)))) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    //System.err.println("Anti Diagonal");
                    return true;
                }
            }
        }

        return false;
    }
}
