package ics.mgs.repository;

import ics.mgs.dao.Bell;
import ics.mgs.dao.SiteUser;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTest {
    private final SiteUserRepository userRepository;
    private final BellRepository bellRepository;
    @Autowired
    private EntityManager em;

    @Autowired
    public RepositoryTest(SiteUserRepository userRepository, BellRepository bellRepository) {
        this.userRepository = userRepository;
        this.bellRepository = bellRepository;
    }

    @AfterEach
    public void afterEach() {
        bellRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void should_saveUserOrBells_when_inputUserOrBells() {
        // given
        SiteUser user = userRepository.save(SiteUser.builder().userId("testUserId")
                .name("testUserName")
                .password("testPassword")
                .email("testEmail@gmail.com")
                .build());

        Set<Bell> bells = new LinkedHashSet<>();
        for (int i = 0; i < 5; i++) {
            bells.add(Bell.builder().url("test" + i + ".com").fileName("testName" + i).build());
        }
        user.addBells(bells);
        bellRepository.saveAll(bells);

        // when
        Optional<SiteUser> predictedUser = userRepository.findSiteUserByUserId("testUserId");

        // then
        assertThat(predictedUser).isPresent();
        assertThat(predictedUser.get().getBells()).hasSize(bells.size());
    }

    @Test
    void should_removeUserOrBells_when_inputUnrelatedUserOrBells() {
        // given
        Set<Bell> bells = new LinkedHashSet<>();
        for (int i = 0; i < 5; i++) {
            bells.add(Bell.builder().url("test" + i + ".com").fileName("testName" + i).build());
        }
        bellRepository.saveAll(bells);

        List<SiteUser> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SiteUser user = SiteUser.builder().userId("testUserId" + i)
                    .name("testUserName" + i)
                    .password("testPassword" + i)
                    .email("testEmail" + i + "@gmail.com")
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users);

        // when
        userRepository.deleteAllInBatch();
        bellRepository.deleteAllInBatch();

        // given
        assertThat(userRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void should_updateUserOrBell_when_inputExistingUserOrBell() {
        // given
        Bell bell = Bell.builder().fileName("beforeFileName").url("beforeUri").build();
        SiteUser user = SiteUser.builder().name("beforeName").userId("beforeUserId").password("beforePassword").email("beforeEmail").build();
        Bell saveBell = bellRepository.save(bell);
        SiteUser saveUser = userRepository.save(user);

        // when
        saveBell.setFileName("afterFileName");
        saveUser.setName("afterName");
        bellRepository.save(saveBell);
        userRepository.save(saveUser);

        // then
        assertThat(userRepository.findById(saveUser.getId()).get().getName()).isEqualTo("afterName");
        assertThat(bellRepository.findById(saveBell.getId()).get().getFileName()).isEqualTo("afterFileName");
    }
}