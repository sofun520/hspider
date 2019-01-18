package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.Product;

import java.util.Map;

public interface ProductService extends BaseService<Product> {

    Page queryPage(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception;
}
