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
