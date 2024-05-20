package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response
{
    private ResponseStatus responseStatus;
    private String errorMessage;

    public Response(ResponseStatus responseStatus, String errorMessage)
    {
        this.responseStatus = responseStatus;
        this.errorMessage = errorMessage;
    }

    public static Response getSuccessResponse()
    {
        return new Response(ResponseStatus.SUCCESS, null);
    }

    public static Response getFailureResponse(String errorMessage)
    {
        return new Response(ResponseStatus.FAILURE, errorMessage);
    }
}
