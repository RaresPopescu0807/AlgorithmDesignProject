package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.logic.move.IMoveFinder;
import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.state.Player;
import thebugbusters.pa.project.xboard.command.util.IDecoder;
import thebugbusters.pa.project.xboard.command.util.IEncoder;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * If the move is illegal, print an error message; see the section "Commands from the engine to xboard". If the
 * move is legal and in turn, make it. If not in force mode, stop the opponent's clock, start the engine's clock,
 * start thinking, and eventually make a move.
 * When xboard sends your engine a move, it normally sends coordinate algebraic notation.
 * </blockquote>
 */
class MoveCommand implements ICommand {
    private final String move;

    private final IMoveFinder moveFinder;
    private final IEncoder<Move> moveEncoder;
    private final IDecoder<Move> moveDecoder;

    /**
     * Constructs a MoveCommand and takes its dependencies.
     *
     * @param move        the move received from XBoard.
     * @param moveFinder  a move finding algorithm.
     * @param moveEncoder a move encoder for the response.
     * @param moveDecoder a move decoder fot the input move.
     */
    MoveCommand(final String move, final IMoveFinder moveFinder,
                final IEncoder<Move> moveEncoder,
                final IDecoder<Move> moveDecoder) {
        this.move = move;

        this.moveFinder = moveFinder;
        this.moveEncoder = moveEncoder;
        this.moveDecoder = moveDecoder;
    }

    /**
     * If not in force mode, decode the input move and think of a move for us. May or may not find one. Updates the
     * game state.
     *
     * @return response containing a move (if one was found).
     */
    @Override
    public Optional<String> execute() {
        /* We just received the opponent's move so we decode it and update the board. */
        final Move decodedMove = this.moveDecoder.decode(this.move);
        GameState.movePieceOnBoard(decodedMove);

        /* It's our turn now, so let's update the game state. */
        this.switchPlayerOnMove();

        /*
        If the game is in force mode, there's nothing left for us to do.
        But if not, we make a move of our own.

        Don't forget to set the opponent on move again.
         */
        final Optional<String> responseOptional;
        if (!GameState.isInForceMode()) {
            final String response = this.move();
            this.switchPlayerOnMove();

            responseOptional = Optional.of(response);
        } else {
            responseOptional = Optional.empty();
        }

        return responseOptional;
    }

    private void switchPlayerOnMove() {
        if (GameState.getPlayerOnMove() == Player.WHITE) {
            GameState.setPlayerOnMove(Player.BLACK);
        } else {
            GameState.setPlayerOnMove(Player.WHITE);
        }
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
}
