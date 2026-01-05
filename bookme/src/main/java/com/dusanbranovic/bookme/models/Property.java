package com.dusanbranovic.bookme.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PropertyType propertyType;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<BookableUnit> units;

    @OneToMany(mappedBy = "property")
    private List<PropertyFacility> propertyFacilities;

    @OneToMany(mappedBy = "property")
    private List<ContactPerson> contacts;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImage> images;

    private String name;
    private String description;
    private String country;
    private String city;
    private String address;
    private String houseRules;
    private String importantInfo;

    public Property() {
    }

    public Property(User owner,
                    PropertyType propertyType,
                    String name,
                    String description,
                    String country,
                    String city,
                    String address,
                    String houseRules,
                    String importantInfo) {
        this.owner = owner;
        this.propertyType = propertyType;
        this.name = name;
        this.description = description;
        this.country = country;
        this.city = city;
        this.address = address;
        this.houseRules = houseRules;
        this.importantInfo = importantInfo;
    }

    public Property(Long id,
                    User owner,
                    PropertyType propertyType,
                    String name,
                    String description,
                    String country,
                    String city,
                    String address,
                    String houseRules,
                    String importantInfo
    ) {
        this.id = id;
        this.owner = owner;
        this.propertyType = propertyType;
        this.name = name;
        this.description = description;
        this.country = country;
        this.city = city;
        this.address = address;
        this.houseRules = houseRules;
        this.importantInfo = importantInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseRules() {
        return houseRules;
    }

    public void setHouseRules(String houseRules) {
        this.houseRules = houseRules;
    }

    public String getImportantInfo() {
        return importantInfo;
    }

    public void setImportantInfo(String importantInfo) {
        this.importantInfo = importantInfo;
    }

    public List<BookableUnit> getUnits() {
        return units;
    }

    public void setUnits(List<BookableUnit> units) {
        this.units = units;
    }

    public List<PropertyFacility> getPropertyFacilities() {
        return propertyFacilities;
    }

    public void setPropertyFacilities(List<PropertyFacility> propertyFacilities) {
        this.propertyFacilities = propertyFacilities;
    }

    public List<ContactPerson> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactPerson> contacts) {
        this.contacts = contacts;
    }

    public List<PropertyImage> getImages() {
        return images;
    }

    public void setImages(List<PropertyImage> images) {
        this.images = images;
    }
}
