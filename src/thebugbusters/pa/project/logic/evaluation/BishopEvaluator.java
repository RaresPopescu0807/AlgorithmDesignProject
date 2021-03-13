package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.logic.move.PieceOptions;
import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.state.Player;

import java.util.List;

/**
 * Evaluator class for BISHOP.
 */
class BishopEvaluator implements IPieceEvaluator {
    private static final int SINGLE_BISHOP_MATERIAL = 250;
    private static final int BISHOP_PAIR_MATERIAL = 350;
    private static final int[][] BLACK_BISHOP_BOARD;
    private static final int[][] WHITE_BISHOP_BOARD = {
            {-20, -10, -10, -10, -10, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 10, 10, 5, 0, -10},
            {-10, 5, 5, 10, 10, 5, 5, -10},
            {-10, 0, 10, 10, 10, 10, 0, -10},
            {-10, 10, 10, 10, 10, 10, 10, -10},
            {-10, 5, 0, 0, 0, 0, 5, -10},
            {-20, -10, -10, -10, -10, -10, -10, -20}};
    private static int blackBishops;
    private static int whiteBishops;

    static {
        BLACK_BISHOP_BOARD = new int[8][8];

        for (int i = 7; i >= 0; i--) {
            BLACK_BISHOP_BOARD[i] = WHITE_BISHOP_BOARD[7 - i];
        }
    }

    private int material(final char symbol) {
        if (GameState.getPlayerOnMove() == Player.WHITE) {
            whiteBishops = 0;
        } else {
            blackBishops = 0;
        }

        if (Character.isUpperCase(symbol)) {
            whiteBishops++;
            if (whiteBishops == 1) {
                return SINGLE_BISHOP_MATERIAL;
            } else {
                return BISHOP_PAIR_MATERIAL;
            }
        } else {
            blackBishops++;
            if (blackBishops == 1) {
                return -SINGLE_BISHOP_MATERIAL;
            } else {
                return -BISHOP_PAIR_MATERIAL;
            }
        }
    }

    private int attack(final char symbol) {
        if (!PieceOptions.kingIsSafe()) {
            return -material(symbol);
        } else {
            return 0;
        }
    }

    private int positional(final char symbol, final int line, final int column) {
        if (Character.isUpperCase(symbol)) {
            return WHITE_BISHOP_BOARD[line][column];
        } else {
            return -BLACK_BISHOP_BOARD[line][column];
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
