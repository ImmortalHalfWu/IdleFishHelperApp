package browsers.exceptions;

public class ManLoadHtmlException extends Exception{

    public static ManLoadHtmlExceptionCode CODE_YHQ_OVER_TIME = new ManLoadHtmlExceptionCode(0);
    public static ManLoadHtmlExceptionCode CODE_CRASH = new ManLoadHtmlExceptionCode(1);
    public static ManLoadHtmlExceptionCode CODE_NOT_FOUND = new ManLoadHtmlExceptionCode(2);
    public static ManLoadHtmlExceptionCode CODE_SHIELD = new ManLoadHtmlExceptionCode(3);

    private final ManLoadHtmlExceptionCode code;
    private final String message;

    public static ManLoadHtmlException newInstance(ManLoadHtmlExceptionCode code) {
        return new ManLoadHtmlException(code, "");
    }

    private ManLoadHtmlException(ManLoadHtmlExceptionCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public ManLoadHtmlExceptionCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private final static class ManLoadHtmlExceptionCode {

        private final int code;
        ManLoadHtmlExceptionCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
