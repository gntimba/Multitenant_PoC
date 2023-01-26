package com.eskom.back.Exception;

public class MemberNotFound extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MemberNotFound() {
        super();
    }

    public MemberNotFound(String message) {
        super(message);
    }

}
