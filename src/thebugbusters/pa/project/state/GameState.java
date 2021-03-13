package thebugbusters.pa.project.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the chess game state.<p>
 * <p>
 * Holds the player on move, the player assigned to this engine, game modes, time left and the board.<p>
 * <p>
 * The board is represented as a two-dimensional <code>char</code> array.<p>
 */
public abstract class GameState {
    public static int BlackKL, BlackKC, WhiteKL, WhiteKC; // positions of kings
    public static int depth = 4;
    public static List<Move> moveHistory;
    private static boolean xBoardMode;
    private static boolean forceMode;
    private static boolean quitMode;
    private static boolean gameFinished;
    private static Player playerOnMove;
    private static Player playingAs;
    private static long whiteTime;
    private static long blackTime;
    private static char[][] board;

    private static boolean whiteQueenPresent;
    private static boolean blackQueenPresent;

    private static int piecesOnBoard;

    private GameState() {
    }

    /**
     * Resets the game state.
     * <p>
     * Sets all flags false, resets the player on move and playing as, resets time to 0 and reinitialize the board for
     * a fresh new game.
     */
    public static void reset() {
        GameState.xBoardMode = false;
        GameState.forceMode = false;
        GameState.quitMode = false;
        GameState.gameFinished = false;

        GameState.playerOnMove = null;
        GameState.playingAs = null;

        GameState.whiteTime = 0;
        GameState.blackTime = 0;

        GameState.board = new char[][]{
                {'r', 'k', 'b', 'q', 'a', 'b', 'k', 'r'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'R', 'K', 'B', 'Q', 'A', 'B', 'K', 'R'}
        };
        WhiteKL = 7;
        WhiteKC = 4;
        BlackKL = 0;
        BlackKC = 4;

        whiteQueenPresent = true;
        blackQueenPresent = true;

        piecesOnBoard = 32;

        moveHistory = new ArrayList<>();
    }

    public static void setForceMode(final boolean forceMode) {
        GameState.forceMode = forceMode;
    }

    public static boolean isInForceMode() {
        return GameState.forceMode;
    }

    public static void setXBoardMode(final boolean xBoardMode) {
        GameState.xBoardMode = xBoardMode;
    }

    public static boolean isInXBoardMode() {
        return GameState.xBoardMode;
    }

    public static boolean isGameFinished() {
        return GameState.gameFinished;
    }

    public static void setGameFinished(final boolean gameFinished) {
        GameState.gameFinished = gameFinished;
    }

    public static Player getPlayerOnMove() {
        return GameState.playerOnMove;
    }

    public static void setPlayerOnMove(final Player playerOnMove) {
        GameState.playerOnMove = playerOnMove;
    }

    public static Player getPlayingAs() {
        return GameState.playingAs;
    }

    public static void setPlayingAs(final Player playingAs) {
        GameState.playingAs = playingAs;
    }

    public static void setQuitMode(final boolean quitMode) {
        GameState.quitMode = quitMode;
    }

    public static long getWhiteTime() {
        return GameState.whiteTime;
    }

    public static void setWhiteTime(final long whiteTime) {
        GameState.whiteTime = whiteTime;
    }

    public static long getBlackTime() {
        return GameState.blackTime;
    }

    public static void setBlackTime(final long blackTime) {
        GameState.blackTime = blackTime;
    }

    public static boolean isInQuitMode() {
        return GameState.quitMode;
    }

    public static char[][] getBoard() {
        return GameState.board;
    }

    public static boolean isEndGame() {
        return moveHistory.size() > 30;
    }

    public static boolean queensAreGone() {
        return !whiteQueenPresent && !blackQueenPresent;
    }

    public static int numberOfPiecesOnBoard() {
        return piecesOnBoard;
    }

    /**
     * Checks whether a move is en passant.
     *
     * @param move move.
     * @return true or false accordingly.
     */
    private static boolean isEnPassant(final Move move) {
        /*
         If it's not a pawn that's moved, it's not en passant.
         */
        final char expectedToFind = playerOnMove == Player.WHITE ? 'P' : 'p';
        if (!(board[move.getFromLine()][move.getFromColumn()] == expectedToFind)) {
            return false;
        }

        /*
         If it's not from lines 3 or 4 (in our notation), it's not en passant.
         */
        final int expectedFromLine = playerOnMove == Player.WHITE ? 3 : 4;
        if (!(move.getFromLine() == expectedFromLine)) {
            return false;
        }

        /*
         The last move must be a pawn promotion with double step and lines and columns must fit somehow :?.
         */
        final char expectedCapturedPawn = expectedToFind == 'P' ? 'p' : 'P';
        final Move lastMove = moveHistory.get(moveHistory.size() - 1);
        if (!(board[lastMove.getToLine()][lastMove.getToColumn()] == expectedCapturedPawn)) {
            return false;
        }
        if (lastMove.getToColumn() != move.getToColumn()) {
            return false;
        }
        if (Math.abs(lastMove.getToLine() - lastMove.getFromLine()) != 2) {
            return false;
        }
        return Math.abs(move.getToLine() - lastMove.getToLine()) == 1;

    }

    /**
     * Executes a move on the board representation and adds it to the move history.
     *
     * @param move A legal, decoded move.
     * @see Move
     */
    public static void movePieceOnBoard(final Move move) {
        if (move.isCastling()) {
            GameState.board[move.getToLine()][move.getToColumn()] = GameState.board[move.getFromLine()][move.getFromColumn()];
            GameState.board[move.getFromLine()][move.getFromColumn()] = ' ';

            final int rookLine = move.getFromLine();
            final int rookFromColumn = move.getToColumn() == 2 ? 0 : 7;
            final int rookToColumn = move.getToColumn() == 2 ? 3 : 5;
            GameState.board[rookLine][rookToColumn] = GameState.board[rookLine][rookFromColumn];
            GameState.board[rookLine][rookFromColumn] = ' ';
        } else if (move.getPromotion().isPresent()) {
            char promotion = move.getPromotion().get();
            if (GameState.playerOnMove == Player.WHITE) {
                promotion = Character.toUpperCase(promotion);
            }

            if (promotion == 'Q') {
                whiteQueenPresent = true;
            }
            if (promotion == 'q') {
                blackQueenPresent = true;
            }

            GameState.board[move.getToLine()][move.getToColumn()] = promotion;
        } else {
            if (move.getCaptured() == 'q') {
                blackQueenPresent = false;
            }

            if (move.getCaptured() == 'Q') {
                whiteQueenPresent = false;
            }

            if (move.getCaptured() != ' ') {
                piecesOnBoard--;
            }

            if (GameState.getBoard()[move.getFromLine()][move.getFromColumn()] == 'A') {
                WhiteKL = move.getToLine();
                WhiteKC = move.getToColumn();
            }
            if (GameState.getBoard()[move.getFromLine()][move.getFromColumn()] == 'a') {
                BlackKL = move.getToLine();
                BlackKC = move.getToColumn();
            }
            GameState.board[move.getToLine()][move.getToColumn()]
                    = GameState.board[move.getFromLine()][move.getFromColumn()];
        }

        if (isEnPassant(move)) {
            board[move.getFromLine()][move.getToColumn()] = ' ';
        }

        GameState.board[move.getFromLine()][move.getFromColumn()] = ' ';

        moveHistory.add(move);
    }

    /**
     * Reverses a move on the board then removes it from move history.
     * @param move a legal, decoded move.
     */
    public static void reverse(final Move move) {
        if (move.getPromotion().isPresent()) {
            GameState.board[move.getFromLine()][move.getFromColumn()] = move.getPromotedFrom();

        } else {
            if (move.getCaptured() == 'q') {
                blackQueenPresent = true;
            }

            if (move.getCaptured() == 'Q') {
                whiteQueenPresent = true;
            }

            if (move.getCaptured() != ' ') {
                piecesOnBoard++;
            }

            if (GameState.getBoard()[move.getToLine()][move.getToColumn()] == 'A') {
                WhiteKL = move.getFromLine();
                WhiteKC = move.getFromColumn();
            }
            if (GameState.getBoard()[move.getToLine()][move.getToColumn()] == 'a') {
                BlackKL = move.getFromLine();
                BlackKC = move.getFromColumn();
            }
            GameState.board[move.getFromLine()][move.getFromColumn()] = GameState.board[move.getToLine()][move.getToColumn()];
        }
        GameState.board[move.getToLine()][move.getToColumn()] = move.getCaptured();

        moveHistory.remove(moveHistory.size() - 1);
    }
}
