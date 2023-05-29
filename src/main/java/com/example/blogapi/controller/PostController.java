package com.example.blogapi.controller;

import com.example.blogapi.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public  Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result)  {
        log.info("params={}", params.toString());
        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fristFieldError = fieldErrors.get(0);
            String fieldName = fristFieldError.getField(); // title
            String errorMessage = fristFieldError.getDefaultMessage(); //에러메세지

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of();
    }
}
