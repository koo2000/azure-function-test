package koo.myapp.functiontest.app.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InjectedObj {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 環境変数取得の検証（Azure Portalの「環境変数」で設定した値が取得可能）
    @Value("${myfuncProp: myfuncProp is not found. plese set environment value \"myfuncProp\". }")
    private String propValue;
    public String hello() {
        logger.info("myfuncProp = " + propValue);
        return "hello. myfuncProp = " + propValue;
    }
}
