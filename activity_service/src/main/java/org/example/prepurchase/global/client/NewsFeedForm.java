package org.example.prepurchase.global.client;

import lombok.Builder;
import org.example.prepurchase.global.config.NewsFeedType;

@Builder
public class NewsFeedForm {
    private String senderId;
    private String receiverId;
    private NewsFeedType serviceType;
    private String postName;

    public NewsFeedForm() {
    }

    public NewsFeedForm(String senderId, String receiverId, NewsFeedType serviceType, String postName) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.serviceType = serviceType;
        this.postName = postName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public NewsFeedType  getServiceType() {
        return serviceType;
    }

    public void setServiceType(NewsFeedType  serviceType) {
        this.serviceType = serviceType;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
