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

import javax.swing.Box;

import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernCheckButton;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.view.ViewModel;

/**
 * Standardized ribbon menu section for providing basic cut, copy and paste
 * functionality to the currently highlighted control that supports clipboard
 * operations.
 *
 * @author Antony Holmes Holmes
 *
 */
public class RibbonViewSection extends RibbonSection
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The list button. */
  private ModernCheckButton listButton = new ModernCheckButton("List",
      UIService.getInstance().loadIcon("view_list", UIService.ICON_SIZE_16));

  /** The tiles button. */
  private ModernCheckButton tilesButton = new ModernCheckButton("Tiles",
      UIService.getInstance().loadIcon("view_tiles", UIService.ICON_SIZE_16));

  /** The details button. */
  private ModernCheckButton detailsButton = new ModernCheckButton("Details",
      UIService.getInstance().loadIcon("view_details", UIService.ICON_SIZE_16));

  /** The m view model. */
  private ViewModel mViewModel;

  /**
   * Instantiates a new ribbon view section.
   *
   * @param ribbon the ribbon
   * @param viewModel the view model
   */
  public RibbonViewSection(Ribbon ribbon, ViewModel viewModel) {
    super(ribbon, "View");

    mViewModel = viewModel;

    Box container = Ribbon.createToolbarButtonColumnPanel();

    container.add(detailsButton);
    container.add(listButton);
    container.add(tilesButton);

    add(container);

    listButton.setToolTip("List", "Display items in a list.");
    listButton.addClickListener(this);

    tilesButton.setToolTip("Tiles", "Display items as tiles.");
    tilesButton.addClickListener(this);

    detailsButton
        .setToolTip("Details", "Display items in a detailed list.");
    detailsButton.addClickListener(this);

    ModernButtonGroup group = new ModernButtonGroup();

    group.add(listButton);
    group.add(detailsButton);
    group.add(tilesButton);

    detailsButton.doClick();
  }

  /**
   * Enabled or disable all the controls on the panel.
   *
   * @param enabled the new enabled
   */
  @Override
  public final void setEnabled(boolean enabled) {
    detailsButton.setEnabled(enabled);
    tilesButton.setEnabled(enabled);
    listButton.setEnabled(enabled);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    if (mViewModel == null) {
      return;
    }

    if (e.getSource().equals(detailsButton)) {
      mViewModel.setView("Details");
    } else if (e.getSource().equals(tilesButton)) {
      mViewModel.setView("Tiles");
    } else if (e.getSource().equals(listButton)) {
      mViewModel.setView("List");
    } else {
      // do nothing
    }
  }
}
