package thebugbusters.pa.project.xboard.command;

import java.util.Optional;

/**
 * A command XBoard may or may not send (because we already told it to shut up with legacy command but ignores us
 * compeletely).
 */
@FunctionalInterface
public interface ICommand {
    /**
     * Executes the command and returns a response for XBoard if it feels like it.
     *
     * @return an optional <code>String</code> response.
     */
    Optional<String> execute();
}
