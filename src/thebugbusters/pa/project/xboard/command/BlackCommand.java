package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Player;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * (This command is obsolete as of protocol version 2, but is still sent in some situations to accommodate older
 * engines unless you disable it with the feature command.) Set Black on move. Set the engine to play White.
 * Stop clocks.
 * </blockquote>
 */
class BlackCommand implements ICommand {
    /**
     * Sets player on move Black, sets the engine to play white.
     *
     * @return empty response.
     */
    @Override
    public Optional<String> execute() {
        /* Set player on move Black. */
        GameState.setPlayerOnMove(Player.BLACK);

        /* Set the engine to play White. */
        GameState.setPlayingAs(Player.WHITE);

        return Optional.empty();
    }
}
