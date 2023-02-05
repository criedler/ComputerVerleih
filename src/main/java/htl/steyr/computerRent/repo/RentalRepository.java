package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental,Integer> {
}
