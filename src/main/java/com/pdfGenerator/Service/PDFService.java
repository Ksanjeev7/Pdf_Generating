package com.pdfGenerator.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.pdfGenerator.Model.InvoiceRequest;

@Service
public class PDFService {

	@Value("${pdf.storage.path}")
	private String pdfStorgaePath;

	private final TemplateEngine templateEngine;

	public PDFService(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	 public String generatePdf(InvoiceRequest invoiceRequest) throws Exception {
	        // Generate a hash from the input data for uniqueness
	        String fileName = invoiceRequest.hashCode()+ ".pdf";
	        //Storage File path from local System.
	        Path filePath = Paths.get(pdfStorgaePath, fileName);
	        
//	             System.out.println("Path: "+ filePath);

	        // Check if file already exists
	        if (Files.exists(filePath)) {
	            return filePath.toString();
	        }

	        // Create a Thymeleaf context and populate it with data
	        Context context = new Context();
	        
	        context.setVariable("invoice", invoiceRequest);

	        // Process the Thymeleaf template into an HTML string
	        String htmlContent = templateEngine.process("invoice_template", context);

	        // Convert HTML to PDF by Converting filePath to File
	        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
	        	
	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocumentFromString(htmlContent);
	            renderer.layout();
	            renderer.createPDF(fos, false);
	            renderer.finishPDF();  // Complete the PDF generation
	            
	        } catch (Exception e) {
	            throw new Exception("Error generating PDF", e);
	        }
	        

	        return filePath.toString();
	    }
}
