package constant;

import utilities.StringUtils;

import java.time.Instant;

public class Constants {
    public static final String API_URL = "https://anduongapi2.atlassian.net/";
    public static final String BASE_URL = "https://api.atlassian.com/ex/jira/%s";
    public static final String CLOUD_ID_URI = "https://api.atlassian.com/oauth/token/accessible-resources";
    public static final String TOKEN_URI = "https://auth.atlassian.com/oauth/token";

    public static final String TEMPLATE_DIRECTORY = StringUtils.getCurrentDir() + "src/test/resources/template/template.json";
    public static String AUTHORIZATION_CODE = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4YzI1NzlmNy00YzZlLTRmZTgtOWI5Yy1jOGI2MDNhZDQ2OTciLCJzdWIiOiI2MjE2NTc2YTZiN2UyNDAwNmE3YTY4NzQiLCJuYmYiOjE2OTMzNzgzMzMsImlzcyI6ImF1dGguYXRsYXNzaWFuLmNvbSIsImlhdCI6MTY5MzM3ODMzMywiZXhwIjoxNjkzMzc4NjMzLCJhdWQiOiJTTDdJSWVJaGFTVWhCdmJlQTFlMTUwUzYxTTJ4cjluSCIsImNsaWVudF9hdXRoX3R5cGUiOiJQT1NUIiwiaHR0cHM6Ly9pZC5hdGxhc3NpYW4uY29tL3ZlcmlmaWVkIjp0cnVlLCJodHRwczovL2lkLmF0bGFzc2lhbi5jb20vdWp0IjoiOGMyNTc5ZjctNGM2ZS00ZmU4LTliOWMtYzhiNjAzYWQ0Njk3Iiwic2NvcGUiOlsibWFuYWdlOmppcmEtcHJvamVjdCIsIm1hbmFnZTpqaXJhLWNvbmZpZ3VyYXRpb24iLCJtYW5hZ2U6amlyYS1kYXRhLXByb3ZpZGVyIiwicmVhZDpqaXJhLXdvcmsiLCJtYW5hZ2U6amlyYS13ZWJob29rIiwid3JpdGU6amlyYS13b3JrIiwicmVhZDpqaXJhLXVzZXIiXSwiaHR0cHM6Ly9pZC5hdGxhc3NpYW4uY29tL2F0bF90b2tlbl90eXBlIjoiQVVUSF9DT0RFIiwiaHR0cHM6Ly9pZC5hdGxhc3NpYW4uY29tL2hhc1JlZGlyZWN0VXJpIjp0cnVlLCJodHRwczovL2lkLmF0bGFzc2lhbi5jb20vc2Vzc2lvbl9pZCI6ImViZTQzZGJjLTJhZjMtNGM4OC04Y2MxLWUwMzg3MTY1ZGQ1NyIsImh0dHBzOi8vaWQuYXRsYXNzaWFuLmNvbS9wcm9jZXNzUmVnaW9uIjoidXMtZWFzdC0xIn0.tFN3yznsxzWsyVkQPt8bTkLZ7mxAGFE5csEosVsTvUs";
    //Debug
    public static String AUTH_RESPONSE;
    //Debug
    public static String CLOUD_ID_RESPONSE;
}
