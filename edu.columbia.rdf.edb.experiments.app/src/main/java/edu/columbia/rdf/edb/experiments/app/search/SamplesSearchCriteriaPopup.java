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
import java.util.ArrayList;
import java.util.List;

import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.menu.ModernMenuItem;
import org.jebtk.modern.menu.ModernTitleMenuItem;
import org.jebtk.modern.menu.ModernTwoLineMenuItem;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.ui.search.SearchCategory;
import edu.columbia.rdf.edb.ui.search.SearchCategoryGroup;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;

// TODO: Auto-generated Javadoc
/**
 * Specialised combobox for showing search criteria relevant for search through
 * samples within a particular experiment.
 *
 * @author Antony Holmes Holmes
 *
 */
public class SamplesSearchCriteriaPopup extends SearchCriteriaPopup {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new samples search criteria popup.
   *
   * @param parent the parent
   * @param menuItemSize the menu item size
   */
  public SamplesSearchCriteriaPopup(ModernWindow parent,
      Dimension menuItemSize) {
    setup(parent, menuItemSize);
  }

  /**
   * Setup.
   *
   * @param parent the parent
   * @param menuItemSize the menu item size
   */
  private void setup(ModernWindow parent, Dimension menuItemSize) {
    ModernMenuItem item = null;

    List<ModernMenuItem> items = new ArrayList<ModernMenuItem>();

    for (SearchCategoryGroup group : SearchCategoryService.getInstance()) {
      if (!group.display()) {
        continue;
      }

      if (group.getName() == null) {
        continue;
      }

      // don't want to add experiment level
      // searches
      if (group.getName().equals("Experiment")) {
        continue;
      }

      // group title
      item = new ModernTitleMenuItem(group.getName());

      // sort items alphabetically and add to combo

      for (SearchCategory criterion : group) {
        ModernMenuItem subItem = new ModernTwoLineMenuItem(criterion.getName(),
            criterion.getDescription(), UIService.getInstance()
                .loadIcon("search_criterion", UIService.ICON_SIZE_32));

        // subItem.setActionCommand(name);

        UI.setSize(subItem, MENU_ITEM_SIZE);

        item.getSubMenuItems().add(subItem);
      }

      items.add(item);
    }

    addScrollMenuItems(items);

    // addBreakLine();

    // addModernMenuItem(new ModernMenuItem("All Fields"));
  }
}
