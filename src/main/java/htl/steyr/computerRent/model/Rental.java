package htl.steyr.computerRent.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rentalID;

    @Column(nullable = false)
    private LocalDate dateOfIssue;

    @Column(nullable = false)
    private LocalDate returnDate;

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

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
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

