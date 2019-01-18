package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.service.HelloSyncService;
import org.springframework.stereotype.Service;

@Service
public class HelloSyncServiceImpl implements HelloSyncService {

    @Override
    public String saySync(String string) {
        return string;
    }

}
