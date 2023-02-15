package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    @Query(value = "SELECT device.*\n" +
            "FROM device INNER JOIN brand b on device.brand_id = b.brand_id\n" +
            "WHERE device_id NOT IN (\n" +
            "    SELECT d.device_id\n" +
            "    FROM device d\n" +
            "             LEFT OUTER JOIN rental r on d.device_id = r.device_id\n" +
            "    WHERE NOT (return_date < ?2 OR date_of_issue > ?3 OR return_date IS NULL))\n" +
            "AND b.name = ?1 ;", nativeQuery = true)
    List<Device> filterByBrand(String brandname,LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT device.* FROM device\n" +
            "WHERE device_id NOT IN (\n" +
            "    SELECT d.device_id FROM device d\n" +
            "    LEFT OUTER JOIN rental r on d.device_id = r.device_id\n" +
            "    WHERE NOT(return_date < ?1 OR date_of_issue  > ?2 OR return_date IS NULL));", nativeQuery = true)
    List<Device> findAvaiableDevices(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT d.*  FROM device d INNER JOIN rental r WHERE (r.total_cost is null) and r.customer_id=?1 ", nativeQuery = true)
    List<Device> findOpenRentals(int customerID);

    @Query(value = "SELECT price * DATEDIFF(r.return_date,r.date_of_issue) as total_price FROM device d INNER JOIN rental r on d.device_id = r.device_id WHERE r.device_id=?1  ",nativeQuery = true)
    int getTotalPrice(int deviceID);

}
