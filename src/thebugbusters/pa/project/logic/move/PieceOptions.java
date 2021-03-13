package thebugbusters.pa.project.logic.move;

import thebugbusters.pa.project.state.GameState;
import thebugbusters.pa.project.state.Player;

import java.util.Arrays;

/**
 * Abstract piece evaluator. Implements methods for checking king safety.
 * Is extended by all concrete piece move finders.
 */
public abstract class PieceOptions implements IPieceOptions {
    /**
     * Check whether the player on move's king is safe.
     *
     * @return true or false accordingly.
     */
    public static boolean kingIsSafe() {
        return (GameState.getPlayerOnMove() == Player.WHITE && whiteKingIsSafe())
                || (GameState.getPlayerOnMove() == Player.BLACK && blackKingIsSafe());
    }

    /**
     * Checks whether the white king is safe.
     *
     * @return true or false accordingly.
     */
    static boolean whiteKingIsSafe() {
        //b/q
        int radius = 1;
        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                try {
                    while (GameState.getBoard()[GameState.WhiteKL + radius * i][GameState.WhiteKC + radius * j] == ' ') {
                        radius++;
                    }
                    if (('b' == GameState.getBoard()[GameState.WhiteKL + radius * i][GameState.WhiteKC + radius * j])
                            || ('q' == GameState.getBoard()[GameState.WhiteKL + radius * i][GameState.WhiteKC + radius * j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                radius = 1;
            }
        }
        radius = 1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((!(Math.abs(i) == 1 && Math.abs(j) == 1)) && (!(i == 0 && j == 0))) {
                    try {
                        while (GameState.getBoard()[GameState.WhiteKL + radius * i][GameState.WhiteKC + radius * j] == ' ') {
                            radius++;
                        }
                        if (('r' == GameState.getBoard()[GameState.WhiteKL + radius * i][GameState.WhiteKC + radius * j])
                                || ('q' == GameState.getBoard()[GameState.WhiteKL + radius * i][GameState.WhiteKC + radius * j])) {
                            return false;
                        }
                    } catch (Exception e) {
                    }
                    radius = 1;
                }
            }
        }
        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                try {
                    if ('k' == GameState.getBoard()[GameState.WhiteKL + 2 * i][GameState.WhiteKC + j]) {
                        return false;
                    }
                } catch (Exception e) {
                }
                try {
                    if ('k' == GameState.getBoard()[GameState.WhiteKL + i][GameState.WhiteKC + 2 * j]) {
                        return false;
                    }
                } catch (Exception e) {
                }
            }
        }
        //pawn
        for (int j = -1; j <= 1; j = j + 2) {
            try {
                if ('p' == GameState.getBoard()[GameState.WhiteKL - 1][GameState.WhiteKC + j]) {
                    return false;
                }
            } catch (Exception e) {
            }
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    try {
                        if ('a' == GameState.getBoard()[GameState.WhiteKL + i][GameState.WhiteKC + j]) {
                            return false;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks whether the black king is safe.
     *
     * @return true or false accordingly.
     */
    static boolean blackKingIsSafe() {
        //b/q
        int radius = 1;
        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                try {
                    while (GameState.getBoard()[GameState.BlackKL + radius * i][GameState.BlackKC + radius * j] == ' ') {
                        radius++;
                    }
                    if (('B' == GameState.getBoard()[GameState.BlackKL + radius * i][GameState.BlackKC + radius * j])
                            || ('Q' == GameState.getBoard()[GameState.BlackKL + radius * i][GameState.BlackKC + radius * j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                radius = 1;
            }
        }
        radius = 1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((!(Math.abs(i) == 1 && Math.abs(j) == 1)) && (!(i == 0 && j == 0))) {
                    try {
                        while (GameState.getBoard()[GameState.BlackKL + radius * i][GameState.BlackKC + radius * j] == ' ') {
                            radius++;
                        }
                        if (('R' == GameState.getBoard()[GameState.BlackKL + radius * i][GameState.BlackKC + radius * j])
                                || ('Q' == GameState.getBoard()[GameState.BlackKL + radius * i][GameState.BlackKC + radius * j])) {
                            return false;
                        }
                    } catch (Exception e) {
                    }
                    radius = 1;
                }
            }
        }
        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                try {
                    if ('K' == GameState.getBoard()[GameState.BlackKL + 2 * i][GameState.BlackKC + j]) {
                        return false;
                    }
                } catch (Exception e) {
                }
                try {
                    if ('K' == GameState.getBoard()[GameState.BlackKL + i][GameState.BlackKC + 2 * j]) {
                        return false;
                    }
                } catch (Exception e) {
                }
            }
        }
        //pawn
        for (int j = -1; j <= 1; j = j + 2) {
            try {
                if ('P' == GameState.getBoard()[GameState.BlackKL + 1][GameState.BlackKC + j]) {
                    return false;
                }
            } catch (Exception e) {
            }
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    try {
                        if ('A' == GameState.getBoard()[GameState.BlackKL + i][GameState.BlackKC + j]) {
                            return false;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks whether a piece belongs to the opponent of the player on move.
     *
     * @param line line of the piece on board.
     * @param column column of the piece on board.
     * @return true or false accordingly.
     */
    boolean isOpponentPiece(final int line, final int column) {
        if (Arrays.asList('a', 'A').contains(GameState.getBoard()[line][column])) {
            return false;
        }

        return (Character.isLowerCase(GameState.getBoard()[line][column]) && GameState.getPlayerOnMove() == Player.WHITE)
                || (Character.isUpperCase(GameState.getBoard()[line][column]) && GameState.getPlayerOnMove() == Player.BLACK);
    }
}
