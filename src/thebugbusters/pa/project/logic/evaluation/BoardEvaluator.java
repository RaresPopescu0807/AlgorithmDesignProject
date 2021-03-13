package thebugbusters.pa.project.logic.evaluation;

import thebugbusters.pa.project.logic.move.PieceOptions;
import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Move;
import thebugbusters.pa.project.state.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Board evaluator class.
 * <p>
 * Evaluates a chess board. Evaluates a WHITE advantage as positive score and negative otherwise.
 */
public class BoardEvaluator implements IBoardEvaluator {
    private final IPieceEvaluator pawnEvaluator;
    private final IPieceEvaluator rookEvaluator;
    private final IPieceEvaluator knightEvaluator;
    private final IPieceEvaluator bishopEvaluator;
    private final IPieceEvaluator queenEvaluator;
    private final IPieceEvaluator kingEvaluator;

    public BoardEvaluator() {
        this.pawnEvaluator = new PawnEvaluator();
        this.rookEvaluator = new RookEvaluator();
        this.knightEvaluator = new KnightEvaluator();
        this.bishopEvaluator = new BishopEvaluator();
        this.queenEvaluator = new QueenEvaluator();
        this.kingEvaluator = new KingEvaluator();
    }

    private int endGameScore() {
        final int multiplier = GameState.getPlayerOnMove() == Player.WHITE ? 1 : -1;

        if (!PieceOptions.kingIsSafe()) {
            return (Integer.MIN_VALUE + 1) * multiplier;
        } else {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int evaluateBoard(final char[][] board, final List<Move> moves) {
        if (moves.size() == 0) {
            return endGameScore();
        }

        int score = 0;

        for (int i = 0; i < 64; i++) {
            final int line = i / 8;
            final int column = i % 8;

            final List<Move> pieceMoves = moves.stream()
                    .filter(move -> move.getFromLine() == line && move.getFromColumn() == column)
                    .collect(Collectors.toList());
            
            switch (board[line][column]) {
                case 'P':
                case 'p':
                    score += this.pawnEvaluator.evaluatePiece(board[line][column], line, column, pieceMoves);
                    break;
                case 'R':
                case 'r':
                    score += this.rookEvaluator.evaluatePiece(board[line][column], line, column, pieceMoves);
                    break;
                case 'K':
                case 'k':
                    score += this.knightEvaluator.evaluatePiece(board[line][column], line, column, pieceMoves);
                    break;
                case 'B':
                case 'b':
                    score += this.bishopEvaluator.evaluatePiece(board[line][column], line, column, pieceMoves);
                    break;
                case 'Q':
                case 'q':
                    score += this.queenEvaluator.evaluatePiece(board[line][column], line, column, pieceMoves);
                    break;
                case 'A':
                case 'a':
                    score += this.kingEvaluator.evaluatePiece(board[line][column], line, column, pieceMoves);
                    break;
            }
        }

        return score;
    }
}
