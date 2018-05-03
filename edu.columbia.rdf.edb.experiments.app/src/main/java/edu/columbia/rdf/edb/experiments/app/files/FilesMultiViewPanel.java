/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors 
 *     may be used to endorse or promote products derived from this software 
 *     without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.columbia.rdf.edb.experiments.app.files;

import java.awt.event.MouseListener;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.dataview.ModernData;
import org.jebtk.modern.dataview.ModernDataCellsSelectionModel;
import org.jebtk.modern.dataview.ModernDataDateRenderer;
import org.jebtk.modern.dataview.ModernDataListView;
import org.jebtk.modern.dataview.ModernDataModel;
import org.jebtk.modern.dataview.ModernDataTileView;
import org.jebtk.modern.dataview.sort.ModernDataRowDateSorter;
import org.jebtk.modern.dataview.sort.ModernDataRowTextSorter;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.ModernRowTable;
import org.jebtk.modern.tabs.TabsViewPanel;
import org.jebtk.modern.view.ViewModel;

/**
 * The class MultiViewPanel.
 */
public class FilesMultiViewPanel extends TabsViewPanel
    implements ChangeListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member view details.
   */
  private ModernRowTable mTableDetails = new ModernRowTable();

  /**
   * The member view list.
   */
  private ModernDataListView mViewList = new ModernDataListView();

  /**
   * The member view tiles.
   */
  private ModernDataTileView mViewTiles = new ModernDataTileView();

  /**
   * The member view model.
   */
  private ViewModel mViewModel;

  /**
   * Instantiates a new multi view panel.
   *
   * @param viewModel the view model
   */
  public FilesMultiViewPanel(ViewModel viewModel) {
    mViewModel = viewModel;

    mViewModel.addChangeListener(this);

    mTableDetails.getRendererModel().setCol(0, new FilesTableCellRenderer());
    mViewList.getRendererModel().set(new FilesListViewCellRenderer());
    mViewTiles.getRendererModel().set(new FilesTileCellRenderer());

    getTabsModel().addTab("Details", new ModernScrollPane(mTableDetails));
    getTabsModel().addTab("List", new ModernScrollPane(mViewList));
    getTabsModel().addTab("Tiles", new ModernScrollPane(mViewTiles));

    ModernDataRowTextSorter sorter = new ModernDataRowTextSorter();

    mTableDetails.getRowSortModel().set(0, sorter);
    mTableDetails.getRowSortModel().set(1, sorter);
    mTableDetails.getRowSortModel().set(2, new ModernDataRowDateSorter());
    mTableDetails.getRendererModel().setCol(2, new ModernDataDateRenderer());

    mTableDetails.getColumnModel().setWidth(0, 400);
    mTableDetails.getColumnModel().setWidth(1, 200);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
   */
  public void addMouseListener(MouseListener l) {
    mTableDetails.addMouseListener(l);
    mViewList.addMouseListener(l);
    mViewTiles.addMouseListener(l);
  }

  /**
   * Gets the cell selection model.
   *
   * @return the cell selection model
   */
  public ModernDataCellsSelectionModel getCellSelectionModel() {
    switch (getTabsModel().getSelectedIndex()) {
    case 0:
      return mTableDetails.getCellSelectionModel();
    case 1:
      return mViewList.getCellSelectionModel();
    case 2:
      return mViewTiles.getCellSelectionModel();
    default:
      return null;
    }
  }

  /**
   * Gets the data.
   *
   * @return the data
   */
  public ModernData getData() {
    switch (getTabsModel().getSelectedIndex()) {
    case 0:
      return mTableDetails;
    case 1:
      return mViewList;
    case 2:
      return mViewTiles;
    default:
      return null;
    }
  }

  /**
   * Sets the model.
   *
   * @param model the new model
   */
  public void setModel(ModernDataModel model) {
    mTableDetails.setModel(model);
    mViewList.setModel(model);
    mViewTiles.setModel(model);

  }

  /**
   * Convert row index to model.
   *
   * @param i the i
   * @return the int
   */
  public int convertRowIndexToModel(int i) {
    switch (getTabsModel().getSelectedIndex()) {
    case 0:
      return mTableDetails.getModelRowIndex(i);
    case 1:
      return mViewList.getModelRowIndex(i);
    case 2:
      return mViewTiles.getModelRowIndex(i);
    default:
      return i;
    }
  }

  /**
   * View changed.
   */
  private void viewChanged() {
    getTabsModel().changeTab(mViewModel.getView());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
   */
  @Override
  public void changed(ChangeEvent e) {
    viewChanged();
  }
}
