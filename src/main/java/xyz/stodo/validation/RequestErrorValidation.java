package xyz.stodo.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import xyz.stodo.payload.MessageResponse;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestErrorValidation {

    public ResponseEntity<Object> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            if (!CollectionUtils.isEmpty(result.getAllErrors())) {
                for (ObjectError error : result.getAllErrors()) {
                    errorMap.put(error.getCode(), error.getDefaultMessage());
                }
            }

            StringBuilder errors = new StringBuilder();

            errorMap.values().forEach(s -> {
                errors.append(s);
                errors.append(" ");
            });

            MessageResponse messageResponse = new MessageResponse(errors.toString());

            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
