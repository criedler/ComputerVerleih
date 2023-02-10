package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Customer;
import htl.steyr.computerRent.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query(value = "SELECT r.* FROM rental r WHERE r.date_of_issue<=NOW() and r.return_date>= NOW() and r.customer_id=?1 ",nativeQuery = true)
    List<Device> findOpenRentals(int customerID);
}
