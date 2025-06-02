package micronaut.essential.demo;

import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class BasicControllerTest {

    @Test
    void basicTest(@Client("/") HttpClient httpclient) {
        BlockingHttpClient client = httpclient.toBlocking();
        String json = assertDoesNotThrow(() -> client.retrieve("/"));
        assertEquals("{\"message\":\"Hello World!\"}", json);
    }
}
