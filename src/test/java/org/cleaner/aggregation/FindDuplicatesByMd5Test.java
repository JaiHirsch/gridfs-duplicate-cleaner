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
package org.cleaner.aggregation;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.bson.Document;
import org.cleaner.domain.DuplicateStrategy;
import org.cleaner.mongo.FindDuplicatesByMd5;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class FindDuplicatesByMd5Test {

   private FindDuplicatesByMd5 findDups;

   @Mock
   DuplicateStrategy mockStrategy;

   @Before
   public void setup() {
      mockStrategy = mock(DuplicateStrategy.class);
      findDups = new FindDuplicatesByMd5(mockStrategy, null);
   }

   @After
   public void teardown() {
      findDups = null;
   }

   @Test
   public void strategyNotInvokedIfNoDupesFound() {
      MongoCollection<Document> mockCollection = mock(MongoCollection.class);
      MongoCursor<Document> mockCursor = mock(MongoCursor.class);
      AggregateIterable<Document> mockIter = mock(AggregateIterable.class);
      when(mockCollection.aggregate(any(List.class))).thenReturn(mockIter);
      when(mockIter.iterator()).thenReturn(mockCursor);
      when(mockCursor.hasNext()).thenReturn(false);

      findDups.find(mockCollection);
      verify(mockStrategy, never()).handleDuplicates(any(Document.class), null);

   }

   @Test
   public void strategyInvokedWhenDupesFound() {
      MongoCollection<Document> mockCollection = mock(MongoCollection.class);
      MongoCursor<Document> mockCursor = mock(MongoCursor.class);
      AggregateIterable<Document> mockIter = mock(AggregateIterable.class);
      when(mockCollection.aggregate(any(List.class))).thenReturn(mockIter);
      when(mockIter.iterator()).thenReturn(mockCursor);
      when(mockCursor.hasNext()).thenReturn(true).thenReturn(false);
      when(mockCursor.next()).thenReturn(new Document("hi", "there"));
      Mockito.doNothing().when(mockStrategy).handleDuplicates(any(Document.class), null);

      findDups.find(mockCollection);
      verify(mockStrategy, times(1)).handleDuplicates(any(Document.class), null);
   }

}
