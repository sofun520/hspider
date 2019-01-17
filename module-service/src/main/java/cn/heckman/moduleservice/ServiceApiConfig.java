package cn.heckman.moduleservice;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by Tandy on 2016/6/24.
 * 用于外界导入实体配置
 * @Import(JpaEntityScanConfig.class)
 * 这样一来spring jpa实体就会被发现了
 * portal - api /MainClass.java有使用方式
 */
@EntityScan()
public class ServiceApiConfig {
}
