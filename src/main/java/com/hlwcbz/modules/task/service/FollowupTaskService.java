package com.hlwcbz.modules.task.service;

public interface FollowupTaskService {

    boolean send() throws Exception;

    boolean timeout();
}
