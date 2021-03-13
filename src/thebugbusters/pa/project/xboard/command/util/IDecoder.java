package thebugbusters.pa.project.xboard.command.util;

/**
 * Decodes stuff.
 *
 * @param <T> the type of the object to be decoded.
 */
public interface IDecoder<T> {
    /**
     * Decodes something from a <code>String</code>.
     *
     * @param string the <code>String</code> containing the encoding.
     * @return the decoded Object.
     */
    T decode(String string);
}
