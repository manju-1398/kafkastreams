package com.ale.ovng.infra.exercise;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.info.Contact;
import org.eclipse.microprofile.openapi.models.info.Info;
import org.eclipse.microprofile.openapi.models.info.License;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;

import java.util.Collections;

public class InventoryOASFilter implements OASFilter {

    @Override
    public APIResponse filterAPIResponse(APIResponse apiResponse) {
        if ("Missing description".equals(apiResponse.getDescription())) {
            apiResponse.setDescription("Invalid hostname or the system service may not "
                    + "be running on the particular host.");
        }
        return apiResponse;
    }

    @Override
    public void filterOpenAPI(OpenAPI openAPI) {
        openAPI.setInfo(
                OASFactory.createObject(Info.class).title("Infra Train API").version("1.0")
                        .description(
                                "An API which helps to get QoE data of devices present in a specified site in a given time period"
                        ));

        openAPI.addServer(
                OASFactory.createServer()
                        .url("http://qoe_monitoring_ms_url:8080")
                        .description("Development Server")
                        );
    }

}