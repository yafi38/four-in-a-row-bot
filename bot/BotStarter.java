/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package bot;

import java.util.ArrayList;
import java.util.Random;

import board.Board;
import logic.Logic;

/**
 * bot.BotStarter
 * <p>
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 *
 * @author Jim van Eeden <jim@riddles.io>, Joost de Meij <joost@riddles.io>
 */

public class BotStarter {

    /**
     * Makes a turn. Edit this method to make your bot smarter.
     *
     * @return The column where the turn was made.
     */
    /*private int currMove;

    public int calcMove(BotState state, boolean isMe) {

        Board board = state.getBoard();
        currMove = -1;

        optimizer(board, 0,true);

        if (currMove == -1) {
            ArrayList<Integer> moves = board.getValidMoves();
            int index = new Random().nextInt(moves.size());
            currMove = moves.get(index);
        }

        return currMove;
    }

    private int optimizer(Board board, int depth, boolean isMe) {
        if (depth >= 6) {
            if (isMe && Logic.isWinning(board, 4, board.getMyId()))
                return 100;
            else if (!isMe && Logic.isWinning(board, 4, board.getEnemyId()))
                return -200;
            else return -1;
        }

        int score = 0;

        ArrayList<Integer> moves = board.getValidMoves();

        if (isMe) {
            for (int move : moves) {
                Logic.doMove(board, move, board.getMyId());
                if (Logic.isWinning(board, 4, board.getMyId()))
                {
                    currMove = move;
                    return 1000;
                }
                int tempScore = optimizer(board, depth+1, false);
                if (tempScore > score) {
                    score = tempScore;
                    currMove = move;
                }
                Logic.undoMove(board, move);
            }
            return score;
        } else {
            for (int move : moves) {
                Logic.doMove(board, move, board.getMyId());

                if (Logic.isWinning(board, 4, board.getEnemyId()))
                    return -2000;
                int tempScore = optimizer(board, depth+1, true);
                if (tempScore < score)
                    score = tempScore;
                Logic.undoMove(board, move);
            }
            return score;
        }

    }*/
    public static void main(String[] args) {
        BotParser parser = new BotParser();
        parser.run();
    }
}
