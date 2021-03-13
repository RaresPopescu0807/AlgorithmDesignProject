package thebugbusters.pa.project.state;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a chess move.<p>
 */
public class Move {
    private static final char NO_PROMOTION = '\0';
    private final int fromLine;
    private final int fromColumn;
    private final int toLine;
    private final int toColumn;
    private final char captured;
    private final char promotion;
    private final char promotedFrom;
    private final boolean isCastling;

    /**
     * Constructs a Move with no pawn promotion.
     *
     * @param fromLine   line from which a piece is moved.
     * @param fromColumn column from which a piece is moved.
     * @param toLine     line to which a piece is moved.
     * @param toColumn   column to which a piece is moved.
     */
    public Move(final int fromLine, final int fromColumn, final int toLine, final int toColumn) {
        this(fromLine, fromColumn, toLine, toColumn, NO_PROMOTION);
    }

    /**
     * Constructs a Move with pawn promotion.
     *
     * @param fromLine   line from which a piece is moved.
     * @param fromColumn column from which a piece is moved.
     * @param toLine     line to which a piece is moved.
     * @param toColumn   column to which a piece is moved.
     * @param promotion  symbol of the piece the pawn will be promoted to.
     */
    public Move(final int fromLine, final int fromColumn, final int toLine, final int toColumn, final char promotion) {
        this.fromLine = fromLine;
        this.fromColumn = fromColumn;
        this.toLine = toLine;
        this.toColumn = toColumn;
        this.captured = GameState.getBoard()[toLine][toColumn];
        this.promotion = promotion;
        this.promotedFrom = GameState.getBoard()[fromLine][fromColumn];
        this.isCastling = Arrays.asList('a', 'A').contains(GameState.getBoard()[fromLine][fromColumn])
                && fromColumn == 4
                && (toColumn == 2 || toColumn == 6)
                && ((fromLine == 0 && toLine == 0) || (fromLine == 7 && toLine == 7));
    }

    public int getFromLine() {
        return this.fromLine;
    }

    public int getFromColumn() {
        return this.fromColumn;
    }

    public int getToLine() {
        return this.toLine;
    }

    public int getToColumn() {
        return this.toColumn;
    }

    public char getCaptured() {
        return this.captured;
    }

    public Optional<Character> getPromotion() {
        if (this.promotion != NO_PROMOTION) {
            return Optional.of(this.promotion);
        } else {
            return Optional.empty();
        }
    }

    public boolean isCastling() {
        return this.isCastling;
    }

    public void print() {
        System.out.print(this.fromColumn);
        System.out.print(this.fromLine);
        System.out.print(this.toColumn);
        System.out.print(this.toLine);
        System.out.println(this.captured);
    }

    public char getPromotedFrom() {
        return promotedFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return fromLine == move.fromLine &&
                fromColumn == move.fromColumn &&
                toLine == move.toLine &&
                toColumn == move.toColumn &&
                captured == move.captured &&
                promotion == move.promotion &&
                promotedFrom == move.promotedFrom &&
                isCastling == move.isCastling;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromLine, fromColumn, toLine, toColumn, captured, promotion, promotedFrom, isCastling);
    }
}
