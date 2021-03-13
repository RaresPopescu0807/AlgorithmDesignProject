package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.state.Player;

import java.util.Optional;
import java.util.Random;

/**
 * As the name suggests, this class implements a simple, dummy chess move finder.<p>
 * <p>
 * At the start of the game, it will pick a pawn and advance it throughout the game by repeatedly calling
 * {@link DummyMoveFinder#findMove()}. If the chosen pawn no longer exists - i.e. it has been captured - or is blocked
 * by an opponent's piece, the move finder will return an empty <code>Optional</code>.<p>
 *
 * <bi>WARNING</bi> this move finder does not check whether the king is safe, so any moves that this will return while
 * in check will be invalid. Beware!<p>
 *
 * <code>DummyMoveFinder</code> uses a static boolean variable as flag to let it know the game has ended and it needs
 * to reset if it is going to be reused by XBoard. This flag should be set to <code>true</code> when the RESULT command
 * is received from XBoard.<p>
 *
 * @see thebugbusters.pa.project.xboard.command.ResultCommand
 */
public class DummyMoveFinder implements IMoveFinder {
    public static boolean reset = false;

    private boolean pawnChosen = false;

    private int pawnLine;
    private int pawnColumn;

    /**
     * Chooses a pawn at the beginning of every game and gradually advances it, one position at a time, until it gets
     * blocked or captured by the opponent.<p>
     *
     * <bi>WARNING</bi> this move finder does not check whether the king is safe, so any moves that this returns
     * while in check will be invalid. Beware!<p>
     *
     * @return
     */
    @Override
    public Optional<Move> findMove() {
        /* If this is a new game, choose a pawn. */
        if (!this.pawnChosen || DummyMoveFinder.reset) {
            this.choosePawn();
        }

        final Optional<Move> move;
        if (this.chosenPawnIsStillThere() && this.chosenPawnCanAdvance()) {
            /* If the pawn is still there, we advance it one position. */
            move = Optional.of(new Move(this.pawnLine, this.pawnColumn, this.nextPawnLine(), this.pawnColumn));
            this.pawnLine = this.nextPawnLine();
        } else {
            /* If the pawn is blocked or captured, return an empty move. */
            move = Optional.empty();
            this.pawnChosen = false;
        }

        return move;
    }

    private void choosePawn() {
        this.pawnColumn = new Random().nextInt(8);

        if (GameState.getPlayingAs() == Player.WHITE) {
            this.pawnLine = 6;
        } else {
            this.pawnLine = 1;
        }

        this.pawnChosen = true;
        DummyMoveFinder.reset = false;
    }

    private boolean chosenPawnIsStillThere() {
        final char expectedChar;
        if (GameState.getPlayingAs() == Player.WHITE) {
            expectedChar = 'P';
        } else {
            expectedChar = 'p';
        }

        return GameState.getBoard()[this.pawnLine][this.pawnColumn] == expectedChar;
    }

    private boolean chosenPawnCanAdvance() {
        return GameState.getBoard()[this.nextPawnLine()][this.pawnColumn] == ' ';
    }

    private int nextPawnLine() {
        if (GameState.getPlayingAs() == Player.WHITE) {
            return this.pawnLine - 1;
        } else {
            return this.pawnLine + 1;
        }
    }
}
