package com.phucitdev.pickleball_backend.modules.booking.controller;


import com.phucitdev.pickleball_backend.modules.booking.dto.BookingHistoryResponse;
import com.phucitdev.pickleball_backend.modules.booking.dto.CreateBookingRequest;
import com.phucitdev.pickleball_backend.modules.booking.dto.CreateBookingResponse;
import com.phucitdev.pickleball_backend.modules.booking.dto.TimeSlotResponse;
import com.phucitdev.pickleball_backend.modules.booking.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class BookingAPI {
    private final BookingService bookingService;
    public BookingAPI(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping("/api/booking")
    public ResponseEntity<CreateBookingResponse> bookingCourt(@Valid @RequestBody CreateBookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
    }
//    @GetMapping("/api/my-bookings")
//    public ResponseEntity<Page<BookingHistoryResponse>> getMyBookings(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//
//        Pageable pageable = PageRequest.of(
//                page,
//                size,
//                Sort.by("createdAt").descending()
//        );
//
//        return ResponseEntity.ok(
//                bookingService.getMyBookings(pageable)
//        );
//    }
    @GetMapping("/api/available-slots")
    public ResponseEntity<Page<TimeSlotResponse>> getAvailableSlots(
            @RequestParam UUID courtId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(bookingService.getAvailableSlots(courtId,date, pageable));

    }


}
