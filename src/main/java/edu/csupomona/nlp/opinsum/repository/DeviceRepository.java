package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.Device;

import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
public interface DeviceRepository {

    public Device save(Device device);

    public Device loadById(Long id);

    public Device loadByProductId(String productId);

    public List<Device> loadAll();

}
