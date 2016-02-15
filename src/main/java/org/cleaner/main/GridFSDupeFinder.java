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

import org.cleaner.domain.DuplicateLoggerStrategy;
import org.cleaner.mongo.FindDuplicatesByMd5;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;

public class GridFSDupeFinder {

   public static void main(String[] args) {
      try (MongoClient mc = new MongoClient()) {
         MongoDatabase database = mc.getDatabase("gridfs");
         FindDuplicatesByMd5 fd = new FindDuplicatesByMd5(new DuplicateLoggerStrategy(),
               new GridFS(mc.getDB("gridfs")));
         fd.find(database.getCollection("fs.files"));
      }

   }

}
