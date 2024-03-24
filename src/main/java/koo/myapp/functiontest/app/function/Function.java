package koo.myapp.functiontest.app.function;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import koo.myapp.functiontest.app.InjectedObj;

/**
 * Azure Functions with HTTP Trigger.
 */
@Component
public class Function {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InjectedObj obj;
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");

        logger.info("exception log", new RuntimeException("exception test"));

        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);

        // 障害テストで使ってみるコード(ちゃんとインターセプターでエラーになることを確認)
        // if (true) {
        //     throw new RuntimeException("error test");
        // }
        return request.createResponseBuilder(HttpStatus.OK).body("this is example! hello " + obj.hello()).build();
    }
}
