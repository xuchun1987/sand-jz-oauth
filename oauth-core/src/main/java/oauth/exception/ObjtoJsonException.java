package oauth.exception;

public class ObjtoJsonException extends Exception  {

    public ObjtoJsonException() {
        super();
    }

    public ObjtoJsonException(String message) {
        super(message);
    }

    public ObjtoJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjtoJsonException(Throwable cause) {
        super(cause);
    }

    protected ObjtoJsonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
