package ics.mgs.service.database.bell;

import ics.mgs.dao.Bell;
import ics.mgs.dao.SiteUser;
import ics.mgs.dto.FileResponse;

import java.util.List;

public interface BellRepositoryService {
    public SiteUser saveBellToUser(String userId, FileResponse response);

    public List<Bell> findBellsWithFetchJoin();

    public void saveBell(Bell bell);

    public void deleteBellByExpired();
}
