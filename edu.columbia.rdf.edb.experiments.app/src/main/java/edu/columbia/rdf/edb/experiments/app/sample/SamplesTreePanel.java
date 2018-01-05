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
package edu.columbia.rdf.edb.experiments.app.sample;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.core.text.TextUtils;
import org.jebtk.core.tree.TreeNode;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.clipboard.ClipboardService;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.graphics.icons.MinusVectorIcon;
import org.jebtk.modern.graphics.icons.PlusVectorIcon;
import org.jebtk.modern.menu.ModernCheckBoxMenuItem;
import org.jebtk.modern.menu.ModernIconMenuItem;
import org.jebtk.modern.menu.ModernPopupMenu;
import org.jebtk.modern.menu.ModernTitleIconMenuItem;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarLocation;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.search.FilterEventListener;
import org.jebtk.modern.search.FilterModel;
import org.jebtk.modern.tree.ModernTree;
import org.jebtk.modern.tree.ModernTreeNodeRenderer;
import org.jebtk.modern.tree.TreeNodeFileCountRenderer;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.widget.ModernTwoStateWidget;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.sample.sort.SampleSortService;
import edu.columbia.rdf.edb.experiments.app.sample.sort.SamplesSortPanel;
import edu.columbia.rdf.edb.ui.SamplesListTreeNodeRenderer;
import edu.columbia.rdf.edb.ui.ViewPlugin;
import edu.columbia.rdf.edb.ui.ViewPluginService;

// TODO: Auto-generated Javadoc
/**
 * The Class SamplesTreePanel.
 */
public class SamplesTreePanel extends SamplesPanel implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant TREE_RENDERER. */
  private static final ModernTreeNodeRenderer TREE_RENDERER = new TreeNodeFileCountRenderer();

  /** The Constant LIST_RENDERER. */
  private static final ModernTreeNodeRenderer LIST_RENDERER = new SamplesListTreeNodeRenderer();

  /** The m tree. */
  private ModernTree<Sample> mTree = new ModernTree<Sample>();

  /** The m samples sort panel. */
  private SamplesSortPanel mSamplesSortPanel;

  /** The m menu. */
  private ModernPopupMenu mMenu;

  /** The m sort menu item. */
  private ModernTwoStateWidget mSortMenuItem = new ModernCheckBoxMenuItem("Sort Descending");

  /** The expand menu item. */
  private ModernIconMenuItem expandMenuItem = new ModernIconMenuItem("Expand All",
      UIService.getInstance().loadIcon(PlusVectorIcon.class, 16));

  /** The collapse menu item. */
  private ModernIconMenuItem collapseMenuItem = new ModernIconMenuItem("Collapse All",
      UIService.getInstance().loadIcon(MinusVectorIcon.class, 16));

  /** The m view model. */
  private ViewModel mViewModel = new ViewModel(
      SettingsService.getInstance().getAsString("edb.experiments.samples.default-view"));

  /** The m sample model. */
  private SampleModel mSampleModel;

  /** The m scroll pane. */
  private ModernScrollPane mScrollPane;

  private FilterModel mFilterModel;

  /**
   * The Class MouseEvents.
   */
  private class MouseEvents extends MouseAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
      showPopup(e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      showPopup(e);
    }

    /**
     * Show popup.
     *
     * @param e
     *          the e
     */
    private void showPopup(MouseEvent e) {
      if (!e.getSource().equals(mTree)) {
        return;
      }

      if (!e.isPopupTrigger()) {
        return;
      }

      mMenu.showPopup(e.getComponent(), e.getX(), e.getY());
    }

  }

  /**
   * The Class SelectionEvents.
   */
  private class SelectionEvents implements ModernSelectionListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.event.ModernSelectionListener#selectionChanged(org.abh.
     * common.event.ChangeEvent)
     */
    @Override
    public void selectionChanged(ChangeEvent e) {
      filterSamples();
    }

  }

  /**
   * The Class FilterEvents.
   */
  private class FilterEvents implements FilterEventListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.search.FilterEventListener#filterChanged(org.abh.common.
     * event.ChangeEvent)
     */
    @Override
    public void filtersUpdated(ChangeEvent e) {
      loadSamples();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.search.FilterEventListener#filtersUpdated(org.abh.common.
     * event.ChangeEvent)
     */
    @Override
    public void filtersChanged(ChangeEvent e) {
      // filterSamples();
    }

  }

  /**
   * The Class SortEvents.
   */
  private class SortEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.event.ChangeListener#changed(org.abh.common.event.ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      mSortMenuItem.setSelected(!SampleSortService.getInstance().getSortAscending());

      filterSamples();
    }
  }

  /**
   * The Class ViewEvents.
   */
  private class ViewEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.event.ChangeListener#changed(org.abh.common.event.ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      viewChanged();
    }

  }

  /**
   * Instantiates a new samples tree panel.
   *
   * @param parent
   *          the parent
   * @param sampleModel
   *          the sample model
   */
  public SamplesTreePanel(ModernWindow parent, SampleModel sampleModel, FilterModel filterModel) {
    mSampleModel = sampleModel;
    mFilterModel = filterModel;

    mSamplesSortPanel = new SamplesSortPanel(parent, SampleSortService.getInstance(), filterModel, mViewModel);
    mSamplesSortPanel.setBorder(LEFT_BORDER);

    setup();
  }

  /**
   * Setup.
   */
  private void setup() {

    SampleSortService.getInstance().addChangeListener(new SortEvents());
    mFilterModel.addFilterListener(new FilterEvents());
    mSampleModel.addSelectionListener(new SelectionEvents());
    mViewModel.addChangeListener(new ViewEvents());
    // setBackground(Color.PINK);

    setHeader(mSamplesSortPanel);

    // mTree.setNodeRenderer(new SamplesListTreeNodeRenderer());
    // mTree.setNodeRenderer(new SamplesTreeNodeRenderer());

    mTree.addMouseListener(new MouseEvents());
    // mTree.setBorder(RIGHT_BORDER);

    mScrollPane = new ModernScrollPane(mTree);
    mScrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);
    mScrollPane.setVerticalScrollBarPolicy(ScrollBarPolicy.AUTO_SHOW);
    mScrollPane.setVScrollBarLocation(ScrollBarLocation.FLOATING);

    setBody(mScrollPane);

    // setBorder(BORDER);

    createMenu();

    // Set the default sorter
    SampleSortService.getInstance().setSorter("Sample Name");

    // Ensure the UI matches the view
    viewChanged();
  }

  /**
   * Creates the menu.
   */
  private void createMenu() {
    mMenu = new ModernPopupMenu();

    mMenu.addClickListener(this);

    mMenu.add(new ModernIconMenuItem(UI.MENU_COPY, UIService.getInstance().loadIcon("copy", 16)));

    mMenu.add(new ModernTitleIconMenuItem("Sort Options"));

    mMenu.add(expandMenuItem);
    mMenu.add(collapseMenuItem);

    /*
     * List<String> names = new ArrayList<String>();
     * 
     * for (SampleSorter sorter : sortModel) { names.add(sorter.getName()); }
     * 
     * Collections.sort(names);
     * 
     * ModernButtonGroup group = new ModernButtonGroup();
     * 
     * for (String name : names) { ModernCheckBoxMenuItem item = new
     * ModernCheckBoxMenuItem(name);
     * 
     * buttonSortMap.put(name, item);
     * 
     * menu.add(item);
     * 
     * group.add(item); }
     * 
     * menu.add(new ModernMenuSeparator());
     */

    mMenu.add(mSortMenuItem);

    for (ViewPlugin plugin : ViewPluginService.getInstance()) {
      plugin.customizeSampleMenu(mMenu);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.experiments.app.sample.SamplesPanel#addSelectionListener
   * (org.abh.common.ui.event.ModernSelectionListener)
   */
  public final void addSelectionListener(ModernSelectionListener l) {
    mTree.addSelectionListener(l);
  }

  /*
   * public final void showSampleWindows() {
   * 
   * GuiFrame window;
   * 
   * SampleSearchResult sample;
   * 
   * for (ModernTreeNode<SampleSearchResult> node : tree.getSelectedNodes()) { if
   * (node.getData() == null) { continue; }
   * 
   * sample = node.getData();
   * 
   * window = WindowServer.getInstance().findByName(sample.getName());
   * 
   * if (window != null) { WindowServer.getInstance().setFocus(window);
   * 
   * continue; }
   * 
   * window = new SampleWindow(sample);
   * 
   * window.setVisible(true); } }
   */

  /**
   * Filter samples.
   */
  public void filterSamples() {
    SampleSortService.getInstance().getSorter().filter(mSampleModel.getItems(), mFilterModel);

    loadSamples();
  }

  /**
   * Load samples.
   */
  public void loadSamples() {
    SampleSortService.getInstance().getSorter().arrange(mSampleModel.getItems(), mTree,
        SampleSortService.getInstance().getSortAscending(), mFilterModel);

    mTree.getRoot().setChildrenAreExpanded(SampleSortService.getInstance().getExpanded());

    // Select the first node that is not a header
    if (mTree.getChildCount() > 1) {
      mTree.getSelectionModel().setSelection(1);
    }

    // Reset the scrollpane if the data changes, otherwise data may be
    // in limbo
    mScrollPane.resetScrollBars();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.experiments.app.sample.SamplesPanel#getSelectedSamples()
   */
  @Override
  public List<Sample> getSelectedSamples() {

    List<Sample> samples = new ArrayList<Sample>();

    for (TreeNode<Sample> node : mTree.getSelectedNodes()) {
      if (node.getValue() == null) {
        continue;
      }

      Sample sample = node.getValue();

      samples.add(sample);
    }

    return samples;
  }

  /**
   * Sets the selected sample.
   *
   * @param sample
   *          the new selected sample
   */
  public void setSelectedSample(Sample sample) {
    setSelectedSample(sample.getName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.experiments.app.sample.SamplesPanel#setSelectedSample(
   * java.lang.String)
   */
  public void setSelectedSample(String name) {
    setSelectedSample(mTree.getNodeIndexByName(name));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.experiments.app.sample.SamplesPanel#setSelectedSample(
   * int)
   */
  public void setSelectedSample(int row) {
    mTree.selectNode(row);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.event.
   * ModernClickEvent)
   */
  public void clicked(ModernClickEvent e) {
    if (e.getSource().equals(collapseMenuItem)) {
      SampleSortService.getInstance().setExpanded(false);
    } else if (e.getSource().equals(expandMenuItem)) {
      SampleSortService.getInstance().setExpanded(true);
    } else if (e.getSource().equals(mSortMenuItem)) {
      SampleSortService.getInstance().setSortAscending(!mSortMenuItem.isSelected());
    } else if (e.getMessage().equals(UI.MENU_COPY)) {
      copySampleNamesToClipboard();
    } else {
      //
    }
  }

  /**
   * Copy sample names to clipboard.
   */
  private void copySampleNamesToClipboard() {
    StringBuilder buffer = new StringBuilder();

    for (TreeNode<Sample> node : mTree.getSelectedNodes()) {
      if (node.getValue() == null) {
        continue;
      }

      Sample sample = node.getValue();

      buffer.append(sample.getName());
      buffer.append(TextUtils.NEW_LINE);
    }

    ClipboardService.copyToClipboard(buffer);
  }

  /**
   * View changed.
   */
  private void viewChanged() {
    if (mViewModel.getView().equals("tree")) {
      mTree.setNodeRenderer(TREE_RENDERER);
    } else {
      mTree.setNodeRenderer(LIST_RENDERER);
    }

    SettingsService.getInstance().update("experiments.samples.default-view", mViewModel.getView());

    // Attempt to save the settings

    SettingsService.getInstance().save();
  }
}
