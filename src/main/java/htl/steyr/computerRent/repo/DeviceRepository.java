package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Brand;
import htl.steyr.computerRent.model.Device;
import htl.steyr.computerRent.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    @Query(value = "SELECT d FROM Device d JOIN d.brand b " +
            "WHERE d.deviceId NOT IN (" +
            "SELECT d.deviceId FROM Device d " +
            "LEFT JOIN d.rentals r " +
            "WHERE NOT(r.returnDate < :startDate OR r.dateOfIssue > :endDate OR r.returnDate IS NULL))" +
            "AND (:brand is null or d .brand= :brand) " +
            "AND (:model = '' or d.modelName = :model)")
    List<Device> findAvaiableDevices(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                     @Param("brand") Brand brand, @Param("model") String modelname);

    @Query(value = "SELECT DISTINCT d.*  FROM device d " +
            "INNER JOIN rental r ON d.device_id = r.device_id " +
            "WHERE (r.total_cost IS NULL) " +
            "AND r.customer_id=:customerID ;", nativeQuery = true)
    List<Device> findOpenRentals(@Param("customerID") int customerID);


    @Query(value = "SELECT price * DATEDIFF(r.return_date,r.date_of_issue) AS total_price FROM device d " +
            "INNER JOIN rental r ON d.device_id = r.device_id " +
            "WHERE r.rental_id=:rentalID", nativeQuery = true)
    int getTotalPrice(@Param("rentalID") int rentalID);

}
