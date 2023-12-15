package ics.mgs.config.web;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ics.mgs.repository.BellRepository;
import ics.mgs.repository.SiteUserRepository;
import ics.mgs.service.database.bell.BellRepositoryService;
import ics.mgs.service.database.bell.BellRepositoryServiceImpl;
import ics.mgs.service.database.site_user.SiteUserRepositoryService;
import ics.mgs.service.database.site_user.SiteUserRepositoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {
    private final SiteUserRepository userRepository;
    private final BellRepository bellRepository;
    private final JPAQueryFactory queryFactory;


    @Bean
    public SiteUserRepositoryService userRepositoryService() {
        return new SiteUserRepositoryServiceImpl(userRepository,queryFactory);
    }

    @Bean
    public BellRepositoryService bellRepositoryService() {
        return new BellRepositoryServiceImpl(userRepository, bellRepository);
    }
}
