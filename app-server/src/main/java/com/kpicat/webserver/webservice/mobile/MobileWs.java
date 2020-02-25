package com.kpicat.webserver.webservice.mobile;

import com.kpicat.webserver.model.Configuration;
import com.kpicat.webserver.model.Page;
import com.kpicat.webserver.dao.ConfigurationDao;
import com.kpicat.webserver.dao.PageDao;
import com.kpicat.webserver.dao.UserDao;
import com.kpicat.webserver.model.Component;
import com.kpicat.webserver.model.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ws/mobile")
public class MobileWs {

    @Autowired
    PageDao pageDao;
    
    @Autowired
    ConfigurationDao configDao;
    
    @Autowired
    UserDao userDao;

    @RequestMapping(value="/configuration/one", method = RequestMethod.GET)
    public Configuration getConfiguration(@RequestParam("mobileKey") String mobileKey) {
        String accoutKey = userDao.fetchAccountKeyByMoibleKey(mobileKey);
        return configDao.fetchConfiguration(accoutKey);
    }

    @RequestMapping(value="/page/all", method = RequestMethod.GET)
    public List<Page> getPages(@RequestParam("mobileKey") String mobileKey) {
        return pageDao.fetchPageForMobile(mobileKey);
    }

    @RequestMapping(value="/row/all", method = RequestMethod.GET)
    public List<Row> getRowsByPage(@RequestParam("mobileKey") String mobileKey,
                                   @RequestParam("pageId") String pageId) {
        // TODO: validate the mobileKey.
        List<Row> rows = pageDao.fetchRowsByPage(pageId);
        for (Row row : rows) {
            List<Component> components = pageDao.fetchComponentsByRow(row.getRowId());
            row.setComponents(components);
        }
        return rows;
    }


    public long getLastUpdate(String mobileKey, String componentId) {
        /*
        select c.last_update FROM USER u, COMPONENT c
        WHERE u.mobile_key=? AND u.account_key=c.account_key AND c.component_id=?;
        */
        return 0;
    }
}
