/*
    This file is part of gridfs-duplicate-ckeaber.
    gridfs-duplicate-ckeaber is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    gridfs-duplicate-ckeaber is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with gridfs-duplicate-ckeaber.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cleaner.checker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

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
