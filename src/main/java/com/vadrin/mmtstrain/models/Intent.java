package com.vadrin.mmtstrain.models;

import java.util.Map;

public class Intent {

  private IntentName intentName;
  private Map<String, String> slots;

  public IntentName getName() {
    return intentName;
  }

  public void setName(IntentName intentName) {
    this.intentName = intentName;
  }

  public Map<String, String> getSlots() {
    return slots;
  }

  public void setSlots(Map<String, String> slots) {
    this.slots = slots;
  }

  public Intent(IntentName intentName, Map<String, String> slots) {
    super();
    this.intentName = intentName;
    this.slots = slots;
  }

}
