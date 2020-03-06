package com.marshmallow.anblog;

import java.util.Map;

public class LabelFilter {
  private final String key;
  private final String value;
  
  public LabelFilter(final String filter) throws IllegalArgumentException {
    int index = filter.indexOf('=');
    if (index == -1) {
      throw new IllegalArgumentException("cannot find equals in filter: " + filter);
    }
    key = filter.substring(0, index);
    value = filter.substring(index + 1);
  }
  
  public boolean containedIn(final Map<String, String> labels) {
    for (final Map.Entry<String, String> e : labels.entrySet()) {
      if (key.equals(e.getKey()) && value.equals(e.getValue())) {
        return true;
      }
    }
    return false;
  }
}
