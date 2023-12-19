package ics.mgs.config.web;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ics.mgs.repository.BellRepository;
import ics.mgs.repository.SiteUserRepository;
import ics.mgs.service.database.bell.BellRepositoryService;
import ics.mgs.service.database.bell.BellRepositoryServiceImpl;
import ics.mgs.service.database.site_user.SiteUserRepositoryService;
import ics.mgs.service.database.site_user.SiteUserRepositoryServiceImpl;
import ics.mgs.service.musicgen_service.MusicGenService;
import ics.mgs.service.musicgen_service.MusicGenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {
    private final SiteUserRepository userRepository;
    private final BellRepository bellRepository;
    private final JPAQueryFactory queryFactory;
    private final WebClient webClient;


    @Bean
    public SiteUserRepositoryService userRepositoryService() {
        return new SiteUserRepositoryServiceImpl(userRepository, queryFactory);
    }

    @Bean
    public BellRepositoryService bellRepositoryService() {
        return new BellRepositoryServiceImpl(userRepository, bellRepository, queryFactory);
    }

    @Bean
    public MusicGenService musicGenService() {
        return new MusicGenServiceImpl(webClient);
    }
}
