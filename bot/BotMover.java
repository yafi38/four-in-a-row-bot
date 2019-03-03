package bot;

import board.Board;
import logic.Logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BotMover {
    private int currMove;
    private final int INF = 100000;

    public int getMove(BotState state) {
        Board board = state.getBoard();
        currMove = -1;

        calcMove(board, 0, true);

        if (currMove == -1) {
            ArrayList<Integer> moves = board.getValidMoves();
            int index = new Random().nextInt(moves.size());
            currMove = moves.get(index);
        }

        return currMove;
    }

    private int calcMove(Board board, int depth, boolean isMe) {
        if (depth >= 6) {
            return getScore(board);
        }

        int score = 0;

        ArrayList<Integer> moves = board.getValidMoves();

        if (isMe) {
            for (int move : moves) {
                Logic.doMove(board, move, board.getMyId());
                //board.printBoard();
                //System.err.println();

                if (Logic.isWinning(board, 4, board.getMyId())) {
                    currMove = move;
                    //System.err.println("We are winning\n");
                    Logic.undoMove(board, move);
                    return INF;
                }

                int tempScore = calcMove(board, depth + 1, false);
                //System.err.println("Me Score: " + tempScore + "\n");
                if (tempScore > score) {
                    score = tempScore;
                    currMove = move;
                }
                Logic.undoMove(board, move);
            }
            return score;
        } else {
            for (int move : moves) {
                Logic.doMove(board, move, board.getEnemyId());
                //board.printBoard();
                //System.err.println();

                if (Logic.isWinning(board, 4, board.getEnemyId())) {
                    //System.err.println("Enemy winning\n");
                    Logic.undoMove(board, move);
                    return -INF;
                }

                int tempScore = calcMove(board, depth + 1, true);
                //System.err.println("En Score: " + tempScore + "\n");
                if (tempScore < score)
                    score = tempScore;
                Logic.undoMove(board, move);
            }
            return score;
        }

    }

    private int getScore(Board board) {
        //System.currentTimeMillis();
        int myId = board.getMyId();
        int enemyId = board.getEnemyId();
        if (Logic.isWinning(board, 4, myId))
            return INF;
        else if (Logic.isWinning(board, 4, enemyId))
            return -INF;

        int score = 0;

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                String c = board.getFieldAt(new Point(i, j));

                if (!c.equals(Board.EMPTY_FIELD)) {
                    int multiplier = 1;

                    if (c.equals(String.valueOf(enemyId)))
                        multiplier = -1;

                    //vertical
                    int x = 0;
                    while (c.equals(board.getFieldAt(new Point(i + x, j)))) {
                        x++;
                        if (!(x + i < board.getHeight()))
                            break;
                    }
                    x--;
                    score += (multiplier * x * x * x * x);


                    //horizontal
                    x = 0;
                    while (c.equals(board.getFieldAt(new Point(i, j + x)))) {
                        x++;
                        if (!(x + j < board.getWidth()))
                            break;
                    }
                    x--;
                    score += (multiplier * x * x * x * x);

                    //diagonal
                    x = 0;
                    while (c.equals(board.getFieldAt(new Point(i + x, j + x)))) {
                        x++;
                        if (!(x + i < board.getHeight() && x + j < board.getWidth()))
                            break;
                    }
                    x--;
                    score += (multiplier * x * x * x * x);

                    //anti diagonal
                    x = 0;
                    while (c.equals(board.getFieldAt(new Point(i + x, j - x)))) {
                        x++;
                        if (!(x + i < board.getHeight() && j - x >= 0))
                            break;
                    }
                    x--;
                    score += (multiplier * x * x * x * x);
                }
            }
        }

        return score;
    }
}
