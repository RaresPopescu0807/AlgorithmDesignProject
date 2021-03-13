package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds available legal moves for KING.
 */
class KingOptions extends PieceOptions {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Move> findOptions(final int fromLine, final int fromColumn) {
        final List<Move> options = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    try {
                        if (isOpponentPiece(fromLine + i, fromColumn + j)
                                || (GameState.getBoard()[fromLine + i][fromColumn + j] == ' ')) {
                            final Move move = new Move(fromLine, fromColumn, fromLine + i, fromColumn + j);
                            GameState.movePieceOnBoard(move);
                            if (kingIsSafe()) {
                                options.add(move);
                            }
                            GameState.reverse(move);

                        }
                    } catch (final IndexOutOfBoundsException ioobe) {
                    }
                }
            }
        }

        return options;
    }
}
