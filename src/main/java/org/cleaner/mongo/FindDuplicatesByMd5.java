package org.cleaner.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.cleaner.domain.DuplicateStrategy;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.util.JSON;

// db.fs.files.aggregate([{$group:{_id:'$md5',count:{$sum:1},ids:{$push:'$_id'}}},{$match:{count:{$gte:2}}}])


public class FindDuplicatesByMd5 {

   private static final String GROUP_JSON = "{$group:{_id:'$md5',count:{$sum:1},ids:{$push:'$_id'}}}'";
   private static final String MATCH_JSON = "{$match:{count:{$gte:2}}}";
   
   private final DuplicateStrategy duplicateStrategy;
   private final GridFS gridFS;

   public FindDuplicatesByMd5(DuplicateStrategy strategy, GridFS gridFS) {
      this.duplicateStrategy = strategy;
      this.gridFS = gridFS;
   }

   public void find(MongoCollection<Document> collection) {
     
      writeOutDuplicates(collection.aggregate(getPipeline()));
      
   }
   
   @SuppressWarnings("unchecked")
   private List<Document> getPipeline() {
      List<Document> pipeline = new ArrayList<>();
      
      pipeline.add(new Document( (Map<String, Object>) JSON.parse(GROUP_JSON)));
      pipeline.add(new Document( (Map<String, Object>) JSON.parse(MATCH_JSON)));
      
      return pipeline;
   }

   private void writeOutDuplicates(AggregateIterable<Document> aggregate) {
      MongoCursor<Document> iterator = aggregate.iterator();
      while(iterator.hasNext()) {
         duplicateStrategy.handleDuplicates(iterator.next(), gridFS);
      }
   }

}
