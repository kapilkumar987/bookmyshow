package com.scaler.bookmyshow.dtos;

import com.scaler.bookmyshow.models.Ticket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTicketResponseDto
{
    private Ticket ticket;
    private Response response;
}
