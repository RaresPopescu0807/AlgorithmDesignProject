package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;

import java.util.List;

/**
 * Evaluator class for KING.
 */
class KingEvaluator implements IPieceEvaluator {
    private static final int[][] KING_MID_BOARD = {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            {20, 20, 0, 0, 0, 0, 20, 20},
            {20, 30, 10, 0, 0, 10, 30, 20}};
    private static final int[][] KING_END_BOARD = {
            {-50, -40, -30, -20, -20, -30, -40, -50},
            {-30, -20, -10, 0, 0, -10, -20, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -30, 0, 0, 0, 0, -30, -30},
            {-50, -30, -30, -30, -30, -30, -30, -50}};

    private static final int[][] BLACK_KING_MID_BOARD;
    private static final int[][] BLACK_KING_END_BOARD;

    static {
        BLACK_KING_MID_BOARD = new int[8][8];
        BLACK_KING_END_BOARD = new int[8][8];

        for (int i = 7; i >= 0; i--) {
            BLACK_KING_MID_BOARD[i] = KING_MID_BOARD[7 - i];
            BLACK_KING_END_BOARD[i] = KING_END_BOARD[7 - i];
        }

    }

    private int positional(final char symbol, final int line, final int column) {
        if (!GameState.isEndGame()) {
            if (Character.isUpperCase(symbol))
                return KING_MID_BOARD[line][column];
            else
                return BLACK_KING_MID_BOARD[line][column];

        } else {
            if (Character.isUpperCase(symbol))
                return KING_END_BOARD[line][column];
            else
                return BLACK_KING_END_BOARD[line][column];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int evaluatePiece(final char symbol, final int line, final int column, final List<Move> moves) {
        return positional(symbol, line, column);
    }
}
