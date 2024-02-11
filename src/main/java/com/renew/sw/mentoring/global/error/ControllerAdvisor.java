package com.renew.sw.mentoring.global.error;

import com.renew.sw.mentoring.global.debug.model.dto.ErrorResponseDto;
import com.renew.sw.mentoring.global.debug.service.ErrorLogService;
import com.renew.sw.mentoring.global.error.exception.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestControllerAdvice
public class ControllerAdvisor {

    private final MessageSource messageSource;
    private final ErrorLogService errorLogService;

    @Value("${app.enable-test-controller}")
    private boolean isEnabledTest;

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> localizedException(LocalizedMessageException e, Locale locale) {
        ErrorResponseDto dto = new ErrorResponseDto(messageSource, locale, e);
        log.error("A problem has occurred in controller advice: [id={}]", dto.getTrackingId(), e);
        if (containsEnum(e.getStatus())) {
            return filter(e, ResponseEntity.status(e.getStatusCode()).body(dto));
        }
        return filter(e, ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(dto));
    }

    private boolean containsEnum(String constantName) {
        for (CustomHttpStatus status : CustomHttpStatus.VALUES) {
            if (status.name().equals(constantName)) {
                return true;
            }
        }
        return false;
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(HttpMessageNotReadableException e, Locale locale) {
        return localizedException(new BadRequestException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(BindException e, Locale locale) {
        return localizedException(new BindingFailedException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(MethodArgumentTypeMismatchException e, Locale locale) {
        return localizedException(new BadRequestException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badParameter(MissingServletRequestParameterException e, Locale locale) {
        return localizedException(new MissingRequiredParameterException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> badMethod(HttpRequestMethodNotSupportedException e, Locale locale) {
        return localizedException(new NotSupportedMethodException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> accessDenied(AccessDeniedException e, Locale locale) {
        return localizedException(new NotGrantedException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> dataDuplicateException(DataIntegrityViolationException e, Locale locale) {
        return localizedException(new DuplicateDataException(e), locale);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> unexpectedException(Exception e, Locale locale) {
        ErrorResponseDto dto = new ErrorResponseDto(messageSource, locale, LocalizedMessageException.of(e));
        log.error("Unexpected exception has occurred in controller advice: [id={}]", dto.getTrackingId(), e);
        return filter(e, ResponseEntity.internalServerError().body(dto));
    }

    private ResponseEntity<ErrorResponseDto> filter(Throwable t, ResponseEntity<ErrorResponseDto> entity) {
        ErrorResponseDto dto = entity.getBody();
        if (isEnabledTest && dto != null) {
            errorLogService.logError(dto.getTrackingId(), t, dto);
        }
        return entity;
    }
}
