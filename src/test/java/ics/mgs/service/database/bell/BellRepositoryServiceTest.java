package ics.mgs.service.database.bell;

import ics.mgs.config.web.QueryDslConfig;
import ics.mgs.config.web.ServiceConfig;
import ics.mgs.config.web.WebClientConfig;
import ics.mgs.dao.Bell;
import ics.mgs.dao.SiteUser;
import ics.mgs.dto.FileResponse;
import ics.mgs.repository.SiteUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@Import({ServiceConfig.class, QueryDslConfig.class, WebClientConfig.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BellRepositoryServiceTest {
    private final SiteUserRepository userRepository;
    private final BellRepositoryService bellRepositoryService;

    @Autowired
    public BellRepositoryServiceTest(SiteUserRepository userRepository, BellRepositoryService bellRepositoryService) {
        this.userRepository = userRepository;
        this.bellRepositoryService = bellRepositoryService;
    }

    @Test
    void should_saveBell_When_inputRelatedUserIdAndBell() {
        // given
        List<SiteUser> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SiteUser user = SiteUser.builder().userId("testUserId" + i)
                    .name("testUserName" + i)
                    .password("testPassword" + i)
                    .email("testEmail" + i + "@gmail.com")
                    .build();
            users.add(userRepository.save(user));
        }

        // when
        String expiredSecond = "1702960778";
        for (SiteUser user : users) {
            List<List<String>> content = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                content.add(List.of(user.getName() + "\'s FileName" + i, user.getName() + "\'s FileUri" + i, expiredSecond));
            }
            bellRepositoryService.saveBellToUser(user.getUserId(), new FileResponse("OK", content));
        }

        // then
        for (SiteUser user : users) {
            Optional<SiteUser> expectedUser = userRepository.findSiteUserByUserId(user.getUserId());
            assertThat(expectedUser.get().getBells()).hasSize(3);
        }
    }

    @Test
    void should_deleteBell_when_expired() {
        // given
        SiteUser user = userRepository.save(SiteUser.builder().userId("testUserId")
                .name("testUserName")
                .password("testPassword")
                .email("testEmail@gmail.com")
                .build());
        Bell bell1 = Bell.builder()
                .url("testUri1")
                .expiresIn(LocalDateTime.now().plus(1, ChronoUnit.HOURS))
                .fileName("testFileName1")
                .build();
        Bell bell2 = Bell.builder()
                .url("testUri2")
                .expiresIn(LocalDateTime.now().minus(1, ChronoUnit.HOURS))
                .fileName("tesFileName2")
                .build();

        user.addBell(bell1);
        user.addBell(bell2);

        bellRepositoryService.saveBell(bell1);
        bellRepositoryService.saveBell(bell2);
        System.out.println(bellRepositoryService.findBellsWithFetchJoin());

        // when
        bellRepositoryService.deleteBellByExpired();
        List<Bell> remainBells = bellRepositoryService.findBellsWithFetchJoin();

        // then
        assertThat(remainBells).hasSize(1);
        assertThat(remainBells.get(0).getFileName()).isEqualTo("testFileName1");
    }
}