package api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import spark.Request;
import spark.Response;
import util.ResourceAsString;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RootTest {

    static final String RESOURCE_PATH = "api/root/";

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupported() {
        Root endpoint = new Root();
        lenient().when(request.requestMethod()).thenReturn("TRACE");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void get(){
        Root endpoint = new Root();
        lenient().when(request.requestMethod()).thenReturn("GET");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"get/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

}
