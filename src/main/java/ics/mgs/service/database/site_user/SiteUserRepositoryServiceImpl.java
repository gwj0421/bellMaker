package ics.mgs.service.database.site_user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ics.mgs.dao.QBell;
import ics.mgs.dao.QSiteUser;
import ics.mgs.dao.SiteUser;
import ics.mgs.error.UserNotFound;
import ics.mgs.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SiteUserRepositoryServiceImpl implements SiteUserRepositoryService {
    private final SiteUserRepository userRepository;
    private final JPAQueryFactory query;

    @Override
    public SiteUser getSiteUserByUserId(String userId) {
        Optional<SiteUser> user = userRepository.findSiteUserByUserId(userId);
        if (user.isEmpty()) {
            throw new UserNotFound();
        }
        return user.get();
    }

    @Override
    public List<SiteUser> findUsersWithFetchJoin() {
        QSiteUser user = QSiteUser.siteUser;
        return query.select(user).from(user).leftJoin(user.bells, QBell.bell).fetchJoin().fetch();
    }
}
