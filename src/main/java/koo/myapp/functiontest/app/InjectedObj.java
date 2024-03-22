package koo.myapp.functiontest.app;

import org.springframework.stereotype.Component;

@Component
public class InjectedObj {
    
    public String hello() {
        return "hello";
    }
}
