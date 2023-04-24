package com.event.backevents.api.exceptionhandler;

public class ResourceRepeatedException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ResourceRepeatedException(String message) {
        super(message);
    }
}