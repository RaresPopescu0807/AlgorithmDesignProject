package thebugbusters.pa.project.xboard.output;

/**
 * OutputPrinter for XBoard.
 */
public interface OutputPrinter {
    /**
     * Prints a <code>String</code> to <code>STDOUT</code> and flushes.
     *
     * @param string the thing we want to print.
     */
    static void print(final String string) {
        System.out.println(string);
        System.out.flush();
    }
}
