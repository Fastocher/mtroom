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

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoomNotFound.class)
    protected ResponseEntity<MyException> handleRoomNotFound(){
        return new ResponseEntity<>(new MyException("Комната не найдена",HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomAlreadyBooked.class)
    protected ResponseEntity<MyException> handleRoomAlreadyBooked(){
        return new ResponseEntity<>(new MyException("Комната на это время забронирована",HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HistoryNotFound.class)
    protected ResponseEntity<MyException> handleHistoryNotFound(){
        return new ResponseEntity<>(new MyException("Бронирование с таким ID не найдено",HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<MyException> handleNumberFormatException (NumberFormatException e) {
        return new ResponseEntity<>(new MyException("Неверный формат введённых данных: " + e.getMessage(),HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<MyException> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new MyException("Неверный формат введённых данных: " + e.getMessage(),HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<MyException> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new MyException("Неверный формат введённых данных: " + e.getMessage(),HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }


    //Пустое поле вместо ввода (цифры)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        return new ResponseEntity<Object>(new MyException(ex.getCause().getMessage(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<Object>(new MyException(errorMessage,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
}

    //Ошибка к обращению к несуществующим эндпоинтам
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders httpHeaders,
            HttpStatus httpStatus,
            WebRequest webRequest){
        return new ResponseEntity<Object>(new MyException("Такого пути не существует",HttpStatus.BAD_REQUEST),HttpStatus.NOT_FOUND);
    }


    @Data
    @AllArgsConstructor
    private static class MyException{
        private String message;
        private HttpStatus status;
    }
}
