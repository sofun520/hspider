package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.ProductDao;
import cn.heckman.moduleservice.entity.Product;
import cn.heckman.moduleservice.service.ProductService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public BaseDaoInterface<Product, Serializable> getDao() {
        return productDao;
    }

    @Override
    public Page queryPage(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception {
        String hql = "from Product obj ";
        if (null != params.get("tenantCode")) {
            hql = HqlUtil.addCondition(hql, "tenant.tenantCode", params.get("tenantCode"));
        }
        if (null != params.get("tenantId")) {
            hql = HqlUtil.addCondition(hql, "tenant.id", params.get("tenantId"));
        }
        if (null != params.get("productName")) {
            hql = HqlUtil.addCondition(hql, "productName", params.get("productName").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        return pageList(hql, pageNo, pageSize);
    }
}
