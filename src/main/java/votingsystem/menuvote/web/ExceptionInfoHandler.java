package votingsystem.menuvote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import votingsystem.menuvote.util.ValidationUtil;
import votingsystem.menuvote.util.exception.ApplicationException;
import votingsystem.menuvote.util.exception.ErrorInfo;
import votingsystem.menuvote.util.exception.ErrorType;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static votingsystem.menuvote.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler extends ResponseEntityExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Collections.unmodifiableMap(
            new HashMap<String, String>() {
                {
                    put("users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL);
                }
            });

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorInfo> applicationError(HttpServletRequest req, ApplicationException appEx) {
        ErrorInfo errorInfo = logAndGetErrorInfo(req, appEx, false, appEx.getType(), messageUtil.getMessage(appEx));
        return new ResponseEntity<>(errorInfo, appEx.getHttpStatus());
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String, String>> entry = CONSTRAINS_I18N_MAP.entrySet().stream()
                    .filter(it -> lowerCaseMsg.contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()) {
//                return logAndGetErrorInfo(req, e, false, DATA_ERROR, messageUtil.getMessage(entry.get().getValue()));
                return new ResponseEntity<>(logAndGetErrorInfo(req, e, false, DATA_ERROR, messageUtil.getMessage(entry.get().getValue())), HttpStatus.CONFLICT);
            }
        }
//        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
//        ErrorInfo errorInfo = logAndGetErrorInfo(req, e, false, DATA_ERROR, messageUtil.getMessage(entry.get().getValue()));
        return new ResponseEntity<>(logAndGetErrorInfo(req, e, true, DATA_ERROR), HttpStatus.CONFLICT);
    }

//    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
//    @ExceptionHandler({BindException.class})
//    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
//        BindingResult result = e instanceof BindException ?
//                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();
//
//        String[] details = result.getFieldErrors().stream()
//                .map(fe -> messageUtil.getMessage(fe))
//                .toArray(String[]::new);
//
//        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
//    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType,
                messageUtil.getMessage(errorType.getErrorCode()),
                details.length != 0 ? details : new String[]{rootCause.toString()});
    }
}