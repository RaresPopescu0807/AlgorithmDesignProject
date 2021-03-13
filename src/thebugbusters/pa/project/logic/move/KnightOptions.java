package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds available legal moves for KNIGHT.
 */
class KnightOptions extends PieceOptions {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Move> findOptions(final int fromLine, final int fromColumn) {
        final List<Move> options = new ArrayList<>();

        for (int i = -1; i <= 1; i = i + 2)
            for (int j = -1; j <= 1; j = j + 2) {
                try {
                    final Move move;

                    if (isOpponentPiece(fromLine + i, fromColumn + 2 * j)
                            || (GameState.getBoard()[fromLine + i][fromColumn + 2 * j] == ' ')) {
                        move = new Move(fromLine, fromColumn, fromLine + i, fromColumn + 2 * j);
                        GameState.movePieceOnBoard(move);
                        if (kingIsSafe()) {
                            options.add(move);
                        }
                        GameState.reverse(move);

                    }
                } catch (final IndexOutOfBoundsException ioobe) {
                }

                try {
                    final Move move;

                    if (isOpponentPiece(fromLine + 2 * i, fromColumn + j)
                            || (GameState.getBoard()[fromLine + 2 * i][fromColumn + j] == ' ')) {
                        move = new Move(fromLine, fromColumn, fromLine + 2 * i, fromColumn + j);
                        GameState.movePieceOnBoard(move);
                        if (kingIsSafe()) {
                            options.add(move);
                        }
                        GameState.reverse(move);
                    }
                } catch (final IndexOutOfBoundsException ioobe) {
                }
            }

        return options;
    }
}

