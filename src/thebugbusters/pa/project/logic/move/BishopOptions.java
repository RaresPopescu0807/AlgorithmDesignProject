package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds available legal moves for BISHOP.
 */
class BishopOptions extends PieceOptions {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Move> findOptions(final int fromLine, final int fromColumn) {
        final List<Move> options = new ArrayList<>();
        int i, j, radius = 1;
        for (i = -1; i <= 1; i = i + 2) {
            for (j = -1; j <= 1; j = j + 2) {
                try {
                    Move move;
                    while (GameState.getBoard()[fromLine + radius * i][fromColumn + radius * j] == ' ') {
                        move = new Move(fromLine, fromColumn, fromLine + radius * i, fromColumn + radius * j);
                        GameState.movePieceOnBoard(move);
                        if (kingIsSafe()) {
                            options.add(move);
                        }
                        GameState.reverse(move);
                        radius++;
                    }
                    if (isOpponentPiece(fromLine + radius * i, fromColumn + radius * j)) {
                        move = new Move(fromLine, fromColumn, fromLine + radius * i, fromColumn + radius * j);
                        GameState.movePieceOnBoard(move);
                        if (kingIsSafe()) {
                            options.add(move);
                        }
                        GameState.reverse(move);

                    }
                } catch (final IndexOutOfBoundsException ioobe) {
                }
                radius = 1;
            }
        }
        return options;
    }
}
