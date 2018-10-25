package com.amazonaws.lambda.ambrosia.stardewpuller.handlers;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

//I'm borrowing this from the sample code

public class FallbackIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Sorry, I don't know that. Try again?";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Stardew Valley Hints", speechText)
                .withReprompt(speechText)
                .build();
    }

}