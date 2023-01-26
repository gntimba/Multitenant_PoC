package com.eskom.back.Exception;

public class NotUnique extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotUnique() {
        super();
    }

    public NotUnique(String message) {
        super(message);
    }

}
