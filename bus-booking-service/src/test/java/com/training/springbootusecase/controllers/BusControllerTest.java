package com.training.springbootusecase.controllers;

import com.training.springbootusecase.config.JwtTokenUtil;
import com.training.springbootusecase.dto.BookBusDto;
import com.training.springbootusecase.dto.BusSearchDto;
import com.training.springbootusecase.dto.HistoryDto;
import com.training.springbootusecase.dto.SearchResponseDto;
import com.training.springbootusecase.dto.TicketDto;
import com.training.springbootusecase.entity.BusType;
import com.training.springbootusecase.entity.PassengerDetails;
import com.training.springbootusecase.service.BusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BusControllerTest {
    @InjectMocks
    private BusController busController;
    @Mock
    private BusService busService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void testSearchForBus() {
        when(busService.searchForBus(buildBusSearchDto())).thenReturn(buildResponse());
        List<SearchResponseDto> response = busController.searchForBus(buildBusSearchDto()).getBody();
        assertNotNull(response);
        assertEquals(BusType.SEATER,response.get(0).getBusType());
    }
    @Test
    public void testBookSeats() {
        List<TicketDto> ticketDtos = new ArrayList<>();
        ticketDtos.add(TicketDto.builder().passengerDetails(Collections.singletonList(PassengerDetails.builder()
                .name("name").build())).build());
        when(busService.bookSeats(buildBookBusDto(),"email")).thenReturn(ticketDtos);
        when(jwtTokenUtil.getUsernameFromToken("headers")).thenReturn("email");
        List<TicketDto> response = busController.bookSeats(buildBookBusDto(),"headers").getBody();
        assertNotNull(response);
        assertEquals(ticketDtos.get(0).getPassengerDetails(),response.get(0).getPassengerDetails());
    }
    @Test
    public void testFindTickets() {
        List<TicketDto> ticketDtos = new ArrayList<>();
        ticketDtos.add(TicketDto.builder().passengerDetails(Collections.singletonList(PassengerDetails.builder()
                .name("name").build())).build());
        when(busService.findTickets(LocalDate.parse("2020-07-07"), LocalDate.parse("2020-07-07"),"email"))
                .thenReturn(ticketDtos);
        when(jwtTokenUtil.getUsernameFromToken("headers")).thenReturn("email");
        List<TicketDto> response = busController.findTickets("2020-07-07", "2020-07-07","headers").getBody();
        assertNotNull(response);
        assertEquals(ticketDtos.get(0).getPassengerDetails(),response.get(0).getPassengerDetails());
    }
    @Test
    public void testFindHistory() {
        List<HistoryDto> historyDtos = new ArrayList<>();
        historyDtos.add(HistoryDto.builder().userDetails("passenger").build());
        when(busService.findTravelHistory(LocalDate.parse("2020-07-07"), LocalDate.parse("2020-07-07"),"email"))
                .thenReturn(historyDtos);
        when(jwtTokenUtil.getUsernameFromToken("headers")).thenReturn("email");
        List<HistoryDto> response = busController.findTravelHistory("2020-07-07", "2020-07-07","headers").getBody();
        assertNotNull(response);
        assertEquals(historyDtos.get(0).getUserDetails(),response.get(0).getUserDetails());
    }

    private BusSearchDto buildBusSearchDto() {
        return BusSearchDto.builder()
                .source("Madurai")
                .destination("Salem").build();
    }
    private BookBusDto buildBookBusDto() {
        return BookBusDto.builder()
                .build();
    }

    private List<SearchResponseDto> buildResponse() {
        List<SearchResponseDto> response = new ArrayList<>();
        response.add(SearchResponseDto.builder().busType(BusType.SEATER).build());
        return response;
    }
}