package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.util.TicketCurrency;
import com.cinemaUnderTheJava.database.util.TicketStatus;
import com.cinemaUnderTheJava.database.util.TicketType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String filmTitle;
    private LocalDate projectionDate;
    private LocalTime projectionTime;
    private BigDecimal ticketPrice;
    private int rowsNumber;
    private int seatsInRow;
    private int roomNumber;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
    @Enumerated(EnumType.STRING)
    private TicketCurrency ticketCurrency;
}
