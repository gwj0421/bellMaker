package ics.mgs.config.scheduler;

import ics.mgs.service.database.bell.BellRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleanScheduler {
    private final BellRepositoryService bellRepositoryService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void scheduledTaskAtNoon() {
        bellRepositoryService.deleteBellByExpired();
    }
}
