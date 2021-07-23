package com.training.springbootusecase.service;

import com.training.springbootusecase.client.FundTransferInterface;
import com.training.springbootusecase.dto.BookBusDto;
import com.training.springbootusecase.dto.BusSearchDto;
import com.training.springbootusecase.dto.HistoryDto;
import com.training.springbootusecase.dto.SearchResponseDto;
import com.training.springbootusecase.dto.TicketDto;
import com.training.springbootusecase.entity.BusDetails;
import com.training.springbootusecase.entity.JourneyStatus;
import com.training.springbootusecase.entity.PaymentStatus;
import com.training.springbootusecase.entity.Ticket;
import com.training.springbootusecase.entity.TravelHistory;
import com.training.springbootusecase.entity.User;
import com.training.springbootusecase.exceptions.BusNotFoundException;
import com.training.springbootusecase.exceptions.NoArgumentException;
import com.training.springbootusecase.exceptions.TicketNotFoundException;
import com.training.springbootusecase.exceptions.TransactionFailedException;
import com.training.springbootusecase.repository.BusDetailsRepository;
import com.training.springbootusecase.repository.TicketRepository;
import com.training.springbootusecase.repository.TravelHistoryRepository;
import com.training.springbootusecase.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusService {
    @Autowired
    private BusDetailsRepository busDetailsRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TravelHistoryRepository travelHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FundTransferInterface fundTransferInterface;
    private static final String MESSAGE = "Please give required information";

    public List<SearchResponseDto> searchForBus(BusSearchDto busSearchDto) {
        if (busSearchDto.getSource().isEmpty() || busSearchDto.getDestination().isEmpty())
            throw new NoArgumentException("Source or Destination is empty");
        List<BusDetails> busDetails = busDetailsRepository.findAll().stream()
                .filter(busDetails1 -> busDetails1.getSource().equals(busSearchDto.getSource()))
                .filter(busDetails1 -> busDetails1.getDestination().equals(busSearchDto.getDestination()))
                .collect(Collectors.toList());
        if (busDetails.isEmpty())
            throw new BusNotFoundException("No buses found for this source and destination. Sorry for inconvenience");
        List<SearchResponseDto> searchResponseDtos = new ArrayList<>();
        for (BusDetails details : busDetails) {
            searchResponseDtos.add(SearchResponseDto.builder()
                    .busType(details.getBusType())
                    .customerReview(details.getCustomerReview())
                    .destination(details.getDestination())
                    .reachingTime(details.getReachingTime())
                    .source(details.getSource())
                    .startingTime(details.getStartingTime())
                    .ticketPrice(details.getTicketPrice())
                    .noOfAvailableSeats(details.getNoOfAvailableSeats()).build());
        }
        log.info("results");
        return searchResponseDtos;

    }

    public List<TicketDto> bookSeats(BookBusDto bookBusDto, String email) {
        if (bookBusDto.getBusName().isEmpty() || bookBusDto.getPassengerDetails().isEmpty()
                || bookBusDto.getNoOfSeats() == 0 || email.isEmpty())
            throw new NoArgumentException(MESSAGE);
        List<BusDetails> busDetails = busDetailsRepository.findAll().stream()
                .filter(busDetails1 -> busDetails1.getBusName().equals(bookBusDto.getBusName()))
                .collect(Collectors.toList());
        if (busDetails.isEmpty())
            throw new BusNotFoundException("No bus with this name found. Please give correct name");
        BusDetails details = busDetails.get(0);
        long fromAccountId = fundTransferInterface.findAccountByUserEmail(email).getBody();
        String message = fundTransferInterface.fundTransfer(fromAccountId, 4L,
                details.getTicketPrice() * bookBusDto.getNoOfSeats()).getBody();
        if (message.equals("transaction successful")) {
            details.setNoOfAvailableSeats(details.getNoOfAvailableSeats() - bookBusDto.getNoOfSeats());
            busDetailsRepository.save(details);
            User user = userRepository.findByEmail(email);
            Ticket ticket = Ticket.builder()
                    .passengerDetails(user)
                    .noOfTicketsUnderUser(bookBusDto.getNoOfSeats())
                    .startingPlace(details.getSource())
                    .destination(details.getDestination())
                    .fee(bookBusDto.getNoOfSeats() * details.getTicketPrice())
                    .travelDate(LocalDate.now()).build();
            ticketRepository.save(ticket);
            TravelHistory travelHistory = TravelHistory.builder()
                    .busDetails(details)
                    .startingPlace(details.getSource())
                    .destination(details.getDestination())
                    .fee(bookBusDto.getNoOfSeats() * details.getTicketPrice())
                    .travelDate(ticket.getTravelDate())
                    .noOfSeats(ticket.getNoOfTicketsUnderUser())
                    .journeyStatus(JourneyStatus.BOOKED)
                    .paymentStatus(PaymentStatus.PAYMENT_SUCCESS)
                    .user(user).build();
            travelHistoryRepository.save(travelHistory);
            List<TicketDto> ticketDtos = new ArrayList<>();
            for (int i = 0; i < bookBusDto.getNoOfSeats(); i++) {
                ticketDtos.add(TicketDto.builder()
                        .passengerDetails(bookBusDto.getPassengerDetails().get(i).getName())
                        .startingPlace(details.getSource())
                        .destination(details.getDestination())
                        .fee(details.getTicketPrice())
                        .travelDate(ticket.getTravelDate()).build());
            }
            return ticketDtos;
        } else {
            throw new TransactionFailedException("transaction failed");
        }
    }

    public List<TicketDto> findTickets(LocalDate fromDate, LocalDate toDate, String email) {
        if (fromDate == null || toDate == null || email.isEmpty())
            throw new NoArgumentException(MESSAGE);
        List<Ticket> tickets = ticketRepository.findByTravelDateBetween(fromDate, toDate).stream()
                .filter(ticket -> ticket.getPassengerDetails().getEmail().equals(email))
                .collect(Collectors.toList());
        if(tickets.isEmpty())
            throw new TicketNotFoundException("No tickets found between these dates");
        log.info("Got all tickets between " + fromDate + " and " + toDate);
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketDtos.add(TicketDto.builder()
                    .passengerDetails(ticket.getPassengerDetails().getFirstName())
                    .startingPlace(ticket.getStartingPlace())
                    .destination(ticket.getDestination())
                    .travelDate(ticket.getTravelDate())
                    .fee(ticket.getFee()).build());
        }
        return ticketDtos;
    }

    public List<HistoryDto> findTravelHistory(LocalDate fromDate, LocalDate toDate, String email) {
        if (fromDate == null || toDate == null || email.isEmpty())
            throw new NoArgumentException(MESSAGE);
        List<TravelHistory> travelHistories = travelHistoryRepository.findByTravelDateBetween(fromDate, toDate)
                .stream().filter(travelHistory -> travelHistory.getUser().getEmail().equals(email))
                .collect(Collectors.toList());
        if(travelHistories.isEmpty())
            throw new TicketNotFoundException("No history found between these dates");
        log.info("Got all travel history between " + fromDate + " and " + toDate);
        List<HistoryDto> historyDtos = new ArrayList<>();
        for (TravelHistory travelHistory : travelHistories) {
            historyDtos.add(HistoryDto.builder()
                    .startingPlace(travelHistory.getStartingPlace())
                    .destination(travelHistory.getDestination())
                    .fee(travelHistory.getFee())
                    .busName(travelHistory.getBusDetails().getBusName())
                    .journeyStatus(travelHistory.getJourneyStatus())
                    .noOfSeats(travelHistory.getNoOfSeats())
                    .passengerDetails(travelHistory.getUser().getFirstName())
                    .travelDate(travelHistory.getTravelDate())
                    .paymentStatus(travelHistory.getPaymentStatus()).build());
        }
        return historyDtos;
    }
}
