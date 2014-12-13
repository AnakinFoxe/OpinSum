package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Transactional
    public Device save(Device device) {

        return deviceRepository.save(device);
    }

    @Transactional
    public Device findDeviceById(Long id) {
        return deviceRepository.loadById(id);
    }

    @Transactional
    public Device findDeviceByProductId(String productId) {
        return deviceRepository.loadByProductId(productId);
    }

    @Transactional
    public List<Device> findAllDevices() {

        return deviceRepository.loadAll();
    }
}
