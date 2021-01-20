package com.fms.validation;

import com.fms.exception.ApiException;
import com.fms.validation.impl.FileValidator;
import com.fms.validation.impl.LineValidator;
import com.fms.validation.impl.ThingValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory that keeps instances of the validators.
 *
 * @author  Fatih Soylemez
 * @version 1.0
 * @since   2018-04-18
 */
public class ValidationFactory {

    private static Map<String,Validatable> context = new HashMap<>();

    private ValidationFactory(){}

    static {
        context.put("fileValidator",new FileValidator());
        context.put("lineValidator",new LineValidator());
        context.put("thingValidator",new ThingValidator());
    }

    public static Validatable getValidator(String identifier){
        try {
            return context.get(identifier);
        } catch (NullPointerException ex) {
            throw new ApiException("Validator with name: " + identifier + ", doesn't exist");
        }
    }
    }

