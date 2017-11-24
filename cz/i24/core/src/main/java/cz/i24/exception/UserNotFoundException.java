package cz.i24.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User not found")
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
}
