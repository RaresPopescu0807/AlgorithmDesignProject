package thebugbusters.pa.project.xboard.command.util;

import thebugbusters.pa.project.state.Move;

/**
 * This implementation of {@link IDecoder} decodes a chess move from the format XBoard uses into our own {@link Move}
 * format.
 */
public class MoveDecoder implements IDecoder<Move> {
    /**
     * Decodes a chess move from the format XBoard uses into our own {@link Move} format.
     *
     * @param encodedMove {@link Move} encoded in a <code>String</code>
     * @return the decoded {@link Move}.
     */
    @Override
    public Move decode(final String encodedMove) {
        final Move decodedMove;

        final int fromColumn = decodeColumn(encodedMove.charAt(0));
        final int fromLine = decodeLine(encodedMove.charAt(1));
        final int toColumn = decodeColumn(encodedMove.charAt(2));
        final int toLine = decodeLine(encodedMove.charAt(3));

        if (encodedMoveDefinesPromotion(encodedMove)) {
            final char promotion = encodedMove.charAt(4);
            decodedMove = new Move(fromLine, fromColumn, toLine, toColumn, promotion);
        } else {
            decodedMove = new Move(fromLine, fromColumn, toLine, toColumn);
        }

        return decodedMove;
    }

    private int decodeLine(final char encodedLine) {
        return 8 - ((int) encodedLine - (int) '0');
    }

    private int decodeColumn(final char encodedColumn) {
        return (int) encodedColumn - (int) 'a';
    }

    private boolean encodedMoveDefinesPromotion(final String encodedMove) {
        return encodedMove.length() == 5;
    }
}
