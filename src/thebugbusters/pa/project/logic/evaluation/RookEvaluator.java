package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.logic.move.PieceOptions;
import thebugbusters.pa.project.state.Move;

import java.util.List;

/**
 * Evaluator class for ROOK.
 */
class RookEvaluator implements IPieceEvaluator {
    private static final int ROOK_MATERIAL = 500;
    private static final int[][] BLACK_ROOK_BOARD;
    private static final int[][] ROOK_BOARD = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {5, 10, 10, 10, 10, 10, 10, 5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {0, 0, 0, 5, 5, 0, 0, 0}};

    static {
        BLACK_ROOK_BOARD = new int[8][8];

        for (int i = 7; i >= 0; i--) {
            BLACK_ROOK_BOARD[i] = ROOK_BOARD[7 - i];
        }
    }

    private int positional(final char symbol, final int line, final int column) {
        if (Character.isUpperCase(symbol)) {
            return ROOK_BOARD[line][column];
        }
        return -BLACK_ROOK_BOARD[line][column];
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
            return ROOK_MATERIAL;
        }
        return -ROOK_MATERIAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int evaluatePiece(final char symbol, final int line, final int column, final List<Move> moves) {
        return material(symbol) + positional(symbol, line, column) + attack(symbol);
    }
}
