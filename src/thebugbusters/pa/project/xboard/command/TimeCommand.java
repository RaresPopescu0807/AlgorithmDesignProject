package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Player;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * Set a clock that always belongs to the engine. N is a number in centiseconds (units of 1/100 second). Even if the
 * engine changes to playing the opposite color, this clock remains with the engine.
 * </blockquote>
 */
class TimeCommand implements ICommand {
    private final long time;

    TimeCommand(final long time) {
        this.time = time;
    }

    /**
     * Updates this engine's remaining time.
     *
     * @return nothing.
     */
    @Override
    public Optional<String> execute() {
        /* Update out time left. */
        if (GameState.getPlayingAs() == Player.WHITE) {
            GameState.setWhiteTime(this.time);
        } else {
            GameState.setBlackTime(this.time);
        }

        return Optional.empty();
    }
}
