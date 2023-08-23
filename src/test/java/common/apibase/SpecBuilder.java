package common.apibase;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class SpecBuilder {

    public static RequestSpecification getAuthRequestSpec(String URI){
        return (new RequestSpecBuilder())
                .setBaseUri(URI)
                .setContentType(ContentType.JSON)
                .build();
    }


}
