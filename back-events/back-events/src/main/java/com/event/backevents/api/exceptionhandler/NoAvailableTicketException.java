package com.event.backevents.api.exceptionhandler;

public class NoAvailableTicketException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public NoAvailableTicketException(String message) {
        super(message);
    }
}
