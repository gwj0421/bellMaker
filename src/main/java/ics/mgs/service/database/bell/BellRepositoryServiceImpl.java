package ics.mgs.service.database.bell;

import ics.mgs.dao.Bell;
import ics.mgs.dao.SiteUser;
import ics.mgs.dto.FileResponse;
import ics.mgs.repository.BellRepository;
import ics.mgs.repository.SiteUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public SiteUser saveBellToUser(String userId, FileResponse response) {
        Optional<SiteUser> user = userRepository.findSiteUserByUserId(userId);
        if (user.isEmpty()) {
            log.info("gwj : cannot find user in user repository");
            return SiteUser.builder().build();
        }

        Set<Bell> bells = new LinkedHashSet<>();
        for (int i = 0; i < response.getContent().size(); i++) {
            List<String> pathAndUri = response.getContent().get(i);
            Bell bell = Bell.builder().fileName(pathAndUri.get(0)).user(user.get())
                    .url(pathAndUri.get(1))
                    .build();
            bells.add(bell);
        }
        bellRepository.saveAll(bells);
        return userRepository.save(user.get());
    }
}
