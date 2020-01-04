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
package edu.columbia.rdf.edb.experiments.app.sample;

import java.awt.BorderLayout;

import org.jebtk.modern.ModernWidget;
import org.jebtk.modern.text.ModernTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleDataTitlePanel.
 */
public class SampleDataTitlePanel extends ModernWidget {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new sample data title panel.
   *
   * @param text the text
   */
  public SampleDataTitlePanel(String text) {

    ModernTextField box = new ModernTextField(text);
    box.setFont(ModernWidget.TITLE_FONT);
    box.setEditable(false);

    add(box, BorderLayout.CENTER);

    setBorder(BORDER);

    // setCanvasSize(new Dimension(0, 50));

    // addMouseListener(this);

    // setup();
  }

  /*
   * public final void paintComponent(Graphics g) {
   * 
   * Graphics2D g2 = (Graphics2D)g;
   * 
   * g2.setColor(Color.WHITE);
   * 
   * g2.fillRect(0, 0, getWidth(), getHeight());
   * 
   * g2.setColor(DialogButton.DARK_COLOR);
   * 
   * int y = getHeight() - 1;
   * 
   * g2.drawLine(getInsets().left, y, getWidth() - getInsets().left -
   * getInsets().right - 1, y); }
   */
}
