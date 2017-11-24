package cz.i24.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Password is Invalid")
public class InvalidPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;
}
