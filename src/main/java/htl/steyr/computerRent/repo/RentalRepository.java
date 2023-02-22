package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Rental;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental,Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE rental SET total_cost = :totalPrice " +
            "WHERE device_id = :deviceId",nativeQuery = true)
    void insertFinalPrice(@Param("deviceId") int deviceID, @Param("totalPrice") int totalPrice);

    @Query(value = "SELECT r.* FROM rental r " +
            "WHERE r.device_id = :deviceId " +
            "AND r.customer_id = :customerId ",nativeQuery = true)
    List<Rental> findRentalsForDevice(@Param("customerId") int customerID,@Param("deviceId") int deviceID);

    @Query(value = "SELECT rental_id FROM rental WHERE rental_id = LAST_INSERT_ID()", nativeQuery = true)
    int findLastInsert();
}
