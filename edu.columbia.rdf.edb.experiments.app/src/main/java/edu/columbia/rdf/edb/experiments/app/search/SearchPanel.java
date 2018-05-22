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
import java.util.Collection;
import java.util.Deque;

import javax.swing.Box;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.UI;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernOptionalDropDownMenuButton2;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.SearchVectorIcon;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.splitpane.HSplitPane;
import org.jebtk.modern.splitpane.ModernHSplitPaneEllipsis;
import org.jebtk.modern.tooltip.ModernToolTip;
import org.jebtk.modern.widget.ModernClickWidget;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.Species;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;
import edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory;
import edu.columbia.rdf.edb.ui.search.SearchStackElementCategory;
import edu.columbia.rdf.edb.ui.search.UserSearch;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;

/**
 * The Class SearchPanel.
 */
public class SearchPanel extends ModernClickWidget
    implements ModernClickListener, SearchCriteriaCategory {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant SEARCH_QUEUE. */
  public static final String SEARCH_QUEUE = "search_queue";

  /** The Constant FIELD_ADDED. */
  public static final String FIELD_ADDED = "add_field";

  /** The m search fields panel. */
  private SearchCategoriesPanel mSearchFieldsPanel;

  /** The m search button. */
  private ModernButton mSearchButton = new ModernButton(
      AssetService.getInstance().loadIcon(SearchVectorIcon.class, 16)); // Resources.getInstance().loadIcon("search",
                                                                     // Resources.ICON_SIZE_16));

  /** The m clear button. */
  private ModernButton mClearButton = new ModernButton(
      AssetService.getInstance().loadIcon("trash_bw", 16));

  /** The m add field button. */
  private ModernOptionalDropDownMenuButton2 mAddFieldButton;

  // private ModernComboBox searchScopeCombo = new ModernHiddenComboBox();

  /** The m parent. */
  private ModernWindow mParent;

  /** The m data types panel. */
  private DataTypesPanel mDataTypesPanel;

  /**
   * Create a new search category panel.
   *
   * @param parent the parent window.
   */
  public SearchPanel(ModernWindow parent) {
    mParent = parent;

    mSearchFieldsPanel = new SearchCategoriesPanel(parent);

    mDataTypesPanel = new DataTypesPanel();

    mSearchFieldsPanel.addClickListener(this);

    // searchFieldsPanel.setOpaque(true);
    // searchFieldsPanel.setBackground(Color.WHITE);

    // ModernScrollPane scrollPane = new ModernScrollPane(searchFieldsPanel);
    // scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    // scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);
    // scrollPane.setBorder(DialogButton.LINE_BORDER);
    // scrollPane.getViewport().setBackground(DialogButton.LIGHT_COLOR);
    // scrollPane.setBackground(DialogButton.LIGHT_COLOR);
    // scrollPane.setCanvasSize(new Dimension(800, 0));
    // scrollPane.setMaximumSize(new Dimension(800, Short.MAX_VALUE));

    Box box = HBox.create();

    // searchScopeCombo.addMenuItem("All Samples");
    // searchScopeCombo.addMenuItem("Current Experiment");
    // Ui.setSize(searchScopeCombo, new Dimension(150,
    // ModernWidget.WIDGET_HEIGHT));
    // box.add(searchScopeCombo);
    // box.add(ModernTheme.createHorizontalGap());

    // box2.add(Box.createHorizontalGlue());
    mSearchButton.setToolTip(new ModernToolTip("Search",
        "Search for samples matching your search criteria."));

    mSearchButton.addClickListener(this);
    // searchButton.setToolTipText("Search for experiments.");
    box.add(mSearchButton);

    // panel.setMinimumSize(new Dimension(Ribbon.DEFAULT_BUTTON_WIDTH +
    // ModernTheme.getInstance().getClass("widget").getInt("padding"), 0));
    // panel.setCanvasSize(new Dimension(Ribbon.DEFAULT_BUTTON_WIDTH +
    // ModernTheme.getInstance().getClass("widget").getInt("padding"), 0));
    // panel.setMaximumSize(new Dimension(Ribbon.DEFAULT_BUTTON_WIDTH +
    // ModernTheme.getInstance().getClass("widget").getInt("padding"),
    // Short.MAX_VALUE));

    // add(panel);

    box.add(createHGap());

    SearchCriteriaPopup searchCriteriaPopup = new ExperimentsSearchCriteriaPopup(
        parent);

    // SearchSharedMenu.getInstance().setup(searchCriteriaPopup);

    // searchCriteriaPopup.addBreakLine();
    // searchCriteriaPopup.addModernMenuItem(new ModernMenuItem("Show all..."));
    // searchCriteriaPopup.addGlue();

    // searchCriteriaPopup.addClickListener(this);

    mAddFieldButton = new ModernOptionalDropDownMenuButton2(
        AssetService.getInstance().loadIcon("search_field", 16),
        searchCriteriaPopup);

    mAddFieldButton.setClickMessage("add_field");
    mAddFieldButton.setToolTip(new ModernToolTip("Add Field",
        "Add another search field to your search criteria."));
    mAddFieldButton.addClickListener(this);

    box.add(mAddFieldButton);

    box.add(createHGap());

    mClearButton.setToolTip("Remove All Search Criteria",
        "Remove all search criteria and return the search back to its default state.");
    mClearButton.addClickListener(this);
    box.add(mClearButton);

    box.setAlignmentY(TOP_ALIGNMENT);
    box.setAlignmentX(LEFT_ALIGNMENT);
    // box.setBorder(BorderService.getInstance().createBorder(4));

    Box box2 = Box.createVerticalBox();

    box2.add(box);

    // searchFieldsPanel.setBorder(RIGHT_BORDER);
    // box.setBorder(LEFT_BORDER);

    HSplitPane splitPane = new ModernHSplitPaneEllipsis();
    splitPane.addComponent(mSearchFieldsPanel, 0.75);
    splitPane.addComponent(box2, 0.25);
    splitPane.setMinComponentSize(150);
    // splitPane.setDividerLocation(800);

    splitPane.setBorder(BorderService.getInstance().createBorder(2));

    setBody(splitPane);

    // Unremark to add datatypes selection underneath search bar
    // setFooter(mDataTypesPanel);

    setBorder(DOUBLE_BORDER);

    mDataTypesPanel.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        search();
      }
    });
  }

  /**
   * Search.
   */
  private void search() {
    fireClicked(new ModernClickEvent(this, UI.MENU_SEARCH));
  }

  @Override
  public void drawBackground(Graphics2D g2) {
    fill(g2, Color.WHITE);

    // int h = getHeight() - 1;

    // g2.setColor(LINE_COLOR);
    // g2.drawLine(0, h, getWidth(), h);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    String command = null;

    if (e.getSource().equals(mSearchButton)) {
      command = UI.MENU_SEARCH;
    } else if (e.getSource().equals(mAddFieldButton)) {
      if (e.getMessage().equals(FIELD_ADDED)) {
        addUserSearchEntry(UserSearchEntry.createDefaultSearchEntry());
      } else {
        // from drop down menu
        addUserSearchEntry(UserSearchEntry.create(SearchCategoryService
            .getInstance().getSearchCategory(e.getMessage())));
      }
    } else if (e.getSource().equals(mClearButton)) {
      removeUserSearchEntries();

      command = "search_cleared";
    } else if (e.getSource().equals(mSearchFieldsPanel)) {
      // pass the command on
      command = e.getMessage();
    } else {
      // do nothing
    }

    if (command == null) {
      return;
    }

    fireClicked(new ModernClickEvent(this, command));
  }

  /**
   * Removes the user search entries.
   */
  private void removeUserSearchEntries() {
    ModernDialogStatus status = ModernMessageDialog.createDialog(mParent,
        mParent.getAppInfo().getName(),
        "Are you sure you want to remove all of your search criteria?",
        MessageDialogType.WARNING_OK_CANCEL);

    if (status == ModernDialogStatus.CANCEL) {
      return;
    }

    // Remove the user entries
    mSearchFieldsPanel.removeUserSearchEntries();

    // force a new search to reset the search
    fireClicked(new ModernClickEvent(this, UI.MENU_SEARCH));
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#getSearch()
   */
  public UserSearch getSearch() {
    return mSearchFieldsPanel.getSearch();
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#loadSearch(edu.
   * columbia .rdf.edb.ui.search.UserSearch)
   */
  public void loadSearch(UserSearch search) {
    mSearchFieldsPanel.loadSearch(search);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#getSearchStack()
   */
  @Override
  public Deque<SearchStackElementCategory> getSearchStack() throws Exception {
    return mSearchFieldsPanel.getSearchStack();
  }

  /**
   * Sets the user search.
   *
   * @param userSearch the new user search
   */
  public final void setUserSearch(UserSearch userSearch) {
    mSearchFieldsPanel.setUserSearch(userSearch);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#addUserSearch(edu.
   * columbia.rdf.edb.ui.search.UserSearch)
   */
  public void addUserSearch(UserSearch userSearch) {
    mSearchFieldsPanel.addUserSearch(userSearch);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#addUserSearchEntry(
   * edu. columbia.rdf.edb.ui.search.UserSearchEntry)
   */
  public void addUserSearchEntry(UserSearchEntry entry) {
    // Impose a cut off of 8 search criteria to stop
    // users creating unncessarily complex queries
    if (mSearchFieldsPanel.getSearch().size() >= 8) {
      return;
    }

    mSearchFieldsPanel.addUserSearchEntry(entry);
  }

  /**
   * Gets the data types.
   *
   * @return the data types
   */
  public Collection<Type> getDataTypes() {
    return mDataTypesPanel.getDataTypes();
  }

  /**
   * Gets the organisms.
   *
   * @return the organisms
   */
  public Collection<Species> getOrganisms() {
    return mDataTypesPanel.getOrganisms();
  }
}
