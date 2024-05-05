package com.example.assignment11.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    public String message, sender, receiver, title, docId, ownerId, conversationId, receiverId;
    public Timestamp created_at;
    public ArrayList<String> ids = new ArrayList<>();
    public boolean isRead, isReply;
    public ArrayList<String> deletedBy = new ArrayList<>();
    public ArrayList<String> titlePrefixes = new ArrayList<>();

    public Message() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }


    public Message(String message, String sender, String receiver, String title, Timestamp created_at, String receiverId, Boolean isRead, String docId, String ownerId, ArrayList<String> deletedBy, String conversationId, ArrayList<String> ids, Boolean isReply, ArrayList<String> titlePrefixes) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.created_at = created_at;
        this.isRead = isRead;
        this.docId = docId;
        this.ownerId = ownerId;
        this.deletedBy = deletedBy;
        this.conversationId = conversationId;
        this.receiverId = receiverId;
        this.ids = ids;
        this.isReply = isReply;
        this.titlePrefixes = titlePrefixes;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getTitlePrefixes() {
        return titlePrefixes;
    }

    public void setTitlePrefixes(ArrayList<String> titlePrefixes) {
        this.titlePrefixes = titlePrefixes;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ArrayList<String> getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(ArrayList<String> deletedBy) {
        this.deletedBy = deletedBy;
    }
}
