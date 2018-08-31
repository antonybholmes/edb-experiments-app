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
package edu.columbia.rdf.edb.experiments.app.vfs;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import org.jebtk.modern.UI;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.help.ModernAboutDialog;
import org.jebtk.modern.options.ModernOptionsDialog;
import org.jebtk.modern.ribbon.QuickAccessButton;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.ribbon.RibbonMenuItem;
import org.jebtk.modern.status.StatusModel;
import org.jebtk.modern.status.StatusTask;
import org.jebtk.modern.tabs.SizableTab;
import org.jebtk.modern.tooltip.ModernToolTip;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.window.ModernRibbonWindow;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.files.FileViewRibbonPanel;
import edu.columbia.rdf.edb.experiments.app.files.FilesModel;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;

// TODO: Auto-generated Javadoc
/**
 * The Class VfsWindow.
 */
public class VfsWindow extends ModernRibbonWindow
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m file view model. */
  private ViewModel mFileViewModel = new ViewModel();

  /** The m files model. */
  private FilesModel mFilesModel = new FilesModel();


  /** The m view panel. */
  private VfsFilesTreePanel mViewPanel;

  /** The m files panel. */
  private VfsFilesPanel mFilesPanel;

  /** The m download button. */
  private ModernButton mDownloadButton = new RibbonLargeButton("Download",
      AssetService.getInstance().loadIcon("download", 24));

  /**
   * Download files in the background.
   * 
   * @author Antony Holmes Holmes
   *
   */
  private class DownloadTask extends StatusTask {

    /**
     * Instantiates a new download task.
     *
     * @param statusModel the status model
     */
    public DownloadTask(StatusModel statusModel) {
      super(statusModel);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.SwingWorker#doInBackground()
     */
    @Override
    public Void doInBackground() {

      publish("Downloading files...");

      try {
        downloadFiles();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.ui.status.StatusTask#done()
     */
    @Override
    public void done() {
      publish(UI.STATUS_READY);
    }
  }

  /**
   * Instantiates a new vfs window.
   *
   * @param parent the parent
   */
  public VfsWindow(ModernWindow parent) {
    this(parent, new SampleModel());
  }

  /**
   * Instantiates a new vfs window.
   *
   * @param parent the parent
   * @param selectionModel the selection model
   */
  public VfsWindow(ModernWindow parent, SampleModel selectionModel) {
    super(parent.getAppInfo());

    setSubTitle("File Explorer");

    // First organize by experiment
    Map<Experiment, Set<Sample>> sortByExperiment = Experiment
        .sortSamplesByExperiment(selectionModel.getItems());

    mViewPanel = new VfsFilesTreePanel(mFilesModel, sortByExperiment);

    mFilesPanel = new VfsFilesPanel(this, mFilesModel, mFileViewModel);

    try {
      mFilesPanel.setFiles(selectionModel);
    } catch (IOException e) {
      e.printStackTrace();
    }

    createMenus();

    createRibbon();

    createUi();

    createMenus();

    init();
  }

  /**
   * Creates the ribbon.
   */
  public final void createRibbon() {

    //
    // Create the ribbon menu
    //

    // RibbongetRibbonMenu() getRibbonMenu() = new RibbongetRibbonMenu()(6);

    RibbonMenuItem menuItem;

    menuItem = new RibbonMenuItem("Download Files");
    getRibbonMenu().addTabbedMenuItem(menuItem);

    getRibbonMenu().addDefaultItems(getAppInfo());

    getRibbonMenu().setDefaultIndex(2);

    getRibbonMenu().addClickListener(this);

    //
    // Create the ribbon
    //

    // getRibbon() = new Ribbon2();
    getRibbon().setHelpButtonEnabled(getAppInfo());

    //
    // Create the toolbars for the annotation tab
    //

    ModernButtonWidget button;

    button = new QuickAccessButton(
        AssetService.getInstance().loadIcon("download", 16));
    button.setClickMessage("Download");
    button.setToolTip(new ModernToolTip("Download Files",
        "Download the selected files to your computer in a zip archive."));
    button.addClickListener(this);
    addQuickAccessButton(button);

    /*
     * button = new ModernButton(Resources.getInstance().loadIcon("save", 16));
     * button.setActionCommand("Export"); button.setToolTip(new
     * ModernToolTip("Export",
     * "Export the information on the currently selected samples to a text file. This does not include CEL, CHP, or expression data."
     * )); button.addClickListener(this); ribbon.addQuickAccessButton(button);
     */

    /*
     * ModernPopupMenu exportMenu = new ModernPopupMenu();
     * 
     * exportMenu.add(new ModernTwoLineMenuItem("Experiments",
     * "Export experiment information.", new
     * ModernFile32VectorIcon(ColorUtil.decodeHtmlColor("#aaccff"))));
     * exportMenu.add(new ModernTwoLineMenuItem("Samples",
     * "Export sample information.", new
     * ModernFile32VectorIcon(ColorUtil.decodeHtmlColor("#ffaaaa"))));
     * 
     * button = new
     * ModernDropDownMenuButton(UIResources.getInstance().loadScalableIcon(
     * SaveVectorIcon.class, 16), exportMenu); button.setToolTip(new
     * ModernToolTip("Export",
     * "Export the information on the currently selected samples to a text file. This does not include CEL, CHP, or expression data."
     * )); button.addClickListener(this); ribbon.addQuickAccessButton(button);
     */

    // toolbarSection = new ClipboardRibbonSection(getRibbon());
    // toolbar.add(toolbarSection);

    // mSampleViewSection = new SampleFolderRibbonSection(mSampleViewModel,
    // getRibbon());
    // toolbar.add(mSampleViewSection);

    mDownloadButton.setClickMessage("Download");
    mDownloadButton.setToolTip(new ModernToolTip("Download Files",
        "Download the selected files to your computer in a zip archive."));
    mDownloadButton.addClickListener(this);
    getRibbon().getToolbar("Files").getSection("Files").add(mDownloadButton);
    getRibbon().getToolbar("Files")
        .addSection(new FileViewRibbonPanel(getRibbon(), mFileViewModel));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.window.ModernWindow#createUi()
   */
  @Override
  public final void createUi() {

    // mCategoryTreePanel = new CategoryDirectoryTreePanel(mSampleFolderModel);

    //
    // Create the annotation tab.
    //

    // default to showing the user a blank search field to indicate that
    // they can search or narrow down the criteria.

    // searchPanel.setBorder(BorderService.getInstance().createTopBottomBorder(ModernTheme.getInstance().getClass("widget").getInt("padding")));

    // RibbonBarPanel topPanel = new RibbonBarPanel();

    // ModernPanel topPanel = new ModernComponent();

    // Box topPanel = new HBoxPanel();
    // searchPanel.setBorder(ModernPanel.BORDER);

    // topPanel.add(Ui.createHorizontalGap(300));

    // JLabel label = new ModernLabel("Search for");

    /// label.setBorder(BorderService.getInstance().createBorder(10));
    // label.setAlignmentY(TOP_ALIGNMENT);
    // topPanel.add(label);
    // topPanel.add(Ui.createHorizontalGap(20));

     // panel.add(searchPanel, BorderLayout.PAGE_START);
    // panel.setBody(mContentPane);
    // panel.add(mTabBar, BorderLayout.PAGE_END);

    setCard(mFilesPanel);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.window.ModernWindow#init()
   */
  public void init() {
    // mTabsModel.addTabListener(this);

    // they both need to communicate with each other
    // experimentSortRibbonSection.addClickListener(experimentsPanel);

    // Add some default panes so user can search
    addFoldersPane();
    // addExperimentSummaryPane();

    setSize(1080, 720);

    UI.centerWindowToScreen(this);

    // mSearchWithinButton.setSelected(SettingsService.getInstance().getBool("edb.experiments.search.within-current-folder"));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  public final void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.MENU_EXIT)) {
      close();
    } else if (e.getMessage().equals(UI.MENU_OPTIONS)) {
      ModernOptionsDialog.setVisible(this, getAppInfo());
    } else if (e.getMessage().equals(UI.MENU_ABOUT)) {
      ModernAboutDialog.show(this, getAppInfo());
    } else if (e.getMessage().equals("folders_pane")) {
      addFoldersPane();
    } else if (e.getMessage().toLowerCase().contains("download")) {
      try {
        downloadFiles();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else {
      // do nothing
    }
  }

  /**
   * Download files.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void downloadFiles() throws IOException {
    mFilesPanel.downloadFiles();
  }

  /**
   * Adds the folders pane.
   */
  private void addFoldersPane() {
    tabsPane().tabs().left().add(
        new SizableTab("Folders", mViewPanel, 300, 100, 600));
  }
}
