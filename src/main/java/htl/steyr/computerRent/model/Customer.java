package htl.steyr.computerRent.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerID;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;
    @Column
    private String zipcode;
    @Column
    private String village;
    @Column
    private String street;
    @Column
    private String streetnumber;

    @Column(nullable = true)
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Rental> rentals;

    public Customer(String firstname, String lastname, String zipcode, String village, String street, String streetnumber, String email, List<Rental> rentals) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.zipcode = zipcode;
        this.village = village;
        this.street = street;
        this.streetnumber = streetnumber;
        this.email = email;
        this.rentals = rentals;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
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
