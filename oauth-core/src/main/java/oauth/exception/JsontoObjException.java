package oauth.exception;

public class JsontoObjException extends Exception  {

    public JsontoObjException() {
        super();
    }

    public JsontoObjException(String message) {
        super(message);
    }

    public JsontoObjException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsontoObjException(Throwable cause) {
        super(cause);
    }

    protected JsontoObjException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
