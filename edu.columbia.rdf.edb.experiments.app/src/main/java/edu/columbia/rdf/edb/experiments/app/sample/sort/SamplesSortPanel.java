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
package edu.columbia.rdf.edb.experiments.app.sample.sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BoxLayout;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernCheckRadioButton;
import org.jebtk.modern.button.ModernRadioButton;
import org.jebtk.modern.combobox.ModernComboBox2;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.ListVectorIcon;
import org.jebtk.modern.graphics.icons.TreeVectorIcon;
import org.jebtk.modern.menu.ModernPopupMenu2;
import org.jebtk.modern.menu.ModernTitleMenuItem;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.ribbon.RibbonSubSectionSeparator;
import org.jebtk.modern.search.FilterModel;
import org.jebtk.modern.search.SortDirectionButton;
import org.jebtk.modern.search.SortModel;
import org.jebtk.modern.search.Sorter;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.widget.ModernClickWidget;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.Sample;

// TODO: Auto-generated Javadoc
/**
 * Allows user to sort samples.
 *
 * @author Antony Holmes
 *
 */
public class SamplesSortPanel extends ModernWidget
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant SORTER_CHANGED. */
  public static final String SORTER_CHANGED = "sorter_changed";

  // private static final Dimension MENU_SIZE =
  // new Dimension(200, ModernIconMenuItem.HEIGHT);

  // private static final ModernIcon UP_ICON =
  // Resources.getInstance().loadIcon("up_scroll", Resources.ICON_SIZE_16);
  // private static final ModernIcon DOWN_ICON =
  // Resources.getInstance().loadIcon("down_scroll", Resources.ICON_SIZE_16);

  /** The m popup. */
  private ModernPopupMenu2 mPopup;

  /** The m sort field menu button. */
  private ModernComboBox2 mSortFieldMenuButton;

  /** The m sort direction button. */
  private SortDirectionButton mSortDirectionButton = new SortDirectionButton(
      true);

  /** The m list button. */
  private ModernCheckRadioButton mListButton = new ModernCheckRadioButton(
      AssetService.getInstance().loadIcon(ListVectorIcon.class, 16));

  /** The m tree button. */
  private ModernCheckRadioButton mTreeButton = new ModernCheckRadioButton(
      AssetService.getInstance().loadIcon(TreeVectorIcon.class, 16));

  /** The m filter button. */
  // private FilterButton mFilterButton;

  /** The m sort model. */
  private SortModel<Sample> mSortModel;

  /** The m button sort map. */
  private Map<String, ModernClickWidget> mButtonSortMap = new HashMap<String, ModernClickWidget>();

  /** The m view model. */
  private ViewModel mViewModel;

  /**
   * The Class SortEvents.
   */
  private class SortEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.event.ChangeListener#changed(org.abh.common.event.
     * ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      mSortFieldMenuButton.setText(mSortModel.getSorter().getName());

      mSortDirectionButton.setAscending(mSortModel.getSortAscending());

      mButtonSortMap.get(mSortModel.getSorter().getName()).setSelected(true);
    }
  }

  /**
   * Instantiates a new samples sort panel.
   *
   * @param parent the parent
   * @param sortModel the sort model
   * @param filterModel the filter model
   * @param viewModel the view model
   */
  public SamplesSortPanel(ModernWindow parent, SortModel<Sample> sortModel,
      FilterModel filterModel, ViewModel viewModel) {
    mSortModel = sortModel;
    mViewModel = viewModel;

    ModernButtonGroup group = new ModernButtonGroup();

    group.add(mTreeButton);
    group.add(mListButton);

    add(mTreeButton);
    add(mListButton);

    add(new RibbonSubSectionSeparator());

    group = new ModernButtonGroup();

    Map<String, Set<String>> names = new TreeMap<String, Set<String>>();

    for (Sorter<Sample> sorter : sortModel) {
      if (!names.containsKey(sorter.getType())) {
        names.put(sorter.getType(), new TreeSet<String>());
      }

      names.get(sorter.getType()).add(sorter.getName());
    }

    // mFilterButton = new FilterButton(parent, filterModel);

    sortModel.addChangeListener(new SortEvents());

    setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

    mPopup = new ModernPopupMenu2(); // parentFrame);

    ModernTitleMenuItem menuItem;
    ModernClickWidget subMenuItem;

    menuItem = new ModernTitleMenuItem("Common Properties");
    mPopup.add(menuItem);

    ModernComponent gridPanel = new ModernComponent();
    gridPanel.setLayout(new SamplesSortLayout(170, 28)); // new GridLayout(0, 3,
                                                         // 0, 0));
    gridPanel.setBorder(LEFT_RIGHT_BORDER);

    for (String name : names.get("Common Properties")) {
      subMenuItem = new ModernRadioButton(name);
      subMenuItem.addClickListener(this);
      // Ui.setSize(menuItem, MENU_SIZE);
      gridPanel.add(subMenuItem);
      group.add(subMenuItem);
      mButtonSortMap.put(name, subMenuItem);
    }

    mPopup.add(gridPanel);

    for (String type : names.keySet()) {
      if (type.equals("Common Properties")) {
        continue;
      }

      mPopup.add(UI.createVGap(10));

      menuItem = new ModernTitleMenuItem(type);
      // Ui.setSize(menuItem, MENU_SIZE);
      mPopup.add(menuItem);

      gridPanel = new ModernComponent();
      gridPanel.setLayout(new SamplesSortLayout(170, 28)); // new GridLayout(0,
                                                           // 3, 0, 0));
      gridPanel.setBorder(LEFT_RIGHT_BORDER);

      for (String name : names.get(type)) {
        subMenuItem = new ModernRadioButton(name); // new
                                                   // ModernCheckBoxMenuItem(name);
        subMenuItem.addClickListener(this);
        // Ui.setSize(menuItem, MENU_SIZE);
        gridPanel.add(subMenuItem);
        group.add(subMenuItem);
        mButtonSortMap.put(name, subMenuItem);
      }

      mPopup.add(gridPanel);
    }

    // mFilterButton.setToolTip("Filter", "Filter samples by a grouping.");
    // add(mFilterButton);
    // add(new RibbonSubSectionSeparator());

    add(new ModernAutoSizeLabel("Sort by"));

    add(ModernPanel.createHGap());

    // add(Box.createHorizontalGlue());

    // sortFieldMenuButton = new ModernHiddenComboBox("Array Design", popup);
    // mSortFieldMenuButton = new ModernDropDownMenuLabelButton("Sort by",
    // popup);
    mSortFieldMenuButton = new ModernComboBox2(mPopup);
    mSortFieldMenuButton.addClickListener(this);
    UI.setSize(mSortFieldMenuButton, 200, WIDGET_HEIGHT);

    add(mSortFieldMenuButton);

    // add(ModernTheme.createHorizontalGap());

    add(ModernPanel.createHGap());

    // add(ModernTheme.createHorizontalGap());

    add(mSortDirectionButton);

    // add(ModernTheme.createHorizontalGap());

    // addMouseListener(this);

    UI.setSize(this, ModernWidget.MAX_SIZE_24, TOP_BOTTOM_BORDER);

    // addMouseListener(this);
    mSortDirectionButton.addClickListener(this);

    mListButton.addClickListener(this);
    mTreeButton.addClickListener(this);

    if (mViewModel.getView().equals("tree")) {
      mTreeButton.setSelected(true);
    } else {
      mListButton.setSelected(true);
    }
  }

  /*
   * public void paintComponent(Graphics g) { super.paintComponent(g);
   * 
   * Graphics2D g2 = (Graphics2D)g;
   * 
   * int x = getWidth() - Resources.ICON_SIZE_16 -
   * ModernTheme.getInstance().getClass("widget").getInt("padding"); int y =
   * (getHeight() - Resources.ICON_SIZE_16) / 2;
   * 
   * // paint arrows so the user can see the sort order
   * 
   * if (ascending) { DOWN_ICON.paintIcon(this, g2, x, y); } else {
   * UP_ICON.paintIcon(this, g2, x, y); }
   * 
   * //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, //
   * RenderingHints.VALUE_ANTIALIAS_OFF); }
   */

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public final void clicked(ModernClickEvent e) {
    if (e.getSource().equals(mSortDirectionButton)) {
      mSortModel.setSortAscending(!mSortModel.getSortAscending());
    } else if (e.getSource().equals(mListButton)) {
      mViewModel.setView("list");
    } else if (e.getSource().equals(mTreeButton)) {
      mViewModel.setView("tree");
    } else {
      mSortModel.setSorter(e.getMessage());
    }
  }

}
