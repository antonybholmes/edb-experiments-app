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
package edu.columbia.rdf.edb.experiments.app.sample.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.modern.UIService;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernCheckBox;
import org.jebtk.modern.button.ModernCheckButton;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.ribbon.Ribbon;
import org.jebtk.modern.ribbon.RibbonSection;
import org.jebtk.modern.search.SortModel;
import org.jebtk.modern.search.Sorter;

import edu.columbia.rdf.edb.Sample;

/**
 * Allows user to customize how the experiments are listed.
 *
 * @author Antony Holmes Holmes
 *
 */
public class SampleSortRibbonSection extends RibbonSection
    implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The sort descend button. */
  private ModernCheckButton sortDescendButton = new ModernCheckButton(
      UIService.getInstance().loadIcon("sort_descend", 16));

  /** The sort ascend button. */
  private ModernCheckButton sortAscendButton = new ModernCheckButton(
      UIService.getInstance().loadIcon("sort_ascend", 16));

  /** The m button sort map. */
  private Map<String, ModernCheckBox> mButtonSortMap = new HashMap<String, ModernCheckBox>();

  /** The m sort model. */
  private SortModel<Sample> mSortModel;

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
      mButtonSortMap.get(mSortModel.getSorter().getName()).setSelected(true);

      if (mSortModel.getSortAscending()) {
        sortAscendButton.setSelected(true);
      } else {
        sortDescendButton.setSelected(true);
      }
    }
  }

  /**
   * Instantiates a new sample sort ribbon section.
   *
   * @param ribbon the ribbon
   * @param sortModel the sort model
   */
  public SampleSortRibbonSection(Ribbon ribbon, SortModel<Sample> sortModel) {
    super(ribbon, "Sample Sort");

    mSortModel = sortModel;

    sortModel.addChangeListener(new SortEvents());

    ModernButtonGroup group = new ModernButtonGroup();

    int[] rows = { WIDGET_HEIGHT };
    int[] columns = { 120, 120, 120, 120 };

    MatrixPanel grid = new MatrixPanel(rows, columns, 0, 0);

    List<String> names = new ArrayList<String>();

    for (Sorter<Sample> sorter : sortModel) {
      names.add(sorter.getName());
    }

    Collections.sort(names);

    for (String name : names) {
      ModernCheckBox sortCheckBox = new ModernCheckBox(name);

      group.add(sortCheckBox);
      sortCheckBox.addClickListener(this);
      mButtonSortMap.put(name, sortCheckBox);

      grid.add(sortCheckBox);
    }

    add(grid);

    // buttonContainer.addSeparator();

    Box panel = Ribbon.createToolbarButtonColumnPanel(40);

    panel.add(createVGap());
    panel.add(sortAscendButton);
    panel.add(createVGap());
    panel.add(sortDescendButton);

    add(panel);

    sortAscendButton.setSelected(true);

    sortAscendButton.setToolTip("Sort Ascending",
        "Sort the experiments in ascending order.");
    sortAscendButton.addClickListener(this);

    sortDescendButton.setToolTip("Sort Descending",
        "Sort the experiments in descending order.");
    sortDescendButton.addClickListener(this);

    ModernButtonGroup sortGroup = new ModernButtonGroup();

    sortGroup.add(this.sortAscendButton);
    sortGroup.add(this.sortDescendButton);
  }

  /**
   * Sets the sort by name.
   *
   * @param name the new sort by name
   */
  public void setSortByName(String name) {
    ModernCheckBox button = mButtonSortMap.get(name);

    if (button == null) {
      return;
    }

    sortAscendButton.doClick();

    button.doClick();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  public void clicked(ModernClickEvent e) {
    if (e.getSource().equals(sortAscendButton)) {
      mSortModel.setSortAscending(true);
    } else if (e.getSource().equals(sortDescendButton)) {
      mSortModel.setSortAscending(false);
    } else {
      mSortModel.setSorter(e.getMessage());
    }
  }
}
