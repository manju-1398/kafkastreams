package com.ale.ovng.infra.exercise.conroller;


import com.ale.ovng.infra.exercise.Service.RESTAPI;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sites")
@Produces(MediaType.TEXT_PLAIN)
public class Aggregator
{


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



