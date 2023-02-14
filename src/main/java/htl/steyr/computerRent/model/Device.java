package htl.steyr.computerRent.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.List;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deviceId;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL CHECK (model_name <> '')")
    private String modelName;

    @Column(nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "device")
    private List<Rental> rentals;


    public Device(String modelName, float price, Brand brand, List<Rental> rentals) {
        this.modelName = modelName;
        this.price = price;
        this.brand = brand;
        this.rentals = rentals;
    }

    public Device() {
    }

    @Override
    public String toString() {
        return brand + " " + modelName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceID) {
        this.deviceId = deviceID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
