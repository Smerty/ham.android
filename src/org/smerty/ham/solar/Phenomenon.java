package org.smerty.ham.solar;

public class Phenomenon {
  private String location;
  private String name;
  private String value;

  public String getLocation() {
    return this.location;
  }

  public void setLocation(final String locationIn) {
    this.location = locationIn;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String nameIn) {
    this.name = nameIn;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(final String valueIn) {
    this.value = valueIn;
  }
}
