package com.training.springbootusecase.controllers;

import com.training.springbootusecase.config.JwtTokenUtil;
import com.training.springbootusecase.dto.BookBusDto;
import com.training.springbootusecase.dto.BusSearchDto;
import com.training.springbootusecase.dto.HistoryDto;
import com.training.springbootusecase.dto.SearchResponseDto;
import com.training.springbootusecase.dto.TicketDto;
import com.training.springbootusecase.service.BusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.training.springbootusecase.util.Constants.TOKEN_PREFIX;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/bus-booking-service/bus")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "BusBookingService")
public class BusController {
    @Autowired
    private final BusService busService;
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "This method is used to search for buses")
    @PostMapping("/{busSearchDto}")
    public ResponseEntity<List<SearchResponseDto>> searchForBus(@RequestBody BusSearchDto busSearchDto){
        return new ResponseEntity<>( busService.searchForBus(busSearchDto),OK);
    }

    @ApiOperation(value = "This method is used to book seats")
    @PostMapping("/{bookBusDto}")
    public ResponseEntity<List<TicketDto>> bookSeats(@RequestBody BookBusDto bookBusDto,
                                                     @RequestHeader("Authorization") String headers){
        String email = jwtTokenUtil.getUsernameFromToken(headers.replace(TOKEN_PREFIX,""));
        return new ResponseEntity<>( busService.bookSeats(bookBusDto, email),OK);
    }

    @ApiOperation(value = "This method is used to view tickets")
    @GetMapping("/ticket/{fromDate}/{toDate}")
    public ResponseEntity<List<TicketDto>> findTickets(@PathVariable("fromDate") String fromDate,
                                                       @PathVariable("toDate") String toDate,
                                                       @RequestHeader("Authorization") String headers){
        String email = jwtTokenUtil.getUsernameFromToken(headers.replace(TOKEN_PREFIX,""));
        return new ResponseEntity<>(busService.findTickets(LocalDate.parse(fromDate),
                LocalDate.parse(toDate),email), HttpStatus.OK);
    }

    @ApiOperation(value = "This method is used to view history")
    @GetMapping("/{fromDate}/{toDate}")
    public ResponseEntity<List<HistoryDto>> findTravelHistory(@PathVariable("fromDate") String fromDate,
                                                        @PathVariable("toDate") String toDate,
                                                        @RequestHeader("Authorization") String headers){
        String email = jwtTokenUtil.getUsernameFromToken(headers.replace(TOKEN_PREFIX,""));
        return new ResponseEntity<>(busService.findTravelHistory(LocalDate.parse(fromDate),
                LocalDate.parse(toDate),email), HttpStatus.OK);
    }
}
