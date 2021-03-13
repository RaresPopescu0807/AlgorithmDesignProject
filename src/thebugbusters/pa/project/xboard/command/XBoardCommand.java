package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.state.GameState;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * This command will be sent once immediately after your engine process is started. You can use it to put your
 * engine into "xboard mode" if that is needed. If your engine prints a prompt to ask for user input, you must turn
 * off the prompt and output a newline when the "xboard" command comes in.
 * </blockquote>
 */
class XBoardCommand implements ICommand {
    /**
     * Don't do much, really.
     *
     * @return an empty line <code>String</code>.
     */
    @Override
    public Optional<String> execute() {
        /* Put the game engine into XBoard mode. */
        GameState.setXBoardMode(true);

        /* Send a new line over to XBoard to let it know we're ready to kick some ass. */
        return Optional.of("");
    }
}
