package com.amazonaws.lambda.ambrosia.stardewpuller;


import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazonaws.lambda.ambrosia.stardewpuller.handlers.*;

public class StardewStreamHandler extends SkillStreamHandler {
	
	private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new SearchWikiIntentHandler(),
                        new CancelandStopIntentHandler(),
                        new FallbackIntentHandler(),
                        new LaunchRequestHandler(),
                        new HelpHandler(),
                        new ExitHandler())
                .withSkillId("definitely-a-skill-id")
                .build();
    }

    public StardewStreamHandler() {
        super(getSkill());
    }

}
