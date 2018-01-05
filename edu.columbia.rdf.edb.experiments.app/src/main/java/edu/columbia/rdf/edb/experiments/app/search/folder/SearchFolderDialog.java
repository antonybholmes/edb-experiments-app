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
package edu.columbia.rdf.edb.experiments.app.search.folder;

import java.awt.Dimension;

import javax.swing.Box;

import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernDialogButton;
import org.jebtk.modern.dialog.ModernDialogOptionalDropDownMenuButton;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.widget.tooltip.ModernToolTip;
import org.jebtk.modern.window.ModernWindow;
import org.jebtk.modern.window.WindowWidgetFocusEvents;

import edu.columbia.rdf.edb.experiments.app.search.ExperimentsSearchCriteriaPopup;
import edu.columbia.rdf.edb.experiments.app.search.SearchCategoriesPanel;
import edu.columbia.rdf.edb.experiments.app.search.SearchCriteriaPopup;
import edu.columbia.rdf.edb.experiments.app.search.SearchPanel;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;
import edu.columbia.rdf.edb.ui.search.UserSearch;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;

// TODO: Auto-generated Javadoc
/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class SearchFolderDialog extends ModernDialogTaskWindow {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m clear button. */
  private ModernButton mClearButton = new ModernDialogButton(UIService.getInstance().loadIcon("trash_bw", 16));

  /** The m add field button. */
  private ModernDialogOptionalDropDownMenuButton mAddFieldButton;

  /** The m text name. */
  private ModernTextField mTextName = new ModernTextField("New Search");

  /** The m search panel. */
  private SearchCategoriesPanel mSearchPanel = null;

  /**
   * Instantiates a new search folder dialog.
   *
   * @param parent
   *          the parent
   */
  public SearchFolderDialog(ModernWindow parent) {
    this(parent, "New Search", UserSearch.createDefaultSearch());

    setTitle("New Search Folder");
  }

  /**
   * Instantiates a new search folder dialog.
   *
   * @param parent
   *          the parent
   * @param name
   *          the name
   * @param userSearch
   *          the user search
   */
  public SearchFolderDialog(ModernWindow parent, String name, UserSearch userSearch) {
    super(parent);

    setTitle("Edit Search Folder");

    mTextName.setText(name);

    mSearchPanel = new SearchCategoriesPanel(parent);

    mSearchPanel.setUserSearch(userSearch);

    setup();

    createUi();
  }

  /**
   * Setup.
   */
  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(new Dimension(500, 380));

    setResizable(true);

    UI.centerWindowToScreen(this);
  }

  /**
   * Creates the ui.
   */
  private final void createUi() {
    // this.getContentPane().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    ModernComponent content = new ModernComponent();

    Box box = VBox.create();

    Box box2 = HBox.create();

    box2.add(new ModernAutoSizeLabel("Folder Name"));
    box2.add(UI.createHGap(20));
    box2.add(new ModernTextBorderPanel(mTextName, ModernWidget.VERY_LARGE_SIZE));

    box.add(box2);

    box.add(UI.createVGap(20));

    box2 = HBox.create();

    SearchCriteriaPopup searchCriteriaPopup = new ExperimentsSearchCriteriaPopup(mParent);

    mAddFieldButton = new ModernDialogOptionalDropDownMenuButton(UIService.getInstance().loadIcon("search_field", 16),
        searchCriteriaPopup);

    mAddFieldButton.setClickMessage("add_field");
    mAddFieldButton.setToolTip(new ModernToolTip("Add Field", "Add another search field to your search criteria."));
    mAddFieldButton.addClickListener(this);

    box2.add(mAddFieldButton);

    box2.add(ModernPanel.createHGap());

    mClearButton.setToolTip(new ModernToolTip("Remove All Search Criteria",
        "Remove all search criteria and return the search back to its default state."));
    mClearButton.addClickListener(this);
    box2.add(mClearButton);

    box.add(box2);

    box.add(UI.createVGap(10));

    content.setHeader(box);

    mSearchPanel.setBorder(ModernPanel.BORDER);
    ModernScrollPane scrollPane = new ModernScrollPane(mSearchPanel);
    scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);
    // scrollPane.setBorder(ModernWidget.LINE_BORDER);

    content.setBody(scrollPane);

    setContent(content);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.dialog.ModernDialogTaskWindow#clicked(org.abh.common.ui.
   * event.ModernClickEvent)
   */
  @Override
  public final void clicked(ModernClickEvent e) {
    if (e.getSource().equals(mAddFieldButton)) {
      if (e.getMessage().equals(SearchPanel.FIELD_ADDED)) {
        mSearchPanel.addUserSearchEntry(UserSearchEntry.createDefaultSearchEntry());
      } else {
        // from drop down menu
        mSearchPanel.addUserSearchEntry(
            UserSearchEntry.create(SearchCategoryService.getInstance().getSearchCategory(e.getMessage())));
      }
    } else if (e.getSource().equals(mClearButton)) {
      removeUserSearchEntries();
    } else {

    }

    super.clicked(e);
  }

  /**
   * Removes the user search entries.
   */
  private void removeUserSearchEntries() {
    ModernDialogStatus status = ModernMessageDialog.createDialog(mParent, mParent.getAppInfo().getName(),
        "Are you sure you want to remove all of your search criteria?", MessageDialogType.WARNING_OK_CANCEL);

    if (status == ModernDialogStatus.CANCEL) {
      return;
    }

    // Remove the user entries
    mSearchPanel.removeUserSearchEntries();
  }

  /**
   * Gets the user search.
   *
   * @return the user search
   */
  public UserSearch getUserSearch() {
    return mSearchPanel.getSearch();
  }

  /**
   * Gets the search folder name.
   *
   * @return the search folder name
   */
  public String getSearchFolderName() {
    return mTextName.getText();
  }
}
