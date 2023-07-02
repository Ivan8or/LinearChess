package api.v1.sessions;

import model.api.Model;
import model.mappings.Session;
import model.session.SessionTracker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;
import util.JsonConverter;
import util.ResourceAsString;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class V1SessionsTest {

    static final String RESOURCE_PATH = "api/v1/sessions/endpoint/";

    @Mock
    Model model;

    @Mock
    SessionTracker sessions;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void unsupportedMethod() {
        V1Sessions endpoint = new V1Sessions(model);
        when(request.requestMethod()).thenReturn("CONNECT");
        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"unsupported/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void post() {
        V1Sessions endpoint = new V1Sessions(model);
        String sessionJson = ResourceAsString.at(RESOURCE_PATH+"post/result.json").get();
        Session session = JsonConverter.fromJson(sessionJson, Session.class).get();

        when(request.requestMethod()).thenReturn("POST");
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.startSession()).thenReturn(session);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"post/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }

    @Test
    public void delete() {
        V1Sessions endpoint = new V1Sessions(model);
        String sessionJson = ResourceAsString.at(RESOURCE_PATH+"delete/sessionHeader.json").get();
        Session session = JsonConverter.fromJson(sessionJson, Session.class).get();

        when(request.requestMethod()).thenReturn("DELETE");
        when(request.headers("session")).thenReturn(sessionJson);
        when(model.getSessions()).thenReturn(sessions);
        when(sessions.validSession(session)).thenReturn(true);

        String generated = (String) endpoint.handle(request, response);
        generated = generated.replaceAll("\\s", "");

        String expected = ResourceAsString.at(RESOURCE_PATH+"delete/result.json").get();
        expected = expected.replaceAll("\\s", "");

        Assert.assertEquals(expected, generated);
    }
}
