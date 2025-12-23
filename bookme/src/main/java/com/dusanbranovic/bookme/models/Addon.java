package com.dusanbranovic.bookme.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Addon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private BookableUnit bookableUnit;

    @OneToMany(mappedBy = "addon", cascade = CascadeType.ALL)
    private List<PeriodPriceAddon> periodPriceAddonList;

    private String name;

    private boolean perNight;

    public Addon() {
    }

    public Addon(BookableUnit bookableUnit, String name, boolean perNight) {
        this.bookableUnit = bookableUnit;
        this.name = name;
        this.perNight = perNight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookableUnit getBookableUnit() {
        return bookableUnit;
    }

    public void setBookableUnit(BookableUnit bookableUnit) {
        this.bookableUnit = bookableUnit;
    }

    public List<PeriodPriceAddon> getPeriodPriceAddonList() {
        return periodPriceAddonList;
    }

    public void setPeriodPriceAddonList(List<PeriodPriceAddon> periodPriceAddonList) {
        this.periodPriceAddonList = periodPriceAddonList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPerNight() {
        return perNight;
    }

    public void setPerNight(boolean perNight) {
        this.perNight = perNight;
    }
}
