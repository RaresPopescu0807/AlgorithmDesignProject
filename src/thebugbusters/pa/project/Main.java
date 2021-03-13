package thebugbusters.pa.project;

import thebugbusters.pa.project.logic.evaluation.BoardEvaluator;
import thebugbusters.pa.project.logic.evaluation.IBoardEvaluator;
import thebugbusters.pa.project.logic.move.AlphaBetaMoveFinder;
import thebugbusters.pa.project.logic.move.IMoveFinder;
import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.xboard.command.CommandFactory;
import thebugbusters.pa.project.xboard.command.ICommand;
import thebugbusters.pa.project.xboard.command.util.IDecoder;
import thebugbusters.pa.project.xboard.command.util.IEncoder;
import thebugbusters.pa.project.xboard.command.util.MoveDecoder;
import thebugbusters.pa.project.xboard.command.util.MoveEncoder;
import thebugbusters.pa.project.xboard.input.CommandParser;
import thebugbusters.pa.project.xboard.input.IParser;
import thebugbusters.pa.project.xboard.output.OutputPrinter;

import java.util.Optional;
import java.util.Scanner;

/**
 * If there's somethin' strange in your Java code
 * Who ya gonna call?
 * (BugBusters!)
 * <p>
 * If it's somethin' weird an' it don't look good
 * Who ya gonna call?
 * (BugBusters!)
 * <p>
 * I ain't afraid o' no bug
 * I ain't afraid o' no bug
 * <p>
 * If you're seein' things runnin' through your bash
 * Who can you call?
 * (BugBusters!)
 * <p>
 * An invisible bug sleepin' in your RAM
 * Oh who ya gonna call?
 * (BugBusters!)
 *
 * @author Mihai-Valentin Bita
 * @author Dan Dumitru Tipa
 * @author Rares Bogdan Popescu
 */
public class Main {
    public static void main(final String[] args) {
        /*
        Let's set up an XBoard command parser with a simple minimax finder.
        */
        final IBoardEvaluator boardEvaluator = new BoardEvaluator();
        final IMoveFinder moveFinder = new AlphaBetaMoveFinder(boardEvaluator);
        final IEncoder<Move> moveEncoder = new MoveEncoder();
        final IDecoder<Move> moveDecoder = new MoveDecoder();
        final CommandFactory commandFactory = new CommandFactory(moveFinder, moveEncoder, moveDecoder);
        final IParser<ICommand> commandParser = new CommandParser(commandFactory);

        /*
        This is the main loop. What we do is, we keep listening for new commands from XBoard via STDIN, then parse and
        execute them to the best of our abilities.
        If the command returns a response for XBoard, we print it.
        We do this until we eventually run across the quit command, which - when executed - puts us into quit mode and
        therefore elegantly end the loop.
         */
        final Scanner scanner = new Scanner(System.in);
        while (!GameState.isInQuitMode()) {
            /* Read a command from XBoard. */
            final String commandString = scanner.nextLine();

            /* We figure out what that means. */
            final ICommand xBoardCommand;
            try {
                xBoardCommand = commandParser.parse(commandString);
            } catch (final UnsupportedOperationException exception) {
                /*
                If we reach this bit, we have ourselves a command which we do no support - now or ever.
                Much like any other problems in life, it is best left unattended.
                */
                continue;
            }

            /* We execute the command. */
            final Optional<String> commandResponseOptional = xBoardCommand.execute();

            /* If the command returned a response, we print it. */
            if (commandResponseOptional.isPresent()) {
                OutputPrinter.print(commandResponseOptional.get());
            }
        }
    }
}
