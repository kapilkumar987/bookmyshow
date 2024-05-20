package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.exceptions.SeatsUnavailableException;
import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.*;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TicketServiceImplementation implements TicketService
{

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;
    private ShowSeatRepository showSeatRepository;
    private SeatTypeShowRepository seatTypeShowRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImplementation(UserRepository userRepository, ShowRepository showRepository, SeatRepository seatRepository , ShowSeatRepository showSeatRepository, SeatTypeShowRepository seatTypeShowRepository, TicketRepository ticketRepository)
    {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatTypeShowRepository = seatTypeShowRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public Ticket bookTicket(int userId, int showId, List<Integer> seatIds) throws Exception
    {
         /*
        1. Check if the user is valid
        2. showId in showSeatIds and given showId should match
        3. Start Transaction (SERIALIZABLE)
        4. Select * from show_seats where id in (showSeatIds) and seat_status = 'Available' and showId = {{showId}} for update
        5. If all seats are not available | throw error and rollback the transaction
        6. Update show_seats set seat_status = 'BLOCKED' where id in (showSeatIds)
        7. Generate Ticket object | Store Ticket object in DB and return
         */

        /*
        validate if the user is a valid user
        validate if the show is present in the db
        validate if the show can be booked now? startTime + 10 minutes is allowed
        validate if the seats are valid
        now fetch available show seats from db
        if avl seat count == # of seats in the request
            update the seat status to blocked
        else
            throw exception saying few or all seats are unavailable
         */

        Optional<User> userOptional = this.userRepository.findById(userId);
        if(userOptional.isEmpty())
        {
            throw new InvalidRequestStateException("User is invalid");
        }

        User user = userOptional.get();

        Show show = this.showRepository.findById(showId).orElseThrow(() -> new InvalidRequestStateException("Show is invalid"));

        long currentDateTime = new Date().getTime();
        if(show.getStartTime().getTime() + (10 * 60L) < currentDateTime)
        {
            throw new InvalidRequestStateException("This show cannot be booked");
        }

        List<Seat> seats = seatRepository.findAllByIdIn(seatIds);
        if(seats.size() != seatIds.size())
        {
            throw new InvalidRequestStateException("Seats are invalid");
        }

        // TODO:  Check if the seats belong to the screen on which the show is going to be run
        blockSeatForUser(user, show, seatIds);

        List<SeatTypeShow> showSeatTypes = seatTypeShowRepository.findAllByShowId(showId);
        Map<SeatType, Double> pricingMap = new HashMap<>();
        for(SeatTypeShow seatType: showSeatTypes){
            pricingMap.put(seatType.getSeatType(), seatType.getPrice());
        }
        double totalAmount = 0;
        for(Seat seat: seats)
        {
            totalAmount += pricingMap.get(seat.getSeatType());
        }

        // TODO: Apply strategy pattern to compute convenience fee and add it to the total amount

        Ticket ticket = new Ticket();
        ticket.setPaymentStatus(PaymentStatus.PENDING);
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeats(seats);
        ticket.setPrice(totalAmount);

        return this.ticketRepository.save(ticket);

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void blockSeatForUser(User user, Show show, List<Integer> seatIds) throws SeatsUnavailableException
    {
        List<ShowSeat> showSeats = this.showSeatRepository.findAllByShowIdAndSeatIdsInAndSeatStatus_Available(show.getId(), seatIds);

        if(showSeats.size() != seatIds.size())
        {
            throw new SeatsUnavailableException("Some or all seats are unavailable");
        }

        showSeats.stream().forEach(ss -> {
            ss.setSeatStatus(SeatStatus.BLOCKED);
            ss.setBookedBy(user);
        });

        // if the objects that we are trying to store have ids (it means that these
        // objects are already present in the DB), hence saveAll will fire an update
        // else saveAll leads to an insert query

        this.showSeatRepository.saveAll(showSeats);
    }

}
