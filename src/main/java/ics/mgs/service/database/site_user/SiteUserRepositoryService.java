package ics.mgs.service.database.site_user;

import ics.mgs.dao.SiteUser;

import java.util.List;

public interface SiteUserRepositoryService {
    public SiteUser getSiteUserByUserId(String userId);
    public List<SiteUser> findUsersWithFetchJoin();
}
