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

import org.jebtk.core.search.SearchStackOperator;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernDropDownMenuLabelButton;
import org.jebtk.modern.menu.ModernMenuItem;
import org.jebtk.modern.menu.ModernPopupMenu;

// TODO: Auto-generated Javadoc
/**
 * Specialised combobox for showing selecting search criteria.
 *
 * @author Antony Holmes Holmes
 */
public class OperatorComboButton extends ModernDropDownMenuLabelButton {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant WIDTH. */
  public static final int WIDTH = 48;

  /** The Constant SIZE. */
  private static final Dimension SIZE = new Dimension(WIDTH, WIDGET_HEIGHT);

  /**
   * Instantiates a new operator combo button.
   *
   * @param text
   *          the text
   */
  public OperatorComboButton(String text) {
    super(text);

    setup();
  }

  /**
   * Instantiates a new operator combo button.
   *
   * @param booleanOperator
   *          the boolean operator
   */
  public OperatorComboButton(SearchStackOperator booleanOperator) {
    this(booleanOperator.toString());
  }

  /**
   * Setup.
   */
  private void setup() {
    ModernPopupMenu menu = new ModernPopupMenu();

    ModernMenuItem menuItem = new ModernMenuItem("AND");
    UI.setSize(menuItem, SIZE);
    menu.add(menuItem);
    menuItem = new ModernMenuItem("OR");
    UI.setSize(menuItem, SIZE);
    menu.add(menuItem);

    setMenu(menu);

    UI.setSize(this, SIZE);
  }
}
