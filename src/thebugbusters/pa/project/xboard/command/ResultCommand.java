package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.logic.move.DummyMoveFinder;
import thebugbusters.pa.project.state.GameState;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * After the end of each game, xboard will send you a result command. You can use this command to trigger learning.
 * RESULT is either 1-0, 0-1, 1/2-1/2, or *, indicating whether white won, black won, the game was a draw, or the
 * game was unfinished. The COMMENT string is purely a human-readable comment; its content is unspecified and
 * subject to change. In ICS mode, it is passed through from ICS uninterpreted. Example:
 * <p>
 * result 1-0 {White mates}
 * <p>
 * Here are some notes on interpreting the "result" command. Some apply only to playing on ICS ("Zippy" mode).
 * <p>
 * If you won but did not just play a mate, your opponent must have resigned or forfeited. If you lost but were not
 * just mated, you probably forfeited on time, or perhaps the operator resigned manually. If there was a draw for
 * some nonobvious reason, perhaps your opponent called your flag when he had insufficient mating material (or vice
 * versa), or perhaps the operator agreed to a draw manually.
 * <p>
 * You will get a result command even if you already know the game ended -- for example, after you just checkmated
 * your opponent. In fact, if you send the "RESULT {COMMENT}" command (discussed below), you will simply get the
 * same thing fed back to you with "result" tacked in front. You might not always get a "result *" command, however.
 * In particular, you won't get one in local chess engine mode when the user stops playing by selecting Reset,
 * Edit Game, Exit or the like.
 * </blockquote>
 */
public class ResultCommand implements ICommand {
    /**
     * Puts the game in finished mode and rests the move finder.
     *
     * @return nope
     */
    @Override
    public Optional<String> execute() {
        /* The results came in, yay! This means the game is done so let's put the game state into finished mode. */
        GameState.setGameFinished(true);

        /*
        Also, reset the Dummy Move Finder. This is a bit of a hack, but required. Otherwise, if this game engine is
        reused by XBoard, it'll try to make an illegal move. We can't have that.
         */
        DummyMoveFinder.reset = true;

        return Optional.empty();
    }
}
