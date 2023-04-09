package com.event.backevents.api.exceptionhandler;

public class ResourceAlrearyExist extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ResourceAlrearyExist(String message) {
        super(message);
    }
}
