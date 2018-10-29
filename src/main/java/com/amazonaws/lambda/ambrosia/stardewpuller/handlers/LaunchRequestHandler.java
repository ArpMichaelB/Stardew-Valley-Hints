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
		String speechText = "Welcome to Stardew Valley Hints. Try asking me to search the Stardew Wiki for an item!";
        String repromptText = "What would you like to search for?.";
        return input.getResponseBuilder()
                .withSimpleCard("Stardew Valley Hints", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .build();
	}

}
