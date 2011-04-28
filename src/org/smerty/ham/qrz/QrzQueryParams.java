package org.smerty.ham.qrz;

import java.util.ArrayList;
import java.util.List;

public class QrzQueryParams {

  private List<QrzQueryParam> params;

  public QrzQueryParams() {
    // TODO Auto-generated constructor stub
    this.params = new ArrayList<QrzQueryParam>();
  }

  public QrzQueryParams(List<QrzQueryParam> paramList) {
    // TODO Auto-generated constructor stub
    this.params = paramList;
  }

  public boolean add(QrzQueryParam param) {
    return this.params.add(param);
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();

    for (int n = 0; n < this.params.size(); n++) {
      output.append(this.params.get(n).getKey()).append("=")
          .append(this.params.get(n).getValue());
      if (n != this.params.size() - 1) {
        output.append(";");
      }
    }

    return output.toString();
  }

}
