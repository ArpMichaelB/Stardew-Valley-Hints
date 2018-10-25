package com.amazonaws.lambda.ambrosia.stardewpuller.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;

import static com.amazon.ask.request.Predicates.requestType;

public class ExitHandler implements RequestHandler {

	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(SessionEndedRequest.class));
	}

	public Optional<Response> handle(HandlerInput input) {
		return input.getResponseBuilder().withSpeech("Goodbye! Come back soon!").withShouldEndSession(true).withSimpleCard("Stardew Valley Hints", "Goodbye! Come back soon!").build();
	}

}
