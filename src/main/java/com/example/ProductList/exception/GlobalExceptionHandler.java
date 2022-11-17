package com.example.ProductList.exception;

import com.example.ProductList.api.response.ApiResponse;
import com.example.ProductList.api.response.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<ResultResponse>> handleMissingServletRequestParameterException(Exception ex) {
        logger.warning(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Missing required parameters",
                ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongParameterException.class)
    public ResponseEntity<ApiResponse<ResultResponse>> handleWrongParameterException(Exception ex) {
        logger.warning(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Bad parameter value...",
                ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInputParameterException.class)
    public ResponseEntity<ApiResponse<ResultResponse>> handleInputParameterException(Exception ex) {
        logger.warning(ex.getLocalizedMessage());
        ApiResponse<ResultResponse> response = new ApiResponse<>();
        response.setDebugMessage("successful request");
        response.setStatus(HttpStatus.OK);
        response.setData(new ResultResponse(ex.getLocalizedMessage()));
        return ResponseEntity.ok(response);
    }
}
