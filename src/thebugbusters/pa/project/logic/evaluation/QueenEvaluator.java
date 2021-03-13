package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.logic.move.PieceOptions;
import thebugbusters.pa.project.state.Move;

import java.util.List;

/**
 * Evaluator class for QUEEN.
 */
class QueenEvaluator implements IPieceEvaluator {
    private static final int QUEEN_MATERIAL = 900;
    private static final int[][] BLACK_QUEEN_BOARD;
    private static final int[][] QUEEN_BOARD = {
            {-20, -10, -10, -5, -5, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 5, 5, 5, 0, -10},
            {-5, 0, 5, 5, 5, 5, 0, -5},
            {0, 0, 5, 5, 5, 5, 0, -5},
            {-10, 5, 5, 5, 5, 5, 0, -10},
            {-10, 0, 5, 0, 0, 0, 0, -10},
            {-20, -10, -10, -5, -5, -10, -10, -20}};

    static {
        BLACK_QUEEN_BOARD = new int[8][8];

        for (int i = 7; i >= 0; i--) {
            BLACK_QUEEN_BOARD[i] = QUEEN_BOARD[7 - i];
        }
    }

    private int positional(final char symbol, final int line, final int column) {
        if (Character.isUpperCase(symbol)) {
            return QUEEN_BOARD[line][column];
        } else {
            return -QUEEN_BOARD[line][column];
        }
    }

    private int attack(final char symbol) {
        if (!PieceOptions.kingIsSafe()) {
            return -material(symbol);
        } else {
            return 0;
        }
    }

    private int material(final char symbol) {
        if (Character.isUpperCase(symbol)) {
            return QUEEN_MATERIAL;
        }
        return -QUEEN_MATERIAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int evaluatePiece(final char symbol, final int line, final int column, final List<Move> moves) {
        return (material(symbol) + positional(symbol, line, column) + attack(symbol));
    }
}
