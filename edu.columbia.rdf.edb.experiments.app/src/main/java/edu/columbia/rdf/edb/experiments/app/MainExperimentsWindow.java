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
package edu.columbia.rdf.edb.experiments.app;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.Function;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.collections.UniqueArrayList;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.Io;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.io.Temp;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.core.stream.Stream;
import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.graphics.icons.FolderVectorIcon;
import org.jebtk.modern.graphics.icons.TableVectorIcon;
import org.jebtk.modern.help.GuiAppInfo;
import org.jebtk.modern.help.ModernAboutDialog;
import org.jebtk.modern.io.RecentFilesService;
import org.jebtk.modern.options.ModernOptionsDialog;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.ribbon.RibbonMenuItem;
import org.jebtk.modern.search.FilterModel;
import org.jebtk.modern.status.StatusModel;
import org.jebtk.modern.status.StatusTask;
import org.jebtk.modern.tabs.IconTabsFolderIcon;
import org.jebtk.modern.tabs.TabPanel;
import org.jebtk.modern.view.ViewModel;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernRibbonWindow;
import org.jebtk.modern.window.ModernWindow;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.DataViewField;
import edu.columbia.rdf.edb.DataViewSection;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.SampleTag;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;
import edu.columbia.rdf.edb.experiments.app.sample.SampleViewPanel;
import edu.columbia.rdf.edb.experiments.app.search.ExperimentSummaryPanel;
import edu.columbia.rdf.edb.experiments.app.search.RibbonWideLayoutSection;
import edu.columbia.rdf.edb.experiments.app.search.SearchGuiFileFilter;
import edu.columbia.rdf.edb.experiments.app.search.SearchPanel;
import edu.columbia.rdf.edb.experiments.app.search.folder.SearchFolderRibbonSection;
import edu.columbia.rdf.edb.experiments.app.search.folder.SearchFolderTreePanel;
import edu.columbia.rdf.edb.experiments.app.vfs.VfsWindow;
import edu.columbia.rdf.edb.ui.RepositorySession;
import edu.columbia.rdf.edb.ui.SampleSortService;
import edu.columbia.rdf.edb.ui.ViewPlugin;
import edu.columbia.rdf.edb.ui.ViewPluginService;
import edu.columbia.rdf.edb.ui.filter.datatypes.DataTypesModel;
import edu.columbia.rdf.edb.ui.filter.datatypes.DataTypesPanel;
import edu.columbia.rdf.edb.ui.filter.datatypes.DataTypesService;
import edu.columbia.rdf.edb.ui.filter.groups.GroupsModel;
import edu.columbia.rdf.edb.ui.filter.groups.GroupsPanel;
import edu.columbia.rdf.edb.ui.filter.groups.GroupsService;
import edu.columbia.rdf.edb.ui.filter.organisms.OrganismsService;
import edu.columbia.rdf.edb.ui.filter.results.ResultsPanel;
import edu.columbia.rdf.edb.ui.search.SearchStackElementCategory;
import edu.columbia.rdf.edb.ui.search.UserSearch;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;
import edu.columbia.rdf.matcalc.bio.app.MainBioMatCalc;

// TODO: Auto-generated Javadoc
/**
 * The Class MainExperimentsWindow.
 */
public class MainExperimentsWindow extends ModernRibbonWindow
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant SEARCH_ROW_OFFSET. */
  private static final int SEARCH_ROW_OFFSET = ModernWidget.WIDGET_HEIGHT;

  /** The Constant SEARCH_ROW_HEIGHT. */
  private static final int SEARCH_ROW_HEIGHT = 28;

  /** The Constant TYPES_OFFSET. */
  private static final int TYPES_OFFSET = 0; // 28;

  /** The m search panel. */
  private SearchPanel mSearchPanel;

  /** The m annotation panel. */
  private SampleViewPanel mAnnotationPanel;

  // private FilesPanel dataTab;

  // private DirectoryTab directoryTab;

  /** The m view model. */
  private ViewModel mViewModel = new ViewModel();

  /** The m layout view model. */
  private ViewModel mLayoutViewModel = new ViewModel();

  /** The m file view model. */
  private ViewModel mFileViewModel = new ViewModel();

  // private ModernOutlookHorizontalTabs mTabBar =
  // new ModernOutlookHorizontalTabs(mViewModel);

  // The samples selected from the folder panel, which are
  /** The m sample folder model. */
  // independent of any filterings or searches
  private SampleModel mSampleFolderModel = new SampleModel();

  /** The m sample search model. */
  private SampleModel mSampleSearchModel = new SampleModel();

  /** The m sample model. */
  // Controls which samples the user sees
  private SampleModel mSampleModel = new SampleModel();

  /** The m sample selection model. */
  // Controls which samples the user selects
  private SampleModel mSampleSelectionModel = new SampleModel();

  // private ExperimentModel searchExperimentModel = new ExperimentModel();
  // private ExperimentModel selectedExperimentModel = new ExperimentModel();

  /** The m experiment summary panel. */
  private ExperimentSummaryPanel mExperimentSummaryPanel = new ExperimentSummaryPanel(
      mSampleSelectionModel);

  /** The m sample folder panel. */
  private SearchFolderTreePanel mSampleFolderPanel; // SampleViewTreePanel

  /** The m files button. */
  private ModernButton mFilesButton = new RibbonLargeButton("Files",
      UIService.getInstance().loadIcon(FolderVectorIcon.class, 24));

  private GroupsPanel mUserGroupsPanel;

  private GroupsModel mUserGroupsModel = GroupsService.getInstance();

  private DataTypesModel mDataTypesModel = DataTypesService.getInstance();

  private OrganismsService mOrganismsModel = OrganismsService.getInstance();

  private DataTypesPanel mDataTypesPanel;

  /** The m filter model. */
  private FilterModel mFilterModel = new FilterModel();

  private ResultsPanel mResultsPanel;

  /**
   * Display an error message to the user that no experiments could be found.
   * 
   * @author Antony Holmes Holmes
   *
   */
  private class SearchError implements Runnable {

    /** The m parent. */
    private ModernWindow mParent;

    /**
     * Instantiates a new search error.
     *
     * @param parent the parent
     */
    public SearchError(ModernWindow parent) {
      mParent = parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
      ModernMessageDialog.createDialog(mParent,
          getAppInfo().getName(),
          "There are no experiments matching your search criteria.",
          MessageDialogType.INFORMATION);
    }

  }

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
   * Controls what happens when a user clicks on a folder.
   * 
   * @author Antony Holmes Holmes
   *
   */
  private class SampleSearchModelEvents implements ModernSelectionListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.event.ModernSelectionListener#selectionChanged(org.abh.
     * common.event.ChangeEvent)
     */
    @Override
    public void selectionChanged(ChangeEvent e) {
      loadSearchResults();
    }

  }

  private class UserGroupModelEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.event.ModernSelectionListener#selectionChanged(org.abh.
     * common.event.ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      search();
    }

  }

  /**
   * The Class SampleFolderModelEvents.
   */
  private class SampleFolderModelEvents implements ModernSelectionListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.ui.event.ModernSelectionListener#selectionChanged(org.abh.
     * common.event.ChangeEvent)
     */
    @Override
    public void selectionChanged(ChangeEvent e) {
      mSampleModel.set(mSampleFolderModel);
    }

  }

  /**
   * Instantiates a new main experiments window.
   *
   * @param appInfo the app info
   */
  public MainExperimentsWindow(GuiAppInfo appInfo) {
    super(appInfo);

    mAnnotationPanel = new SampleViewPanel(this, mViewModel, mLayoutViewModel,
        mSampleModel, mSampleSelectionModel, mFilterModel, mFileViewModel);

    try {
      mSampleFolderPanel = new SearchFolderTreePanel(mSampleFolderModel);
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
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

    // menuItem = new RibbonMenuItem("Download Files");
    // getRibbonMenu().addTabbedMenuItem(menuItem);

    // menuItem = new RibbonMenuItem("Export");
    // getRibbonMenu().addTabbedMenuItem(menuItem);

    menuItem = new RibbonMenuItem(Application.MENU_SAVE_SEARCH);
    getRibbonMenu().addTabbedMenuItem(menuItem);

    menuItem = new RibbonMenuItem(Application.MENU_LOAD_SEARCH);
    getRibbonMenu().addTabbedMenuItem(menuItem);

    getRibbonMenu().addDefaultItems(getAppInfo());

    getRibbonMenu().setDefaultIndex(3);

    getRibbonMenu().addClickListener(this);

    //
    // Create the ribbon
    //

    // getRibbon() = new Ribbon2();
    getRibbon().setHelpButtonEnabled(getAppInfo());

    //
    // Create the toolbars for the annotation tab
    //

    // ModernButtonWidget button;
    // button = new
    // QuickAccessButton(UIResources.getInstance().loadIcon(DownloadVectorIcon.class,
    // 16));
    // button.setClickMessage("Download");
    // button.setToolTip(new ModernToolTip("Download Files",
    // "Download the selected files to your computer in a zip archive."));
    // button.addClickListener(this);
    // addQuickAccessButton(button);

    RibbonLargeButton button = new RibbonLargeButton("Info",
        UIService.getInstance().loadIcon(TableVectorIcon.class, 24));

    button.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        try {
          createInfoTable();
        } catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | FontFormatException | IOException
            | UnsupportedLookAndFeelException e1) {
          e1.printStackTrace();
        }
      }
    });

    getRibbon().getHomeToolbar().getSection("Samples").add(button);

    // add the data view toolbars

    for (ViewPlugin plugin : ViewPluginService.instance()) {
      plugin.init(this,
          getStatusBar().getStatusModel(),
          mSampleSelectionModel);
    }

    getRibbon().getHomeToolbar().getSection("Files").add(mFilesButton);

    getRibbon().getToolbar("View").add(
        new RibbonWideLayoutSection(getRibbon(), "Layout", mLayoutViewModel));

    getRibbon().getToolbar("View")
        .add(new SearchFolderRibbonSection(this, mSampleFolderPanel));

    // getRibbon().getHomeToolbar().getSection("Files").add(mAllFilesButton);

    // add the view toolbar

    /*
     * toolbar = new RibbonToolbar("View");
     * 
     * toolbarSection = new RibbonSection("Show");
     * 
     * mAnnotatedShowSection = new RibbonShowSection(getRibbon());
     * mAnnotatedShowSection.addClickListener(this);
     * toolbar.add(mAnnotatedShowSection);
     * 
     * toolbar.add(new WindowRibbonSection(this, getRibbon()));
     * 
     * getRibbon().addToolbar(toolbar);
     */

    //
    // Data toolbar
    //

    /*
     * RibbonSection buttonContainer = new RibbonSection("Refine");
     * 
     * mSearchExperimentTitleButton.addClickListener(this);
     * mSearchExperimentTitleButton.setToolTip(new
     * ModernToolTip("Search by Experiment Title",
     * "Search experiment titles by keywords."), getRibbon());
     * 
     * Ui.setSize(mSearchExperimentTitleButton, new Dimension(60,
     * Ribbon.LARGE_BUTTON_HEIGHT));
     * buttonContainer.add(mSearchExperimentTitleButton);
     * 
     * mSearchSampleTitleButton.addClickListener(this);
     * mSearchSampleTitleButton.setToolTip(new
     * ModernToolTip("Search by Sample Title",
     * "Search sample titles by keywords."), getRibbon());
     * buttonContainer.add(mSearchSampleTitleButton);
     * 
     * vPanel = Ribbon.createToolbarButtonColumnPanel(120);
     * 
     * Ui.setSize(mGeoAccessionButton, new Dimension(120,
     * ModernWidget.WIDGET_HEIGHT)); mGeoAccessionButton.setToolTip(new
     * ModernToolTip("Search by GEO Accession",
     * "Search for samples with a GEO accession matching search terms."),
     * getRibbon()); mGeoAccessionButton.addClickListener(this);
     * vPanel.add(mGeoAccessionButton);
     * 
     * ModernScrollPopupMenu searchCriteriaPopup = new
     * ExperimentsSearchCriteriaPopup(this);
     * 
     * 
     * otherFieldButton = new ModernDropDownButton("Other",
     * UIResources.getInstance().loadIcon("search_field",
     * UIResources.ICON_SIZE_16), searchCriteriaPopup);
     * 
     * otherFieldButton.setToolTip(new ModernToolTip("Other Search Criteria",
     * "Show all search criteria by which experiments and samples can be searched."
     * ), getRibbon());
     * 
     * otherFieldButton.addClickListener(this);
     * 
     * vPanel.add(otherFieldButton);
     * 
     * buttonContainer.add(vPanel);
     * 
     * mSearchWithinButton = new RibbonLargeCheckButton2("In", "Folder",
     * UIResources.getInstance().loadIcon("search_within_folder", 32));
     * 
     * mSearchWithinButton.setToolTip(new
     * ModernToolTip("Search In Current Folder Only",
     * "Restrict searches to the current folder."), getRibbon());
     * 
     * mSearchWithinButton.addClickListener(this);
     * 
     * buttonContainer.add(mSearchWithinButton);
     * 
     * toolbar.add(buttonContainer);
     */
  }

  /**
   * Load search results.
   */
  private void loadSearchResults() {
    List<Sample> samples = CollectionUtils
        .uniquePreserveOrder(mSampleSearchModel);

    if (samples.size() > 0) {
      // If we are searching within the folder, another
      // intersection is required

      mSampleModel.set(samples);
    } else {
      SwingUtilities.invokeLater(new SearchError(this));
    }

    mStatusBar.getStatusModel()
        .setStatus("Found " + mSampleSearchModel.size() + " samples.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.window.ModernWindow#createUi()
   */
  @Override
  public final void createUi() {

    ChangeListener l = new UserGroupModelEvents();

    mUserGroupsPanel = new GroupsPanel(mUserGroupsModel);
    // Respond when a user group is changed
    mUserGroupsModel.addChangeListener(l);

    mDataTypesPanel = new DataTypesPanel(mDataTypesModel, mOrganismsModel);
    mDataTypesModel.addChangeListener(l);
    mOrganismsModel.addChangeListener(l);

    mResultsPanel = new ResultsPanel(SampleSortService.getInstance(),
        mFilterModel);

    // TabsModel groupTabsModel = new TabsModel();
    getIconTabs().addTab("Results", 'R', mResultsPanel);
    getIconTabs().addTab("Groups", 'G', mUserGroupsPanel);
    getIconTabs().addTab("Data Types", 'T', mDataTypesPanel);
    getIconTabs().addTab("Folders",
        new IconTabsFolderIcon(),
        new TabPanel("Folders", mSampleFolderPanel));

    // mViewPanel = new IconTabsPanel(groupTabsModel, 36, 22); //new
    // ModernComponent(new IconTabsPanel(groupTabsModel, 30, 20),
    // ModernWidget.DOUBLE_BORDER);

    // Show the column groups by default
    getIconTabs().changeTab(0);

    // mViewPanel = new ModernComponent();

    // mViewPanel.setBody(mSampleFolderPanel);
    // mViewPanel.setFooter(mTabBar);

    // mCategoryTreePanel = new CategoryDirectoryTreePanel(mSampleFolderModel);

    //
    // Create the annotation tab.
    //

    mSearchPanel = new SearchPanel(this);

    // The search panel can mimick some of the click events of the UI
    // to trigger a search etc
    mSearchPanel.addClickListener(this);

    ModernComponent panel = new ModernComponent();
    panel.setHeader(mSearchPanel);
    panel.setBody(mAnnotationPanel);
    panel.setBorder(ModernWidget.BORDER);
    
    setCard(panel);

    // panel.add(searchPanel, BorderLayout.PAGE_START);
    // panel.setBody(mContentPane);
    // panel.add(mTabBar, BorderLayout.PAGE_END);
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

    mSearchPanel.addUserSearchEntry(UserSearchEntry.createDefaultSearchEntry());

    if (SettingsService.getInstance()
        .getAsBool("edb.experiments.default-search")) {
      // perform an initial search with no filter to return all experiments so
      // that
      // the
      // the search window shows everything by default
      search();
    }

    // Default to the samples tab
    // mTabsModel.changeTab(0);
    mViewModel.setView(SettingsService.getInstance()
        .getAsString("edb.experiments.default-view"));

    // Set the default layout
    mLayoutViewModel.setView(SettingsService.getInstance()
        .getAsString("edb.experiments.default-layout-view"));

    // Add some default panes so user can search
    // addFoldersPane();
    // addExperimentSummaryPane();

    setSize(1080, 720);

    UI.centerWindowToScreen(this);

    mSampleFolderModel.addSelectionListener(new SampleFolderModelEvents());
    mSampleSearchModel.addSelectionListener(new SampleSearchModelEvents());

    mFilesButton.addClickListener(new ModernClickListener() {
      @Override
      public void clicked(ModernClickEvent e) {
        showFiles();
      }
    });

    /*
     * mAllFilesButton.addClickListener(new ModernClickListener() {
     * 
     * @Override public void clicked(ModernClickEvent e) { showAllFiles(); }});
     */
  }

  /**
   * Show files.
   */
  private void showFiles() {
    VfsWindow window = new VfsWindow(this, mSampleSelectionModel);

    window.setVisible(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public final void clicked(ModernClickEvent e) {
    if (e.getSource().equals(mSearchPanel)) {
      resizeSeachPanel();

      if (e.getMessage().equals(UI.MENU_SEARCH)) {
        search();
      }
    } else if (e.getMessage().equals(Application.MENU_SAVE_SEARCH)) {
      try {
        saveUserSearch();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (e.getMessage().equals(Application.MENU_LOAD_SEARCH)) {
      try {
        loadUserSearch();
      } catch (ParserConfigurationException e1) {
        e1.printStackTrace();
      } catch (SAXException e1) {
        e1.printStackTrace();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (e.getMessage().equals(UI.MENU_EXIT)) {
      close();
    } else if (e.getMessage().equals(Application.MENU_RELOAD_CACHE)) {
      try {
        reloadCache();
      } catch (SAXException e1) {
        e1.printStackTrace();
      } catch (IOException e1) {
        e1.printStackTrace();
      } catch (ParserConfigurationException e1) {
        e1.printStackTrace();
      } catch (KeyManagementException e1) {
        e1.printStackTrace();
      } catch (NoSuchAlgorithmException e1) {
        e1.printStackTrace();
      } catch (FontFormatException e1) {
        e1.printStackTrace();
      } catch (ClassNotFoundException e1) {
        e1.printStackTrace();
      } catch (InstantiationException e1) {
        e1.printStackTrace();
      } catch (IllegalAccessException e1) {
        e1.printStackTrace();
      } catch (UnsupportedLookAndFeelException e1) {
        e1.printStackTrace();
      }
    } else if (e.getMessage().equals(UI.MENU_OPTIONS)) {
      ModernOptionsDialog.setVisible(this, getAppInfo());
    } else if (e.getMessage().equals(UI.MENU_ABOUT)) {
      ModernAboutDialog.show(this, getAppInfo());
    } else if (e.getMessage().equals("categories_pane")) {
      // addCategoryPane();
    } else if (e.getMessage().equals("summary_pane")) {
      addExperimentSummaryPane();
    } else {
      // do nothing
    }
  }

  /**
   * Reload cache.
   *
   * @throws SAXException the SAX exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException the parser configuration exception
   * @throws KeyManagementException the key management exception
   * @throws NoSuchAlgorithmException the no such algorithm exception
   * @throws FontFormatException the font format exception
   * @throws ClassNotFoundException the class not found exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   * @throws UnsupportedLookAndFeelException the unsupported look and feel
   *           exception
   */
  private void reloadCache()
      throws SAXException, IOException, ParserConfigurationException,
      KeyManagementException, NoSuchAlgorithmException, FontFormatException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      UnsupportedLookAndFeelException {

    ModernDialogStatus status = ModernMessageDialog.createDialog(this,
        getAppInfo().getName(),
        "Are you sure you want to reload the cache?",
        MessageDialogType.INFORMATION_YES_NO);

    if (status != ModernDialogStatus.OK) {
      return;
    }

    // Io.clearDirectory(new File("cache/experiments"));

    Io.delete(RepositorySession.SESSION_FILE);

    RepositorySession.clear();

    status = ModernMessageDialog.createDialog(this,
        getAppInfo().getName(),
        MessageDialogType.INFORMATION_YES_NO,
        "Reloading the cache requires " + getAppInfo().getName()
            + " to be restarted.",
        "Do you want to restart now?");

    if (status != ModernDialogStatus.OK) {
      return;
    }

    // We do not want the application to exit
    close(false);

    MainExperiments.main();
  }

  /**
   * Adjusts the height of the search bar depending on how many criteria the
   * user has added.
   */
  private void resizeSeachPanel() {
    // annotationPanel.setDividerLocation(SEARCH_ROW_OFFSET + SEARCH_ROW_HEIGHT
    // *
    // Math.max(1, searchPanel.getSearch().size()));

    mSearchPanel.setPreferredSize(new Dimension(Short.MAX_VALUE,
        SEARCH_ROW_OFFSET + TYPES_OFFSET + SEARCH_ROW_HEIGHT
            * Math.max(1, mSearchPanel.getSearch().size())));

    validate();
    repaint();
  }

  /**
   * Download files.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void downloadFiles() throws IOException {
    /*
     * switch (mTabsModel.getSelectedIndex()) { case 0: case 2:
     * downloadSampleFiles(); break; case 1: dataTab.downloadFiles(); break; }
     */
  }

  /**
   * Gets the search.
   *
   * @return the search
   */
  private UserSearch getSearch() {
    return mSearchPanel.getSearch();
  }

  /**
   * Loads a user's saved search from an XML file.
   *
   * @throws ParserConfigurationException the parser configuration exception
   * @throws SAXException the SAX exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void loadUserSearch()
      throws ParserConfigurationException, SAXException, IOException {
    JFileChooser fc = new JFileChooser();

    FileFilter filter = new SearchGuiFileFilter();

    fc.addChoosableFileFilter(filter);
    fc.setAcceptAllFileFilterUsed(false);

    fc.setCurrentDirectory(RecentFilesService.getInstance().getPwd().toFile());

    fc.setFileFilter(filter);

    if (fc.showOpenDialog(this) == JFileChooser.CANCEL_OPTION) {
      return;
    }

    File file = fc.getSelectedFile();

    // UserSearch search = UserSearch.parse(file);

    /*
     * SAXParserFactory factory = SAXParserFactory.newInstance(); SAXParser
     * saxParser = factory.newSAXParser();
     * 
     * UserSearchXmlHandler handler = new UserSearchXmlHandler();
     * 
     * saxParser.parse(file.getAbsolutePath(), handler);
     * 
     * UserSearch search = handler.getSearch();
     */

    // setUserSearch(search);

    // Automatically search for items
    search();
  }

  /**
   * Saves a user's search as an XML file.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void saveUserSearch() throws IOException {
    JFileChooser fc = new JFileChooser();

    FileFilter filter = new SearchGuiFileFilter();

    fc.addChoosableFileFilter(filter);
    fc.setAcceptAllFileFilterUsed(false);

    fc.setCurrentDirectory(RecentFilesService.getInstance().getPwd().toFile());

    fc.setFileFilter(filter);

    if (fc.showSaveDialog(this) == JFileChooser.CANCEL_OPTION) {
      return;
    }

    java.nio.file.Path file = fc.getSelectedFile().toPath();

    file = PathUtils.addExtension(file, "search");

    if (FileUtils.exists(file)) {
      ModernDialogStatus status = ModernMessageDialog
          .createFileReplaceDialog(this, file);

      if (status == ModernDialogStatus.CANCEL) {
        return;
      }
    }

    // UserSearch.write(getSearch(), file);

    /*
     * XmlDocument doc = new XmlDocument();
     * 
     * doc.addChild(getSearch().toXml());
     * 
     * doc.writeXml(file);
     */
  }

  /*
   * private void downloadSampleFiles() throws IOException, ParseException {
   * List<Sample> samples = mAnnotationPanel.getSelectedSamples();
   * 
   * if (samples.size() == 0) { ModernMessageDialog.createDialog(this,
   * getAppInfo().getName(), "You must select at least one sample.",
   * MessageDialogType.WARNING);
   * 
   * return; }
   * 
   * SampleDownloadDialog dialog = new SampleDownloadDialog(this);
   * 
   * dialog.setVisible(true);
   * 
   * if (dialog.getStatus() == ModernDialogStatus.CANCEL) { return; }
   * 
   * Set<FileDescriptor> files = new HashSet<FileDescriptor>();
   * 
   * //searchBox.addTerm(searchBox.getText());
   * 
   * boolean cel = dialog.getDownloadCel(); boolean chp =
   * dialog.getDownloadCHP(); boolean mas5 = dialog.getDownloadMAS5(); boolean
   * rma = dialog.getDownloadRMA();
   * 
   * Repository repository = RepositoryService.getInstance().getRepository();
   * 
   * for (FileDescriptor file : repository.getSampleFiles(samples)) {
   * 
   * boolean download = false;
   * 
   * if (cel && file.getName().toLowerCase().endsWith("cel")) { download = true;
   * }
   * 
   * if (chp && file.getName().toLowerCase().endsWith("chp")) { download = true;
   * }
   * 
   * if (mas5 && file.getName().toLowerCase().endsWith(DownloadManager.MAS5)) {
   * download = true; }
   * 
   * if (rma && file.getName().toLowerCase().endsWith(DownloadManager.RMA)) {
   * download = true; }
   * 
   * if (download) { files.add(file); } }
   * 
   * if (files.size() == 0) { ModernMessageDialog.createDialog(this,
   * getAppInfo().getName(),
   * "You must select at least one type of file to download.",
   * MessageDialogType.WARNING);
   * 
   * downloadSampleFiles();
   * 
   * return; }
   * 
   * DownloadManager.downloadAsZip(this, files); }
   */

  /*
   * private void showSDRF() throws IOException { List<Sample> samples =
   * annotationPanel.getSelectedSamples();
   * 
   * if (samples.size() == 0) { ModernMessageDialog.createDialog(this,
   * getAppInfo().getName(), "You must select at least one sample.",
   * MessageDialogType.WARNING);
   * 
   * return; }
   * 
   * SDRF sdrf = new SDRF();
   * 
   * sdrf.showSDRFTable(this, samples); }
   */

  /**
   * Sets the user search.
   *
   * @param search the new user search
   */
  private void setUserSearch(UserSearch search) {
    mSearchPanel.setUserSearch(search);
  }

  /**
   * Search.
   */
  private void search() {

    // start a threaded search
    // (new Thread(new SearchTask(this))).start();

    // determine what to search for
    Deque<SearchStackElementCategory> searchStack;

    try {
      searchStack = mSearchPanel.getSearchStack();
    } catch (Exception e) {
      e.printStackTrace();

      ModernMessageDialog.createDialog(this,
          getAppInfo().getName(),
          "Your search query is badly formed.",
          MessageDialogType.WARNING);

      return;
    }

    // ModernClickListener searchTask = new SearchTask(this, searchStack);
    // ModernClickListener searchTask = new SearchCategoryTask(this,
    // searchStack);

    // SearchCategoryTask searchTask = new
    // SearchCategoryTask(mSampleSearchModel,
    // searchStack,
    // mSearchPanel.getDataTypes(),
    // mSearchPanel.getOrganisms(),
    // mUserGroupsModel.getGroups());

    SearchCategoryTask searchTask = new SearchCategoryTask(mSampleSearchModel,
        searchStack, mDataTypesModel.getTypes(), mOrganismsModel.getOrganisms(),
        mUserGroupsModel.getGroups());

    searchTask.execute();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.window.ModernWindow#close()
   */
  @Override
  public void close() {
    Temp.deleteTempFiles();

    super.close();
  }

  /**
   * We take control of the tab changing so we can adjust the ribbon and other
   * features as well as the tab.
   * 
   */
  /*
   * private void changeTab() { if (mTabsModel.getSelectedTab() == null) {
   * return; }
   * 
   * if (mTabsModel.getSelectedTab().getName().equals("Files")) {
   * //getRibbon().changeView("data");
   * 
   * //contentPane.getModel().clear();
   * 
   * mContentPane.getModel().setCenterTab(new CenterTab(dataTab));
   * 
   * //addExperimentsPane();
   * 
   * 
   * setSubTitle("Files"); } else { //getRibbon().changeView("annotation");
   * 
   * //viewPanel.changeView(annotationPanel);
   * 
   * //contentPane.getModel().clear();
   * 
   * ModernPanel panel = new ModernPanel();
   * 
   * panel.add(mSearchPanel, BorderLayout.PAGE_START);
   * panel.add(mAnnotationPanel, BorderLayout.CENTER);
   * 
   * 
   * mContentPane.getModel().setCenterTab(new CenterTab(panel));
   * 
   * //addExperimentsPane(); //addExperimentSummaryPane();
   * 
   * setSubTitle("Annotation"); } }
   */

  /*
   * private void addCategoryPane() { if
   * (mContentPane.getModel().contains("Categories")) { return; }
   * 
   * if (mContentPane.getModel().contains("Folders")) {
   * mContentPane.getModel().removeTab("Folders"); }
   * 
   * mContentPane.getModel().addLeft(new SizableContentPane("Categories", new
   * CloseableHTab("Categories", mCategoryTreePanel, mContentPane.getModel()),
   * 500, 300, 600)); }
   */

  /*
   * private void addFoldersPane() { if
   * (getTabsPane().getModel().containsTab("Folders")) { return; }
   * 
   * getTabsPane().addLeftTab("Folders", mViewPanel, 200, 100, 500); }
   */

  /**
   * Adds the experiment summary pane.
   */
  private void addExperimentSummaryPane() {
    if (tabsPane().tabs().right().contains("Experiment")) {
      return;
    }

    tabsPane()
        .tabs().right().add("Experiment", mExperimentSummaryPanel, 250, 200, 500);
  }

  /**
   * Creates a table of all the sample field data.
   *
   * @throws ClassNotFoundException the class not found exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   * @throws FontFormatException the font format exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws UnsupportedLookAndFeelException the unsupported look and feel
   *           exception
   */
  private void createInfoTable() throws ClassNotFoundException,
      InstantiationException, IllegalAccessException, FontFormatException,
      IOException, UnsupportedLookAndFeelException {
    List<Sample> samples = mSampleSelectionModel.getItems();

    if (samples.size() == 0) {
      ModernMessageDialog.createWarningDialog(this,
          "You must select at least one sample.");

      return;
    }

    // Determine all fields

    List<DataViewField> fields = new UniqueArrayList<DataViewField>();

    Map<DataViewField, Integer> indexMap = new HashMap<DataViewField, Integer>();

    for (Sample sample : samples) {
      DataView dataView = ViewPluginService.instance().getView(sample)
          .getDataView();

      for (DataViewSection viewSection : dataView) {
        for (DataViewField field : viewSection) {
          if (!fields.contains(field)) {
            fields.add(field);
            indexMap.put(field, indexMap.size());
          }
        }
      }
    }

    DataFrame matrix = DataFrame.createDataFrame(samples.size(), fields.size());

    List<String> names = Stream.of(fields)
        .map(new Function<DataViewField, String>() {
          @Override
          public String apply(DataViewField field) {
            return field.getName();
          }
        }).toList();

    matrix.setColumnNames(names);

    int row = 0;

    for (Sample sample : samples) {
      DataView dataView = ViewPluginService.instance().getView(sample)
          .getDataView();

      for (DataViewSection viewSection : dataView) {
        for (DataViewField field : viewSection) {
          int column = indexMap.get(field);

          SampleTag tag = sample.getTag(field);

          if (tag != null) {
            matrix.set(row, column, sample.getTag(field).getValue());
          } else {
            matrix.set(row, column, TextUtils.NA);
          }
        }
      }

      ++row;
    }

    matrix.setName("Sample Expression");

    MainBioMatCalc.openMatrix(matrix);
  }
}
