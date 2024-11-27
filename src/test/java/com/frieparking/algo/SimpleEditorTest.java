package com.frieparking.algo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleEditorTest {
  private SimpleEditor editor;

  @BeforeEach
  public void setup() {
    editor = new SimpleEditor();
  }

  @Test
  void testBasicCommands() {
    List<String> commands = Arrays.asList(
        "append abc",
        "append def",
        "backspace",
        "append xy"
    );

    List<String> expected = Arrays.asList(
        "abc",
        "abcdef",
        "abcde",
        "abcdexy"
    );

    assertEquals(expected, editor.processCommands(commands));
  }

}
