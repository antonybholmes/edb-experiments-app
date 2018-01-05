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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Box;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernCheckButton;
import org.jebtk.modern.button.ModernCheckRadioButton;
import org.jebtk.modern.dialog.ModernDialogFlatButton;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.panel.MultiViewPanel;
import org.jebtk.modern.tabs.TabsModel;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.FileDescriptor;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.files.FilesDataViewGridModel;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;
import edu.columbia.rdf.edb.ui.DownloadManager;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class FilesPanel.
 */
public class FilesPanel extends ModernPanel implements ModernSelectionListener, ChangeListener, ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m filtered files. */
  private List<FileDescriptor> mFilteredFiles = null;

  // private ModernRowTable table = new ModernRowTable();
  // private ModernDataViewGrid table = new ModernDataViewGrid();
  /** The m table. */
  // private ModernDataViewList table = new ModernDataViewList();
  private MultiViewPanel mTable;

  // private ExperimentFilesTreePanel experimentsTreePanel = new
  // ExperimentFilesTreePanel();

  /** The m parent. */
  private ModernWindow mParent;

  // private ModernTextField searchBox = new ModernTextField();

  /** The m download button. */
  private ModernButton mDownloadButton = new ModernDialogFlatButton("Download",
      UIService.getInstance().loadIcon("download", 16));

  /** The m button details. */
  private ModernCheckButton mButtonDetails = new ModernCheckRadioButton(
      UIService.getInstance().loadIcon("view_details", 16));

  /** The m button list. */
  private ModernCheckButton mButtonList = new ModernCheckRadioButton(UIService.getInstance().loadIcon("view_list", 16));

  /** The m button icons. */
  private ModernCheckButton mButtonIcons = new ModernCheckRadioButton(
      UIService.getInstance().loadIcon("view_tiles", 16));

  /** The m sample selection model. */
  private SampleModel mSampleSelectionModel;

  /** The m view model. */
  private ViewModel mViewModel;

  /**
   * Instantiates a new files panel.
   *
   * @param parent
   *          the parent
   * @param sampleModel
   *          the sample model
   * @param sampleSelectionModel
   *          the sample selection model
   * @param viewModel
   *          the view model
   */
  public FilesPanel(ModernWindow parent, SampleModel sampleModel, SampleModel sampleSelectionModel,
      ViewModel viewModel) {

    mParent = parent;
    mSampleSelectionModel = sampleSelectionModel;
    mViewModel = viewModel;
    mSampleSelectionModel.addSelectionListener(this);

    mTable = new MultiViewPanel(viewModel);

    createUi();

    initialize();
  }

  /**
   * Creates the ui.
   */
  public final void createUi() {
    Box topPanel = HBox.create();

    topPanel.add(mDownloadButton);
    topPanel.add(UI.createHGap(10));
    topPanel.add(mButtonDetails);
    topPanel.add(mButtonList);
    topPanel.add(mButtonIcons);

    topPanel.setBorder(BorderService.getInstance().createTopBottomBorder(10));

    // topPanel.add(new ModernLabel("Search"));
    // topPanel.add(Ui.createHorizontalGap(DOUBLE_PADDING));

    // ModernTextPanel textPanel = new ModernTextPanel(searchBox);

    // Ui.setSize(textPanel,
    // new Dimension(400, WIDGET_HEIGHT));
    // topPanel.add(textPanel);

    // topPanel.add(ModernTheme.createHorizontalGap());

    // searchButton.setToolTip(new ModernToolTip("Search",
    // "Search for files containing the keywords you have specified."));
    // topPanel.add(searchButton);
    // topPanel.setBorder(BorderService.getInstance().createBorder(10));

    // ModernPanel content = new ModernPanel();

    // content.add(topPanel, BorderLayout.PAGE_START);
    // content.add(table, BorderLayout.CENTER);

    // HHidePaneLeft hidePaneLeft =
    // new ModernHidePaneLeftLine(experimentsTreePanel, content, 300, false);

    setHeader(topPanel);

    setBody(mTable);

    setBorder(BORDER);
  }

  /**
   * Initialize.
   */
  public final void initialize() {
    mDownloadButton.addClickListener(this);

    new ModernButtonGroup(mButtonDetails, mButtonList, mButtonIcons);

    mButtonDetails.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        mViewModel.setView("Details");
      }
    });

    mButtonList.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        mViewModel.setView("List");
      }
    });

    mButtonIcons.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        mViewModel.setView("Tiles");
      }
    });

    mButtonDetails.doClick();
  }

  /**
   * Display filtered files.
   */
  private void displayFilteredFiles() {
    mTable.setModel(new FilesDataViewGridModel(mFilteredFiles));
  }

  /**
   * Download files.
   *
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public void downloadFiles() throws IOException {

    Set<FileDescriptor> files = new TreeSet<FileDescriptor>();

    System.err.println(mFilteredFiles.toString());

    for (int i : mTable.getCellSelectionModel().getRowSelectionModel()) {
      files.add(mFilteredFiles.get(mTable.convertRowIndexToModel(i)));
    }

    DownloadManager.downloadAsZip(mParent, files);
  }

  /**
   * Gets the tabs model.
   *
   * @return the tabs model
   */
  public TabsModel getTabsModel() {
    return mTable.getTabsModel();
  }

  /**
   * Search files.
   *
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws ParseException
   *           the parse exception
   */
  private void searchFiles() throws IOException, ParseException {

    mFilteredFiles = new ArrayList<FileDescriptor>();

    // searchBox.addTerm(searchBox.getText());

    Repository repository = RepositoryService.getInstance().getRepository(RepositoryService.DEFAULT_REP);

    for (Sample sample : mSampleSelectionModel) {

      for (FileDescriptor file : repository.getSampleFiles(sample)) { // sample.getFiles()) {
        mFilteredFiles.add(file);
      }
    }

    displayFilteredFiles();
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
    try {
      searchFiles();
    } catch (IOException | ParseException e1) {
      e1.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.event.ChangeListener#changed(org.abh.common.event.ChangeEvent)
   */
  @Override
  public void changed(ChangeEvent e) {
    try {
      searchFiles();
    } catch (IOException | ParseException e1) {
      e1.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.event.
   * ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    try {
      downloadFiles();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }
}
