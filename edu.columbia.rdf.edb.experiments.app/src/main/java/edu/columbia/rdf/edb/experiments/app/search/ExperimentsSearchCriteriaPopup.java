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

import javax.swing.BoxLayout;

import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernRadioButton;
import org.jebtk.modern.dialog.ModernDialogTaskType;
import org.jebtk.modern.menu.ModernMenuItem;
import org.jebtk.modern.menu.ModernTitleMenuItem;
import org.jebtk.modern.widget.ModernClickWidget;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.experiments.app.sample.sort.SamplesSortLayout;
import edu.columbia.rdf.edb.ui.search.SearchCategory;
import edu.columbia.rdf.edb.ui.search.SearchCategoryGroup;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;

/**
 * Specialised combobox for showing search criteria relevant for search through
 * experiments and samples.
 *
 * @author Antony Holmes Holmes
 *
 */
public class ExperimentsSearchCriteriaPopup extends SearchCriteriaPopup {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant MENU_WIDTH. */
  // public static final Dimension MENU_ITEM_SIZE = new Dimension(400,
  // Resources.ICON_SIZE_48);
  public static final int MENU_WIDTH = 150;

  /** The Constant COLUMNS. */
  public static final int COLUMNS = 4;

  /**
   * Instantiates a new experiments search criteria popup.
   *
   * @param parent the parent
   */
  public ExperimentsSearchCriteriaPopup(ModernWindow parent) {

    // The scroll pane needs to be non-uniform to cope with different
    // box sizes
    getScrollPanel()
        .setLayout(new BoxLayout(getScrollPanel(), BoxLayout.PAGE_AXIS));

    ModernMenuItem item = null;
    ModernClickWidget subItem = null;

    // List<ModernMenuItem> items = new ArrayList<ModernMenuItem>();

    ModernButtonGroup group = new ModernButtonGroup();

    for (SearchCategoryGroup categoryGroup : SearchCategoryService
        .getInstance()) {
      if (!categoryGroup.display()) {
        continue;
      }

      // group title
      if (categoryGroup.getName() == null) {
        continue;
      }

      // item = new ModernMenuItemGridBlock(categoryGroup.getName(), MENU_WIDTH,
      // COLUMNS);

      // addScrollMenuItem()

      item = new ModernTitleMenuItem(categoryGroup.getName());
      // Ui.setSize(menuItem, MENU_SIZE);
      addScrollMenuItem(item);

      ModernComponent gridPanel = new ModernComponent();
      gridPanel.setLayout(new SamplesSortLayout(180, 28)); // new GridLayout(0,
                                                           // 3, 0, 0));
      gridPanel.setBorder(ModernWidget.LEFT_RIGHT_BORDER);

      // sort items alphabetically and add to combo

      for (SearchCategory category : categoryGroup) {
        // ModernMenuItem subItem = new ModernTwoLineMenuItem(name,
        // c.get(name).getDescription(),
        // Resources.getInstance().loadIcon("search_criterion",
        // Resources.ICON_SIZE_32));

        // ModernMenuItem subItem = new ModernMenuItem(category.getName());

        subItem = new ModernRadioButton(category.getName());
        subItem.addClickListener(this);

        // System.err.println(name + " " +
        // UserSearchEntryCategory.DEFAULT_FIELD);

        // ensure the default item is checked
        if (category.getName()
            .equals(UserSearchEntry.DEFAULT_FIELD.getName())) {
          subItem.doClick();
        }

        // Ui.setSize(subItem, MENU_ITEM_SIZE);

        // item.addMenuItem(subItem);
        gridPanel.add(subItem);

        group.add(subItem);
      }

      // items.add(item);

      addScrollMenuItem(gridPanel);
      addScrollMenuItem(UI.createVGap(10));
    }

    /*
     * for (SearchFieldGroup group : SearchFieldGroupsService.getInstance()) {
     * if (!group.display()) { continue; }
     * 
     * // group title if (group.getName() == null) { continue; }
     * 
     * item = new ModernTitleMenuItem(group.getName());
     * 
     * Map<String, SearchField> c = new HashMap<String, SearchField>();
     * 
     * // sort items alphabetically and add to combo
     * 
     * for (SearchField criterion : group) { c.put(criterion.getDisplayName(),
     * criterion); }
     * 
     * String[] ordered = new String[c.size()];
     * 
     * c.keySet().toArray(ordered);
     * 
     * Arrays.sort(ordered);
     * 
     * for (String name : ordered) { ModernMenuItem subItem = new
     * ModernTwoLineMenuItem(name, c.get(name).getDescription(),
     * Resources.getInstance().loadIcon("search_criterion",
     * Resources.ICON_SIZE_32));
     * 
     * Ui.setSize(subItem, MENU_ITEM_SIZE);
     * 
     * item.getSubModernMenuItems().add(subItem); }
     * 
     * items.add(item); }
     */

    // addScrollMenuItems(items);
    
    setup(ModernDialogTaskType.OK);
  }
}
