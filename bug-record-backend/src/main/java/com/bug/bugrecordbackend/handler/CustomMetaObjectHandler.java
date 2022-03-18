package com.bug.bugrecordbackend.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.bug.bugrecordbackend.entity.UserEntity;
import com.bug.bugrecordbackend.util.SessionUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    SessionUtil sessionUtil;

    @Override
    public void insertFill(MetaObject metaObject) {
        SessionUtil.CurrentUser currentUser = this.getCurrentUser();

        this.setFieldValByName("createById", currentUser.getUserEntity().getId(), metaObject);
        this.setFieldValByName("createByName", currentUser.getUserEntity().getName(), metaObject);
        this.setFieldValByName("updateById", currentUser.getUserEntity().getId(), metaObject);
        this.setFieldValByName("updateByName", currentUser.getUserEntity().getName(), metaObject);
        //拦截设置创建时间和更新时间
        Date current = new Date();
        this.setFieldValByName("createTime", current, metaObject);
        this.setFieldValByName("updateTime", current, metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SessionUtil.CurrentUser currentUser = this.getCurrentUser();

        this.setFieldValByName("updateById", currentUser.getUserEntity().getId(), metaObject);
        this.setFieldValByName("updateByName", currentUser.getUserEntity().getName(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }


    public SessionUtil.CurrentUser getCurrentUser(){
        SessionUtil.CurrentUser currentUser  = (SessionUtil.CurrentUser) sessionUtil.getCurrentUser();
        if(currentUser == null){
            currentUser = new SessionUtil.CurrentUser();

            UserEntity userEntity = new UserEntity();
            userEntity.setId(-1);
            userEntity.setUsername("anonymous");
            userEntity.setName("匿名用户");
            currentUser.setUserEntity(userEntity);
        }

        return currentUser;
    }
}
