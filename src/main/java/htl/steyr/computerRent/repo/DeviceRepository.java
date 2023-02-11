package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    @Query(value = "SELECT * FROM device INNER JOIN brand b on device.brand_id = b.brandid LEFT OUTER JOIN rental r on device.deviceid = r.device_id WHERE b.name=?1 and (return_date < ?2 or date_of_issue  > ?3 or return_date is null) ", nativeQuery = true)
    List<Device> filterByBrand(String brandname, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT * FROM device LEFT OUTER JOIN  rental r on device.deviceid = r.device_id WHERE return_date < ?1 or date_of_issue  > ?2 or return_date is null", nativeQuery = true)
    List<Device> findAvaiableDevices(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT d.*  FROM device d INNER JOIN rental r WHERE r.date_of_issue<=CURRENT_DATE() and r.return_date>= CURRENT_DATE() and r.customer_id=?1 ", nativeQuery = true)
    List<Device> findOpenRentals(int customerID);


    @Query(value = "SELECT price FROM device WHERE deviceid = ?1", nativeQuery = true)
    int getPrice(int deviceID);

    @Query(value = "SELECT price * DATEDIFF(r.return_date,r.date_of_issue) as total_price FROM device d INNER JOIN rental r on d.deviceid = r.device_id WHERE r.device_id=?1  ",nativeQuery = true)
    double getTotalPrice(int deviceID);
}
