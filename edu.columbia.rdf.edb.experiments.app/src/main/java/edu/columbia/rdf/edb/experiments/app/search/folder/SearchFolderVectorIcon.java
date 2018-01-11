/**
 * Copyright 2017 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.columbia.rdf.edb.experiments.app.search.folder;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jebtk.modern.graphics.icons.FolderVectorIcon;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchFolderVectorIcon.
 */
public class SearchFolderVectorIcon extends FolderVectorIcon {

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.graphics.icons.FolderVectorIcon#drawIcon(java.awt.
   * Graphics2D, int, int, int, int)
   */
  @Override
  public void drawIcon(Graphics2D g2,
      int x,
      int y,
      int w,
      int h,
      Object... params) {
    super.drawIcon(g2, x, y, w, h, params);

    double hf = h * HEIGHT_SCALE * 0.5;
    double wf = hf;

    double xf = x + (w - wf) / 2.0;
    double yf = y + (h - hf) / 2;

    g2.setColor(Color.WHITE);

    g2.drawOval((int) xf, (int) yf, (int) (wf * 0.8), (int) (wf * 0.8));

    g2.drawLine((int) (xf + wf * 0.8),
        (int) (yf + wf * 0.8),
        (int) (xf + wf),
        (int) (yf + wf));
  }
}
