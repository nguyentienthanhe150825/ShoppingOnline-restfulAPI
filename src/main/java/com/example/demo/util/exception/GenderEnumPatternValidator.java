package com.example.demo.util.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderEnumPatternValidator implements ConstraintValidator<GenderEnumPattern, Enum<?>>{

    private Pattern pattern;

    @Override
    public void initialize(GenderEnumPattern enumPattern) {
        try {
            pattern = Pattern.compile(enumPattern.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Matcher m = pattern.matcher(value.name());
        return m.matches();
    }
    
}
