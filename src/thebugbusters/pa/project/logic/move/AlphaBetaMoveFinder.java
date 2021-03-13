package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.logic.evaluation.IBoardEvaluator;
import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.state.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * As the name suggests, this class implements a standard minimax move finder. No alpha-beta pruning.<p>
 * <p>
 * It depends on an {@link IBoardEvaluator}.
 */
public class AlphaBetaMoveFinder implements IMoveFinder {
    private final IBoardEvaluator boardEvaluator;

    private final KingOptions kingOptions = new KingOptions();
    private final QueenOptions queenOptions = new QueenOptions();
    private final BishopOptions bishopOptions = new BishopOptions();
    private final RookOptions rookOptions = new RookOptions();
    private final KnightOptions knightOptions = new KnightOptions();
    private final PawnOptions pawnOptions = new PawnOptions();

    private Move move;

    /**
     * Constructs the move finder.
     * @param boardEvaluator your board evaluator of choice.
     */
    public AlphaBetaMoveFinder(final IBoardEvaluator boardEvaluator) {
        this.boardEvaluator = boardEvaluator;
    }

    private long ourTime() {
        return GameState.getPlayingAs() == Player.WHITE ? GameState.getWhiteTime() : GameState.getBlackTime();
    }

    /**
     * Detects whether we reached a move repetition. That would cause an automatic draw.
     * <p>
     * This checks 2 move pairs behind.
     *
     * @return true if we have ourselves a repetition.
     */
    private boolean isRepetition() {
        final int size = GameState.moveHistory.size();

        return size >= 8
                && GameState.moveHistory.get(size - 1).equals(GameState.moveHistory.get(size - 5))
                && GameState.moveHistory.get(size - 2).equals(GameState.moveHistory.get(size - 6))
                && GameState.moveHistory.get(size - 3).equals(GameState.moveHistory.get(size - 7))
                && GameState.moveHistory.get(size - 4).equals(GameState.moveHistory.get(size - 8));
    }

    /**
     * Initiates the minimax move search. The search depth is configured in {@link GameState#depth}.
     *
     * @return best move we got.
     */
    private Move minimax() {
        final long time = ourTime();

        if (time > 12000 && (GameState.queensAreGone() || GameState.numberOfPiecesOnBoard() <= 15)) {
            GameState.depth = 6;
        } else if (time > 9000) {
            GameState.depth = 5;
        } else if (time > 6000){
            GameState.depth = 4;
        } else {
            GameState.depth = 3;
        }

        if (GameState.getPlayingAs() == Player.WHITE) {
            max(GameState.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            min(GameState.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        return move;
    }

    /**
     * The min part. Grab all available moves available and select the one with the minimum evaluation score.
     * The selected move will be saved in {@link AlphaBetaMoveFinder#move} to be later returned by
     * {@link AlphaBetaMoveFinder#minimax()}.
     *
     * @param depth the desired depth.
     * @return the minimum move score found.
     */
    private int min(final int depth, final int alpha, int beta) {
        /*
         If depth limit is reached, evaluate current state.
         */
        if (depth == 0) {
            /*
             If this is a repetition draw, return 0 (obvious draw score).
             */
            if (isRepetition()) {
                return 0;
            }

            return this.boardEvaluator.evaluateBoard(GameState.getBoard(), Arrays.asList(new Move(0, 0, 0, 0)));
        }

        final List<Move> moves = getAllOptions();

        if (moves.isEmpty()) {
            return this.boardEvaluator.evaluateBoard(GameState.getBoard(), moves);
        }

        /*
         If we have valid moves, evaluate each one recursively and return the lowest evaluation score found.
         */
        for (final Move move : moves) {
            GameState.movePieceOnBoard(move);
            GameState.setPlayerOnMove(Player.WHITE);

            final int newScore = max(depth - 1, alpha, beta);

            if (newScore <= alpha) {
                GameState.reverse(move);
                GameState.setPlayerOnMove(Player.BLACK);
                return alpha;    // alpha cut-off
            }

            // dintre toate variantele pe care maxi le permite (!!!),
            // mini o va alege pe cea cu scor minim
            if (newScore < beta) {
                if (GameState.depth == depth) {
                    this.move = move;
                }
                beta = newScore;
            }

            GameState.reverse(move);
            GameState.setPlayerOnMove(Player.BLACK);
        }

        return beta;
    }

    /**
     * The max part. Grab all available moves available and select the one with the maximum evaluation score.
     * The selected move will be saved in {@link AlphaBetaMoveFinder#move} to be later returned by
     * {@link AlphaBetaMoveFinder#minimax()}.
     *
     * @param depth the desired depth.
     * @return the maximum move score found.
     */
    private int max(final int depth, int alpha, final int beta) {
        /*
         If depth limit is reached, evaluate current state.
         */
        if (depth == 0) {
            /*
             If this is a repetition draw, return 0 (obvious draw score).
             */
            if (isRepetition()) {
                return 0;
            }

            return this.boardEvaluator.evaluateBoard(GameState.getBoard(), Arrays.asList(new Move(0, 0, 0, 0)));
        }

        final List<Move> moves = getAllOptions();

        if (moves.isEmpty()) {
            return this.boardEvaluator.evaluateBoard(GameState.getBoard(), moves);
        }

        /*
         If we have valid moves, evaluate each one recursively and return the highest evaluation score found.
         */
        for (final Move move : moves) {
            GameState.movePieceOnBoard(move);
            GameState.setPlayerOnMove(Player.BLACK);

            final int newScore = min(depth - 1, alpha, beta);

            if (newScore >= beta) {
                GameState.reverse(move);
                GameState.setPlayerOnMove(Player.WHITE);
                return beta;    // beta cut-off
            }

            // dintre toate variantele pe care mini le permite (!!!),
            // maxi o va alege pe cea cu scor maxim
            if (newScore > alpha) {
                if (GameState.depth == depth) {
                    this.move = move;
                }
                alpha = newScore;
            }

            GameState.reverse(move);
            GameState.setPlayerOnMove(Player.WHITE);
        }

        return alpha;
    }

    /**
     * Find all possible moves for all available pieces of the current player.
     *
     * @return list of all possible moves.
     */
    public List<Move> getAllOptions() {
        final List<Move> allOptions = new ArrayList<>();

        for (int i = 0; i < 64; i++) {
            final int line = i / 8;
            final int column = i % 8;

            if (GameState.getPlayerOnMove() == Player.WHITE) {
                if ('A' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(kingOptions.findOptions(line, column));
                }
                if ('Q' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(queenOptions.findOptions(line, column));
                }
                if ('B' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(bishopOptions.findOptions(line, column));
                }
                if ('R' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(rookOptions.findOptions(line, column));
                }
                if ('K' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(knightOptions.findOptions(line, column));
                }
                if ('P' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(pawnOptions.findOptions(line, column));
                }
            } else {
                if ('a' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(kingOptions.findOptions(line, column));
                }
                if ('q' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(queenOptions.findOptions(line, column));
                }
                if ('b' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(bishopOptions.findOptions(line, column));
                }
                if ('r' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(rookOptions.findOptions(line, column));
                }
                if ('k' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(knightOptions.findOptions(line, column));
                }
                if ('p' == (GameState.getBoard()[line][column])) {
                    allOptions.addAll(pawnOptions.findOptions(line, column));
                }
            }
        }

        return allOptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Move> findMove() {
        return Optional.of(minimax());
    }
}
