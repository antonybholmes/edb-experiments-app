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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jebtk.core.NetworkFileException;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.modern.ModernWidget;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.panel.MultiViewPanel;
import org.jebtk.modern.status.StatusModel;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.modules.microarray.MicroarrayExpressionData;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;
import edu.columbia.rdf.edb.ui.microarray.Mas5Dialog;
import edu.columbia.rdf.edb.ui.microarray.MicroarrayNormalizationType;

// TODO: Auto-generated Javadoc
/**
 * Present samples in an SDRF like view.
 *
 * @author Antony Holmes
 */
public class AttributesTableTab extends ModernWidget {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The filtered samples. */
  private List<Sample> filteredSamples = null;

  /** The table. */
  // private ModernRowTable table = new ModernRowTable();
  private MultiViewPanel table;

  /** The m parent. */
  private ModernWindow mParent;

  /** The m status model. */
  private StatusModel mStatusModel;

  /** The m view model. */
  private ViewModel mViewModel;

  /** The m sample model. */
  private SampleModel mSampleModel;

  /** The m view. */
  private DataView mView;

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
    public void selectionAdded(ChangeEvent e) {
      searchSamples();
    }

    @Override
    public void selectionRemoved(ChangeEvent e) {
      // TODO Auto-generated method stub
      
    }
  }

  /**
   * Instantiates a new attributes table tab.
   *
   * @param parent the parent
   * @param sampleModel the sample model
   * @param viewModel the view model
   * @param statusModel the status model
   * @param view the view
   */
  public AttributesTableTab(ModernWindow parent, SampleModel sampleModel,
      ViewModel viewModel, StatusModel statusModel, DataView view) {
    mParent = parent;
    mStatusModel = statusModel;
    mViewModel = viewModel;
    mSampleModel = sampleModel;
    mView = view;

    sampleModel.addSelectionListener(new SelectionEvents());

    createUi();
  }

  /**
   * Creates the ui.
   */
  public final void createUi() {

    table = new MultiViewPanel(mViewModel);

    add(table);
  }

  /**
   * Search samples.
   */
  private void searchSamples() {

    filteredSamples = new ArrayList<Sample>();

    for (Sample sample : mSampleModel.getItems()) {
      filteredSamples.add(sample);
    }

    displayFilteredSamples();
  }

  /**
   * Display filtered samples.
   */
  private void displayFilteredSamples() {
    Collections.sort(filteredSamples);

    table.setModel(new AttributesTableModel(filteredSamples, mView));

    // for (int i = 0; i < table.getColumnModel().size(); ++i) {
    // table.getColumnModel().get(i).setWidth(100);
    // }
  }

  /**
   * Show expression data.
   *
   * @param type the type
   * @throws NetworkFileException the network file exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  public void showExpressionData(MicroarrayNormalizationType type)
      throws NetworkFileException, IOException, ParseException {

    if (table.getCellSelectionModel().getRowSelectionModel().size() == 0) {
      // Since we are using a row table, we must use the
      // row model to determine which rows are currently
      // selected.

      ModernMessageDialog.createDialog(mParent,
          mParent.getAppInfo().getName(),
          "You must select at least one sample.",
          MessageDialogType.WARNING);

      return;
    }

    List<Sample> expressionSamples = getSelectedSamples();

    MicroarrayExpressionData expressionData = new MicroarrayExpressionData();

    if (type == MicroarrayNormalizationType.MAS5) {
      Mas5Dialog dialog = new Mas5Dialog(mParent);

      dialog.setVisible(true);

      if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
        return;
      }

      expressionData.showTables(mParent,
          expressionSamples,
          type,
          dialog.getColumns(),
          null,
          true,
          mStatusModel);
    } else {
      // we all all columns since there is only the data column with rma
      expressionData.showTables(mParent,
          expressionSamples,
          type,
          CollectionUtils.asList(true),
          null,
          true,
          mStatusModel);
    }
  }

  /**
   * Gets the selected samples.
   *
   * @return the selected samples
   */
  public List<Sample> getSelectedSamples() {
    List<Sample> samples = new ArrayList<Sample>();

    for (int i : table.getCellSelectionModel().getRowSelectionModel()) {
      samples.add(filteredSamples.get(table.convertRowIndexToModel(i)));
    }

    return samples;
  }
}
