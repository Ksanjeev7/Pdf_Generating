package com.pdfGenerator.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pdfGenerator.Service.PDFService;

@WebMvcTest(PDFController.class)
public class PDFControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PDFService pdfService;

    @Test
    public void testGeneratePdf() throws Exception {
        // Mocking service behavior
        Mockito.when(pdfService.generatePdf(Mockito.any())).thenReturn("test.pdf");

        // Create request body
        String requestBody = "{ \"seller\": \"XYZ Pvt. Ltd.\", \"sellerGstin\": \"29AABBCCDD121ZD\", \"sellerAddress\": \"New Delhi, India\", \"buyer\": \"Vedant Computers\", \"buyerGstin\": \"29AABBCCDD131ZD\", \"buyerAddress\": \"New Delhi, India\", \"items\": [{\"name\": \"Product 1\", \"quantity\": \"12 Nos\", \"rate\": 123.00, \"amount\": 1476.00}]}";

        // Perform POST request and verify
        mockMvc.perform(post("/api/pdf/generate")
                .contentType("application/json")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
