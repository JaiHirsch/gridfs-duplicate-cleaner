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
package org.cleaner.domain;

import java.io.InputStream;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.cleaner.checker.DuplicateStreamChecker;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public interface DuplicateStrategy {

   public void hashCollisionStrategy(GridFSDBFile findFile1, GridFSDBFile findFile2);

   public void duplicateStrategy(GridFSDBFile findFile1, GridFSDBFile findFile2);

   default public boolean checkMd5Collision(InputStream is1, InputStream is2) {
      return new DuplicateStreamChecker().isDuplicateStream(is1, is2);
   }

   @SuppressWarnings("unchecked")
   default public void handleDuplicates(Document t, GridFS gridFS) {
      List<ObjectId> ids = (List<ObjectId>) t.get("ids");

      for (int i = 0; i < ids.size() - 1; i++) {
         GridFSDBFile findFile1 = gridFS.find(ids.get(i));
         InputStream is1 = findFile1.getInputStream();
         GridFSDBFile findFile2 = gridFS.find(ids.get(i + 1));
         InputStream is2 = findFile2.getInputStream();
         if (new DuplicateStreamChecker().isDuplicateStream(is1, is2)) {
            duplicateStrategy(findFile1, findFile2);
         } else {
            hashCollisionStrategy(findFile1, findFile2);
         }

      }
   }

}
