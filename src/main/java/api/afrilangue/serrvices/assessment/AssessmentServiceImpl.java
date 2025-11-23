package api.afrilangue.serrvices.assessment;

import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Assessment;
import api.afrilangue.models.RequestAssessmentCreate;
import api.afrilangue.models.User;
import api.afrilangue.repositories.AssessmentRepository;
import api.afrilangue.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {
    private final AssessmentRepository repository;
    private final UserRepository userRepository;

    @Override
    public DefaultResponse create(RequestAssessmentCreate request) {
        User user = userRepository.findByUsername(request.getUser())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec nom d'utilisateur : " + request.getUser()));
        Assessment assessment = Assessment.builder()
                .user(user)
                .build();
        repository.save(assessment);
        return DefaultResponse.builder().build();
    }

    @Override
    public Assessment find(String id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("L'évaluation " + id + " n'existe pas ");
        });
    }

}
