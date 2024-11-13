package com.pdfGenerator.Controller;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdfGenerator.Model.InvoiceRequest;
import com.pdfGenerator.Service.PDFService;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {
	
	@Autowired
	private PDFService pdfService;
	
	
	@PostMapping("/generate")
    public ResponseEntity<InputStreamResource> generatePdf(@RequestBody InvoiceRequest invoiceRequest) throws Exception {
        String pdfFilePath = pdfService.generatePdf(invoiceRequest);

        Path path = Paths.get(pdfFilePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
