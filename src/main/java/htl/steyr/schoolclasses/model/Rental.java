package htl.steyr.schoolclasses.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rentalID;

    @Column(nullable = false)
    private Date dateOfIssue;

    @Column(nullable = false)
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name= "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name= "device_id", nullable = false)
    private Device device;

    public Rental(Date dateOfIssue, Date returnDate, Customer customer, Device device) {
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

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
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

