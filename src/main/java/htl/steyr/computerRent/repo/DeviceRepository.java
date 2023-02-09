package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    @Query(value = "SELECT * FROM device INNER JOIN brand b on device.brand_id = b.brandid LEFT OUTER JOIN rental r on device.deviceid = r.device_id WHERE b.name=?1 and (return_date < ?2 or date_of_issue  > ?3 or return_date is null) ", nativeQuery = true)
    List<Device> filterByBrand(String brandname,LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT * FROM device LEFT OUTER JOIN  rental r on device.deviceid = r.device_id WHERE return_date < ?1 or date_of_issue  > ?2 or return_date is null", nativeQuery = true)
    List<Device> findAvaiableDevices(LocalDate startDate, LocalDate endDate);

}
