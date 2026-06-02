package com.example.touring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
    private Long id;
    private String description;
    private String days;
    private String transportation;
    private String departureSchedule;
    private double price;
}
