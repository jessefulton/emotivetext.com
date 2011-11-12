package com.eklekt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import synesketch.emotion.*;

public class EmotionalStateWrapper {
	
	private double generalWeight = 0;
	private double valence = 0;
	private String text;
	private HashMap<String, Double> emotions;
	
	public static EmotionalStateWrapper feel(String text) throws IOException {
		Empathyscope empathyScope = Empathyscope.getInstance();
		EmotionalState state = empathyScope.feel(text);
		
		return new EmotionalStateWrapper(state);
	}
	
	private EmotionalStateWrapper(EmotionalState state) {
		this.text = state.getText();
		this.emotions = new HashMap<String, Double>();
		this.generalWeight = state.getGeneralWeight();
		this.valence = state.getValence();
		//let's get all of the emotions generated (10 is more than max possible)
		List<Emotion> emotions = state.getFirstStrongestEmotions(10);
		for(Emotion emotion : emotions) {
			if (emotion.getType() == Emotion.ANGER) {
				this.emotions.put("anger", state.getAngerWeight());
			}
			else if (emotion.getType() == Emotion.DISGUST) {
				this.emotions.put("disgust", state.getDisgustWeight());
			}
			else if (emotion.getType() == Emotion.FEAR) {
				this.emotions.put("fear", state.getFearWeight());
			}
			else if (emotion.getType() == Emotion.HAPPINESS) {
				this.emotions.put("happiness", state.getHappinessWeight());
			}
			//else if (emotion.getType() == Emotion.NEUTRAL) {}
			else if (emotion.getType() == Emotion.SADNESS) {
				this.emotions.put("sadness", state.getSadnessWeight());
			}
			else if (emotion.getType() == Emotion.SURPRISE) {
				this.emotions.put("surprise", state.getSurpriseWeight());
			}
		}

		
	}
	
	
	
}
