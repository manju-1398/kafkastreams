package org.acme.rest.json;

import java.util.Collections;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.info.Contact;
import org.eclipse.microprofile.openapi.models.info.License;
import org.eclipse.microprofile.openapi.models.info.Info;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;

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
                OASFactory.createObject(Info.class).title("Fruit API").version("1.0")
                        .description(
                                "App for performing operations on fruits")
                        .contact(
                                OASFactory.createObject(Contact.class).name("Suraj")
                                .email("suraj.gudimetla@al-enterprise.com")
                        )
                        .license(
                                OASFactory.createObject(License.class)
                                        .name("Eclipse Public License - v 1.0").url(
                                        "https://www.eclipse.org/legal/epl-v10.html")));

        openAPI.addServer(
                OASFactory.createServer()
                        .url("http://localhost:{port}")
                        .description("Development Server")
                        .variables(Collections.singletonMap("port",
                                OASFactory.createServerVariable()
                                        .defaultValue("8080")
                                        .description("Server HTTP port."))));
    }

}