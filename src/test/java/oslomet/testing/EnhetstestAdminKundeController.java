package oslomet.testing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {
    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController ;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKundeEnhetTest(){
        // arrange
        List<Kunde> kundeList = new ArrayList<>();
        Kunde kunde1 = new Kunde("1001922233", "Ciwan","Kurd",
                "1357","Parksvingen 5",
                "Bekkstua","12124424","12345");
        Kunde kunde2 = new Kunde("10033255", "Ciwan2","Kurd2",
                "1357","Parksvingen 5",
                "Bekkstua","12124424","12345");
        kundeList.add(kunde1);
        kundeList.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("1001922233");

        when(repository.hentAlleKunder()).thenReturn(kundeList);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kundeList, resultat);
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat1 = adminKundeController.hentAlle();

        // assert
        assertNull(resultat1);
    }


    @Test
    public void lagreKundeEnhetTest()  {
        // arrage

        Kunde kunde1 = new Kunde("1001922233", "Ciwan","Kurd",
                "1357","Parksvingen 5",
                "Bekkstua","12124424","12345");
        when(sjekk.loggetInn()).thenReturn("1001922233");
        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("OK", resultat);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat1 = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat1);
    }

    @Test
    public void endreKundeEnhetTest(){
        // arrage
        Kunde kunde1 = new Kunde("1001922233", "Ciwan","Kurd",
                "1357","Parksvingen 5",
                "Bekkstua","12124424","12345");
        when(sjekk.loggetInn()).thenReturn("1001922233");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertEquals("OK", resultat);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat1 = adminKundeController.endre(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat1);
    }

    @Test
    public void slettKundeEnhetTest(){
        // arrage
        Kunde kunde1 = new Kunde("1001922233", "Ciwan","Kurd",
                "1357","Parksvingen 5",
                "Bekkstua","12124424","12345");
        when(sjekk.loggetInn()).thenReturn("1001922233");
        when(repository.slettKunde(anyString())).thenReturn("OK");

        // act
        String resultat = adminKundeController.slett("1001922233");

        // assert
        assertEquals("OK", resultat);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat1 = adminKundeController.slett("1001922233");

        // assert
        assertEquals("Ikke logget inn", resultat1);
    }




}

