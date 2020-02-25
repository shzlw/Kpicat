package com.kpicat.apiserver.model.message;

/**
 * {
         "to" : /topics/foo-bar",
         "priority" : "high",
         "notification" : {
             "body" : "This is a Firebase Cloud Messaging Topic Message!",
             "title" : "FCM Message",
         }
     }
 */
public class FcmMessage {

    private String to;

    private String priority = "high";

    private Notification notification;

    private class Notification {

        private String title;

        private String body;

        public Notification(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    public FcmMessage(String topic, String title, String body) {
        this.to = "/topics/" + topic;
        this.notification = new Notification(title, body);
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
