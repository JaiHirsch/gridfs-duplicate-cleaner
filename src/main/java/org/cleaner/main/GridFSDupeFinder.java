package org.cleaner.main;

import org.cleaner.domain.DuplicateLoggerStrategy;
import org.cleaner.mongo.FindDuplicatesByMd5;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;

public class GridFSDupeFinder {

   public static void main(String[] args) {
      try(MongoClient mc = new MongoClient()) {
         MongoDatabase database = mc.getDatabase("gridfs");
         FindDuplicatesByMd5 fd = new FindDuplicatesByMd5(new DuplicateLoggerStrategy(),new GridFS(mc.getDB("gridfs")));
         fd.find(database.getCollection("fs.files"));
      }
      
   }

}
