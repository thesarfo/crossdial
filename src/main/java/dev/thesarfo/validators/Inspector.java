package dev.thesarfo.validators;


public interface Inspector<T> {
    boolean isValid(T value);
}
