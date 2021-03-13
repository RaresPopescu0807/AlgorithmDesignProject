package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Player;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * Reset the board to the standard chess starting position. Set White on move. Leave force mode and set the engine
 * to play Black. Associate the engine's clock with Black and the opponent's clock with White. Reset clocks and time
 * controls to the start of a new game. Use wall clock for time measurement. Stop clocks. Do not ponder on this
 * move, even if pondering is on. Remove any search depth limit previously set by the sd command.
 * </blockquote>
 */
class NewCommand implements ICommand {
    /**
     * Resets the board, sets White on move, leaves force mode and sets the engine to play black.
     *
     * @return abslutely nothing.
     */
    @Override
    public Optional<String> execute() {
        /* Reset the board and what not. */
        GameState.reset();

        /* Set player on move White. */
        GameState.setPlayerOnMove(Player.WHITE);

        /* Set the engine to play Black. */
        GameState.setPlayingAs(Player.BLACK);

        return Optional.empty();
    }
}
