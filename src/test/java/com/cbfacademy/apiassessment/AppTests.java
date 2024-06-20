package com.cbfacademy.apiassessment;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doAnswer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.cbfacademy.apiassessment.reports.Category;
import com.cbfacademy.apiassessment.reports.Report;
import com.cbfacademy.apiassessment.reports.ReportRepository;
import com.cbfacademy.apiassessment.reports.ReportService;

@SpringBootTest(classes = ReportSearchRestApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTests {

	@LocalServerPort
	private int port;

	private URI baseURI;

	@Autowired
	private TestRestTemplate restTemplate;

	private List<Report> mockReports;
	private Report report1, report2, report3, report4;
	Category testCategory = Category.CORPORATE_INITIATIVES;


	@MockBean
	private ReportService reportService;
	private ReportRepository mockRepository;

	@BeforeEach
    void initializeTestData() throws URISyntaxException {
		this.baseURI = UriComponentsBuilder.newInstance()
			.scheme("http")
			.host("localhost")
			.port(port)
			.path("/api/reports")
			.build()
			.toUri();

        report1 = new Report("www.url1.co.uk", "Description1 test", Category.BEST_PRACTICES, Instant.now());
        report2 = new Report("www.url2.co.uk", "Description2", Category.REGULATIONS, Instant.now());
        report3 = new Report("www.url3.com", "Description3", Category.BEST_PRACTICES, Instant.now());
        report4 = new Report("www.url4.co.uk", "Description4", Category.REGULATIONS, Instant.now());

		//adding the reports to the mockReports.
		mockReports = new ArrayList<>();
		mockReports.add(report1);
		mockReports.add(report2);
		mockReports.add(report3);
		mockReports.add(report4);

    }

	@Test
	@Description("/GET /api/reports returns all reports.")
	void testGetExistingAllReports() {
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
	@Description("GET /api/reports returns internal server error when an exception occurs.")
	void testGetNonExistingAllReports() {
		Mockito.when(reportService.getAllReports()).thenThrow(new RuntimeException("The reports were not found."));

		ResponseEntity<String> response = restTemplate.exchange(
			baseURI,
			HttpMethod.GET,
			null,
			String.class
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	@Description("GET /api/reports/{id} returns reports by their id")
	void testFindExistingReportById() {
		Long reportId = 1L;
		
		Mockito.when(mockRepository.findById(reportId)).thenReturn(Optional.of(report1));

		String url = baseURI.resolve(String.format("/api/reports/%d", reportId)).toString();
		ResponseEntity<Report> response = restTemplate.exchange(
			url,
			HttpMethod.GET,
			null,
			Report.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		Report responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals(report1.getId(), responseBody.getId());


		Mockito.verify(mockRepository).findById(reportId);
	}

	@Test
	@Description("GET /api/reports/{id} returns not found when there is no report associated with the id")
	void testFindNofoundReportById() {
		Long nonExistingReport = 999L;

		Mockito.when(mockRepository.findById(nonExistingReport)).thenThrow(new NoSuchElementException("There is no report associated to this id: "+ nonExistingReport));
	
		String url = baseURI.resolve(String.format("/api/reports/%d", nonExistingReport)).toString();
		ResponseEntity<Report> response = restTemplate.exchange(
			url,
			HttpMethod.GET,
			null,
			Report.class
		);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Description("GET /api/repors/{id} returns internal server error when an exception occurs")
	void testFindReportByIdException() {
		Long nonExistingReport = 999L;

		Mockito.when(mockRepository.findById(nonExistingReport)).thenThrow(new RuntimeException("The report with this id was not found."));

		String url = baseURI.resolve(String.format("/api/reports/%d", nonExistingReport)).toString();
		ResponseEntity<Report> response = restTemplate.exchange(
			url,
			HttpMethod.GET,
			null,
			Report.class
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	@Description("/GET /category/{category} returns list of all reports by their category.")
	void testGetExistingReportByCategory() {
		List <Report > mockReguReport = Arrays.asList(report2, report4);
		List <Report> mockBestPReport = Arrays.asList(report1, report3);
		
		Mockito.when(reportService.getAllReportByCategory(Category.REGULATIONS)).thenReturn(mockReguReport);
		Mockito.when(reportService.getAllReportByCategory(Category.BEST_PRACTICES)).thenReturn(mockBestPReport);

		//Testing the regulations category
		ResponseEntity<List<Report>> responseRegu = restTemplate.exchange(
			baseURI.resolve(String.format("/category/%s", Category.REGULATIONS)),
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<Report>>() {}
		);
		
		
		assertEquals(HttpStatus.OK, responseRegu.getStatusCode());
		List<Report> responseBodyRegu = responseRegu.getBody();
		assertNotNull(responseBodyRegu);
		assertEquals(2, responseBodyRegu.size());
		
		//Testing the best practices category
		ResponseEntity<List<Report>> responseBestP = restTemplate.exchange(
			baseURI.resolve(String.format("/category/%s", Category.BEST_PRACTICES)),
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<Report>>() {}
		);	
		
		assertEquals(HttpStatus.OK, responseBestP.getStatusCode());
		List<Report> responseBodyBestP = responseBestP.getBody();
		assertNotNull(responseBodyBestP);
		assertEquals(2, responseBodyBestP.size());		
	}

	@Test
	@Description("GET /category/{category} returns no content when the category is empty.")
	void testGetEmptyReportByCategory() {
	Mockito.when(reportService.getAllReportByCategory(testCategory)).thenReturn(Collections.emptyList());

	ResponseEntity<Void> response = restTemplate.exchange(
		baseURI.resolve(String.format("/category/%s", testCategory)),
		HttpMethod.GET,
		null,
		Void.class
	);

	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

	}

	@Test
	@Description("Get /category/{category} returns internal server error when an exception occurs")
	void testGetAllReportByCategoryException() {
		Mockito.when(reportService.getAllReportByCategory(testCategory)).thenThrow(new RuntimeException("An unexpected error occurred while retrieving reports"));

		ResponseEntity<String> response = restTemplate.exchange(
			baseURI.resolve(String.format("/category/%s", testCategory)),
			HttpMethod.GET,
			null,
			String.class
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	@Description("GET /api/reports/search returns reports matching the keyword.")
	void testGetSearchByKeywordExistingReport() {
		String keyword = "test";
		List<Report> matchingReports = Arrays.asList(report1);

		Mockito.when(mockRepository.searchByKeyword(keyword)).thenReturn(matchingReports);
		ResponseEntity<List<Report>> response = restTemplate.exchange(
			baseURI.resolve("search?wordToFind="+ keyword),
			HttpMethod.GET,
			null, 
			new ParameterizedTypeReference<List<Report>>() {}
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Report> responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals(matchingReports.size(), responseBody.size());

		Mockito.verify(mockRepository).searchByKeyword(keyword);
	}

	@Test
	@Description("GET /api/reports/search returns no content when no report match with the keyword.")
	void testGetEmptySearchByKeyword() {
		String keyword = "nonexistent";

		Mockito.when(mockRepository.searchByKeyword(keyword)).thenReturn(Collections.emptyList());

		ResponseEntity<List<Report>> response = restTemplate.exchange(
			baseURI.resolve("/search?wordToFind="+ keyword),
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<Report>>() {}
		);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	@Description("GET /api/reports/search returns internal server error when exception occurs")
	void testGetExceptionSearchByKeyword() {
		String keyword = "test";

		Mockito.when(mockRepository.searchByKeyword(keyword)).thenThrow(new RuntimeException("An unexpected error occured while retrieving reports matching the keyword."));

		ResponseEntity<List<Report>> response = restTemplate.exchange(
			baseURI.resolve("/search?wordToFind="+ keyword),
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<Report>>() {}
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	@Description("POST /api/reports/ creates a new report.")
	void testCreateReportSuccesful() {
		Report newReport = new Report("www.testnewurl1.co.uk", "New description1", Category.CORPORATE_INITIATIVES, Instant.now());

		Mockito.when(reportService.createReport(Mockito.any(Report.class))).thenReturn(newReport);
		
		ResponseEntity<Report> response = restTemplate.postForEntity(
			baseURI,
			newReport, 
			Report.class
		);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		Report responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals(newReport.getUrl(), responseBody.getUrl());
		assertEquals(newReport.getDescription(), responseBody.getDescription());

		Mockito.verify(reportService).createReport(Mockito.any(Report.class));
	}

	@Test
	@Description("POST /api/reports/ returns bad request when the creation of the report failed.")
	void testPostBadRequestCreateReport() {
		Report failReport = new Report(null, "New Description1", Category.CORPORATE_INITIATIVES, Instant.now());

		Mockito.when(reportService.createReport(Mockito.any(Report.class))).thenReturn(failReport);

		ResponseEntity<Report> response = restTemplate.postForEntity(
			baseURI, 
			failReport, 
			Report.class
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Description("POST /api/reports/ returns internal server error when an exception occurs.")
	void testPostExceptionCreateReport() {
		Report failReport = new Report(null, "New Description1", Category.CORPORATE_INITIATIVES, Instant.now());
		Mockito.when(reportService.createReport(Mockito.any(Report.class))).thenThrow(new RuntimeException("An unexpected error occured while creating the report."));

		ResponseEntity<Report> response = restTemplate.postForEntity(
			baseURI, 
			failReport, 
			Report.class
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	@Description("PUT /api/reports/{id} updated an existing report")
	void testUpdateExistingReport() {
		Long reportId = 1L;
		Report existingReport = new Report("www.testurl1", "description", Category.CORPORATE_INITIATIVES, Instant.now());
		Report reportUpdated = new Report("www.testurl1", "Updated description", Category.CORPORATE_INITIATIVES, Instant.now());
	
		Mockito.when(mockRepository.findById(reportId)).thenReturn(Optional.of(existingReport));

		Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> {
			Report report = invocation.getArgument(0);
			existingReport.setDescription(report.getDescription());
			return existingReport;
		});

		String url = baseURI.resolve(String.format("/api/reports/%d", reportId)).toString();
		ResponseEntity<Report> response = restTemplate.exchange(
			url,
			HttpMethod.PUT,
			new HttpEntity<>(reportUpdated),
			Report.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		Report responseBody = response.getBody();
		assertNotNull(responseBody);
		assertEquals(reportUpdated.getDescription(), responseBody.getDescription());

		Mockito.verify(mockRepository).findById(reportId);
		Mockito.verify(mockRepository).save(Mockito.any(Report.class));
	}


	@Test
	@Description("PUT /api/reports/{id} return not found when id report to update is not found.")
	void testUpdateNotfoundReport() {
		Long nonexistentId = 999L;
		Report reportUpdated = new Report("www.testurl1", "Updated description", Category.CORPORATE_INITIATIVES, Instant.now());

		Mockito.when(mockRepository.findById(nonexistentId)).thenReturn(Optional.empty());

		String url = baseURI.resolve(String.format("/api/reports/%d", nonexistentId)).toString();
		ResponseEntity<Report> response = restTemplate.exchange(
			url,
			HttpMethod.PUT,
			new HttpEntity<>(reportUpdated),
			Report.class
		);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Description("PUT /api/reports/{id} returns internal server error when an exception occurs.")
	void testUpdateExceptionReport() {
		Long reportId = 1L;
		Report existingReport = new Report("www.testurl1", "description", Category.CORPORATE_INITIATIVES, Instant.now());
		Report reportUpdated = new Report("www.testurl1", "Updated description", Category.CORPORATE_INITIATIVES, Instant.now());
	
		Mockito.when(mockRepository.findById(reportId)).thenReturn(Optional.of(existingReport));

		Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenThrow(new RuntimeException("An unexpected error occured while updating the report with ID "+ reportId));
	
		String url = baseURI.resolve(String.format("/api/reports/%d", reportId)).toString();
		ResponseEntity<Report> response = restTemplate.exchange(
			url,
			HttpMethod.PUT,
			new HttpEntity<>(reportUpdated),
			Report.class
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	@Description("DELETE /api/reports/{id} deletes the report.")
	void testDeleteExistingReport() {
		Long reportId = 1L;
		Report existingReport = new Report("www.testurl1", "description", Category.CORPORATE_INITIATIVES, Instant.now());
		
		Mockito.when(mockRepository.findById(reportId)).thenReturn(Optional.of(existingReport));

		String url = baseURI.resolve(String.format("/api/reports/%d", reportId)).toString();
		
		doNothing().when(mockRepository).deleteById(reportId);
		
		ResponseEntity<Void> response = restTemplate.exchange(
			url,
			HttpMethod.DELETE,
			null,
			Void.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		Mockito.verify(mockRepository).findById(reportId);
		Mockito.verify(mockRepository).deleteById(reportId);
		
	}


	@Test
	@Description("DELETE /api/reports/{id} returns not found when the report to delete was not found.")
	void testDeleteNotfoundReport() {
		Long nonexistentId = 999L;

		Mockito.when(mockRepository.findById(nonexistentId)).thenReturn(Optional.empty());

		String url = baseURI.resolve(String.format("/api/reports/%d", nonexistentId)).toString();

		ResponseEntity<Void> response = restTemplate.exchange(
			url,
			HttpMethod.DELETE,
			null,
			Void.class
		);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		Mockito.verify(mockRepository).findById(nonexistentId);
		Mockito.verify(mockRepository, Mockito.never()).deleteById(nonexistentId);
	}

	@Test
	@Description("DELETE /api/reports/{id} returns internal server error when an exception occurs.")
	void testDeleteExceptionReport() {
		Long reportId = 1L;

		Mockito.when(mockRepository.findById(reportId)).thenReturn(Optional.of(new Report("www.newurl1", "Description new", Category.BEST_PRACTICES, Instant.now())));
		doThrow(new RuntimeException("An error occurred while deleting the report with id: " + reportId));

		String url = baseURI.resolve(String.format("/api/reports/%d", reportId)).toString();

		ResponseEntity<Void> response = restTemplate.exchange(
			url,
			HttpMethod.DELETE,
			null,
			Void.class
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

		Mockito.verify(mockRepository).findById(reportId);
		Mockito.verify(mockRepository).deleteById(reportId);
	}
}
