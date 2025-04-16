package com.receipt.processor.receipt_processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptProcessorApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private String morningReceiptJson;
	private String simpleReceiptJson;

	@BeforeEach
	void setup() throws Exception {
		morningReceiptJson = readJsonFromResource("morning-receipt.json");
		simpleReceiptJson = readJsonFromResource("simple-receipt.json");
	}

	private String readJsonFromResource(String filename) throws Exception {
		Path path = new ClassPathResource(filename).getFile().toPath();
		return Files.readString(path);
	}

	@Test
	@DisplayName("Process morning receipt and return receipt ID")
	void testMorningReceiptProcessing() throws Exception {
		mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(morningReceiptJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists());
	}

	@Test
	@DisplayName("Process simple receipt and return receipt ID")
	void testSimpleReceiptProcessing() throws Exception {
		mockMvc.perform(post("/receipts/process")
						.contentType(MediaType.APPLICATION_JSON)
						.content(simpleReceiptJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists());
	}

}
