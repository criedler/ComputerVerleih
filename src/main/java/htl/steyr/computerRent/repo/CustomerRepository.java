package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
