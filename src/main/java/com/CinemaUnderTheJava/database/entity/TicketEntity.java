package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.util.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    private String filmTitle;
    private LocalDateTime screeningDate;
    private Long screeningId;
    private String cinemaRoomId;
    private int rowNumber;
    private int seatNumber;
    private Long userId;
}
