package br.com.financial.integration_test.config;

public class TestConfig {

    public static final int SERVER_PORT = 8080;
    public static final String HEADER_PARAM_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_PARAM_ACCEPT = "Accept";
    public static final String HEADER_PARAM_ACCEPT_ALL = "*/*";
    public static final String HEADER_AUTHORIZATION= "Authorization";
    public static final String HEADER_AUTHORIZATION_BEARER = "Bearer ";

    public static final String URL_ACCOUNT_POST = "/api/account/";
    public static final String URL_ACCOUNT_PUT = "/api/account/1";
    public static final String URL_ACCOUNT_GET_ID = "/api/account/1";
    public static final String URL_ACCOUNT_GET_FULL_PAID = "/api/account/getFullPaid";
    public static final String URL_ACCOUNT_POST_IMPORT_CSV = "/api/account/importCsvFile";
    public static final String URL_ACCOUNT_DELETE = "/api/account/3";
    public static final String URL_AUTH_SIGNIN_POST = "/auth/signin";
}
