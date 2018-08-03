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
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.jebtk.core.search.SearchStackOperator;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickEventProducer;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernClickListeners;
import org.jebtk.modern.graphics.icons.CloseVectorIcon;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.text.SearchTextBorderPanel;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.ui.search.SearchCategory;

/**
 * The Class SearchCategoryPanel.
 */
public class SearchCategoryPanel extends HBox
    implements KeyListener, ModernClickListener, ModernClickEventProducer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m logical button. */
  private ModernButtonWidget mLogicalButton;

  /** The m field. */
  private SearchCategoryDropDownMenuButton mField;

  /** The m remove button. */
  private ModernButton mRemoveButton;

  /** The m listeners. */
  private ModernClickListeners mListeners = new ModernClickListeners();

  /** The m search text. */
  private SearchField mSearchText;

  /** The Constant CRITERIA_SIZE. */
  private static final Dimension CRITERIA_SIZE = new Dimension(150,
      ModernWidget.WIDGET_HEIGHT);

  /**
   * Instantiates a new search category panel.
   *
   * @param parent the parent
   * @param index the index
   * @param booleanOperator the boolean operator
   * @param text the text
   * @param field the field
   */
  public SearchCategoryPanel(ModernWindow parent, int index,
      SearchStackOperator booleanOperator, String text, SearchCategory field) {

    setBorder(BorderService.getInstance().createBottomBorder(2));

    // setMinimumSize(new Dimension(ModernWidget.WIDGET_HEIGHT,
    // ModernWidget.WIDGET_HEIGHT));
    // setMaximumSize(new Dimension(Short.MAX_VALUE,
    // ModernWidget.WIDGET_HEIGHT));
    // setPreferredSize(new Dimension(Short.MAX_VALUE,
    // ModernWidget.WIDGET_HEIGHT));

    mLogicalButton = new OperatorComboButton(booleanOperator);

    if (index == 0) {
      add(UI.createHGap(OperatorComboButton.WIDTH));
    } else {
      add(mLogicalButton);
    }

    add(ModernPanel.createHGap());

    //
    // What to look for
    //

    mSearchText = new SearchField(text);
    mSearchText.addKeyListener(this);

    SearchTextBorderPanel panel = new SearchTextBorderPanel(mSearchText);
    panel.setPreferredSize(
        new Dimension(Short.MAX_VALUE, ModernWidget.WIDGET_HEIGHT));
    panel.setMaximumSize(
        new Dimension(Short.MAX_VALUE, ModernWidget.WIDGET_HEIGHT));
    add(panel);

    add(ModernPanel.createHGap());
    // add(new ModernVerticalSeparator());
    // add(ModernTheme.createHorizontalGap());

    //
    // What can be searched
    //

    SearchCriteriaPopup searchCriteriaPopup = new ExperimentsSearchCriteriaPopup(
        parent);

    mField = new SearchCategoryDropDownMenuButton(field, searchCriteriaPopup);

    UI.setSize(mField, CRITERIA_SIZE);

    add(mField);

    add(ModernPanel.createHGap());

    //
    // The logical button
    //
    // logicalButton = new OperatorComboButton(MatchStackOperator.AND);
    // add(logicalButton);

    // add(new ModernVerticalSeparator());
    // add(ModernTheme.createHorizontalGap());

    //
    // Can we remove it

    mRemoveButton = new ModernButton(
        AssetService.getInstance().loadIcon(CloseVectorIcon.class, 16));
    mRemoveButton.setClickMessage(SearchCategoriesPanel.FIELD_REMOVED);
    mRemoveButton.addClickListener(this);
    mRemoveButton.setToolTip("Remove Entry", "Remove the search entry.");

    // we don't allow users to remove the first search entry so as
    // not to confuse them with a blank display
    if (index == 0) {
      add(UI.createHGap(mRemoveButton.getWidth()));
    } else {
      add(mRemoveButton);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      fireClicked(new ModernClickEvent(this, UI.MENU_SEARCH));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    fireClicked(new ModernClickEvent(this, e.getMessage()));
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

  /**
   * Sets the text.
   *
   * @param text the new text
   */
  public void setText(String text) {
    mSearchText.setText(text);
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText() {
    return mSearchText.getText();
  }

  /**
   * Gets the logical.
   *
   * @return the logical
   */
  public String getLogical() {
    return mLogicalButton.getText();
  }

  /**
   * Gets the field.
   *
   * @return the field
   */
  public String getField() {
    return mField.getText();
  }

  /**
   * Sets the logical.
   *
   * @param booleanOperator the new logical
   */
  public void setLogical(SearchStackOperator booleanOperator) {
    setLogical(booleanOperator.toString());
  }

  /**
   * Sets the logical.
   *
   * @param text the new logical
   */
  public void setLogical(String text) {
    mLogicalButton.setText(text);
  }
}
