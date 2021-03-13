package thebugbusters.pa.project.xboard.command.util;

import thebugbusters.pa.project.state.Move;

/**
 * This implementation of {@link IEncoder} encodes a chess move from the format we use ({@link Move}) into the format
 * XBoard uses.
 */
public class MoveEncoder implements IEncoder<Move> {
    /**
     * Encodes a chess move from the format we use ({@link Move}) into the format XBoard uses.
     *
     * @param move the move in {@link Move} format.
     * @return the move encoded in a <code>String</code>.
     */
    @Override
    public String encode(final Move move) {
        final StringBuilder moveEncodingBuilder = new StringBuilder();

        moveEncodingBuilder.append(encodeColumn(move.getFromColumn()));
        moveEncodingBuilder.append(encodeLine(move.getFromLine()));
        moveEncodingBuilder.append(encodeColumn(move.getToColumn()));
        moveEncodingBuilder.append(encodeLine(move.getToLine()));

        if (move.getPromotion().isPresent()) {
            moveEncodingBuilder.append(move.getPromotion().get());
        }

        return moveEncodingBuilder.toString();
    }

    private char encodeLine(final int line) {
        return (char) (8 - line + (int) '0');
    }

    private char encodeColumn(final int column) {
        return (char) (column + (int) 'a');
    }
}
