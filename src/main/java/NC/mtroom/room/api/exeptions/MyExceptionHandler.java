package NC.mtroom.room.api.exeptions;

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
    protected ResponseEntity<MyException> handleRoomNotFound(){
        return new ResponseEntity<>(new MyException("Room doesn't exist",HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomAlreadyBooked.class)
    protected ResponseEntity<MyException> handleRoomAlreadyBooked(){
        return new ResponseEntity<>(new MyException("Room booked ",HttpStatus.CONFLICT,HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HistoryNotFound.class)
    protected ResponseEntity<MyException> handleHistoryNotFound(){
        return new ResponseEntity<>(new MyException("No such booking ID",HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
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
        private String message;
        private HttpStatus status;
        private int status_code;
    }
}
