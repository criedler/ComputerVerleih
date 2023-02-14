package htl.steyr.computerRent.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL CHECK (first_name <> '')")
    private String firstName;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL CHECK (last_name <> '')")
    private String lastName;

    @Column
    private String zipcode;

    @Column
    private String village;

    @Column
    private String street;

    @Column
    private String streetNumber;

    @Column
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Rental> rentals;

    public Customer(String firstName, String lastname, String zipcode, String village, String street, String streetnumber, String email, List<Rental> rentals) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.zipcode = zipcode;
        this.village = village;
        this.street = street;
        this.streetNumber = streetnumber;
        this.email = email;
        this.rentals = rentals;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerID) {
        this.customerId = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetnumber) {
        this.streetNumber = streetnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
