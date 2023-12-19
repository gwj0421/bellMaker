package ics.mgs.service.database.site_user;

import ics.mgs.config.web.QueryDslConfig;
import ics.mgs.config.web.ServiceConfig;
import ics.mgs.config.web.WebClientConfig;
import ics.mgs.dao.Bell;
import ics.mgs.dao.SiteUser;
import ics.mgs.error.UserNotFound;
import ics.mgs.repository.BellRepository;
import ics.mgs.repository.SiteUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import({ServiceConfig.class, QueryDslConfig.class, WebClientConfig.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SiteUserRepositoryServiceTest {
    private final SiteUserRepository userRepository;
    private final BellRepository bellRepository;
    private final SiteUserRepositoryService userRepositoryService;

    @Autowired
    public SiteUserRepositoryServiceTest(SiteUserRepository userRepository, BellRepository bellRepository, SiteUserRepositoryService userRepositoryService) {
        this.userRepository = userRepository;
        this.bellRepository = bellRepository;
        this.userRepositoryService = userRepositoryService;
    }

    @AfterEach
    public void endUp() {
        bellRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void getSiteUserByUserId() {
        // given
        SiteUser user = SiteUser.builder().userId("testUserId")
                .name("testUserName")
                .password("testPassword")
                .email("testEmail@gmail.com")
                .build();
        userRepository.save(user);

        // when
        SiteUser realUser = userRepositoryService.getSiteUserByUserId("testUserId");

        // then
        assertThat(realUser).isEqualTo(user);
        assertThatThrownBy(() -> userRepositoryService.getSiteUserByUserId("Nothing")).hasMessageContaining("non-existent user").isInstanceOf(UserNotFound.class);
    }

    @Test
    void findUsersWithFetchJoin() {
        // given
        for (int i = 0; i < 5; i++) {
            SiteUser user = userRepository.save(SiteUser.builder().userId("testUserId" + i)
                    .name("testUserName" + i)
                    .password("testPassword" + i)
                    .email("testEmail" + i + "@gmail.com")
                    .build());
            for (int j = 0; j < 3; j++) {
                Bell bell = Bell.builder().url("test" + j + ".com").fileName("testName" + j).build();
                user.addBell(bell);
                bellRepository.save(bell);
            }
        }

//        List<SiteUser> everyUsers = userRepository.findAll();
        List<SiteUser> everyUsers = userRepositoryService.findUsersWithFetchJoin();
        List<String> everyBellNames = new ArrayList<>();
        for (SiteUser user : everyUsers) {
            for (Bell bell : user.getBells()) {
                everyBellNames.add(bell.getFileName());
            }
        }
        assertThat(everyBellNames).hasSize(15);
    }
}