/**
 * Copyright 2016 Antony Holmes
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
package edu.columbia.rdf.edb.experiments.app;

import java.awt.Color;

import org.jebtk.modern.ColorTheme;
import org.jebtk.modern.graphics.icons.ModernApplicationIcon;
import org.jebtk.modern.theme.ThemeService;

/**
 * The Class ExperimentsIcon.
 */
public class ExperimentsIcon extends ModernApplicationIcon {

  /** The Constant COLOR2. */
  private static final Color COLOR2 = ThemeService.getInstance().getColors()
      .getBlueTheme().getColor(2);

  /** The Constant COLOR1. */
  private static final Color COLOR1 = ThemeService.getInstance().getColors()
      .getBlueTheme().getColor(6);

  /**
   * Instantiates a new experiments icon.
   */
  public ExperimentsIcon() {
    super("E", "x", ColorTheme.BLUE);
  }
}
