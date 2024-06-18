package com.cbfacademy.apiassessment;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.cbfacademy.apiassessment.reports.Category;
import com.cbfacademy.apiassessment.reports.Report;
import com.cbfacademy.apiassessment.reports.ReportService;

@SpringBootTest(classes = ReportSearchRestApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTests {

	@LocalServerPort
	private int port;

	private URI baseURI;

	@Autowired
	private TestRestTemplate restTemplate;

	private List<Report> mockReports;

	@MockBean
	private ReportService reportService;

	@BeforeEach
    void setUp() throws URISyntaxException {
		this.baseURI = UriComponentsBuilder.newInstance()
			.scheme("http")
			.host("localhost")
			.port(port)
			.path("/api/reports")
			.build()
			.toUri();

        // adding to the defaultReports
        mockReports = new ArrayList<>();
        mockReports.add(new Report("www.url1.co.uk", "Description1", Category.BEST_PRACTICES, Instant.now()));
        mockReports.add(new Report("www.url2.co.uk", "Description2", Category.REGULATIONS, Instant.now()));
        mockReports.add(new Report("www.url3.com", "Description3", Category.BEST_PRACTICES, Instant.now()));
        mockReports.add(new Report("www.url4.co.uk", "Description4", Category.REGULATIONS, Instant.now()));
    }

	@Test
	@Description("/GET /api/reports returns all reports.")
	public void testGetExistingAllReports() throws Exception {
		Mockito.when(reportService.getAllReports()).thenReturn(mockReports);

		ResponseEntity<List<Report>> response = restTemplate.exchange(
			baseURI,
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<Report>>() {}
			);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Report> responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals(4, responseBody.size());
	}

	@Test
	@Description("/greeting endpoint returns expected response for specified name parameter")
	public void greeting_ExpectedResponseWithNameParam() {
		//ResponseEntity<String> response = restTemplate.getForEntity(base.toString() + "?name=John", String.class);

		//assertEquals(200, response.getStatusCode().value());
		//assertEquals("Hello John", response.getBody());
	}
}
