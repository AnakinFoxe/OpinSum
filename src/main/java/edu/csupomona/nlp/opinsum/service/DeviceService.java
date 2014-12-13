package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Device;

import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
public interface DeviceService {

    /**
     * Save device information
     * @param device
     * @return
     */
    public Device save(Device device);

    /**
     * Find the device using its id
     * @param id
     * @return
     */
    public Device findDeviceById(Long id);

    /**
     * Find the device using its product id
     * @param productId
     * @return
     */
    public Device findDeviceByProductId(String productId);

    /**
     * Grab information for all the devices
     * @return
     */
    public List<Device> findAllDevices();
}
