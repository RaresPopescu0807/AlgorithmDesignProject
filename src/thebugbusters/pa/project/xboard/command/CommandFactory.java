package thebugbusters.pa.project.xboard.command;

import thebugbusters.pa.project.logic.move.IMoveFinder;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.xboard.command.util.IDecoder;
import thebugbusters.pa.project.xboard.command.util.IEncoder;

/**
 * Factory for XBoard commands.
 * Does decoupling + dependency injection.
 */
public class CommandFactory {
    private final IMoveFinder moveFinder;
    private final IEncoder<Move> moveEncoder;
    private final IDecoder<Move> moveDecoder;

    public CommandFactory(final IMoveFinder moveFinder,
                          final IEncoder<Move> moveEncoder,
                          final IDecoder<Move> moveDecoder) {
        this.moveFinder = moveFinder;
        this.moveEncoder = moveEncoder;
        this.moveDecoder = moveDecoder;
    }

    public ICommand getXBoardCommand() {
        return new XBoardCommand();
    }

    public ICommand getProtoverCommand(final int protocolVersion) {
        return new ProtoverCommand(protocolVersion);
    }

    public ICommand getNewCommand() {
        return new NewCommand();
    }

    public ICommand getForceCommand() {
        return new ForceCommand();
    }

    public ICommand getGoCommand() {
        return new GoCommand(this.moveFinder, this.moveEncoder);
    }

    public ICommand getWhiteCommand() {
        return new WhiteCommand();
    }

    public ICommand getBlackCommand() {
        return new BlackCommand();
    }

    public ICommand getTimeCommand(final long time) {
        return new TimeCommand(time);
    }

    public ICommand getOTimCommand(final long time) {
        return new OTimCommand(time);
    }

    public ICommand getResultCommand() {
        return new ResultCommand();
    }

    public ICommand getQuitCommand() {
        return new QuitCommand();
    }

    public ICommand getMoveCommand(final String move) {
        return new MoveCommand(move, this.moveFinder, this.moveEncoder, this.moveDecoder);
    }
}
