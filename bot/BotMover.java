package bot;

import board.Board;
import logic.Logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BotMover {
    private int currMove;
    private final int INF = 10000000;
    private int roundNo;
    private int depthToSearch;
    private long startTime;
    private boolean timeOut;

    public BotMover() {
        roundNo = 0;
    }

    public int getMove(BotState state) {
        startTime = System.currentTimeMillis();
        roundNo++;
        Board board = state.getBoard();

        if (roundNo == 1 && board.getMyId() == 0)
            return board.getWidth() / 2;

        int prevMove;
        currMove = -1;
        depthToSearch = 1;
        timeOut = false;

        ArrayList<Integer> moves = board.getValidMoves();
        int index = new Random().nextInt(moves.size());
        prevMove = moves.get(index);


        while (depthToSearch < 20) {
            int finalScore = calcMove(state, 0, true, -INF, INF);
            if (finalScore == INF) {
                System.err.println("Winning move " + currMove + " found at round: " + roundNo);
                break;
            }

            if (timeOut) {
                currMove = prevMove;
                System.err.println("Round: " + roundNo + " Timed Out");
                depthToSearch--;
                break;
            } else if (currMove == -1 && finalScore == -INF) {
                //currMove = prevMove;
                System.err.println("Round: " + roundNo + " Depth: " + depthToSearch + " No good move found");
                break;
            } else {
                prevMove = currMove;
                currMove = -1;
                System.err.println("Round: " + roundNo + " Set prev to: " + prevMove + " Depth: " + depthToSearch + " Final score: " + finalScore);
            }
            depthToSearch++;
        }

        if (currMove == -1) currMove = prevMove;
        return currMove;
    }

    private int calcMove(BotState state, int depth, boolean isMe, int alpha, int beta) {
        Board board = state.getBoard();

        if (depth >= depthToSearch) {
            return getBetterScore(board);
        }

        if (System.currentTimeMillis() - startTime > state.getTimePerMove() + state.getExtraTime()) {
            timeOut = true;
            return -INF;
        }

        ArrayList<Integer> moves = board.getValidMoves();

        if (isMe) { //maximizer
            int score = -INF;

            //pre-checking if winning move is available
            for (int move : moves) {
                Logic.doMove(board, move, board.getMyId());
                if (Logic.isWinning(board, 4, board.getMyId())) {
                    if (depth == 0) currMove = move;
                    Logic.undoMove(board, move);
                    return INF;
                }
                Logic.undoMove(board, move);
            }

            for (int move : moves) {
                Logic.doMove(board, move, board.getMyId());

                int tempScore = calcMove(state, depth + 1, false, alpha, beta);
                Logic.undoMove(board, move);

                if (tempScore > score) {
                    score = tempScore;
                    if (depth == 0) currMove = move;
                }

                if (tempScore > alpha)
                    alpha = tempScore;

                if (alpha >= beta)
                    break;
            }
            return score;
        } else { //minimizer
            int score = INF;

            //pre-checking if winning move is available
            for (int move : moves) {
                Logic.doMove(board, move, board.getEnemyId());
                if (Logic.isWinning(board, 4, board.getEnemyId())) {
                    Logic.undoMove(board, move);
                    return -INF;
                }
                Logic.undoMove(board, move);
            }

            for (int move : moves) {
                Logic.doMove(board, move, board.getEnemyId());

                int tempScore = calcMove(state, depth + 1, true, alpha, beta);
                Logic.undoMove(board, move);

                if (tempScore < score)
                    score = tempScore;

                if (tempScore < beta)
                    beta = tempScore;

                if (alpha >= beta)
                    break;
            }
            return score;
        }

    }

    private int getBetterScore(Board board) {
        int enemyId = board.getEnemyId();

        int score = 0;

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                String c = board.getFieldAt(new Point(i, j));

                if (!c.equals(Board.EMPTY_FIELD)) {
                    int multiplier = 1;

                    if (c.equals(String.valueOf(enemyId)))
                        multiplier = -1;

                    int gap;

                    //vertical
                    int x = 0;
                    while (c.equals(board.getFieldAt(new Point(i + x, j)))) {
                        x++;
                        if (!(x + i < board.getHeight()))
                            break;
                    }
                    x--;
                    //if there is no empty space on top, there is no use of it
                    if ((i != 0 && board.isEmpty(i - 1, j)))
                        score += (multiplier * x * x * x);


                    //horizontal
                    x = 0;
                    gap = 0;
                    while (true) {
                        Point p = new Point(i, j + x);
                        x++;

                        if (!c.equals(board.getFieldAt(p))) {
                            if (board.isEmpty(p.x, p.y)) {
                                if (gap == 1) break;
                                gap++;
                            } else break;
                        }

                        if (!(x + j < board.getWidth()))
                            break;
                    }
                    x--;
                    if (gap == 1) x--;

                    if ((j != 0 && board.isEmpty(i, j - 1)) || gap == 1)
                        score += calcScore(x, multiplier);


                    //diagonal
                    x = 0;
                    gap = 0;
                    while (true) {
                        Point p = new Point(i + x, j + x);
                        x++;
                        if (!c.equals(board.getFieldAt(p))) {
                            if (board.isEmpty(p.x, p.y)) {
                                if (gap == 1) break;
                                gap++;
                            } else break;
                        }

                        if (!(x + i < board.getHeight() && x + j < board.getWidth()))
                            break;
                    }
                    x--;
                    if (gap == 1) x--;

                    if ((i != 0 && j != 0 && board.isEmpty(i - 1, j - 1)) || gap == 1)
                        score += calcScore(x, multiplier);

                    //anti diagonal
                    x = 0;
                    gap = 0;
                    while (true) {
                        Point p = new Point(i + x, j - x);
                        x++;
                        if (!c.equals(board.getFieldAt(p))) {
                            if (board.isEmpty(p.x, p.y)) {
                                if (gap == 1) break;
                                gap++;
                            } else break;
                        }

                        if (!(x + i < board.getHeight() && j - x >= 0))
                            break;
                    }
                    x--;
                    if (gap == 1) x--;

                    if ((i != 0 && j != board.getWidth() - 1 && board.isEmpty(i - 1, j + 1)) || gap == 1)
                        score += calcScore(x, multiplier);
                }
            }
        }

        return score;
    }

    private int calcScore(int x, int multiplier) {
        if (x > 2) x = 2;
        return (multiplier * x * x * x);
    }

}
