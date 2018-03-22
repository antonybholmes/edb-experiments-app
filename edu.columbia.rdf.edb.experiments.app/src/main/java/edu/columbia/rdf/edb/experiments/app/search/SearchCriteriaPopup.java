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

import org.jebtk.modern.UIService;
import org.jebtk.modern.menu.ModernIconMenuItem;
import org.jebtk.modern.menu.ModernScrollPopupMenu2;

// TODO: Auto-generated Javadoc
/**
 * Specialised combobox for showing search criteria. This class should be
 * subclassed and not used directly.
 *
 * @author Antony Holmes Holmes
 *
 */
public class SearchCriteriaPopup extends ModernScrollPopupMenu2 {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant MENU_ITEM_SIZE. */
  public static final Dimension MENU_ITEM_SIZE = new Dimension(400,
      UIService.ICON_SIZE_48);

  /** The Constant MAX_HEIGHT. */
  public static final int MAX_HEIGHT = ModernIconMenuItem.HEIGHT * 15;

  /**
   * Instantiates a new search criteria popup.
   */
  public SearchCriteriaPopup() {
    super(MAX_HEIGHT);
  }
}
