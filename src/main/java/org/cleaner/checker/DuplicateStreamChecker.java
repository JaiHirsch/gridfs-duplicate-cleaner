package org.cleaner.checker;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DuplicateStreamChecker {
   
   Logger logger = LoggerFactory.getLogger(this.getClass());

   public boolean isDuplicateStream(InputStream is1, InputStream is2) {
      boolean isSame = true;
      int val1 = -1;
      int val2 = -1;
      do{
         try {
            val1 = is1.read();
            val2 = is2.read();
            if(val1 != val2) {
               isSame = false;
               break;
            }
         } catch (IOException e) {
            logger.warn(e.getMessage());
            isSame = false;
            break;
         }
         
      }while(val1 >=0);
      return isSame;
   }

}
