package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.entity.WxUser;

public interface WxUserService extends BaseService<WxUser> {
    WxUser getWxUser(String openid) throws Exception;
}
