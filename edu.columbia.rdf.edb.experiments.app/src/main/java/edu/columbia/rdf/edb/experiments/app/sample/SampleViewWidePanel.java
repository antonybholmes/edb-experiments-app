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

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.search.FilterModel;
import org.jebtk.modern.splitpane.ModernVSplitPaneLine;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.ViewPluginService;

// TODO: Auto-generated Javadoc
/**
 * Offers a column oriented view of the sample data.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class SampleViewWidePanel extends ModernWidget implements ModernSelectionListener, ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  // private JList samplesList;

  // private SamplePanel samplePanel;

  /** The m samples tree panel. */
  private SamplesPanel mSamplesTreePanel;

  /** The m split pane. */
  private ModernVSplitPaneLine mSplitPane;

  /** The m sample panel. */
  private ModernComponent mSamplePanel;

  /** The m sampl selection model. */
  private SampleModel mSamplSelectionModel;

  // private FilesPanel mFilesPanel;

  /** The m view model. */
  private ViewModel mViewModel;

  /*
   * private class ViewEvents implements ChangeListener {
   * 
   * @Override public void changed(ChangeEvent e) { viewChanged(); } }
   */

  /**
   * Instantiates a new sample view wide panel.
   *
   * @param parent
   *          the parent
   * @param viewModel
   *          the view model
   * @param sampleModel
   *          the sample model
   * @param sampleSelectionModel
   *          the sample selection model
   * @param fileViewModel
   *          the file view model
   */
  public SampleViewWidePanel(ModernWindow parent, ViewModel viewModel, SampleModel sampleModel,
      SampleModel sampleSelectionModel, FilterModel filterModel, ViewModel fileViewModel) {
    mViewModel = viewModel;
    mSamplSelectionModel = sampleSelectionModel;

    mSamplesTreePanel = new SamplesTreePanel(parent, sampleModel, filterModel);

    // mFilesPanel = new FilesPanel(parent,
    // sampleModel,
    // sampleSelectionModel,
    // fileViewModel);

    mSamplesTreePanel.addSelectionListener(this);
    // mSamplesTreePanel.setBorder(ModernPanel.BORDER);
    // samplesPanel.addClickListener(this);

    // samplePanel = new SamplePanel(parent);

    // splitPaneH = new HHidePaneLeft(new SideTabPanel("Samples", samplesListPanel),
    // samplePanel, 200,
    // ModernTheme.getInstance().getClass("widget").getInt("padding"), false);

    // splitPaneH = new HSplitPane(new SideTabPanel("Samples", samplesListPanel),
    // samplePanel);

    mSplitPane = new ModernVSplitPaneLine();
    mSplitPane.addComponent(mSamplesTreePanel, 0.4);
    mSplitPane.setDividerWidth(0);

    add(mSplitPane, BorderLayout.CENTER);

    mSamplesTreePanel.setSelectedSample(1);

    // viewModel.addChangeListener(new ViewEvents());

    // viewChanged();

    showSampleDetails();
  }

  /**
   * Sets the selected sample.
   *
   * @param row
   *          the new selected sample
   */
  public final void setSelectedSample(int row) {

    mSamplesTreePanel.setSelectedSample(row);

    showSampleDetails();
  }

  /**
   * Displays the details of the currently selected sample in the middle info
   * pane.
   */
  private void showSampleDetails() {

    // on startup ensure that that there is at least one
    // sample in the list before trying to display something

    List<Sample> samples = mSamplesTreePanel.getSelectedSamples();

    if (samples.size() == 0) {
      return;
    }

    // show details for the first selected sample

    if (mViewModel.getView().equals("Annotation")) {
      Sample sample = samples.get(0);

      mSamplePanel = ViewPluginService.getInstance().getView(sample.getExpressionType().getName())
          .getSamplePanel(sample);
      // mSamplePanel.setBorder(ModernPanel.BORDER);

      // mSplitPaneH.setComponent2(mSamplePanel);
      // samplePanel.setSample(sample);

      // list files for all selected samples

      // samplePanel.setSampleFiles(samplesPanel.getSelectedSamples());

      mSplitPane.replaceComponent(mSamplePanel, 0.6, 1);
    }

    mSamplSelectionModel.set(samples);
  }

  /**
   * Mouse clicked.
   *
   * @param e
   *          the e
   */
  public final void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
      // double clicked so open sample windows for highlighted experiments

      // showSampleWindows();
    } else {
      showSampleDetails();
    }
  }

  /**
   * Gets the samples panel.
   *
   * @return the samples panel
   */
  public final SamplesPanel getSamplesPanel() {
    return mSamplesTreePanel;
  }

  /**
   * Gets the selected samples.
   *
   * @return the selected samples
   */
  public List<Sample> getSelectedSamples() {
    return mSamplesTreePanel.getSelectedSamples();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernSelectionListener#selectionChanged(org.abh.
   * common.event.ChangeEvent)
   */
  @Override
  public void selectionChanged(ChangeEvent e) {
    showSampleDetails();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.event.
   * ModernClickEvent)
   */
  public void clicked(ModernClickEvent e) {
    if (mSamplesTreePanel.getSelectedSamples().size() == 0) {
      return;
    }

    JFrame window = new SampleWindow(mSamplesTreePanel.getSelectedSamples().get(0));

    UI.centerWindowToScreen(window);

    window.setVisible(true);
  }

  /*
   * private void viewChanged() { if (mViewModel.getView().equals("Annotation")) {
   * //if (mSamplePanel != null) { // mSplitPane.replaceComponent(mSamplePanel,
   * 0.6, 1); //} } else { mSplitPane.replaceComponent(mFilesPanel, 0.6, 1); } }
   */
}
