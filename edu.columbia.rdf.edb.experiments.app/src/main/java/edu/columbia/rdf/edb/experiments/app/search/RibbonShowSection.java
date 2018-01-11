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

import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.Raster32Icon;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.widget.tooltip.ModernToolTip;
import org.jebtk.modern.widget.tooltip.ModernToolTipModel;

// TODO: Auto-generated Javadoc
/**
 * Standardized ribbon menu section for providing basic cut, copy and paste
 * functionality to the currently highlighted control that supports clipboard
 * operations.
 *
 * @author Antony Holmes Holmes
 *
 */
public class RibbonShowSection extends RibbonSection
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The experiments button. */
  private ModernButton experimentsButton;

  /** The categories button. */
  private ModernButton categoriesButton;

  /** The summary button. */
  private ModernButton summaryButton;

  /**
   * Instantiates a new ribbon show section.
   *
   * @param ribbon the ribbon
   * @param tooltipModel the tooltip model
   */
  public RibbonShowSection(Ribbon ribbon, ModernToolTipModel tooltipModel) {
    super(ribbon, "Show");

    experimentsButton = new RibbonLargeButton("Folders",
        new Raster32Icon(new ExperimentsPane32VectorIcon()));
    // Ui.setSize(experimentsButton,
    // ModernTheme.getInstance().getClass("ribbon-large-button").getDimension("extra-wide"));
    experimentsButton.setToolTip(new ModernToolTip("Show Folders Pane",
        "Display the folders in a tree."), tooltipModel);
    experimentsButton.setClickMessage("folders_pane");
    experimentsButton.addClickListener(this);
    add(experimentsButton);

    categoriesButton = new RibbonLargeButton("Categories",
        new Raster32Icon(new CategoryPane32VectorIcon()));
    categoriesButton.addClickListener(this);
    categoriesButton.setToolTip(new ModernToolTip("Show Categories Pane",
        "Display the category directory."), tooltipModel);
    categoriesButton.setClickMessage("categories_pane");
    // Ui.setSize(categoriesButton,
    // ModernTheme.getInstance().getClass("ribbon-large-button").getDimension("extra-wide"));
    add(categoriesButton);

    summaryButton = new RibbonLargeButton("Summary",
        new Raster32Icon(new Summary32VectorIcon()));
    summaryButton.setToolTip(new ModernToolTip("Show Summary Pane",
        "Display the experiment summary."), tooltipModel);
    // Ui.setSize(summaryButton,
    // ModernTheme.getInstance().getClass("ribbon-large-button").getDimension("wide"));
    summaryButton.setClickMessage("summary_pane");
    summaryButton.addClickListener(this);
    add(summaryButton);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  public void clicked(ModernClickEvent e) {
    this.fireClicked(new ModernClickEvent(this, e.getMessage()));
  }
}
