package com.ale.ovng.infra.exercise.conroller;


import com.ale.ovng.infra.exercise.service.RESTAPI;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sites")
@Produces(MediaType.TEXT_PLAIN)
public class Aggregator
{

    @Operation(
            summary = "Get devices associated with given site id",
            description = "This returns all the devices that are present in the site"
                    + " in the specified time period."
    )

    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "Device not found"),
                    @APIResponse(
                            responseCode = "200",
                            description = "Devices found with siteId",
                            content = @Content(mediaType = "application/json"))})

    @Parameter(
            name = "siteId",
            description = "The id of the site where the devices are deployed.",
            required = true,
            schema = @Schema(type = SchemaType.STRING))

    @Parameter(
            name = "startperiod",
            description = "The starting period expressed in milliseconds.",
            required = true,
            schema = @Schema(type = SchemaType.INTEGER))

    @Parameter(
            name = "endperiod",
            description = "The ending period expressed in milliseconds.",
            required = true,
            schema = @Schema(type = SchemaType.INTEGER))

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{siteId}/qoe/timetoconnect")
    public Response getAvg(@PathParam("siteId") String siteId,
                           @QueryParam("startperiod") String speriod,
                           @QueryParam("endperiod") String eperiod) {
        double[] res= RESTAPI.findDev(siteId, speriod,eperiod);
        JSONObject result=new JSONObject();
        if (res.length<3){
            return  Response
                    .status(404)
                    .entity(result).build();
        }
        else{
        result.put("deviceQuantity",res[0]);
        result.put("avgTimeToConnect",res[1]);
        }

        return  Response
                .status(200)
                .entity(result).build();
    }

}



