package net.leanix.springtest.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.leanix.springtest.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    @Autowired
    private PersonDao personDao;

    @Transactional
    @HystrixCommand(fallbackMethod = "getServerNameFallback")
    public String getServerName() {
        personDao.add("AA2", "BB2");
        if (true) {
            throw new RuntimeException();
        }
        return "Spring Test";
    }

    public String getServerNameFallback() {
        return "Fall Back";
    }

}
