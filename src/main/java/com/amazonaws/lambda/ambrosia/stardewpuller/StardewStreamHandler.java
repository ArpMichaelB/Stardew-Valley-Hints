package com.amazonaws.lambda.ambrosia.stardewpuller;


import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazonaws.lambda.ambrosia.stardewpuller.handlers.*;

public class StardewStreamHandler extends SkillStreamHandler {
	
	private static Skill getSkill() {
		//TODO: update interaction model to be the better^tm
        return Skills.standard()
                .addRequestHandlers(
                        new SearchWikiIntentHandler(),
                        new CancelandStopIntentHandler(),
                        new FallbackIntentHandler(),
                        new LaunchRequestHandler())
                .withSkillId("definitely-a-real-skill-id")
                .build();
    }

    public StardewStreamHandler() {
        super(getSkill());
    }

}
