package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Player;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * (This command is obsolete as of protocol version 2, but is still sent in some situations to accommodate older
 * engines unless you disable it with the feature command.) Set White on move. Set the engine to play Black.
 * Stop clocks.
 * </blockquote>
 */
class WhiteCommand implements ICommand {
    /**
     * Sets player on move White and sets the engine to play Black.
     *
     * @return nothing to say.
     */
    @Override
    public Optional<String> execute() {
        /* Set player on move White. */
        GameState.setPlayerOnMove(Player.WHITE);

        /* Set the engine to play black. */
        GameState.setPlayingAs(Player.BLACK);

        return Optional.empty();
    }
}
