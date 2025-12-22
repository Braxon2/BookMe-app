package com.dusanbranovic.bookme.models;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class PeriodPriceAddon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "addon_id")
    private Addon addon;

    private double price;

    private LocalDate startDate;
    private LocalDate endDate;

    public PeriodPriceAddon() {
    }

    public PeriodPriceAddon(Addon addon, double price, LocalDate startDate, LocalDate endDate) {
        this.addon = addon;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Addon getAddon() {
        return addon;
    }

    public void setAddon(Addon addon) {
        this.addon = addon;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
