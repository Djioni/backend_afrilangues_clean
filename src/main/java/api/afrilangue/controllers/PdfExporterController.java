package api.afrilangue.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfExporterController {

    private final PdfExporter pdfExporter;

    @GetMapping("/export")
    public void exportPdf(HttpServletResponse response) throws IOException {
        List<String> dataList = Arrays.asList("Donnée 1", "Donnée 2", "Donnée 3");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=data.pdf");
        pdfExporter.export(dataList, response);
    }
}
