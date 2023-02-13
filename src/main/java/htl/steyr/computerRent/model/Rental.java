package htl.steyr.computerRent.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rentalId;

    @Column(nullable = false)
    private LocalDate dateOfIssue;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column
    private double totalCost;

    @ManyToOne
    @JoinColumn(name= "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name= "device_id", nullable = false)
    private Device device;

    public Rental(LocalDate dateOfIssue, LocalDate returnDate, Customer customer, Device device) {
        this.dateOfIssue = dateOfIssue;
        this.returnDate = returnDate;
        this.customer = customer;
        this.device = device;
    }

    public Rental() {
    }

    @Override
    public String toString() {
        return customer + " " + device;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalID) {
        this.rentalId = rentalID;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}

