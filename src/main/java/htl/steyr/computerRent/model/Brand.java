package htl.steyr.computerRent.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.List;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL CHECK (name <> '')")
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Device> devices;

    public Brand() {
    }

    @Override
    public String toString() {
        return name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandID) {
        this.brandId = brandID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
