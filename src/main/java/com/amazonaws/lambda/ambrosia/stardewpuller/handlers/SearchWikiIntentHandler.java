package com.amazonaws.lambda.ambrosia.stardewpuller.handlers;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazonaws.lamda.ambrosia.stardewpuller.backend.WikiPuller;

import static com.amazon.ask.request.Predicates.intentName;

public class SearchWikiIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("SearchWikiIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		try {
			Request request = input.getRequestEnvelope().getRequest();
			IntentRequest intentRequest = (IntentRequest) request;
			Intent intent = intentRequest.getIntent();
			Map<String, Slot> slots = intent.getSlots();
			Slot itemNameSlot = slots.get("ItemName");
			if(itemNameSlot != null)
			{
				String toFind = itemNameSlot.getValue();
				String speechText = WikiPuller.getDescription(toFind);
				return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("Stardew Wiki", speechText).build();
			}
			else
			{
				return input.getResponseBuilder().withSpeech("I'm not sure what you want to hear about!").withSimpleCard("Stardew Wiki", "Try again!").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return input.getResponseBuilder().withSpeech("I'm having trouble, sorry!")
					.withSimpleCard("Stardew Wiki",
							"I'm having trouble, sorry! Email glaciernester@gmail.com that he's got work to do")
					.build();
		}
	}

}
