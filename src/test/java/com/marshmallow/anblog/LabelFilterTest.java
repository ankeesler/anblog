package com.marshmallow.anblog;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class LabelFilterTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNoEquals() {
    new LabelFilter("nope");
  }
  
  @Test
  public void testEmptyValue() {
    final LabelFilter a = new LabelFilter("a=");
    final Map<String, String> m = new HashMap<String, String>();
    m.put("a", "b");
    m.put("a", "c");
    assertFalse(a.containedIn(m));
  }
  
  @Test
  public void testContainedIn() {
    final LabelFilter ab = new LabelFilter("a=b");
    final LabelFilter ac = new LabelFilter("a=c");
    final LabelFilter ba = new LabelFilter("b=a");
    
    final Map<String, String> m0 = new HashMap<String, String>();
    m0.put("a", "b");
    m0.put("b", "c");
    assertTrue(ab.containedIn(m0));
    assertFalse(ac.containedIn(m0));
    assertFalse(ba.containedIn(m0));
    
    final Map<String, String> m1 = new HashMap<String, String>();
    m1.put("a", "b");
    m1.put("a", "c");
    assertFalse(ab.containedIn(m1));
    assertTrue(ac.containedIn(m1));
    assertFalse(ba.containedIn(m1));
  }
}
