package thebugbusters.pa.project.xboard.command;

import java.util.Optional;

/**
 * From xboard documentation:
 * <blockquote>
 * Beginning in protocol version 2 (in which N=2), this command will be sent immediately after the "xboard" command.
 * If you receive some other command immediately after "xboard" (such as "new"), you can assume that protocol
 * version 1 is in use. The "protover" command is the only new command that xboard always sends in version 2. All
 * other new commands to the engine are sent only if the engine first enables them with the "feature" command.
 * Protocol versions will always be simple integers so that they can easily be compared.
 * Your engine should reply to the protover command by sending the "feature" command (see below) with the list of
 * non-default feature settings that you require, if any.
 * Your engine should never refuse to run due to receiving a higher protocol version number than it is expecting!
 * New protocol versions will always be compatible with older ones by default; the larger version number is simply
 * a hint that additional "feature" command options added in later protocol versions may be accepted.
 * </blockquote>
 */
class ProtoverCommand implements ICommand {
    private final int protocolVersion;

    ProtoverCommand(final int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    /**
     * Disables signals, sets engine name and disables the obsolete WHITE and BLACK commands.
     *
     * @return the response for XBoard in a <code>String</code>.
     */
    @Override
    public Optional<String> execute() {
        final StringBuilder responseBuilder = new StringBuilder();

        /* Yo Dre, I got something to say */
        responseBuilder.append("feature done=0").append("\n");

        /* Tell XBoard we don't want any SIGINTs or SIGTERMs because otherwise all goes down in flames. */
        responseBuilder.append("feature sigint=0").append("\n");

        /* Standard notation moves, please. */
        responseBuilder.append("feature san=0").append("\n");

        /* Who ya gonna call? (BugBusters!) */
        responseBuilder.append("feature myname=\"TheBugBusters\"").append("\n");

        /* The BLACK and WHITE commands are there for backwards compatibility only. So shut it, will ya? */
        responseBuilder.append("feature colors=0").append("\n");

        /* Activate debug messages in case we need them. */
        responseBuilder.append("feature debug=1").append("\n");

        /* Tell XBoard we're done with the features so it doesn't have to wait 2 more excruciating seconds to realize it
         on its own. */
        responseBuilder.append("feature done=1");

        return Optional.of(responseBuilder.toString());
    }
}
