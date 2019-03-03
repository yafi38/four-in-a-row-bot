package bot;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import board.Board;
import player.Player;

/**
 * bot.BotState - Created on 11-5-17
 *
 * Keeps track of the complete game state, such as the board
 * and game settings
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class BotState {

    private int MAX_TIMEBANK;
    private int TIME_PER_MOVE;
    private int MAX_ROUNDS;

    private int roundNumber;
    private int timebank;
    private String myName;
    private HashMap<String, Player> players;

    private Board board;

    BotState() {
        this.board = new Board();
        this.players = new HashMap<>();
    }

    public void setTimebank(int value) {
        this.timebank = value;
    }

    public void setMaxTimebank(int value) {
        this.MAX_TIMEBANK = value;
    }

    public void setTimePerMove(int value) {
        this.TIME_PER_MOVE = value;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public void setMaxRounds(int value) {
        this.MAX_ROUNDS = value;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getTimebank() {
        return this.timebank;
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public HashMap<String, Player> getPlayers() {
        return this.players;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getMyName() {
        return this.myName;
    }

    public int getMaxTimebank() {
        return this.MAX_TIMEBANK;
    }

    public int getTimePerMove() {
        return this.TIME_PER_MOVE;
    }

    public int getMaxRound() {
        return this.MAX_ROUNDS;
    }

    public ArrayList<Integer> getValidMoves() {
        ArrayList<Integer> moves = new ArrayList<>();

        for(int i=0; i<7; i++)
        {
            Point p = new Point(0, i);
            if(board.getFieldAt(p).equals(board.EMPTY_FIELD))
                moves.add(i);
        }
        return moves;
    }
}
