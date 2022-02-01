package oslomet.testing;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController ;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;
    @Test
    public void hentAllekontiEnhetTest(){
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKonti()).thenReturn(konti);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(konti, resultat);
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat1 = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat1);
    }


    @Test
    public void registrerKontoEnhetTest()  {
        // arrage

       Konto konto =  new Konto("132322323", "1234321", 2122.23, "Visa", "kr",null);
       when(sjekk.loggetInn()).thenReturn("132322323");
       when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        // act
        String resultat = adminKontoController.registrerKonto(konto);

        // assert
        assertEquals("OK", resultat);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat1 = adminKontoController.registrerKonto(konto);

        // assert
        assertEquals("Ikke innlogget", resultat1);
    }
    @Test
    public void endreKontEnhetTest(){
        // arrage

        Konto konto =  new Konto("132322323", "1234321", 2122.23, "Visa", "kr",null);
        when(sjekk.loggetInn()).thenReturn("132322323");
        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        // act
        String resultat = adminKontoController.endreKonto(konto);

        // assert
        assertEquals("OK", resultat);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat1 = adminKontoController.endreKonto(konto);

        // assert
        assertEquals("Ikke innlogget", resultat1);
    }

    @Test
    public void slettKontoEnhetTest(){
        // arrage

        Konto konto =  new Konto("132322323", "1234321", 2122.23, "Visa", "kr",null);
        when(sjekk.loggetInn()).thenReturn("132322323");
        when(repository.slettKonto(anyString())).thenReturn("OK");

        // act
        String resultat = adminKontoController.slettKonto("1234321");

        // assert
        assertEquals("OK", resultat);

        when(sjekk.loggetInn()).thenReturn(null);
        // act
        String resultat1 = adminKontoController.slettKonto("1234321");

        // assert
        assertEquals("Ikke innlogget", resultat1);
    }

}

