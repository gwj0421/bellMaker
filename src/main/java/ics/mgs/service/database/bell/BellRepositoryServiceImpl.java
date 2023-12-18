package ics.mgs.service.database.bell;

import ics.mgs.dao.Bell;
import ics.mgs.dao.SiteUser;
import ics.mgs.dto.FileResponse;
import ics.mgs.error.UserNotFound;
import ics.mgs.repository.BellRepository;
import ics.mgs.repository.SiteUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class BellRepositoryServiceImpl implements BellRepositoryService {
    private final SiteUserRepository userRepository;
    private final BellRepository bellRepository;

    @Autowired
    public BellRepositoryServiceImpl(SiteUserRepository userRepository, BellRepository bellRepository) {
        this.userRepository = userRepository;
        this.bellRepository = bellRepository;
    }

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
                    .url(pathAndUri.get(1))
                    .build();
            user.get().addBell(bell);
            bells.add(bell);
        }
        bellRepository.saveAll(bells);
        return user.get();
    }
}
