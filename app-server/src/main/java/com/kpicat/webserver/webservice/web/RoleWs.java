package com.kpicat.webserver.webservice.web;

import com.kpicat.webserver.dao.PageDao;
import com.kpicat.webserver.dao.RoleDao;
import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.model.Page;
import com.kpicat.webserver.model.Role;
import com.kpicat.webserver.service.Common;
import com.kpicat.webserver.service.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ws/web/role")
public class RoleWs {

    @Autowired
    RoleDao roleDao;
    
    @Autowired
    PageDao pageDao;
    
    @Autowired
    UserDao userDao;

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public List<Role> getRoles(@CookieValue(Constants.SESSION_KEY) String sessionKey) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        return roleDao.fetchRoles(accountKey);
    }

    @RequestMapping(value="/one", method = RequestMethod.GET)
    public Role getRole(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                        @RequestParam("roleId") String roleId) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        
        Role role = roleDao.fetchRole(roleId, accountKey);
        
        // Get all pages that belong to this account.
        List<Page> allPages= pageDao.fetchPages(accountKey);
        
        // Get the pages assigned to this role.
        List<String> pageIds = pageDao.fetchPagesByRole(roleId);
        
        for (Page p : allPages) {
            for (String pageId : pageIds) {
                if (p.getPageId().equals(pageId)) {
                    p.setAssigned(1);
                    break;
                }
            }
        }
        
        role.setPages(allPages);
        return role;
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @Transactional
    public Role saveRole(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                         @RequestBody Role role) {

        if (Common.isEmpty(role.getName())) {
            return null;
        }

        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        String roleId = role.getRoleId();

        if (Common.isEmpty(roleId)) {

            roleId = "rol_" + Common.getUniqueId();
            roleDao.createRole(roleId, accountKey, role.getName());

            for (Page p : role.getPages()) {
                roleDao.createPageRole(p.getPageId(), roleId);
            }

        } else {

            roleDao.updateRole(roleId, role.getName());

            // Delete all. 
            roleDao.deletePageRoleByRole(roleId);

            // Recreate them.
            for (Page p : role.getPages()) {
                roleDao.createPageRole(p.getPageId(), roleId);
            }
        }
        
        return getRole(sessionKey, roleId);
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    @Transactional
    public void deleteRole(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                           @RequestParam("roleId") String roleId) {

        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        Role role = roleDao.fetchRole(roleId, accountKey);
        if (role != null) {
            roleDao.deletePageRoleByRole(roleId);
            userDao.updateUserRole(roleId);
            roleDao.deleteRole(roleId);
        }
    }
}
