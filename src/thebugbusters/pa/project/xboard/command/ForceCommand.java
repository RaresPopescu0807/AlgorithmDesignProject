package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * Set the engine to play neither color ("force mode"). Stop clocks. The engine should check that moves received in
 * force mode are legal and made in the proper turn, but should not think, ponder, or make moves of its own.
 * </blockquote>
 */
class ForceCommand implements ICommand {
    /**
     * Sets the engine to play neither color and sets the game engine to not reply to moves.
     *
     * @return zilch.
     */
    @Override
    public Optional<String> execute() {
        /* Enable force mode. */
        GameState.setForceMode(true);

        return Optional.empty();
    }
}
