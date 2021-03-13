package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.logic.move.IMoveFinder;
import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.state.Player;
import thebugbusters.pa.project.xboard.command.util.IEncoder;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * Leave force mode and set the engine to play the color that is on move. Associate the engine's clock with the
 * color that is on move, the opponent's clock with the color that is not on move. Start the engine's clock.
 * Start thinking and eventually make a move.
 * </blockquote>
 */
class GoCommand implements ICommand {
    private final IMoveFinder moveFinder;
    private final IEncoder<Move> moveEncoder;

    /**
     * Constructs a <code>GoCommand</code> and takes the dependecies.
     *
     * @param moveFinder  move finding algorithm.
     * @param moveEncoder move encoder for communication with XBoard.
     */
    GoCommand(final IMoveFinder moveFinder, final IEncoder<Move> moveEncoder) {
        this.moveFinder = moveFinder;
        this.moveEncoder = moveEncoder;
    }

    /**
     * Leaves force mode, sets the engine to play the color that is on move and attempts to find a move.
     *
     * @return the encoded move.
     */
    @Override
    public Optional<String> execute() {
        /* Leave force mode. */
        GameState.setForceMode(false);

        /* Set the engine to play the color that is on move */
        GameState.setPlayingAs(GameState.getPlayerOnMove());

        /* Make a move */
        final String response = this.move();

        /* Don't forget to set the opponent on move again. */
        this.switchPlayerOnMove();

        return Optional.of(response);
    }

    private String move() {
        /* Try to find a move */
        final Optional<Move> nextMove = this.moveFinder.findMove();

        /* If successful, update the board. If not, resign. */
        final String response;
        if (nextMove.isPresent()) {
            GameState.movePieceOnBoard(nextMove.get());

            final String encodedMove = this.moveEncoder.encode(nextMove.get());
            response = String.format("move %s", encodedMove);
        } else {
            response = "resign";
        }

        /* Send XBoard the response */
        return response;
    }

    private void switchPlayerOnMove() {
        if (GameState.getPlayerOnMove() == Player.WHITE) {
            GameState.setPlayerOnMove(Player.BLACK);
        } else {
            GameState.setPlayerOnMove(Player.WHITE);
        }
    }
}
