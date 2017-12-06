package com.amazonaws.lambda.getdistributorlist;

import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.APIUtil;

public class GetDistributorsList implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	context.getLogger().log("Input: " + input);
        try{        	
        	context.getLogger().log("DEBUG::From production");
        	JSONObject parameters=new JSONObject(input.toString());
        	return APIUtil.getDistributorsList(parameters).toString();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return "Unable to process the request. "+input;
    }

}
