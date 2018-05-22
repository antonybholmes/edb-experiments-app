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
package edu.columbia.rdf.edb.experiments.app.files;

import org.jebtk.modern.AssetService;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonLargeCheckButton;
import org.jebtk.modern.ribbon.RibbonLargeRadioButton;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.view.ViewModel;

/**
 * The Class FileViewRibbonPanel.
 */
public class FileViewRibbonPanel extends RibbonSection {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m button details. */
  private RibbonLargeCheckButton mButtonDetails = new RibbonLargeRadioButton(
      "Details", AssetService.getInstance().loadIcon("view_details", 24));

  /** The m button list. */
  private RibbonLargeCheckButton mButtonList = new RibbonLargeRadioButton(
      "List", AssetService.getInstance().loadIcon("view_list", 24));

  /** The m button icons. */
  private RibbonLargeCheckButton mButtonIcons = new RibbonLargeRadioButton(
      "Icons", AssetService.getInstance().loadIcon("view_tiles", 24));

  /**
   * Instantiates a new file view ribbon panel.
   *
   * @param ribbon the ribbon
   * @param viewModel the view model
   */
  public FileViewRibbonPanel(Ribbon ribbon, final ViewModel viewModel) {
    super(ribbon, "View");

    add(mButtonDetails);
    add(mButtonList);
    add(mButtonIcons);

    new ModernButtonGroup(mButtonDetails, mButtonList, mButtonIcons);

    mButtonDetails.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        viewModel.setView("Details");
      }
    });

    mButtonList.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        viewModel.setView("List");
      }
    });

    mButtonIcons.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        viewModel.setView("Tiles");
      }
    });

    mButtonDetails.doClick();
  }

}
