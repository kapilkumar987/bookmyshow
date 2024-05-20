package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.BookTicketRequestDto;
import com.scaler.bookmyshow.dtos.BookTicketResponseDto;
import com.scaler.bookmyshow.dtos.Response;
import com.scaler.bookmyshow.exceptions.InvalidBookTicketRequestException;
import com.scaler.bookmyshow.models.Ticket;
import com.scaler.bookmyshow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController
{
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService)
    {
        this.ticketService = ticketService;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto)
    {
        BookTicketResponseDto responseDto = new BookTicketResponseDto();

        try
        {
            validateBookTicketRequest(requestDto);
            Ticket ticket = ticketService.bookTicket(requestDto.getUserId(), requestDto.getShowId(), requestDto.getSeatIds());
            Response response = Response.getSuccessResponse();
            responseDto.setTicket(ticket);
            responseDto.setResponse(response);
        }
        catch (Exception ex)
        {
            Response response = Response.getFailureResponse(ex.getMessage());
            responseDto.setResponse(response);
        }

        return  responseDto;
    }

    private static void validateBookTicketRequest(BookTicketRequestDto requestDto) throws InvalidBookTicketRequestException
    {
        if(requestDto.getShowId() <= 0)
        {
            throw new InvalidBookTicketRequestException("Show Id cannot be negative or zero");
        }

        if(requestDto.getUserId() <= 0)
        {
            throw new InvalidBookTicketRequestException("User Id cannot be negative or zero");
        }

        if(requestDto.getSeatIds() == null || requestDto.getSeatIds().isEmpty())
        {
            throw new InvalidBookTicketRequestException("Seat Ids should be present");
        }
    }
}
