package htl.steyr.schoolclasses.repo;

import htl.steyr.schoolclasses.model.Device;
import htl.steyr.schoolclasses.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Integer> {
}
