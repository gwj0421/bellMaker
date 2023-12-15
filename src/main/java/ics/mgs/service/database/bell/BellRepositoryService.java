package ics.mgs.service.database.bell;

import ics.mgs.dao.SiteUser;
import ics.mgs.dto.FileResponse;

public interface BellRepositoryService {
    public SiteUser saveBellToUser(String userId, FileResponse response);
}
