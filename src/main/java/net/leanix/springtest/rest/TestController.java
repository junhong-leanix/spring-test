package net.leanix.springtest.rest;

import net.leanix.springtest.TestService;
import net.leanix.springtest.rest.TestFilter.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/testTransaction")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s! This is %s!", name, testService.getServerName());
    }

    @GetMapping("/testFilter")
    public String sayHello(CustomPrincipal principal) {
        return String.format("Hello %s!", principal.getName());
    }
}
