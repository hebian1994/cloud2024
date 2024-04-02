package org.example.cloud.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.cloud.resp.ResultData;
import org.example.cloud.resp.ReturnCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e) {
        log.error("Global error {}", e.getMessage());
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }
}
