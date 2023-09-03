package com.vadrin.mmtstrain.models;

import java.util.Map;

public class Intent {

  private IntentName intentName;
  private Map<String, String> info;

  public IntentName getName() {
    return intentName;
  }

  public void setName(IntentName intentName) {
    this.intentName = intentName;
  }

  public Map<String, String> getInfo() {
    return info;
  }

  public void setInfo(Map<String, String> info) {
    this.info = info;
  }

  public Intent(IntentName intentName, Map<String, String> info) {
    super();
    this.intentName = intentName;
    this.info = info;
  }

}
