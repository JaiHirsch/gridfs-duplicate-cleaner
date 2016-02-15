package org.cleaner.checker;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DuplicateStreamCheckerTest {

   DuplicateStreamChecker checker = new DuplicateStreamChecker();

   private static final String SAME_STRING = "This is the same string for testing";
   private static final String DIFFERENT_STRING = "This is a different string for testing";

   @Test
   public void returnTrueIfStreamssAreTheSame() {
      InputStream is1 = new ByteArrayInputStream(SAME_STRING.getBytes(StandardCharsets.UTF_8));
      InputStream is2 = new ByteArrayInputStream(SAME_STRING.getBytes(StandardCharsets.UTF_8));

      assertTrue(checker.isDuplicateStream(is1, is2));
   }

   @Test
   public void returnFalseIfStreamsAreDifferent() {
      InputStream is1 = new ByteArrayInputStream(SAME_STRING.getBytes(StandardCharsets.UTF_8));
      InputStream is2 = new ByteArrayInputStream(DIFFERENT_STRING.getBytes(StandardCharsets.UTF_8));

      assertFalse(checker.isDuplicateStream(is1, is2));
   }

   @Test
   public void returnFalseIfStreamThrowsAnExceptionOnRead() throws IOException {
      InputStream exceptionStream = mock(InputStream.class);
      when(exceptionStream.read()).thenThrow(new IOException());

      InputStream is2 = new ByteArrayInputStream(SAME_STRING.getBytes(StandardCharsets.UTF_8));

      assertFalse(checker.isDuplicateStream(exceptionStream, is2));
      
   }

}
