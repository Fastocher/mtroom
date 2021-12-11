package NC.mtroom.room.api.exeptions;

import NC.mtroom.user.api.exeptions.UserAlreadyExist;
import NC.mtroom.user.api.exeptions.UserNotFound;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoomNotFound.class)
    protected ResponseEntity<MyException> handleRoomNotFound(RoomNotFound e){
        return new ResponseEntity<>(new MyException(e.getMessage(), HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    protected ResponseEntity<MyException> handleUserAlreadyExist(UserAlreadyExist e){
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFound.class)
    protected ResponseEntity<MyException> handleUserNotFound(UserNotFound e){
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentials.class)
    protected ResponseEntity<MyException> handleInvalidCredentials(InvalidCredentials e){
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectBookingTime.class)
    protected ResponseEntity<MyException> handleIncorrectBookingTime(IncorrectBookingTime e){
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomAlreadyBooked.class)
    protected ResponseEntity<MyException> handleRoomAlreadyBooked(RoomAlreadyBooked e){
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.CONFLICT,HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PastBooking.class)
    protected ResponseEntity<MyException> handlePastBooking(PastBooking e){
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HistoryNotFound.class)
    protected ResponseEntity<MyException> handleHistoryNotFound(HistoryNotFound e){
        return new ResponseEntity<>(new MyException(e.getMessage(), HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<MyException> handleNumberFormatException (NumberFormatException e) {
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<MyException> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<MyException> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new MyException(e.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }


    //Пустое поле вместо ввода (цифры)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return new ResponseEntity<Object>(new MyException(ex.getCause().getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    //Ошибки по валидации
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                              HttpHeaders headers, HttpStatus status,
                                                              WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .findFirst()
            .orElse(ex.getMessage());
        return new ResponseEntity<Object>(new MyException(errorMessage,HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
}

    //Ошибка к обращению к несуществующим эндпоинтам
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders httpHeaders,
            HttpStatus httpStatus,
            WebRequest webRequest){
        return new ResponseEntity<Object>(new MyException("Path doesn't exist",HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.NOT_FOUND);
    }


    @Data
    @AllArgsConstructor
    private static class MyException{
        private String body;
        private HttpStatus statusCode;
        private int statusCodeValue;
    }
}
