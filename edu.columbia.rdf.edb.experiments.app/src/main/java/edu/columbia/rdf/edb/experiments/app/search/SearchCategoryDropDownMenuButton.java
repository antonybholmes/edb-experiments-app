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

import org.jebtk.modern.button.ModernDropDownMenuLabelButton;

import edu.columbia.rdf.edb.ui.search.SearchCategory;

// TODO: Auto-generated Javadoc
/**
 * Acts like a flat drop down menu button, but alters the button text as well.
 *
 * @author Antony Holmes Holmes
 */
public class SearchCategoryDropDownMenuButton
    extends ModernDropDownMenuLabelButton {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new search category drop down menu button.
   *
   * @param field the field
   * @param searchCriteriaPopup the search criteria popup
   */
  public SearchCategoryDropDownMenuButton(SearchCategory field,
      SearchCriteriaPopup searchCriteriaPopup) {
    super(field.getName(), searchCriteriaPopup);
  }
}
