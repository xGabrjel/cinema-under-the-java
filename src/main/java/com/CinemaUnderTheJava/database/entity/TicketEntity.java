package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.util.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
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
}
