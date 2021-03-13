package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.Move;

import java.util.Optional;

/**
 * This interface defines a chess move finding algorithm.<p>
 * <p>
 * The {@link IMoveFinder#findMove()} method will attempt to find a valid move but may not succeed, which is why it returns an
 * <code>Optional</code>.<p>
 *
 * @see Move
 */
@FunctionalInterface
public interface IMoveFinder {
    /**
     * This method will attempt to find a valid and legal chess move but may not succeed, which is why the result is
     * wrapped with <code>Optional</code>.
     *
     * @return a valid chess move, wrapped with <code>Optional</code>. If no valid legal moves are found, <code>
     * Optional.empty()</code> will be returned.
     */
    Optional<Move> findMove();
}
