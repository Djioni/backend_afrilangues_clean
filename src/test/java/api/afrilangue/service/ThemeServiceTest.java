package api.afrilangue.service;

import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Language;
import api.afrilangue.models.Theme;
import api.afrilangue.repositories.ThemeRepository;
import api.afrilangue.serrvices.theme.ThemeServiceImpl;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

public class ThemeServiceTest {
    @InjectMocks
    private ThemeServiceImpl service;
    @Mock
    private ThemeRepository repository;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

   /* @Test
    public void test_theme_create() {
        Theme themeRequest = buildBuild("test");
        Mockito.when(repository.save(themeRequest)).thenReturn(themeRequest);
        Theme theme = service.create(themeRequest);
        assertThat(theme).isNotNull();
        assertThat(theme.getLanguage()).isNotNull();
        assertThat(theme.getName()).isNotNull();

    } */


    @Test
    public void test_theme_find() {
        Theme themeRequest = buildBuild("test");
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(themeRequest));
        Theme theme = service.find("1");
        assertThat(theme).isNotNull();
        assertThat(theme.getLanguage()).isNotNull();
        assertThat(theme.getName()).isNotNull();

    }

    @Test
    public void test_theme_delete() {
        Theme themeRequest = buildBuild("test");
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(themeRequest));
        DefaultResponse response = service.delete("1");
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo("A été effectuée avec succès");

    }

    @Test
    public void test_theme_update() {
        Theme themeRequest = buildBuild("test");
        Theme themeRequestUpdate = buildBuild("test theme");
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(themeRequest));
        DefaultResponse response = service.update("1", themeRequestUpdate);
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo("A été effectuée avec succès");

    }

    @Test
    public void test_theme_find_all() {
        Theme themeRequest = buildBuild("test theme");
        Mockito.when(repository.findAll()).thenReturn(List.of(themeRequest));
        List<Theme> themes = service.findAll();
        assertThat(themes).isNotNull();
        assertThat(themes.stream().findFirst().get().getName()).isEqualTo("test theme");
        assertThat(themes.stream().findFirst().get().getLanguage()).isNotNull();

    }

    private Theme buildBuild(String name) {
        return Theme.builder()
                .id("1")
                .name(name)
                .language(Language.builder()
                        .id("1")
                        .name("test language")
                        .image("test image")
                        .build())
                .build();
    }
}

