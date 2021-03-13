package thebugbusters.pa.project.xboard.command.util;

/**
 * Encodes stuff.
 *
 * @param <T> the type of the object to be encoded.
 */
public interface IEncoder<T> {
    /**
     * Encodes something something a <code>String</code>.
     *
     * @param object the object to be encoded.
     * @return the <code>String</code> encoding.
     */
    String encode(T object);
}
