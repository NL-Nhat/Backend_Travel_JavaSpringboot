package com.example.travel.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoTicketQR {

    private Integer idTicket;
    private String tourName;
    private LocalDate startDate;
    private LocalTime startTime;
}
