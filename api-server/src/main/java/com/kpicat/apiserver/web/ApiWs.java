package com.kpicat.apiserver.web;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.kpicat.apiserver.model.component.RootObject;
import com.kpicat.apiserver.model.component.RootResponse;
import com.kpicat.apiserver.model.User;
import com.kpicat.apiserver.model.message.Notification;
import com.kpicat.apiserver.service.ApiService;
import com.kpicat.apiserver.service.DataManager;
import com.kpicat.apiserver.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
@RequestMapping("/api/")
public class ApiWs {

    @Autowired
    ApiService apiService;

    @Autowired
    DataManager dm;

    @Autowired
    FirebaseService firebaseService;

    @RequestMapping(value="/component", method= RequestMethod.POST)
    public RootResponse updateComponent(@RequestBody RootObject r) {
        return apiService.handleComponentUpdate(r);
    }

    @RequestMapping(value="/usage", method= RequestMethod.GET)
    public long getApiUsage(@RequestParam("api-key") String apiKey,
                            @RequestParam("date") String date) {
        User user = dm.getUserByApiKey(apiKey);
        return dm.countApiCall(user.getAccountKey(), date);
    }

    @RequestMapping(value="/message", method= RequestMethod.POST)
    public String sendNotification(@RequestBody Notification n)  {
        User user = dm.getUserByApiKey(n.getApiKey());
        String componentId = dm.findComponentIdByNotification(user.getAccountKey(), n.getPageName());
        if (componentId == null) {
            return "Page cannot be found";
        } else {
            String message = n.getMessage();
            firebaseService.sendMessage(componentId, n.getPageName(), message);
            return "Message sent successfully.";
        }
    }
}
