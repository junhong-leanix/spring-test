package net.leanix.springtest;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String getServerName() {
        return "Spring Test";
    }

}
