package com.amazonaws.lambda.ambrosia.stardewpuller.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazonaws.lamda.ambrosia.stardewpuller.backend.Constants;

import static com.amazon.ask.request.Predicates.intentName;

public class HelpHandler implements RequestHandler {

	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.HelpIntent"));
	}

	public Optional<Response> handle(HandlerInput input) {
		String speechText = Constants.helpMessage;
		String repromptText = Constants.helpResponse;
		return input.getResponseBuilder()
                .withSimpleCard("Stardew Valley Hints", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .build();
	}

}
