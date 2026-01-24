package dolpi.Report_Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex){
        return new ResponseEntity<>("image not upload",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourcesNotFound.class)
    public ResponseEntity<?> handle(ResourcesNotFound ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }
}
