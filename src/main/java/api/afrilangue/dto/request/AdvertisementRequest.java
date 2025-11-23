package api.afrilangue.dto.request;

import api.afrilangue.models.ExerciseMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String link;
    private List<ExerciseMedia> exerciseMedia;
}
