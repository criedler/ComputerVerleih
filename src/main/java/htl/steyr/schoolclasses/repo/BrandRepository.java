package htl.steyr.schoolclasses.repo;

import htl.steyr.schoolclasses.model.Brand;
import htl.steyr.schoolclasses.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand,Integer> {

}
