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

import java.awt.Color;
import java.awt.Graphics2D;

import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernChipButton;
import org.jebtk.modern.theme.ThemeService;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterButton.
 */
public class FilterButton extends ModernChipButton {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new filter button.
   *
   * @param text1
   *          the text 1
   */
  public FilterButton(String text1) {
    this(text1, false);
  }

  /**
   * Instantiates a new filter button.
   *
   * @param text1
   *          the text 1
   * @param selected
   *          the selected
   */
  public FilterButton(String text1, boolean selected) {
    super(text1, selected);

    setForeground(ThemeService.getInstance().colors().getHighlight32(24));
  }
}
