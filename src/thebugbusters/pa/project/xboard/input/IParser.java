package thebugbusters.pa.project.xboard.input;

/**
 * Represents a parser. Parses an object from a <code>String</code>.
 *
 * @param <E> the type of the object to be parsed.
 */
@FunctionalInterface
public interface IParser<E> {
    /**
     * Parse an object from a <code>String</code>.
     *
     * @param string the <code>String</code> to parse from.
     * @return the parsed object.
     */
    E parse(String string);
}
