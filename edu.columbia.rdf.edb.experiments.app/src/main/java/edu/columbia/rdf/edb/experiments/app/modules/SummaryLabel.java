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
package edu.columbia.rdf.edb.experiments.app.modules;

import java.awt.Color;

import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.theme.ThemeService;

/**
 * The Class SummaryLabel.
 */
public class SummaryLabel extends ModernAutoSizeLabel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The color1. */
  public static Color COLOR1 = ThemeService.getInstance().colors()
      .getGray32(24);

  /**
   * Instantiates a new summary label.
   *
   * @param text the text
   */
  public SummaryLabel(String text) {
    super(text, COLOR1);
  }
}
