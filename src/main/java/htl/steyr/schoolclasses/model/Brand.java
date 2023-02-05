package htl.steyr.schoolclasses.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandID;


    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Device> devices;

    public Brand(String name, List<Device> devices) {
        this.name = name;
        this.devices = devices;
    }

    public Brand() {
    }

    @Override
    public String toString() {
        return name;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
