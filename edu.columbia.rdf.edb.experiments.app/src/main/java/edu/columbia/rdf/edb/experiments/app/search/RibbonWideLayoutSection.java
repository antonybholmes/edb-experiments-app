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

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernCheckButton;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.ViewVerticalVectorIcon;
import org.jebtk.modern.graphics.icons.ViewWideVectorIcon;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonLargeRadioButton;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.view.ViewModel;

// TODO: Auto-generated Javadoc
/**
 * Standardized ribbon menu section for providing basic cut, copy and paste
 * functionality to the currently highlighted control that supports clipboard
 * operations.
 *
 * @author Antony Holmes Holmes
 *
 */
public class RibbonWideLayoutSection extends RibbonSection implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m vertical button. */
  private ModernCheckButton mVerticalButton = new RibbonLargeRadioButton("Vertical",
      UIService.getInstance().loadIcon(ViewVerticalVectorIcon.class, 32));

  /** The m wide button. */
  private ModernCheckButton mWideButton = new RibbonLargeRadioButton("Wide",
      UIService.getInstance().loadIcon(ViewWideVectorIcon.class, 32));

  // private ModernCheckButton listButton =
  // new RibbonLargeRadioButton2("List",
  // UIResources.getInstance().loadIcon("view_list", UIResources.ICON_SIZE_16));

  // private ModernCheckButton tilesButton =
  // new ModernCheckButton("Tiles",
  // UIResources.getInstance().loadIcon("view_tiles", UIResources.ICON_SIZE_16));

  // private ModernCheckButton detailsButton =
  // new ModernCheckButton("Details",
  // UIResources.getInstance().loadIcon("view_details",
  // UIResources.ICON_SIZE_16));

  /** The m model. */
  private ViewModel mModel;

  /**
   * The Class ViewEvents.
   */
  private class ViewEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.abh.common.event.ChangeListener#changed(org.abh.common.event.ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      if (mModel.getView().equals("Wide")) {
        mWideButton.setSelected(true);
      } else if (mModel.getView().equals("Vertical")) {
        mVerticalButton.setSelected(true);
      } else {
        // do nothing
      }

      // Write the settings

      SettingsService.getInstance().update("experiments.annotation.default-view", mModel.getView());
    }
  }

  /**
   * Instantiates a new ribbon wide layout section.
   *
   * @param ribbon
   *          the ribbon
   * @param name
   *          the name
   * @param model
   *          the model
   */
  public RibbonWideLayoutSection(Ribbon ribbon, String name, ViewModel model) {
    super(ribbon, name);

    mModel = model;
    mModel.addChangeListener(new ViewEvents());

    mWideButton.setToolTip("Wide Layout", "Display items in a wide view with additional information.");
    mWideButton.addClickListener(this);

    mVerticalButton.setToolTip("Vertical Layout", "Display items in a vertical view with additional information.");
    mVerticalButton.addClickListener(this);

    add(mVerticalButton);
    add(mWideButton);

    ModernButtonGroup group = new ModernButtonGroup();

    group.add(mWideButton);
    group.add(mVerticalButton);

    // verticalButton.doClick();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.event.
   * ModernClickEvent)
   */
  public void clicked(ModernClickEvent e) {
    if (mModel == null) {
      return;
    }

    if (e.getSource().equals(mWideButton)) {
      mModel.setView("Wide");
    } else if (e.getSource().equals(mVerticalButton)) {
      mModel.setView("Vertical");
    } else {
      // do nothing
    }
  }
}
