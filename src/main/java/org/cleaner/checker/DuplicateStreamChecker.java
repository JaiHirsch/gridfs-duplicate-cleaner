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
      do {
         try {
            val1 = is1.read();
            val2 = is2.read();
            if (val1 != val2) {
               isSame = false;
               break;
            }
         } catch (IOException e) {
            logger.warn(e.getMessage());
            isSame = false;
            break;
         }

      } while (val1 >= 0);
      return isSame;
   }

}
