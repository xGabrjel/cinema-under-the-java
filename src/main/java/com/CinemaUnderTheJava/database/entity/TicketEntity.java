package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.enums.TicketCurrency;
import com.cinemaUnderTheJava.database.enums.TicketStatus;
import com.cinemaUnderTheJava.database.enums.TicketType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@With
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class TicketEntity extends AbstractUUIDEntity {

    private String name;
    private String filmTitle;
    private LocalDate projectionDate;
    private LocalTime projectionTime;
    private BigDecimal ticketPrice;
    private int rowNumber;
    private int seatInRow;
    private int roomNumber;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
    @Enumerated(EnumType.STRING)
    private TicketCurrency ticketCurrency;
}
