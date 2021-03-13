package thebugbusters.pa.project.xboard.input;

import java.util.regex.Pattern;

abstract class CommandPatterns {
    private static final String XBOARD_REGEX = "^xboard$";
    public static final Pattern XBOARD_PATTERN = Pattern.compile(XBOARD_REGEX);
    private static final String PROTOVER_REGEX = "^protover (\\d)$";
    public static final Pattern PROTOVER_PATTERN = Pattern.compile(PROTOVER_REGEX);
    private static final String NEW_REGEX = "^new$";
    public static final Pattern NEW_PATTERN = Pattern.compile(NEW_REGEX);
    private static final String FORCE_REGEX = "^force$";
    public static final Pattern FORCE_PATTERN = Pattern.compile(FORCE_REGEX);
    private static final String GO_REGEX = "^go$";
    public static final Pattern GO_PATTERN = Pattern.compile(GO_REGEX);
    private static final String WHITE_REGEX = "^white$";
    public static final Pattern WHITE_PATTERN = Pattern.compile(WHITE_REGEX);
    private static final String BLACK_REGEX = "^black$";
    public static final Pattern BLACK_PATTERN = Pattern.compile(BLACK_REGEX);
    private static final String TIME_REGEX = "^time (\\d+)$";
    public static final Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX);
    private static final String OTIM_REGEX = "^otim (\\d+)$";
    public static final Pattern OTIM_PATTERN = Pattern.compile(OTIM_REGEX);
    private static final String RESULT_REGEX = "^result (?:(?:\\d-\\d)|\\*) \\{.*\\}$";
    public static final Pattern RESULT_PATTERN = Pattern.compile(RESULT_REGEX);
    private static final String QUIT_REGEX = "^quit$";
    public static final Pattern QUIT_PATTERN = Pattern.compile(QUIT_REGEX);
    private static final String MOVE_REGEX = "^((?:\\w\\d\\w\\d)(?:\\w)?)$";
    public static final Pattern MOVE_PATTERN = Pattern.compile(MOVE_REGEX);

    private CommandPatterns() {
    }
}
