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

import org.jebtk.modern.AssetService;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.menu.ModernPopupMenu;
import org.jebtk.modern.menu.ModernTwoLineMenuItem;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.ribbon.RibbonLargeDropDownButton;
import org.jebtk.modern.ribbon.RibbonSection;

/**
 * Allows user to customize how the experiments are listed.
 *
 * @author Antony Holmes Holmes
 *
 */
public class DataExportRibbonSection extends RibbonSection
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The download button. */
  private ModernButtonWidget downloadButton = new RibbonLargeButton("Download",
      "Files", AssetService.getInstance().loadIcon("download", 32));

  /** The export button. */
  private RibbonLargeDropDownButton exportButton;

  /**
   * Instantiates a new data export ribbon section.
   *
   * @param ribbon the ribbon
   */
  public DataExportRibbonSection(Ribbon ribbon) {
    super(ribbon, "Export");

    ModernPopupMenu exportMenu = new ModernPopupMenu();

    exportMenu
        .add(new ModernTwoLineMenuItem("Samples", "Export sample information.",
            AssetService.getInstance().loadIcon("file", 32)));
    exportMenu.add(new ModernTwoLineMenuItem("Experiments",
        "Export experiment information.",
        AssetService.getInstance().loadIcon("file", 32)));

    exportButton = new RibbonLargeDropDownButton("Export",
        AssetService.getInstance().loadIcon("save", 32), exportMenu);
    exportButton.addClickListener(this);
    exportButton.setToolTip("Export",
        "Export the information on the currently selected samples to a text file. This does not include CEL, CHP, or expression data.");
    add(exportButton);

    downloadButton.addClickListener(this);
    downloadButton.setToolTip("Download Files",
        "Download the selected files to your computer in a zip archive.");
    downloadButton.setClickMessage("download");
    add(downloadButton);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  public void clicked(ModernClickEvent e) {
    fireClicked(new ModernClickEvent(this, e.getMessage()));
  }
}
