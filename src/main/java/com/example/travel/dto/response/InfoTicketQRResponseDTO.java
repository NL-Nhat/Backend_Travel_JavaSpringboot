package com.example.travel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoTicketQRResponseDTO {

    private Integer idBooking;
    private String idTicket;
    private String tourName;
}
