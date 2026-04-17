package com.example.travel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoTicketQRResponse {

    private Integer idBooking;
    private String idTicket;
    private String tourName;
}
