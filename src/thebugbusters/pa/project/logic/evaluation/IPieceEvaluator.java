package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.state.Move;

import java.util.List;

/**
 * Piece evaluator.
 *
 * Evaluates a piece on the board.
 */
interface IPieceEvaluator {
    /**
     * Evaluates a piece on the board.
     * @param symbol piece symbol.
     * @param line piece line on board.
     * @param column piece column on board.
     * @return evaluation score.
     */
    int evaluatePiece(char symbol, int line, int column, List<Move> moves);
}
