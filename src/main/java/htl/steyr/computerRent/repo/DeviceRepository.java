package htl.steyr.computerRent.repo;

import htl.steyr.computerRent.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Integer> {

}
