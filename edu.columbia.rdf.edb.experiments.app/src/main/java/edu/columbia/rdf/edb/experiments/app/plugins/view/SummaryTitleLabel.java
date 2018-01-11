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
package edu.columbia.rdf.edb.experiments.app.plugins.view;

import java.awt.Font;

import org.jebtk.core.settings.SettingsService;
import org.jebtk.modern.font.FontService;
import org.jebtk.modern.text.ModernAutoSizeLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class SummaryTitleLabel.
 */
public class SummaryTitleLabel extends ModernAutoSizeLabel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant FONT. */
  private static final Font FONT = FontService.getInstance()
      .loadFont(
          SettingsService.getInstance()
              .getAsString("theme.widget.fonts.medium-text.family"),
          14);

  /**
   * Instantiates a new summary title label.
   *
   * @param text the text
   */
  public SummaryTitleLabel(String text) {
    super(text, FONT);
  }
}
