package api.afrilangue.service;

import api.afrilangue.dto.request.LanguageRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Language;
import api.afrilangue.repositories.LanguageRepository;
import api.afrilangue.serrvices.language.LanguageServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

public class LanguageServiceTest {
    @InjectMocks
    private LanguageServiceImpl service;
    @Mock
    private LanguageRepository repository;


    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_find() {
        when(repository.findById("1")).thenReturn(Optional.of(buildBuild("1")));
        Language language = service.find("1");
        assertThat(language).isNotNull();
        assertThat(language.getName()).isEqualTo("test");
        assertThat(language.getImage()).isEqualTo("test");
    }


  /*  @Test
    public void test_language_create() {
        Language buildLanguage = buildBuild("3");
        LanguageRequest languageRequest = buildLanguageRequest();
        when(repository.save(buildLanguage)).thenReturn(buildLanguage);
        when(repository.existsByName(buildLanguage.getName())).thenReturn(false);
        Language language = service.create(languageRequest);
        assertThat(language).isNotNull();
        assertThat(language.getName()).isEqualTo("test");
        assertThat(language.getImage()).isEqualTo("test");
    } */


    @Test
    public void test_language_find_all() {
        when(repository.findAll()).thenReturn(List.of(buildBuild("2")));
        List<Language> languages = service.findAll();
        assertThat(languages).isNotNull();
        assertThat(languages.stream().findFirst().get().getName()).isEqualTo("test");
        assertThat(languages.stream().findFirst().get().getImage()).isEqualTo("test");
    }

    @Test
    public void test_language_delete() {
        when(repository.findById("4")).thenReturn(Optional.of(buildBuild("4")));
        DefaultResponse response = service.delete("4");
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo("A été effectuée avec succès");
    }

    @Test
    public void test_language_update() {
        Language languageRequest = Language.builder()
                .id("6")
                .name("test1")
                .image("test1")
                .build();
        when(repository.findById("6")).thenReturn(Optional.of(languageRequest));
        DefaultResponse response = service.update("6", languageRequest);
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo("A été effectuée avec succès");
    }

    private Language buildBuild(String id) {
        return Language.builder()
                .id(id)
                .name("test")
                .image("test")
                .build();
    }

    private LanguageRequest buildLanguageRequest() {
        return LanguageRequest.builder()
                .name("test")
                .image("test")
                .build();
    }
}
