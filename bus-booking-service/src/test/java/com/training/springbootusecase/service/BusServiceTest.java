package com.training.springbootusecase.service;

import com.training.springbootusecase.client.FundTransferInterface;
import com.training.springbootusecase.dto.BookBusDto;
import com.training.springbootusecase.dto.BusSearchDto;
import com.training.springbootusecase.dto.HistoryDto;
import com.training.springbootusecase.dto.PassengerDetails;
import com.training.springbootusecase.dto.SearchResponseDto;
import com.training.springbootusecase.dto.TicketDto;
import com.training.springbootusecase.entity.BusDetails;
import com.training.springbootusecase.entity.Ticket;
import com.training.springbootusecase.entity.TravelHistory;
import com.training.springbootusecase.entity.User;
import com.training.springbootusecase.exceptions.BusNotFoundException;
import com.training.springbootusecase.exceptions.NoArgumentException;
import com.training.springbootusecase.exceptions.TicketNotFoundException;
import com.training.springbootusecase.exceptions.TransactionFailedException;
import com.training.springbootusecase.repository.BusDetailsRepository;
import com.training.springbootusecase.repository.PassengerDetailsRepository;
import com.training.springbootusecase.repository.TicketRepository;
import com.training.springbootusecase.repository.TravelHistoryRepository;
import com.training.springbootusecase.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BusServiceTest {
    @InjectMocks
    private BusService service;
    @Mock
    private BusDetailsRepository busDetailsRepository;
    @Mock
    private FundTransferInterface fundTransferInterface;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TravelHistoryRepository travelHistoryRepository;
    @Mock
    private PassengerDetailsRepository passengerDetailsRepository;

    @Test
    public void testSearchForBus() {
        BusSearchDto busSearchDto = buildBusSearchDto();
        List<BusDetails> busDetails = buildBusDetails();
        when(busDetailsRepository.findAll())
                .thenReturn(busDetails);
        List<SearchResponseDto> response = service.searchForBus(busSearchDto);
        assertNotNull(response);
        assertEquals("Madurai",response.get(0).getSource());
    }
    @Test
    public void testWhenSearchForBusHasEmptyArguments() {
        BusSearchDto busSearchDto = BusSearchDto.builder().destination("").source("").build();
        assertThrows(NoArgumentException.class,() -> service.searchForBus(busSearchDto));
    }
    @Test
    public void testWhenSearchForBusHasNoResults() {
        BusSearchDto busSearchDto = buildBusSearchDto();
        List<BusDetails> busDetails = new ArrayList<>();
        when(busDetailsRepository.findAll())
                .thenReturn(busDetails);
        assertThrows(BusNotFoundException.class,() -> service.searchForBus(busSearchDto));
    }
    @Test
    public void testBookSeats() {
        when(busDetailsRepository.findAll())
                .thenReturn(buildBusDetails());
        when(fundTransferInterface.findAccountByUserEmail("email")).thenReturn(new ResponseEntity<>(1L, OK));
        when(fundTransferInterface.fundTransfer(1L, 6L,700))
                .thenReturn(new ResponseEntity<>("transaction successful", OK));
        when(userRepository.findByEmail("email")).thenReturn(buildUser());
        List<TicketDto> response = service.bookSeats(buildBookBusDto(), "email");
        assertNotNull(response);
        assertEquals("Salem",response.get(0).getDestination());
    }
    @Test
    public void testWhileBookSeatsHasFailedTransaction(){
        when(busDetailsRepository.findAll())
                .thenReturn(buildBusDetails());
        when(fundTransferInterface.findAccountByUserEmail("email")).thenReturn(new ResponseEntity<>(1L, OK));
        when(fundTransferInterface.fundTransfer(1L, 6L, 700))
                .thenReturn(new ResponseEntity<>("transaction failed", OK));
        assertThrows(TransactionFailedException.class,() ->service.bookSeats(buildBookBusDto(), "email"));
    }
    @Test
    public void testWhileBookSeatsHasEmptyArguments(){
        BookBusDto bookBusDto = BookBusDto.builder().busName("").passengerDetails(new ArrayList<>()).noOfSeats(0).build();
        assertThrows(NoArgumentException.class,() -> service.bookSeats(bookBusDto,""));
    }
    @Test
    public void testWhileBookSeatsHasNoResults(){
        List<BusDetails> busDetails = new ArrayList<>();
        when(busDetailsRepository.findAll())
                .thenReturn(busDetails);
        assertThrows(BusNotFoundException.class,() ->service.bookSeats(buildBookBusDto(), "email"));
    }

    @Test
    public void testFindTickets() {

        when(ticketRepository.findByTravelDateBetween(LocalDate.now(), LocalDate.now()))
                .thenReturn(buildTickets());
        List<TicketDto> response = service.findTickets(LocalDate.now(), LocalDate.now(), "email");
        assertNotNull(response);
        assertEquals("Bangalore",response.get(0).getStartingPlace());
    }
    @Test
    public void testWhileFindTicketsHasNoResults() {
        List<Ticket> tickets = new ArrayList<>();
        when(ticketRepository.findByTravelDateBetween(LocalDate.now(), LocalDate.now()))
                .thenReturn(tickets);
        assertThrows(TicketNotFoundException.class,()->service.findTickets(LocalDate.now(), LocalDate.now(), "email"));
    }
    @Test
    public void testFindHistory() {

        when(travelHistoryRepository.findByTravelDateBetween(LocalDate.now(), LocalDate.now()))
                .thenReturn(buildHistories());
        List<HistoryDto> response = service.findTravelHistory(LocalDate.now(), LocalDate.now(), "email");
        assertNotNull(response);
        assertEquals("Bangalore",response.get(0).getStartingPlace());
    }
    @Test
    public void testWhileFindHistoryHasNoResults() {
        List<TravelHistory> travelHistories = new ArrayList<>();
        when(travelHistoryRepository.findByTravelDateBetween(LocalDate.now(), LocalDate.now()))
                .thenReturn(travelHistories);
        assertThrows(TicketNotFoundException.class,()-> service.findTravelHistory(LocalDate.now(), LocalDate.now(), "email"));
    }

    private List<TravelHistory> buildHistories() {
        List<TravelHistory> travelHistories = new ArrayList<>();
        travelHistories.add(TravelHistory.builder().user(User.builder().email("email").build())
                .busDetails(buildBusDetails().get(0))
                .startingPlace("Bangalore").build());
        return travelHistories;
    }

    private List<Ticket> buildTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(Ticket.builder()
                .startingPlace("Bangalore").userDetails(User.builder().email("email").build()).build());
        return tickets;
    }

    private User buildUser() {
        return User.builder().build();
    }

    private List<BusDetails> buildBusDetails() {
        List<BusDetails> busDetails = new ArrayList<>();
        BusDetails details = new BusDetails();
        details.setSource("Madurai");
        details.setDestination("Salem");
        details.setBusName("busName");
        details.setTicketPrice(700);
        details.setNoOfAvailableSeats(5);
        busDetails.add(details);
        return busDetails;
    }

    private BusSearchDto buildBusSearchDto() {
        return BusSearchDto.builder()
                .source("Madurai")
                .destination("Salem").build();
    }

    private BookBusDto buildBookBusDto() {
        List<PassengerDetails> passengerDetails = new ArrayList<>();
        passengerDetails.add(PassengerDetails.builder()
                .age(20).name("name").build());
        return BookBusDto.builder()
                .busName("busName")
                .noOfSeats(1)
                .passengerDetails(passengerDetails).build();
    }

}