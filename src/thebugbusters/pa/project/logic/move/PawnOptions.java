package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.state.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds available legal moves for PAWN.
 */
class PawnOptions extends PieceOptions {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Move> findOptions(final int fromLine, final int fromColumn) {
        final int pawnStep = GameState.getPlayerOnMove() == Player.WHITE ? -1 : 1;
        final int promotionLine = GameState.getPlayerOnMove() == Player.WHITE ? 1 : 6;
        final int startingLine = GameState.getPlayerOnMove() == Player.WHITE ? 6 : 1;
        final char promotionChar = GameState.getPlayerOnMove() == Player.WHITE ? 'Q' : 'q';

        final List<Move> options = new ArrayList<>();

        for (int j = -1; j <= 1; j = j + 2) {//capture
            try {
                if ((fromLine != promotionLine) && (isOpponentPiece(fromLine + pawnStep, fromColumn + j))) {
                    final Move move = new Move(fromLine, fromColumn, fromLine + pawnStep, fromColumn + j);
                    GameState.movePieceOnBoard(move);
                    if (kingIsSafe()) {
                        options.add(move);
                    }
                    GameState.reverse(move);

                }
            } catch (final IndexOutOfBoundsException ioobe) {
            }
        }
        try { //move one up & move two up
            if ((fromLine != promotionLine) && (GameState.getBoard()[fromLine + pawnStep][fromColumn] == ' ')) {
                Move move = new Move(fromLine, fromColumn, fromLine + pawnStep, fromColumn);
                GameState.movePieceOnBoard(move);
                if (kingIsSafe()) {
                    options.add(move);
                }
                GameState.reverse(move);
                if ((fromLine == startingLine) && (GameState.getBoard()[fromLine + 2 * pawnStep][fromColumn] == ' ')) {
                    move = new Move(fromLine, fromColumn, fromLine + 2 * pawnStep, fromColumn);
                    GameState.movePieceOnBoard(move);
                    if (kingIsSafe()) {
                        options.add(move);
                    }
                    GameState.reverse(move);

                }

            }
        } catch (final IndexOutOfBoundsException ioobe) {
        }
        for (int j = -1; j <= 1; j = j + 2) {//capture & promote
            try {
                if ((fromLine == promotionLine)
                        && (Character.isLowerCase(GameState.getBoard()[fromLine + pawnStep][fromColumn + j]))) {
                    final Move move = new Move(fromLine, fromColumn, fromLine + pawnStep, fromColumn + j, promotionChar);
                    GameState.movePieceOnBoard(move);
                    if (kingIsSafe()) {
                        options.add(move);
                    }
                    GameState.reverse(move);

                }
            } catch (final IndexOutOfBoundsException ioobe) {
            }
        }
        try //move one and promote
        {
            if ((fromLine == promotionLine) && (GameState.getBoard()[fromLine + pawnStep][fromColumn] == ' ')) {
                final Move move = new Move(fromLine, fromColumn, fromLine + pawnStep, fromColumn, promotionChar);
                GameState.movePieceOnBoard(move);
                if (kingIsSafe()) {
                    options.add(move);
                }
                GameState.reverse(move);

            }
        } catch (final IndexOutOfBoundsException ioobe) {
        }

        return options;
    }
}
