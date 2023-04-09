package com.event.backevents.api.exceptionhandler;

public class TicketException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public TicketException(String message) {
        super(message);
    }
}