package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Player;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * Set a clock that always belongs to the opponent. N is a number in centiseconds (units of 1/100 second). Even if
 * the opponent changes to playing the opposite color, this clock remains with the opponent.
 * If needed for purposes of board display in force mode (where the engine is not participating in the game) the
 * time clock should be associated with the last color that the engine was set to play, the otim clock with the
 * opposite color.
 * </blockquote>
 */
class OTimCommand implements ICommand {
    private final long time;

    OTimCommand(final long time) {
        this.time = time;
    }

    /**
     * Update opponent time.
     *
     * @return nada.
     */
    @Override
    public Optional<String> execute() {
        /* Update the opponent's time. */
        if (GameState.getPlayingAs() == Player.WHITE) {
            GameState.setBlackTime(this.time);
        } else {
            GameState.setWhiteTime(this.time);
        }

        return Optional.empty();
    }
}
