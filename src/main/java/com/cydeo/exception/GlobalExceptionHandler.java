package com.cydeo.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {
    public String serviceException(Model model, UserNotFoundException exception) {

        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    public String genericException(Throwable ex, HandlerMethod handlerMethod, Model model) {
        String message = " Something went wrong! Contact with Sparkle development team!";
        Optional<DefaultExceptionMessageDto> defaultMessage = getMessageFromAnnotation(handlerMethod.getMethod());
        if (defaultMessage.isPresent()) {
            message = defaultMessage.get().getMessage();
        } else if (ex.getMessage() != null) {
            message = ex.getMessage();
        }
        model.addAttribute("message", message);
        return "error";

    }

    private Optional<DefaultExceptionMessageDto> getMessageFromAnnotation(Method method) {
        SparkleAccountingExceptionMessage defaultExceptionMessage = method
                .getAnnotation(SparkleAccountingExceptionMessage.class);
        if (defaultExceptionMessage != null) {
            DefaultExceptionMessageDto defaultExceptionMessageDto = DefaultExceptionMessageDto
                    .builder()
                    .message(defaultExceptionMessage.defaultMessage())
                    .build();
            return Optional.of(defaultExceptionMessageDto);
        }
        return Optional.empty();
    }

}
