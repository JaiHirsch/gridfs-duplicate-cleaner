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
