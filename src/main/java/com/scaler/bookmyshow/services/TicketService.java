package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Ticket;

import java.util.List;

public interface TicketService
{
    Ticket bookTicket(int userId, int showId, List<Integer> seatIds) throws Exception;
}
