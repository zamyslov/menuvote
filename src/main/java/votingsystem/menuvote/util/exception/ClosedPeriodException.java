package votingsystem.menuvote.util.exception;

import org.springframework.http.HttpStatus;

public class ClosedPeriodException extends ApplicationException {
    private static final String CLOSED_PERIOD_EXCEPTION = "exception.common.notFound";

    public ClosedPeriodException(String message) {
        super(ErrorType.DATA_NOT_FOUND, CLOSED_PERIOD_EXCEPTION, HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
