package thebugbusters.pa.project.xboard.input;

import thebugbusters.pa.project.xboard.command.CommandFactory;
import thebugbusters.pa.project.xboard.command.ICommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements an XBoard command parser.
 */
public class CommandParser implements IParser<ICommand> {
    private final CommandFactory commandFactory;

    public CommandParser(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    /**
     * Parses an XBoard command from a <code>String</code> and returns it.
     *
     * @param string the command <code>String</code>.
     * @return the parsed {@link ICommand}.
     */
    @Override
    public ICommand parse(final String string) {
        final ICommand command;

        if (this.stringMatchesPattern(string, CommandPatterns.MOVE_PATTERN)) {
            command = this.parseMoveString(string);
        } else if (this.stringMatchesPattern(string, CommandPatterns.TIME_PATTERN)) {
            command = this.parseTimeString(string);
        } else if (this.stringMatchesPattern(string, CommandPatterns.OTIM_PATTERN)) {
            command = this.parseOTimString(string);
        } else if (this.stringMatchesPattern(string, CommandPatterns.GO_PATTERN)) {
            command = this.commandFactory.getGoCommand();
        } else if (this.stringMatchesPattern(string, CommandPatterns.WHITE_PATTERN)) {
            command = this.commandFactory.getWhiteCommand();
        } else if (this.stringMatchesPattern(string, CommandPatterns.BLACK_PATTERN)) {
            command = this.commandFactory.getBlackCommand();
        } else if (this.stringMatchesPattern(string, CommandPatterns.FORCE_PATTERN)) {
            command = this.commandFactory.getForceCommand();
        } else if (this.stringMatchesPattern(string, CommandPatterns.PROTOVER_PATTERN)) {
            command = this.parseProtoverString(string);
        } else if (this.stringMatchesPattern(string, CommandPatterns.NEW_PATTERN)) {
            command = this.commandFactory.getNewCommand();
        } else if (this.stringMatchesPattern(string, CommandPatterns.RESULT_PATTERN)) {
            command = this.commandFactory.getResultCommand();
        } else if (this.stringMatchesPattern(string, CommandPatterns.QUIT_PATTERN)) {
            command = this.commandFactory.getQuitCommand();
        } else if (this.stringMatchesPattern(string, CommandPatterns.XBOARD_PATTERN)) {
            command = this.commandFactory.getXBoardCommand();
        } else {
            throw new UnsupportedOperationException("Unknown command pattern: " + string);
        }

        return command;
    }

    private boolean stringMatchesPattern(final String string, final Pattern pattern) {
        return pattern.matcher(string).matches();
    }

    private Matcher match(final Pattern pattern, final String string) {
        final Matcher matcher = pattern.matcher(string);
        final boolean matched = matcher.find();

        assert matched : string;

        return matcher;
    }

    private ICommand parseProtoverString(final String string) {
        final Matcher matcher = this.match(CommandPatterns.PROTOVER_PATTERN, string);

        final int protocolVersion = Integer.parseInt(matcher.group(1));
        return commandFactory.getProtoverCommand(protocolVersion);
    }

    private ICommand parseMoveString(final String string) {
        final Matcher matcher = this.match(CommandPatterns.MOVE_PATTERN, string);

        final String move = matcher.group(1);
        return commandFactory.getMoveCommand(move);
    }

    private ICommand parseTimeString(final String string) {
        final Matcher matcher = this.match(CommandPatterns.TIME_PATTERN, string);

        final long time = Long.parseLong(matcher.group(1));
        return commandFactory.getTimeCommand(time);
    }

    private ICommand parseOTimString(final String string) {
        final Matcher matcher = this.match(CommandPatterns.OTIM_PATTERN, string);

        final long time = Long.parseLong(matcher.group(1));
        return commandFactory.getOTimCommand(time);
    }
}
