package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.User;

public interface UserService extends BaseService<User> {

    RestResponse login(String username, String password) throws Exception;

}
