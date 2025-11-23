package api.afrilangue.serrvices.advertisement;

import api.afrilangue.dto.request.AdvertisementRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Advertisement;
import api.afrilangue.repositories.AdvertisementRepository;
import api.afrilangue.serrvices.mediaobject.MediaObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static api.afrilangue.helpers.DefaultPayloadBuilder.getDefaultResponse;
import static api.afrilangue.helpers.UserHelper.recoveryUser;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository repository;
    private final MediaObjectService mediaObjectService;

    @Override
    public Advertisement create(AdvertisementRequest advertisementRequest) {
        if (advertisementRequest.getTitle() == null || advertisementRequest.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Le titre de la publicité ne doit être vide ou nul");
        }

        if ( advertisementRequest.getExerciseMedia() == null || advertisementRequest.getExerciseMedia().isEmpty()) {
            throw new IllegalArgumentException("Les images de la publicité ne doivent pas être vides ou nulles");
        }
        if (advertisementRequest.getEndDate().isBefore(advertisementRequest.getStartDate())) {
            throw new IllegalArgumentException("La langue ne doit pas être vide ou nulle ");
        }
        Advertisement advertisement = Advertisement.builder()
                .title(advertisementRequest.getTitle())
                .description(advertisementRequest.getDescription())
                .link(advertisementRequest.getLink())
                .startDate(advertisementRequest.getStartDate())
                .endDate(advertisementRequest.getEndDate())
                .exerciseMedia(advertisementRequest.getExerciseMedia())
                .build();

        return repository.save(advertisement);
    }


    @Override
    public Advertisement find(String id) {
        return repository.findById(id).orElseGet(() -> {
            throw new IllegalArgumentException("La publicité " + id + " n'existe pas");
        });
    }

    @Override
    public List<Advertisement> findAll() {
        return repository.findAll();
    }

    @Override
    public DefaultResponse update(String id, Advertisement object) {
        Advertisement saved = find(id);
        object.setId(saved.getId());
        object.setModifiedByUser(recoveryUser());
        repository.save(object);
        return getDefaultResponse();
    }

    @Override
    public DefaultResponse delete(String id) {
        Advertisement object = find(id);
        object.getExerciseMedia().forEach(exerciseMedia -> {
            if (exerciseMedia.getMedia() != null) {
                mediaObjectService.deleteFile(exerciseMedia.getMedia());
            }
        });

        repository.delete(object);
        return getDefaultResponse();
    }

    @Override
    public void performUpdateAdvertisement() {
        List<Advertisement> advertisements = repository.findAll();

        advertisements.forEach(advertisement -> {
            if (advertisement.getEndDate().isBefore(advertisement.getStartDate())) {
                advertisement.setActive(false);
                repository.save(advertisement);
            }
        });
    }
}
