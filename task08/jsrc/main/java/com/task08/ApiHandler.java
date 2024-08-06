package com.task08;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "api_handler",
	roleName = "api_handler-role",
	isPublishVersion = false,
	aliasName = "${lambdas_alias_name}",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class ApiHandler implements RequestHandler<Object, Map<String, Object>> {

	private OpenMeteoClient weatherClient = new OpenMeteoClient();

	@Override
	public Map<String, Object> handleRequest(Object request, Context context) {
		Map<String, Object> response = new HashMap<>();
		try {
			String weatherData = weatherClient.getWeatherData();
			response.put("statusCode", 200);
			response.put("body", weatherData);
		} catch (Exception e) {
			response.put("statusCode", 500);
			response.put("body", "Error fetching weather data");
		}
		return response;
	}
}