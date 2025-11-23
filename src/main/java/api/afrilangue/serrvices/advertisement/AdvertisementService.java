package api.afrilangue.serrvices.advertisement;

import api.afrilangue.dto.request.AdvertisementRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Advertisement;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface AdvertisementService {
    Advertisement create(AdvertisementRequest advertisement);

    Advertisement find(String id);

    List<Advertisement> findAll();

    DefaultResponse update(String id, Advertisement advertisement);

    DefaultResponse delete(String id);

    void performUpdateAdvertisement();


}
