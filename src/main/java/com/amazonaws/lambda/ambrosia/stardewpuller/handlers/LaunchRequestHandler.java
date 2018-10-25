package com.amazonaws.lambda.ambrosia.stardewpuller.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText = "Welcome to Stardew Valley Hints.";
        String repromptText = "Please tell me what you want to learn about.";
        return input.getResponseBuilder()
                .withSimpleCard("Stardew Valley Hints", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .build();
	}

}
