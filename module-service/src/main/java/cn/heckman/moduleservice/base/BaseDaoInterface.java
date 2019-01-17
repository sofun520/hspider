package cn.heckman.moduleservice.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by heckman on 2018/2/9.
 */
@NoRepositoryBean
public interface BaseDaoInterface<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {


}