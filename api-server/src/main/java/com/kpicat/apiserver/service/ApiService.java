package com.kpicat.apiserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpicat.apiserver.model.*;
import com.kpicat.apiserver.model.component.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataManager dm;

    @Autowired
    ObjectMapper om;

    @Transactional
    public RootResponse handleComponentUpdate(RootObject r) {

        String apiKey = r.getApiKey();
        String componentId = r.getComponentId();
        long lastUpdate = r.getTimestamp();
        String data = "";
        String type = "";

        try {
            if (r.getData() instanceof VerticalBox) {
                VerticalBox v = (VerticalBox) r.getData();
                data = om.writeValueAsString(v);
                type = "verticalBox";
            } else if (r.getData() instanceof HorizontalBox) {
                HorizontalBox h = (HorizontalBox) r.getData();
                data = om.writeValueAsString(h);
                type = "horizontalBox";
            } else if (r.getData() instanceof PieChart) {
                PieChart p = (PieChart) r.getData();
                data = om.writeValueAsString(p);
                type = "pieChart";
            } else if (r.getData() instanceof LineChart) {
                LineChart l = (LineChart) r.getData();
                data = om.writeValueAsString(l);
                type = "lineChart";
            } else if (r.getData() instanceof BarChart) {
                BarChart b = (BarChart) r.getData();
                data = om.writeValueAsString(b);
                type = "barChart";
            } else {
                return new RootResponse("invalidComponentJsonData");
            }
        } catch (JsonProcessingException e) {
            return new RootResponse("invalidComponentJsonData");
        }

        User user = dm.getUserByApiKey(apiKey);
        if (user == null) {
            return new RootResponse("invalidApiKey");
        } else {
            String accountKey = user.getAccountKey();
            long apiCount = dm.countApiCall(accountKey, DataManager.getCurrentDay());
            if (apiCount == -1) {
                if (dm.updateComponent(accountKey, componentId, type, lastUpdate, data) == 1) {
                    dm.createNewApiMetric(accountKey);
                    apiCount = 1;
                } else {
                    // invalid account, componentId
                    return new RootResponse("invalidCombinationOfApiKeyAndComponentId");
                }

            } else {
                long apiLimit = Constants.MEMBERSHIP_MAP.get(user.getMembership()).getApi();
                if (apiCount <= apiLimit) {
                    if (dm.updateComponent(accountKey, componentId, type, lastUpdate, data) == 1) {
                        dm.updateApiMetric(accountKey);
                        apiCount++;
                    } else {
                        // invalid account, componentId
                        new RootResponse("invalidCombinationOfApiKeyAndComponentId");
                    }
                } else {
                    // exceeds api limit
                    new RootResponse("exceedApiLimit", apiCount);
                }

            }

            return new RootResponse("updateSuccess", apiCount);
        }
    }

}
