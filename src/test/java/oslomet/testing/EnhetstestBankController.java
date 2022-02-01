package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
        hentKundeInfo_IkkeloggetInn();
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
        hentKonti_IkkeLoggetInn();
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_LoggetInn()  {


        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(konto1);

        // act
        Konto resultat = bankController.hentTransaksjoner("01010110523","30.01.2020","20.10.2022");

        // assert
        assertEquals(konto1, resultat);
        hentTransaksjoner_IkkeLoggetInn();
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("01010110523","30.01.2020","20.10.2022");

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn()  {
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
        hentSaldi_IkkeLoggetInn();
    }

    @Test
    public void hentSaldi_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }
    @Test
    public void registrerBetaling_LoggetInn()  {


        Transaksjon transaksjon1 = new Transaksjon(1,"2113411",121.21,"12.01.2022",
                "førstTrans","Ciwan Kurd","32321432544");
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(transaksjon1);

        // assert
        assertEquals("OK", resultat);
        registrerBetaling_IkkeLoggetInn();
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn(){
        // arrange
        Transaksjon transaksjon1 = new Transaksjon(1,"2113411",121.21,"12.01.2022",
                "førstTrans","Ciwan Kurd","32321432544");
        when(sjekk.loggetInn()).thenReturn(null);
//        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn(null);
        // act
        String resultat = bankController.registrerBetaling(transaksjon1);

        // assert
        assertNull(resultat);
    }
    @Test
    public void hentBetalinger_LoggetInn()  {
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1,"2113411",121.21,"12.01.2022",
                "førstTrans","Ciwan Kurd","32321432544");

        Transaksjon transaksjon2 = new Transaksjon(2,"2112311",126.21,"12.02.2022",
                "førstTrans","Ciwan Kurd","32321434344");
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjonList);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjonList, resultat);
        hentBetalinger_IkkeLoggetInn();
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }
    @Test
    public void utforBetaling_LoggetInn()  {
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1,"2113411",121.21,"12.01.2022",
                "førstTrans","Ciwan Kurd","32321432544");

        Transaksjon transaksjon2 = new Transaksjon(2,"2112311",126.21,"12.02.2022",
                "førstTrans","Ciwan Kurd","32321434344");
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);
        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(sjekk.loggetInn())).thenReturn(transaksjonList);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(2);

        // assert
        assertEquals(transaksjonList, resultat);
        utforBetaling_IkkeLoggetInn();
    }

    @Test
    public void utforBetaling_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);
//        when(repository.hentBetalinger(anyString())).thenReturn(null);
        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertNull(resultat);
    }
    @Test
    public void endre_LoggetInn()  {
        when(sjekk.loggetInn()).thenReturn("01010110523");
        Kunde enKunde = new Kunde(sjekk.loggetInn(),
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("endret");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("endret", resultat);
    }

    @Test
    public void endre_IkkeLoggetInn()  {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }
}

