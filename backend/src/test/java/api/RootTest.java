package api;

import api.Root;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RootTest {

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void testEndpointGet1(){



        String generated = (String) new Root().handle(request, response);
        generated = JsonConverter.minimize(generated);

        Reference from = new Reference(new Endpoint("/api/v1", "GET"));
        String expected = JsonConverter.toJson(from);

        Assert.assertEquals(expected, generated);
    }

}
