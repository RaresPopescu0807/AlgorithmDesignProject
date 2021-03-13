package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds available legal moves for ROOK.
 */
class RookOptions extends PieceOptions {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Move> findOptions(final int fromLine, final int fromColumn) {
        final List<Move> options = new ArrayList<>();

        int radius = 1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((!(Math.abs(i) == 1 && Math.abs(j) == 1)) && (!(i == 0 && j == 0))) {
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
        }

        return options;
    }
}
