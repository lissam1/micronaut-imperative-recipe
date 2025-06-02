package micronaut.essential.demo;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Collections;
import java.util.Map;

@Controller
public class BasicController {
    private static final Map<String, Object> Message =
            Collections.singletonMap("message", "Hello World!");

    @Get
    Map<String, Object> index() {return Message; }
}
