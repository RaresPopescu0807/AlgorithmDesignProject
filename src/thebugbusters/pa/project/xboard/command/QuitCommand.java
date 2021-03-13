package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * The chess engine should immediately exit. This command is used when xboard is itself exiting, and also between
 * games if the -xreuse command line option is given (or -xreuse2 for the second engine).
 * </blockquote>
 */
class QuitCommand implements ICommand {
    /**
     * Puts the game in quit mode so we can get this over with.
     *
     * @return not a single thing.
     */
    @Override
    public Optional<String> execute() {
        /*
        Put the game state into quit mode and the main loop will exit instead of reading another command from XBoard.
        Hopefully, it won't send any of its damned signals and mess things up.

        It hurts to set you free
        But you'll never follow me
        The end of laughter and soft lies
        The end of nights we tried to die
        This is the end
         */
        GameState.setQuitMode(true);

        return Optional.empty();
    }
}
