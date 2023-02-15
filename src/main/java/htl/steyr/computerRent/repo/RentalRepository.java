package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Rental;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental,Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE rental SET total_cost = ?2 " +
            "WHERE device_id = ?1",nativeQuery = true)
    void insertFinalPrice(int deviceID, double totalPrice);

    @Query(value = "SELECT r.* FROM rental r " +
            "INNER JOIN device d ON r.device_id = d.device_id " +
            "WHERE r.device_id = ?1",nativeQuery = true)
    List<Rental> findRentalsForDevice(int deviceID);
}
