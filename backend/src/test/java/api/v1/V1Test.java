package api.v1;

import api.v1.V1;
import model.mappings.Endpoint;
import model.mappings.Reference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;
import util.JsonConverter;

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class V1Test {

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void testEndpointGet1(){
        lenient().when(request.requestMethod()).thenReturn("GET");

        String generated = (String) new V1().handle(request, response);
        generated = JsonConverter.minimize(generated);

        Reference from = new Reference(new Endpoint[]{
                new Endpoint("/api/v1/lobbies", new String[]{"GET", "POST"}),
                new Endpoint("/api/v1/sessions", new String[]{"POST", "DELETE"})
        });
        String expected = JsonConverter.toJson(from);

        Assert.assertEquals(expected, generated);
    }
}
