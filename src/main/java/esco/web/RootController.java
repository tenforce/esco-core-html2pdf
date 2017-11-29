package esco.web;

import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class RootController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    @RequestMapping(value = "/pdf/{file_name}", method = RequestMethod.POST)
    public HttpEntity<byte[]> createPdf(
            @PathVariable("file_name") String fileName,
            @RequestBody String html) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        byte[] documentBody = outputStream.toByteArray();
        HttpHeaders header = new HttpHeaders();
        setHeaders(header, "attachment; filename="+fileName);
        header.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, header);
    }

    @RequestMapping(path = "/pdf", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<byte[]> createPdf(
            @RequestBody String html) throws Exception {
        return createPdf("description.pdf",html);
    }

    @RequestMapping(path = "/test")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public HttpEntity<byte[]> test(@RequestParam(value = "html", defaultValue = "<html><body>Hello World</body></html>") String html) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        byte[] documentBody = outputStream.toByteArray();
        HttpHeaders header = new HttpHeaders();
        setHeaders(header, "attachment; filename=description.pdf");
        header.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, header);
    }

    private void setHeaders(HttpHeaders header, String headerValue) {
        header.setContentType(new MediaType("application", "pdf"));
        header.set("Content-Disposition",headerValue);
    }

}