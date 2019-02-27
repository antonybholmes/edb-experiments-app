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

import java.util.Deque;

import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernClickListeners;
import org.jebtk.modern.panel.HAutoStretchLayout;
import org.jebtk.modern.search.ModernSearchPanel;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.ui.search.SearchStackElementCategory;
import edu.columbia.rdf.edb.ui.search.UserSearch;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;

/**
 * The Class SearchPanel.
 */
public class SimpleSearchPanel extends ModernComponent implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;
  
  private ModernSearchPanel mSearchPanel = new ModernSearchPanel();
  
  private ModernClickListeners mListeners = new ModernClickListeners();
  
  /**
   * Create a new search category panel.
   *
   * @param parent the parent window.
   */
  public SimpleSearchPanel(ModernWindow parent) {
    setLayout(new HAutoStretchLayout(0.5));
    
    UI.setSize(mSearchPanel, 500);
    
    add(mSearchPanel);
    
    mSearchPanel.addClickListener(this);

    setBorder(DOUBLE_BORDER);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    mListeners.fireClicked(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.search.SearchCriteriaCategory#getSearch()
   */
  public UserSearch getSearch() {
    UserSearch search = new UserSearch();

    search.add(UserSearchEntry.create(mSearchPanel.getText()));
    
    return search;
  }

  public Deque<SearchStackElementCategory> getSearchStack() throws Exception {
    return SearchStackElementCategory.getSearchStack(getSearch());
  }

  public void addClickListener(ModernClickListener l) {
    mListeners.addClickListener(l);
  }
}
