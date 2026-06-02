package com.example.touring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private Long id;
    private Customer customer;
    private Date departureDate;
    private int noAdults;
    private int noChildren;
    private Tour tour;
}
