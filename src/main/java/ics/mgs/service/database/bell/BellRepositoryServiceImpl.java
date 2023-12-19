package ics.mgs.service.database.bell;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ics.mgs.dao.Bell;
import ics.mgs.dao.QBell;
import ics.mgs.dao.QSiteUser;
import ics.mgs.dao.SiteUser;
import ics.mgs.dto.FileResponse;
import ics.mgs.error.UserNotFound;
import ics.mgs.repository.BellRepository;
import ics.mgs.repository.SiteUserRepository;
import ics.mgs.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BellRepositoryServiceImpl implements BellRepositoryService {
    private final SiteUserRepository userRepository;
    private final BellRepository bellRepository;
    private final JPAQueryFactory query;

    @Override
    @Transactional
    public SiteUser saveBellToUser(String userId, FileResponse response) {
        Optional<SiteUser> user = userRepository.findSiteUserByUserId(userId);
        if (user.isEmpty()) {
            log.info("gwj : cannot find user in user repository");
            throw new UserNotFound();
        }

        Set<Bell> bells = new LinkedHashSet<>();
        for (int i = 0; i < response.getContent().size(); i++) {
            List<String> pathAndUri = response.getContent().get(i);
            Bell bell = Bell.builder().fileName(pathAndUri.get(0))
                    .url(pathAndUri.get(1)).expiresIn(TimeUtils.convertSecond2LocalDateTime(pathAndUri.get(2)))
                    .build();

            user.get().addBell(bell);
            bells.add(bell);
        }
        bellRepository.saveAll(bells);
        return user.get();
    }

    @Override
    public List<Bell> findBellsWithFetchJoin() {
        QBell bell = QBell.bell;
        return query.select(bell).from(bell).leftJoin(bell.user, QSiteUser.siteUser).fetchJoin().fetch();
    }

    @Override
    public void saveBell(Bell bell) {
        bellRepository.save(bell);
    }

    @Override
    public void deleteBellByExpired() {
        List<Bell> bells = findBellsWithFetchJoin();
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Bell> deleteList = new ArrayList<>();
        log.info("gwj : " + currentDateTime);
        for (Bell bell : bells) {
            log.info("gwj : " + bell.getExpiresIn());
        }
        for (Bell bell : bells) {
            if (currentDateTime.isAfter(bell.getExpiresIn())) {
                log.info("gwj remove target = " + bell.getFileName());
                log.info(bell.getExpiresIn().toString());
                bell.setUser(null);
                deleteList.add(bell);
            }
        }
        bellRepository.deleteAllInBatch(deleteList);
    }
}
