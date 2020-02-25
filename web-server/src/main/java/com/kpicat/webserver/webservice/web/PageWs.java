package com.kpicat.webserver.webservice.web;

import com.kpicat.webserver.dao.PageDao;
import com.kpicat.webserver.dao.RoleDao;
import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.model.Component;
import com.kpicat.webserver.model.Page;
import com.kpicat.webserver.model.Row;
import com.kpicat.webserver.service.Common;
import com.kpicat.webserver.service.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ws/web/page")
public class PageWs {

    @Autowired
    PageDao pageDao;

    @Autowired
    RoleDao roleDao;
    
    @Autowired
    UserDao userDao;

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public List<Page> getPages(@CookieValue(Constants.SESSION_KEY) String sessionKey) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        List<Page> pages = pageDao.fetchPages(accountKey);
        return pages;
    }

    @RequestMapping(value="/one", method = RequestMethod.GET)
    public Page getPageById(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                            @RequestParam("pageId") String pageId) {

        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        return fetchPage(pageId, accountKey);
    }

    private Page fetchPage(String pageId, String accountKey) {
        Page page = pageDao.fetchPage(pageId, accountKey);
        List<Row> rows = pageDao.fetchRowsByPage(pageId);
        for (Row row : rows) {
            List<Component> components = pageDao.fetchComponentsByRow(row.getRowId());
            row.setComponents(components);
        }
        page.setRows(rows);
        return page;
    }

    private void createRow(String pageId, String accountKey, int sequence, int columns) {
        String rowId = "row_" + Common.getUniqueId();
        pageDao.createRow(pageId, rowId, sequence, columns);

        for (int i = 1; i <= columns; i++) {
            String componentId = "com_" + Common.getUniqueId();
            pageDao.createComponent(rowId, accountKey, componentId, i);
        }
    }



    @RequestMapping(value="/save", method = RequestMethod.POST)
    @Transactional
    public Page savePageLayout(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                               @RequestBody Page page) {

        if (Common.isEmpty(page.getName())) {
            return null;
        }

        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        String pageId = page.getPageId();

        if (Common.isEmpty(pageId)) {
            // create a new page

            pageId = "pag_" + Common.getUniqueId();
            pageDao.createPage(pageId, accountKey, page.getName(), page.getDescription(), page.getTitleColor(), page.getBgColor());

            int sequence = 1;
            for (Row row : page.getRows()) {
                createRow(pageId, accountKey, sequence, row.getColumns());
                sequence++;
            }
        } else {
            // update page
            pageDao.updatePage(pageId, page.getName(), page.getDescription(), page.getTitleColor(), page.getBgColor());

            // Page oldPage = dm.fetchPage(pageId, accountKey);
            List<Row> oldRows = pageDao.fetchRowsByPage(pageId);

            List<String> removedRows = new ArrayList<>();

            // Calculate which rows need to be removed.
            for (Row oldRow : oldRows) {
                boolean isFound = false;
                for (Row newRow : page.getRows()) {
                    if (oldRow.getRowId().equals(newRow.getRowId())) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    removedRows.add(oldRow.getRowId());
                }
            }

            // Remove rows not needed.
            for (String rowId : removedRows) {
                pageDao.deleteComponentsByRow(rowId);
                pageDao.deleteRow(rowId);
            }

            // Create/update rows
            int sequence = 1;
            for (Row row : page.getRows()) {
                if (Common.isEmpty(row.getRowId())) {
                    createRow(pageId, accountKey, sequence, row.getColumns());
                } else {
                    pageDao.updateRow(row.getRowId(), sequence);
                }

                sequence++;
            }
        }

        return fetchPage(pageId, accountKey);
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    @Transactional
    public void deletePage(@CookieValue(Constants.SESSION_KEY) String sessionKey,
                           @RequestParam("pageId") String pageId) {
        String accountKey = userDao.fetchAccountKeyBySessionKey(sessionKey);
        Page page = pageDao.fetchPage(pageId, accountKey);
        if (page != null) {
            List<Row> rows = pageDao.fetchRowsByPage(pageId);
            for (Row row : rows) {
                pageDao.deleteComponentsByRow(row.getRowId());
                pageDao.deleteRow(row.getRowId());
            }

            roleDao.deletePageRoleByPage(pageId);
            pageDao.deletePage(pageId);
        }
    }
}
