package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.Move;

import java.util.List;

/**
 * Finds all legal move options for a piece.
 */
public interface IPieceOptions {
    /**
     * Finds all legal move options for a piece given its position on the chess board.
     * @param fromLine line of piece on board.
     * @param fromColumn column of piece on board.
     * @return list of all legal moves for piece.
     */
    List<Move> findOptions(int fromLine, int fromColumn);
}
