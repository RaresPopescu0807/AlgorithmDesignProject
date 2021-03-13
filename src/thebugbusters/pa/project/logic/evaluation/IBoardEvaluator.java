package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.state.Move;

import java.util.List;

/**
 * Board evaluator.
 * <p>
 * Evaluates a board and returns the evaluation as an int value.
 */
public interface IBoardEvaluator {
    /**
     * Evaluates a board and returns the evaluation as an int value.
     * @param board board representation.
     * @param moves the moves available to the current player.
     * @return evaluation score.
     */
    int evaluateBoard(char[][] board, List<Move> moves);
}
