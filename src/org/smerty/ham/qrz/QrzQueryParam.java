package org.smerty.ham.qrz;

public class QrzQueryParam {

  private String key;
  private String value;

  public QrzQueryParam(final String keyIn, final String valueIn) {
    super();
    this.key = keyIn;
    this.value = valueIn;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public void setKey(String keyIn) {
    this.key = keyIn;
  }

  public void setValue(String valueIn) {
    this.value = valueIn;
  }

}
