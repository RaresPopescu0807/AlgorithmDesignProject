package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.logic.move.PieceOptions;
import thebugbusters.pa.project.state.Move;

import java.util.List;

/**
 * Evaluator class for KNIGHT.
 */
class KnightEvaluator implements IPieceEvaluator {
    private static final int[][] BLACK_KNIGHT_BOARD;
    private static final int KNIGHT_MATERIAL = 300;
    private static final int[][] KNIGHT_BOARD = {
            {-50, -40, -30, -30, -30, -30, -40, -50},
            {-40, -20, 0, 0, 0, 0, -20, -40},
            {-30, 0, 10, 15, 15, 10, 0, -30},
            {-30, 5, 15, 20, 20, 15, 5, -30},
            {-30, 0, 15, 20, 20, 15, 0, -30},
            {-30, 5, 10, 15, 15, 10, 5, -30},
            {-40, -20, 0, 5, 5, 0, -20, -40},
            {-50, -40, -30, -30, -30, -30, -40, -50}};

    static {
        BLACK_KNIGHT_BOARD = new int[8][8];

        for (int i = 7; i >= 0; i--) {
            BLACK_KNIGHT_BOARD[i] = KNIGHT_BOARD[7 - i];
        }
    }

    private int positional(final char symbol, final int line, final int column) {
        if (Character.isUpperCase(symbol)) {
            return KNIGHT_BOARD[line][column];
        }
        return -BLACK_KNIGHT_BOARD[line][column];
    }

    private int material(final char symbol) {
        if (Character.isUpperCase(symbol)) {
            return KNIGHT_MATERIAL;
        }
        return -KNIGHT_MATERIAL;
    }

    private int attack(final char symbol) {
        if (!PieceOptions.kingIsSafe()) {
            return -material(symbol);
        } else {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int evaluatePiece(final char symbol, final int line, final int column, final List<Move> moves) {
        return material(symbol) + positional(symbol, line, column) + attack(symbol);
    }
}
