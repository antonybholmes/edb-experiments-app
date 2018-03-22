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
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.NetworkFileException;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.UI;
import org.jebtk.modern.UIService;
import org.jebtk.modern.clipboard.ClipboardRibbonSection;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.help.RibbonPanelProductInfo;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.ribbon.RibbonMenuItem;
import org.jebtk.modern.status.ModernStatusBar;
import org.jebtk.modern.tooltip.ModernToolTip;
import org.jebtk.modern.window.ModernRibbonWindow;
import org.jebtk.modern.window.ModernWindowConstructor;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.ExperimentsInfo;
import edu.columbia.rdf.edb.experiments.app.modules.microarray.MicroarrayExpressionData;
import edu.columbia.rdf.edb.ui.ViewPluginService;
import edu.columbia.rdf.edb.ui.microarray.Mas5Dialog;
import edu.columbia.rdf.edb.ui.microarray.MicroarrayNormalizationType;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleWindow.
 */
public class SampleWindow extends ModernRibbonWindow
    implements ModernWindowConstructor, ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The export button. */
  private RibbonLargeButton exportButton = new RibbonLargeButton("Export",
      "Sample",
      UIService.getInstance().loadIcon("export", UIService.ICON_SIZE_32));

  /** The download button. */
  private RibbonLargeButton downloadButton = new RibbonLargeButton("Download",
      "Files", UIService.getInstance().loadIcon("zip", UIService.ICON_SIZE_32));

  /** The m status bar. */
  private ModernStatusBar mStatusBar = new ModernStatusBar();

  /** The m sample. */
  private Sample mSample;

  /** The sample panel. */
  private ModernComponent samplePanel;

  /**
   * Instantiates a new sample window.
   *
   * @param sample the sample
   */
  public SampleWindow(Sample sample) {
    super(new ExperimentsInfo());

    setTitle(sample.getName() + " - Sample - " + getAppInfo().getName());

    mSample = sample;

    createRibbon();

    createMenus();

    createUi();

    init();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.window.ModernWindowConstructor#createRibbon()
   */
  public final void createRibbon() {

    // RibbongetRibbonMenu() getRibbonMenu() = new RibbongetRibbonMenu()(2);

    getRibbonMenu().setDefaultIndex(2);

    RibbonMenuItem menuItem;

    menuItem = new RibbonMenuItem(UI.MENU_INFO);

    getRibbonMenu().addTabbedMenuItem(menuItem,
        new RibbonPanelProductInfo(getAppInfo()));

    menuItem = new RibbonMenuItem(UI.MENU_OPTIONS);
    getRibbonMenu().addTabbedMenuItem(menuItem);

    menuItem = new RibbonMenuItem(UI.MENU_CLOSE,
        UIService.getInstance().loadIcon("exit", UIService.ICON_SIZE_16));
    menuItem.addClickListener(this);
    getRibbonMenu().addTabbedMenuItem(menuItem);

    // ribbon = new Ribbon();

    getRibbon().setHelpButtonEnabled(getAppInfo());

    // download

    downloadButton.addClickListener(this);
    // downloadButton.setCanvasSize(new Dimension(70,
    // getRibbon().DEFAULT_BUTTON_HEIGHT));
    // toolbar.getComponentGroup().add(downloadButton);

    getRibbon().getHomeToolbar().add(new ClipboardRibbonSection(getRibbon()));

    downloadButton.setToolTip("Download Files",
            "Download the CEL and CHP files for this sample in a zip archive.");
    getRibbon().getHomeToolbar().getSection("data").add(downloadButton);

    exportButton.setToolTip("Export Sample",
        "Export the information on the sample as a text file. This does not include CEL, CHP or expression data.");
    getRibbon().getHomeToolbar().getSection("data").add(exportButton);

    // put it all together
    getRibbon().setSelectedIndex(1);

    // showAllButton.addClickListener(this);
    // toolbar.add(showAllButton);
    // toolbar.add(displaySearchRecordsOnly);

    // setRibbon(ribbon, getRibbonMenu());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.window.ModernWindow#createUi()
   */
  public final void createUi() {

    ModernPanel content = new ModernPanel();

    samplePanel = ViewPluginService.instance().getSamplePanel(mSample);

    content.add(samplePanel, BorderLayout.CENTER);

    content.setBorder(ModernPanel.BORDER);

    setBody(content);

    setFooter(mStatusBar);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.window.ModernWindow#init()
   */
  public final void init() {
    exportButton.addClickListener(this);

    setSize(960, 640);

    UI.centerWindowToScreen(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  public final void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.MENU_CLOSE)) {
      close();
    }
  }

  /**
   * Show expression data.
   *
   * @param type the type
   * @throws NetworkFileException the network file exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  private void showExpressionData(MicroarrayNormalizationType type)
      throws NetworkFileException, IOException, ParseException {

    List<Sample> samples = new ArrayList<Sample>();

    samples.add(mSample);

    MicroarrayExpressionData expressionData = new MicroarrayExpressionData();

    if (type == MicroarrayNormalizationType.MAS5) {
      Mas5Dialog dialog = new Mas5Dialog(this);

      dialog.setVisible(true);

      if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
        return;
      }

      expressionData.showTables(this,
          samples,
          type,
          dialog.getColumns(),
          null,
          true,
          mStatusBar.getStatusModel());
    } else {
      // we all all columns since there is only the data column with rma
      expressionData.showTables(this,
          samples,
          type,
          CollectionUtils.asList(true),
          null,
          true,
          mStatusBar.getStatusModel());
    }
  }
}
