package com.example.touring.controller;

import com.example.touring.model.Booking;
import com.example.touring.model.Customer;
import com.example.touring.model.Tour;
import com.example.touring.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class TourController {
    @Autowired
    private TourService tourService;

    //Tour list
    @GetMapping({"/", "/list_tour"})
    public String listTour(Model model) {
        model.addAttribute("tours", tourService.getAllTours());
        return "list_tour";
    }

    //Tour detail with ID
    @GetMapping("/tour_detail/{id}")
    public String tourDetail(@PathVariable long id, Model model) {
        Tour tour = tourService.getTour(id);
        if (tour == null) {
            return "redirect:/list_tour"; //No detail -> return to list page
        }
        model.addAttribute("tour", tour);
        return "tour_detail";
    }

    //Booking form
    @GetMapping("/book_tour/{id}")
    public String showBookTour(@PathVariable long id, Model model) {
        Tour tour = tourService.getTour(id);
        if (tour == null) {
            return "redirect:/list_tour";
        }
        model.addAttribute("tour", tour);
        model.addAttribute("customer", new Customer());
        model.addAttribute("booking", new Booking());

        return "book_tour";
    }

    //Booking submit
    @PostMapping("/book_tour")
    public String processBookTour(
            @RequestParam Long tourId,
            @RequestParam String name,
            @RequestParam(required = false) String address,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date departureDate,
            @RequestParam int noAdults,
            @RequestParam(defaultValue = "0") int noChildren,
            Model model
    ) {
        //Create & save Customer
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setPhone(phone);
        tourService.saveCustomer(customer);

        //Create & save Booking
        Tour tour = tourService.getTour(tourId);
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTour(tour);
        booking.setDepartureDate(departureDate);
        booking.setNoAdults(noAdults);
        booking.setNoChildren(noChildren);
        tourService.saveBooking(booking);

        //Move to confirm page
        model.addAttribute("customer", customer);
        model.addAttribute("booking", booking);
        return "confirm";
    }
}
