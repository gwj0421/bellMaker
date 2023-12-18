package ics.mgs.service.database.bell;

import ics.mgs.config.web.QueryDslConfig;
import ics.mgs.config.web.ServiceConfig;
import ics.mgs.config.web.WebClientConfig;
import ics.mgs.dao.SiteUser;
import ics.mgs.dto.FileResponse;
import ics.mgs.repository.SiteUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (SiteUser user : users) {
            List<List<String>> content = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                content.add(List.of(user.getName() + "\'s FileName" + i, user.getName() + "\'s FileUri" + i));
            }
            bellRepositoryService.saveBellToUser(user.getUserId(), new FileResponse("OK", content));
        }

        // then
        for (SiteUser user : users) {
            Optional<SiteUser> expectedUser = userRepository.findSiteUserByUserId(user.getUserId());
            assertThat(expectedUser.get().getBells()).hasSize(3);
        }
    }
}