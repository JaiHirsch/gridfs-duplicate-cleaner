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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.gridfs.GridFSDBFile;

public class DuplicateLoggerStrategy implements DuplicateStrategy {
   Logger logger = LoggerFactory.getLogger(this.getClass());

   public void hashCollisionStrategy(GridFSDBFile findFile1, GridFSDBFile findFile2) {
      logger.info("hash collision: "+findFile1.getId()+" "+findFile2.getId());
   }

   public void duplicateStrategy(GridFSDBFile findFile1, GridFSDBFile findFile2) {
      
      logger.info("duplicates: "+findFile1.getId()+" "+findFile2.getId());
   }

}
