package esco.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RootControllerTest {

    @Autowired
    private RootController rootController;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletOutputStream outputStream;

    @Test(expected= Exception.class)
    public void nonValidXHTMLTest() throws Exception {
        String validXhtml = new String(Files.readAllBytes(Paths.get("./src/test/resources/2512.html")));
        rootController.createPdf("test.pdf",validXhtml);
    }

    @Test
    public void validXHTMLTest() throws Exception {
        String validXhtml = new String(Files.readAllBytes(Paths.get("./src/test/resources/2512_clean.html")));
        rootController.createPdf("test.pdf",validXhtml);
    }

    @Test
    public void validEscoTest() throws Exception {
        String validXhtml = new String(Files.readAllBytes(Paths.get("./src/test/resources/escoConcept.html")));
        rootController.createPdf("test.pdf",validXhtml);
    }

}