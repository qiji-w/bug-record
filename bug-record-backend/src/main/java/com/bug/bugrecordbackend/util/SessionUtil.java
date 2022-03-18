package com.bug.bugrecordbackend.util;

import com.bug.bugrecordbackend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.Serializable;


@Component
public class SessionUtil {
    @Autowired
    HttpSession httpSession;

    private final String CURRENT_USER_KEY = "currentLoginUser";

    /**
     * 设置当前用户
     *
     * @param currentUser
     */
    public void setCurrentUser(CurrentUser currentUser) {
        httpSession.setAttribute(CURRENT_USER_KEY, currentUser);
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public CurrentUser getCurrentUser() {
        return (CurrentUser) httpSession.getAttribute(CURRENT_USER_KEY);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CurrentUser implements Serializable {
        private UserEntity userEntity;
    }
}

