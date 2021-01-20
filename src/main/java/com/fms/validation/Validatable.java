package com.fms.validation;


public interface Validatable<T> {

    void validate(T argument);
}
