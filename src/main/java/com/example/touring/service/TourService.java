package com.example.touring.service;

import com.example.touring.model.Booking;
import com.example.touring.model.Customer;
import com.example.touring.model.Tour;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourService {
    private static final List<Tour> tours = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Booking> bookings = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final String JSON_FILE_PATH = "src/main/resources/static/data/bookings.json";


    //Mockup Data
    static {
        tours.add(new Tour(1L,
                "PHÚ QUỐC (Khuyến mãi mùa hè)",
                "3 ngày 2 đêm",
                "Máy bay",
                "Hằng ngày",
                1595000));

        tours.add(new Tour(2L,
                "NHA TRANG",
                "2 ngày 2 đêm",
                "Tàu hỏa",
                "Tối thứ 6 và CN",
                1540000));

        tours.add(new Tour(3L,
                "CÔN ĐẢO",
                "3 ngày 2 đêm",
                "Máy bay",
                "Hằng ngày",
                1345000));

        tours.add(new Tour(4L,
                "PHAN THIẾT - MŨI NÉ",
                "2 ngày 1 đêm",
                "Xe ô tô",
                "Thứ 7 mỗi tuần",
                1250000));

        tours.add(new Tour(5L,
                "ĐÀ LẠT - ĐỒI MỘNG MƠ",
                "4 ngày 3 đêm",
                "Xe ô tô",
                "Thứ 7 mỗi tuần",
                1320000));

        tours.add(new Tour(6L,
                "BUÔN MA THUỘT - GIA LAI - KOMTUM",
                "4 ngày 3 đêm",
                "Xe ô tô",
                "Định kỳ",
                1790000));
    }

    //Get all tours
    public static List<Tour> getAllTours() {
        return tours;
    }

    //Get a specific tour
    public Tour getTour(Long id) {
        return tours.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //Save customer data
    public void saveCustomer(Customer customer) {
        customer.setId((long) (customers.size() + 1)); //Auto generate ID
        customers.add(customer);
    }

    //Save booking data
    public void saveBooking(Booking booking) {
        booking.setId((long) (bookings.size() + 1)); //Auto generate ID
        bookings.add(booking);
        saveBookingToJson(booking);
    }

    private void saveBookingToJson(Booking booking) {
        try {
            List<Booking> allBookings = new ArrayList<>();
            File file = new File(JSON_FILE_PATH);

            if (file.exists() && file.length() > 0) {
                // Read existing bookings
                allBookings = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Booking.class));
            } else {
                // Ensure parent directory exists
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
            }

            allBookings.add(booking);

            // Write back to file
            objectMapper.writeValue(file, allBookings);
        } catch (IOException e) {
            System.err.println("Error saving booking to JSON: " + e.getMessage());
        }
    }
}
