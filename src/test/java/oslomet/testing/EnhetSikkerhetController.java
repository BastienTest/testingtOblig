package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.util.Assert;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetSikkerhetController {
    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhetsController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;

    @Before
    public void initSession() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }

    @Test
    public void test_sjekkLoggetInn() {
        // arrange
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");
        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_sjekkLoggetInngfeil() {
        // arrange
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil i personnummer eller passord");
        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "HeiHeiHei");
        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }
    @Test
    public void test_sjekkLoggetInngFeilPNr() {
        // act
        String resultat = sikkerhetsController.sjekkLoggInn("AB", "14145463346");
        // assert
        assertEquals("Feil i personnummer", resultat);
    }
    @Test
    public void test_sjekkLoggetInngFeilPass() {
        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901", "AB");
        // assert
        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void test_LoggeUt() {
        try {
            // act
            sikkerhetsController.loggUt();
            Assert.isTrue(true);
        }
        catch(Exception e){
            Assert.isTrue(false);
        }
    }

    @Test
    public void test_loggInnAdmin() {
        // act
        String resultat = sikkerhetsController.loggInnAdmin("Admin", "Admin");
        // assert
        assertEquals("Logget inn", resultat);
    }
    @Test
    public void test_loggInnAdminFeil() {
        // act
        String resultat = sikkerhetsController.loggInnAdmin("Ad", "Adm");
        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void test_loggetInn() {
        // arrange
        session.setAttribute("Innlogget","logget");
        // act
        String resultat = sikkerhetsController.loggetInn();
        // assert
        assertEquals("logget", resultat);
    }
    @Test
    public void test_loggetInnfeil() {
        // arrange
        session.setAttribute("Innlogget",null);
        // act
        String resultat = sikkerhetsController.loggetInn();
        // assert
        assertNull(resultat);
    }
}
