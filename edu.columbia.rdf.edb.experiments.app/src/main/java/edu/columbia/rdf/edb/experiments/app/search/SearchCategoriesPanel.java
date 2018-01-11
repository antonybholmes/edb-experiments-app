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

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jebtk.core.search.SearchStackOperator;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickEventProducer;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernClickListeners;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.search.SearchTermsService;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.ui.search.SearchCategory;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;
import edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory;
import edu.columbia.rdf.edb.ui.search.SearchStackElementCategory;
import edu.columbia.rdf.edb.ui.search.UserSearch;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchCategoriesPanel.
 */
public class SearchCategoriesPanel extends VBox implements
    SearchCriteriaCategory, ModernClickListener, ModernClickEventProducer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * The default field to suggest when a new search criterion is added.
   */
  // private SearchField defaultField; //"All Fields";

  public static SearchStackOperator DEFAULT_BOOL_TERM = SearchStackOperator.AND;

  // private static final int[] rows = {HEIGHT};
  // private static final int[] columns = {900}; // //{30, 70, 200, 400,
  // HEIGHT};

  /** The Constant FIELD_REMOVED. */
  public static final String FIELD_REMOVED = "field_removed";

  /** The Constant FIELD_ADDED. */
  public static final String FIELD_ADDED = "field_added";

  // private List<JLabel> indices = new CopyOnWriteArrayList<JLabel>();
  // private List<ModernComboBox> logical = new ArrayList<ModernComboBox>();
  // private List<ModernComboBox> fields = new ArrayList<ModernComboBox>();
  // private List<ModernComboBox> searchText = new ArrayList<ModernComboBox>();
  // private List<ModernButton> removeButtons = new ArrayList<ModernButton>();

  /** The m panels. */
  private List<SearchCategoryPanel> mPanels = new ArrayList<SearchCategoryPanel>();

  /** The m remove map. */
  private Map<SearchCategoryPanel, Integer> mRemoveMap = new HashMap<SearchCategoryPanel, Integer>();

  /** The m listeners. */
  private ModernClickListeners mListeners = new ModernClickListeners();

  /** The m parent. */
  private ModernWindow mParent;

  /**
   * Instantiates a new search categories panel.
   *
   * @param parent the parent
   */
  public SearchCategoriesPanel(ModernWindow parent) {
    mParent = parent;

    setAlignmentY(TOP_ALIGNMENT);

    // setBackground(Color.RED);

    // setBorder(BorderFactory.createEmptyBorder());

    // setBorder(BorderFactory.createCompoundBorder(ModernTheme.getInstance().getClass("widget").getBorder("line"),
    // BorderService.getInstance().createBorder(2)));
  }

  /**
   * Removes all but the first user search entry, so that from a user
   * perspective, there is always one search field available to them.
   */
  public final void removeUserSearchEntries() {
    int count = mPanels.size() - 1;

    // remove everything but the first entry
    // so the user always has a least one
    // search bar

    for (int i = count; i > 0; --i) {
      removeUserSearchEntry(i);
    }

    mPanels.get(0).setText("");
  }

  /**
   * Remove all search entries.
   */
  private final void removeAllSearchEntries() {
    int count = mPanels.size() - 1;

    // remove everything but the first entry
    // so the user always has a least one
    // search bar

    for (int i = count; i >= 0; --i) {
      removeUserSearchEntry(i);
    }
  }

  /**
   * Removes the user search entry.
   *
   * @param index the index
   */
  public final void removeUserSearchEntry(int index) {

    // removeRow(index);

    // logical.remove(index);
    // fields.remove(index);
    // searchText.remove(index);
    // removeButtons.remove(index);

    remove(mPanels.get(index));
    mPanels.remove(index);

    revalidate();// forces panel to lay out components again
    repaint();

    /*
     * indices.remove(index);
     * 
     * for (int i = 0; i < indices.size(); ++i) { indices.get(i).setText((i + 1)
     * + "."); }
     */

    /*
     * if (panels.size() > 0) { logical.get(0).setSelectedIndex(0);
     * logical.get(0).setEnabled(false); logical.get(0).setVisible(false);
     * 
     * removeButtons.get(0).setVisible(false); }
     */

    mapRemoveObjects();

    fireClicked(new ModernClickEvent(this, FIELD_REMOVED));

    // fireAction(new ModernClickEvent(this, Ui.MENU_SEARCH));
  }

  /**
   * Creates a map to easily find out which button is mapped to which row.
   */
  private void mapRemoveObjects() {
    mRemoveMap.clear();

    for (int i = 0; i < mPanels.size(); ++i) {
      mRemoveMap.put(mPanels.get(i), i);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#loadSearch(edu.
   * columbia .rdf.edb.ui.search.UserSearch)
   */
  public final void loadSearch(UserSearch search) {
    removeAllSearchEntries();

    addUserSearch(search);
  }

  /**
   * Sets the user search.
   *
   * @param userSearch the new user search
   */
  public final void setUserSearch(UserSearch userSearch) {
    removeAllSearchEntries();

    addUserSearch(userSearch);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#addUserSearch(edu.
   * columbia.rdf.edb.ui.search.UserSearch)
   */
  public final void addUserSearch(UserSearch userSearch) {
    for (UserSearchEntry userSearchEntry : userSearch) {
      addUserSearchEntry(userSearchEntry);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#addUserSearchEntry(
   * edu. columbia.rdf.edb.ui.search.UserSearchEntry)
   */
  public final void addUserSearchEntry(UserSearchEntry userSearchEntry) {
    addUserSearchEntry(userSearchEntry.getOperator(),
        userSearchEntry.getField(),
        userSearchEntry.getText());
  }

  /**
   * Adds an entry to the list of search fields.
   *
   * @param booleanOperator the boolean operator
   * @param field the field
   * @param text the text
   */
  public final void addUserSearchEntry(SearchStackOperator booleanOperator,
      SearchCategory field,
      String text) {

    SearchCategoryPanel panel = new SearchCategoryPanel(mParent, mPanels.size(),
        booleanOperator, text, field);

    panel.addClickListener(this);

    mPanels.add(panel);
    add(panel);

    mapRemoveObjects();

    /*
     * ModernComboBox logicalCombo = new OperatorComboBox(booleanOperator);
     * logicalCombo.setEnabled(getRowCount() != 0 && removable);
     * logicalCombo.setVisible(getRowCount() != 0); logical.add(logicalCombo);
     * add(logicalCombo);
     * 
     * SearchCriteriaPopup searchCriteriaPopup;
     * 
     * searchCriteriaPopup = new ExperimentsSearchCriteriaPopup(parent, new
     * Dimension(180,
     * ModernTheme.getInstance().getClass("widget").getInt("height")), 1);
     * 
     * ModernComboBox searchCriteriaComboBox = new
     * SearchCategoryComboBox(searchCriteriaPopup, field);
     * 
     * //comboBox.setEditable(fieldEditable);
     * //comboBox.setEnabled(fieldEditable);
     * 
     * fields.add(searchCriteriaComboBox); add(searchCriteriaComboBox);
     * 
     * //JLabel containsLabel = new ModernLabel("containing", JLabel.CENTER);
     * //add(containsLabel);
     * 
     * ModernComboBox searchTermsComboBox = new
     * SearchTermsComboBox(SearchTermsService.getInstance());
     * searchTermsComboBox.setText(text);
     * searchTermsComboBox.addKeyListener(this);
     * searchTermsComboBox.setEditable(true);
     * searchText.add(searchTermsComboBox); add(searchTermsComboBox);
     * 
     * ModernButton removeButton = new
     * ModernButton(UIResources.getInstance().loadIcon(CloseVectorIcon.class,
     * 16));
     * 
     * removeButton.setEnabled(removable); removeButton.setVisible(removable);
     * removeButton.addClickListener(this);
     * 
     * // we don't allow users to remove the first search entry so as // not to
     * confuse them with a blank display removeButton.setVisible(getRowCount() >
     * 1);
     * 
     * removeMap.put(removeButton, getRowCount() - 1);
     * 
     * removeButtons.add(removeButton);
     * 
     * // add onto panel add(removeButton);
     * 
     */

    revalidate();
    repaint();

    fireClicked(new ModernClickEvent(this, FIELD_ADDED));

    // fireAction(new ModernClickEvent(this, Ui.MENU_SEARCH));
  }

  /**
   * Allows the search to be replicated and copied.
   *
   * @return the search
   */
  public UserSearch getSearch() {
    UserSearch search = new UserSearch();

    for (int i = 0; i < mPanels.size(); ++i) {
      // We always and clauses since the text
      // box can be used for or

      // Words are always anded together. If user wants
      // an or clause, they can add a new search entry.

      // if (mPanels.get(i).getText().length() < 1) {
      // continue;
      // }

      SearchStackOperator op = SearchStackOperator
          .parseOperator(mPanels.get(i).getLogical());

      search.add(UserSearchEntry.create(op,
          SearchCategoryService.getInstance()
              .getSearchCategory(mPanels.get(i).getField()),
          mPanels.get(i).getText()));
    }

    return search;
  }

  /**
   * Builds a stack representation of an expression in reverse polish notation.
   *
   * @return a stack representation of the search tree.
   * @throws Exception {
   */
  @Override
  public final Deque<SearchStackElementCategory> getSearchStack()
      throws Exception {
    // The user has started a search, so cache the search terms.

    for (int i = 0; i < mPanels.size(); ++i) {
      SearchTermsService.getInstance().addTerm(mPanels.get(i).getText());
    }

    UserSearch search = getSearch();

    Deque<SearchStackElementCategory> stack = SearchStackElementCategory
        .getSearchStack(search);

    return stack;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  public final void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(FIELD_REMOVED)) {
      removeUserSearchEntry(mRemoveMap.get(e.getSource()));
    } else {
      // pass along

      fireClicked(new ModernClickEvent(this, e.getMessage()));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickEventProducer#addClickListener(org.abh.
   * common.ui.event.ModernClickListener)
   */
  @Override
  public void addClickListener(ModernClickListener l) {
    mListeners.addClickListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickEventProducer#removeClickListener(org.
   * abh. common.ui.event.ModernClickListener)
   */
  @Override
  public void removeClickListener(ModernClickListener l) {
    mListeners.removeClickListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickEventProducer#fireClicked(org.abh.
   * common. ui.event.ModernClickEvent)
   */
  @Override
  public void fireClicked(ModernClickEvent event) {
    mListeners.fireClicked(event);
  }
}
