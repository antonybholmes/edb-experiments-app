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

import java.awt.Dimension;

import org.jebtk.modern.UI;
import org.jebtk.modern.text.ModernLabelBold;

/**
 * The Class SummaryTitleLabel.
 */
public class SummaryTitleLabel extends ModernLabelBold {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new summary title label.
   *
   * @param text the text
   */
  public SummaryTitleLabel(String text) {
    super(text);

    UI.setSize(this, new Dimension(Short.MAX_VALUE, 24));
  }
}
