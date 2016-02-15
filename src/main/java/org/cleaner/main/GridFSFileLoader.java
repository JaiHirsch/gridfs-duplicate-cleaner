package org.cleaner.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;

public class GridFSFileLoader {

   private static final String DIRECTORY_TO_LOAD = "./";

   @SuppressWarnings("deprecation")
   public static void main(String[] args) throws IOException {
      try(MongoClient mc = new MongoClient()) {
         DB db = mc.getDB("gridfs");
         GridFS gfs = new GridFS(db);
         try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(DIRECTORY_TO_LOAD))) {
            for (Path entry: stream) {
                File file = entry.toFile();
               if(file.isDirectory()) continue;
               else gfs.createFile(file).save();
            }
        }
         
      }

   }

}
