package api.v1.lobbies.shops;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;
import util.ResourceAsString;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class V1LobbiesShopsTest {

    static final String RESOURCE_PATH = "api/v1/lobbies/shops/endpoint/";

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupportedMethod() {
        V1LobbiesShops endpoint = new V1LobbiesShops();
        when(request.requestMethod()).thenReturn("CONNECT");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get() {
        V1LobbiesShops endpoint = new V1LobbiesShops();
        when(request.requestMethod()).thenReturn("GET");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
