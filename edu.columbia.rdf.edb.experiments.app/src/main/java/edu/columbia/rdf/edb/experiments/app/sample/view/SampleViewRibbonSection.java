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
package edu.columbia.rdf.edb.experiments.app.sample.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernCheckButton;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.OpenFolderVectorIcon;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.search.Sorter;
import org.jebtk.modern.widget.ModernClickWidget;
import org.jebtk.modern.widget.ModernWidget;

import edu.columbia.rdf.edb.Sample;

/**
 * Allows user to customize how the experiments are listed.
 *
 * @author Antony Holmes
 *
 */
public class SampleViewRibbonSection extends RibbonSection
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant SIZE. */
  private static final Dimension SIZE = new Dimension(120,
      ModernWidget.WIDGET_HEIGHT);

  /** The m button sort map. */
  private Map<String, ModernClickWidget> mButtonSortMap = new HashMap<String, ModernClickWidget>();

  /** The m sample view model. */
  private SampleViewModel mSampleViewModel;

  /**
   * The Class SortEvents.
   */
  private class SortEvents implements ChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.event.ChangeListener#changed(org.abh.common.event.
     * ChangeEvent)
     */
    @Override
    public void changed(ChangeEvent e) {
      mButtonSortMap.get(mSampleViewModel.getSorter().getName())
          .setSelected(true);
    }
  }

  /**
   * Instantiates a new sample view ribbon section.
   *
   * @param ribbon the ribbon
   * @param sampleViewModel the sample view model
   */
  public SampleViewRibbonSection(Ribbon ribbon,
      SampleViewModel sampleViewModel) {
    super(ribbon, "Folders");

    mSampleViewModel = sampleViewModel;

    mSampleViewModel.addChangeListener(new SortEvents());

    ModernButtonGroup group = new ModernButtonGroup();

    List<String> names = new ArrayList<String>();

    for (Sorter<Sample> sorter : mSampleViewModel) {
      names.add(sorter.getName());
    }

    Collections.sort(names);

    // Add the default button

    // Dimension dim = new Dimension(70, Ribbon.LARGE_BUTTON_HEIGHT);

    // List<String> tokens =
    // TextUtils.fastSplit(sortModel.getDefault().getName(),
    // TextUtils.SPACE_DELIMITER);

    ModernClickWidget sortCheckBox;

    // if (tokens.size() > 1) {
    // sortCheckBox = new RibbonLargeCheckButton(tokens.get(0), tokens.get(1),
    // ModernVectorIcon.FOLDER_32_ICON);
    // } else {
    // sortCheckBox = new RibbonLargeCheckButton(tokens.get(0),
    // ModernVectorIcon.FOLDER_32_ICON);
    // }

    // Ui.setSize(sortCheckBox, dim);

    // group.add(sortCheckBox);
    // sortCheckBox.addClickListener(this);
    // mButtonSortMap.put(sortModel.getDefault().getName(), sortCheckBox);

    // buttonContainer.add(sortCheckBox);

    // Add the other buttons

    int c = 0;

    Box box = null;

    for (String name : names) {
      if (c % 3 == 0) {
        box = Box.createVerticalBox();
        add(box);
      }

      sortCheckBox = new ModernCheckButton(name,
          AssetService.getInstance().loadIcon(OpenFolderVectorIcon.class, 16));
      UI.setSize(sortCheckBox, SIZE);
      group.add(sortCheckBox);
      sortCheckBox.addClickListener(this);
      mButtonSortMap.put(name, sortCheckBox);

      box.add(sortCheckBox);

      ++c;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  public void clicked(ModernClickEvent e) {
    // if (e.getSource().equals(sortAscendButton)) {
    // fireAction(new ModernClickEvent(this, "sort"));
    // }

    mSampleViewModel.setSorter(e.getMessage());
  }

}
