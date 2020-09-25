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
package edu.columbia.rdf.edb.experiments.app.search;

import java.awt.Graphics2D;

import org.jebtk.modern.ModernWidget;
import org.jebtk.modern.graphics.icons.LeftPane32VectorIcon;

/**
 * The Class ExperimentsPane32VectorIcon.
 */
public class ExperimentsPane32VectorIcon extends LeftPane32VectorIcon {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.graphics.icons.LeftPane32VectorIcon#drawIcon(java.awt.
   * Graphics2D, int, int, int, int)
   */
  @Override
  public void drawIcon(Graphics2D g2,
      int x,
      int y,
      int w,
      int h,
      Props props) {
    super.drawIcon(g2, x, y, w, h, props);

    x = x + (w - WIDTH) / 2 + 2;
    y = y + (h - HEIGHT) / 2 + BAR_HEIGHT + 2;

    int x2 = x + 3;

    int w1 = PANE_WIDTH - 5;
    int w2 = PANE_WIDTH - 8;

    g2.setColor(ModernWidget.LINE_COLOR);

    g2.drawLine(x, y, x + w1, y);

    y += 2;

    g2.drawLine(x2, y, x2 + w2, y);

    y += 2;

    g2.drawLine(x2, y, x2 + w2, y);

    y += 4;

    g2.drawLine(x, y, x + w1, y);

    y += 2;

    g2.drawLine(x2, y, x2 + w2, y);

    y += 2;

    g2.drawLine(x2, y, x2 + w2, y);
  }
}
