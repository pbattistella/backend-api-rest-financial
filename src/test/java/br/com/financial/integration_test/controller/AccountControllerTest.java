package br.com.financial.integration_test.controller;

import br.com.financial.integration_test.config.TestConfig;
import br.com.financial.integration_test.dto.UserAuthDTOTest;
import br.com.financial.integration_test.dto.UserDTOTest;
import br.com.financial.model.Account;
import br.com.financial.integration_test.testcontainer.AbstractIntegrationTest;
import br.com.financial.util.AccountTypeEnum;
import br.com.financial.util.StatusEnum;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Account account;
    private static Account accountTwo;
    private static Account accountToDelete;
    private static final Date EXPIRATION = new Date();
    private static final Date PAYMENT = new Date();
    private static UserDTOTest user;
    private static UserAuthDTOTest userAuth;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        account = new Account();
        accountTwo = new Account();
        accountToDelete = new Account();
        user = new UserDTOTest();
        userAuth = new UserAuthDTOTest();
    }

    @Test
    @Order(1)
    public void testGetUser() throws IOException {
        mockUser();
        specification = new RequestSpecBuilder()
            .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
            .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
            .setBasePath(TestConfig.URL_AUTH_SIGNIN_POST)
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content =
            given()
                    .spec(specification)
                    .contentType(TestConfig.CONTENT_TYPE_JSON)
                    .body(user)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .asString();
        userAuth = objectMapper.readValue(content, UserAuthDTOTest.class);
        assertNotNull(userAuth);
    }

    @Test
    @Order(2)
    public void testCreate() throws IOException {
        mockAccount();

        specification = new RequestSpecBuilder()
            .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
            .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
            .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
            .setBasePath(TestConfig.URL_ACCOUNT_POST)
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(account)
                    .when()
                        .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        account = objectMapper.readValue(content, Account.class);

        assertNotNull(account.getId());
        assertNotNull(account.getDescription());
        assertNotNull(account.getAccountType());
        assertNotNull(account.getStatus());
        assertNotNull(account.getExpirationDate());
        assertNotNull(account.getPaymentDate());

        assertTrue(account.getId() > 0);

        assertEquals("Uma conta", account.getDescription());
        assertEquals(AccountTypeEnum.TO_PAY, account.getAccountType());
        assertEquals(StatusEnum.CREATED, account.getStatus());
        assertEquals(EXPIRATION, account.getExpirationDate());
        assertEquals(PAYMENT, account.getPaymentDate());
        assertEquals(150.50, account.getPaymentValue());
    }

    @Test
    @Order(3)
    public void testCreateTwo() throws IOException {
        mockAccountTow();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
                .setBasePath(TestConfig.URL_ACCOUNT_POST)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(accountTwo)
                    .when()
                        .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        accountTwo = objectMapper.readValue(content, Account.class);

        assertNotNull(accountTwo.getId());
        assertNotNull(accountTwo.getDescription());
        assertNotNull(accountTwo.getAccountType());
        assertNotNull(accountTwo.getStatus());
        assertNotNull(accountTwo.getExpirationDate());
        assertNotNull(accountTwo.getPaymentDate());

        assertTrue(accountTwo.getId() > 0);

        assertEquals("Uma segunda conta", accountTwo.getDescription());
        assertEquals(AccountTypeEnum.TO_PAY, accountTwo.getAccountType());
        assertEquals(StatusEnum.APPROVED, accountTwo.getStatus());
        assertEquals(EXPIRATION, accountTwo.getExpirationDate());
        assertEquals(PAYMENT, accountTwo.getPaymentDate());
        assertEquals(259.12, accountTwo.getPaymentValue());
    }

    @Test
    @Order(4)
    public void testCreateToDelete() throws IOException {
        mockAccountToDelete();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
                .setBasePath(TestConfig.URL_ACCOUNT_POST)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(accountToDelete)
                    .when()
                        .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        accountToDelete = objectMapper.readValue(content, Account.class);

        assertNotNull(accountToDelete.getId());
        assertNotNull(accountToDelete.getDescription());
        assertNotNull(accountToDelete.getAccountType());
        assertNotNull(accountToDelete.getStatus());
        assertNotNull(accountToDelete.getExpirationDate());
        assertNotNull(accountToDelete.getPaymentDate());

        assertTrue(accountToDelete.getId() > 0);

        assertEquals("Uma conta para deletar", accountToDelete.getDescription());
        assertEquals(AccountTypeEnum.TO_PAY, accountToDelete.getAccountType());
        assertEquals(StatusEnum.CREATED, accountToDelete.getStatus());
        assertEquals(EXPIRATION, accountToDelete.getExpirationDate());
        assertEquals(PAYMENT, accountToDelete.getPaymentDate());
        assertEquals(200.40, accountToDelete.getPaymentValue());
    }

    @Test
    @Order(5)
    public void testUpdate() throws IOException {
        account.setDescription("Uma nova descrição");
        account.setPaymentValue(130.20);

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
                .setBasePath(TestConfig.URL_ACCOUNT_PUT)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(account)
                    .when()
                        .put()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        account = objectMapper.readValue(content, Account.class);

        assertNotNull(account.getId());
        assertNotNull(account.getDescription());
        assertNotNull(account.getAccountType());
        assertNotNull(account.getStatus());
        assertNotNull(account.getExpirationDate());
        assertNotNull(account.getPaymentDate());

        assertTrue(account.getId() > 0);

        assertEquals("Uma nova descrição", account.getDescription());
        assertEquals(AccountTypeEnum.TO_PAY, account.getAccountType());
        assertEquals(StatusEnum.CREATED, account.getStatus());
        assertEquals(EXPIRATION, account.getExpirationDate());
        assertEquals(PAYMENT, account.getPaymentDate());
        assertEquals(130.20, account.getPaymentValue());
    }

    @Test
    @Order(6)
    public void testGetFullPaid() {
        var formatter = new SimpleDateFormat("yyyy-MM-dd");
        var dateStartString = formatter.format(PAYMENT);
        var dateEndString = formatter.format(new Date());

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
                .addParam("paymentDateStart", dateStartString)
                .addParam("paymentDateEnd", dateEndString)
                .addParam("type", "TO_PAY")
                .setBasePath(TestConfig.URL_ACCOUNT_GET_FULL_PAID)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                    .when()
                        .get()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        assertFalse(content.isEmpty());
    }

    @Test
    @Order(7)
    public void testFindById() throws IOException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
                .setBasePath(TestConfig.URL_ACCOUNT_GET_ID)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                            .spec(specification)
                            .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .when()
                            .get()
                        .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

        account = objectMapper.readValue(content, Account.class);

        assertNotNull(account.getId());
        assertNotNull(account.getDescription());
        assertNotNull(account.getAccountType());
        assertNotNull(account.getStatus());
        assertNotNull(account.getExpirationDate());
        assertNotNull(account.getPaymentDate());

        assertTrue(account.getId() > 0);
    }

    @Test
    @Order(8)
    public void testDelete() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_CONTENT_TYPE, TestConfig.CONTENT_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
                .setBasePath(TestConfig.URL_ACCOUNT_DELETE)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                    .when()
                        .delete()
                    .then()
                        .statusCode(204)
                        .extract()
                        .body()
                        .asString();
        assertTrue(content.isEmpty());
    }

    @Test
    @Order(9)
    public void testImportCsvFile() {
        var file = new File("src/test/resources/products_to_pay.csv");

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ACCEPT, TestConfig.HEADER_PARAM_ACCEPT_ALL)
                .addHeader(TestConfig.HEADER_AUTHORIZATION, TestConfig.HEADER_AUTHORIZATION_BEARER + userAuth.getAcessToken())
                .setContentType(ContentType.MULTIPART)
                .setBasePath(TestConfig.URL_ACCOUNT_POST_IMPORT_CSV)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given()
                .spec(specification)
                .multiPart("file", file, "text/csv")
                .body(file)
            .when()
                .post()
            .then()
                .statusCode(200);
    }

    private void mockUser() {
        user.setUsername("paulo");
        user.setPassword("admin2024");
    }

    private void mockAccount() {
        account.setDescription("Uma conta");
        account.setAccountType(AccountTypeEnum.TO_PAY);
        account.setStatus(StatusEnum.CREATED);
        account.setExpirationDate(EXPIRATION);
        account.setPaymentDate(PAYMENT);
        account.setPaymentValue(150.50);
    }

    private void mockAccountTow() {
        accountTwo.setDescription("Uma segunda conta");
        accountTwo.setAccountType(AccountTypeEnum.TO_PAY);
        accountTwo.setStatus(StatusEnum.APPROVED);
        accountTwo.setExpirationDate(EXPIRATION);
        accountTwo.setPaymentDate(PAYMENT);
        accountTwo.setPaymentValue(259.12);
    }

    private void mockAccountToDelete() {
        accountToDelete.setDescription("Uma conta para deletar");
        accountToDelete.setAccountType(AccountTypeEnum.TO_PAY);
        accountToDelete.setStatus(StatusEnum.CREATED);
        accountToDelete.setExpirationDate(EXPIRATION);
        accountToDelete.setPaymentDate(PAYMENT);
        accountToDelete.setPaymentValue(200.40);
    }

}