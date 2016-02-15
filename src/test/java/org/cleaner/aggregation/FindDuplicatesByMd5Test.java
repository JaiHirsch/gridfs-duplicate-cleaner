package org.cleaner.aggregation;



import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

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
      when(mockCursor.next()).thenReturn(new Document("hi","there"));
      Mockito.doNothing().when(mockStrategy).handleDuplicates(any(Document.class), null);
      
      findDups.find(mockCollection);
      verify(mockStrategy, times(1)).handleDuplicates(any(Document.class), null);
   }

}
