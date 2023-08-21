package utilities;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import common.report.ExtentReportManager;

import java.io.IOException;

public class JacksonObjectUtils {
public static void connectJacksonObjectMapperToUnirest(){
    Unirest.setObjectMapper(new ObjectMapper() {
        private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        @Override
        public <T> T readValue(String valueKey, Class<T> valueType) {
            try {
                return jacksonObjectMapper.readValue(valueKey, valueType);
            } catch (IOException exception){
                ExtentReportManager.logFailure("[function]: connectJacksonObjectMapperToUnirest() throws an exception: "
                        + exception.getMessage());

                throw new RuntimeException(exception);
            }
        }

        @Override
        public String writeValue(Object value) {
            try{
                return jacksonObjectMapper.writeValueAsString(value);
            } catch (Exception exception){
                ExtentReportManager.logFailure("[function]: connectJacksonObjectMapperToUnirest() throws an exception: "
                        + exception.getMessage());
                throw new RuntimeException(exception);
            }
        }
    });
}
}
