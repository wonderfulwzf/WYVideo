package pxxy.wzf.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxxy.wzf.server.domain.Test;
import pxxy.wzf.server.mapper.TestMapper;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.selectByExample(null);
    }
}

