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

import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.path.Path;
import org.jebtk.modern.table.ModernTableModel;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.DataViewField;
import edu.columbia.rdf.edb.DataViewSection;
import edu.columbia.rdf.edb.Sample;

// TODO: Auto-generated Javadoc
/**
 * Display a sample attributes (e.g. the SDRF details).
 * 
 * @author Antony Holmes Holmes
 *
 */
public class AttributesTableModel extends ModernTableModel {

  /** The m paths. */
  private List<Path> mPaths = new ArrayList<Path>();

  /** The m column names. */
  private List<String> mColumnNames = new ArrayList<String>();

  /** The column display names. */
  private List<String> columnDisplayNames = new ArrayList<String>();

  /** The m samples. */
  private List<Sample> mSamples = new ArrayList<Sample>();

  /** The m view. */
  private DataView mView;

  /**
   * Instantiates a new attributes table model.
   *
   * @param view the view
   */
  public AttributesTableModel(DataView view) {
    mView = view;

    setup();
  }

  /**
   * Instantiates a new attributes table model.
   *
   * @param samples the samples
   * @param view the view
   */
  public AttributesTableModel(List<Sample> samples, DataView view) {
    mView = view;

    setup();

    mSamples = samples;
  }

  /**
   * Setup.
   */
  private void setup() {
    for (DataViewSection section : mView) {
      for (DataViewField field : section) {
        mPaths.add(field.getPath());
        columnDisplayNames.add(field.getName());
        mColumnNames.add(field.getName());
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getColumnCount()
   */
  @Override
  public final int getColCount() {
    return mColumnNames.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getRowCount()
   */
  @Override
  public final int getRowCount() {
    return mSamples.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.table.ModernTableModel#getColumnAnnotationText(int)
   */
  @Override
  public final List<String> getColumnAnnotationText(int column) {
    return CollectionUtils.asList(columnDisplayNames.get(column));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getValueAt(int, int)
   */
  @Override
  public final Object getValueAt(int row, int col) {
    if (mSamples.size() == 0) {
      return "";
    }

    return mSamples.get(row).getTags().getTag(mPaths.get(col)).getValue();
  }
}
