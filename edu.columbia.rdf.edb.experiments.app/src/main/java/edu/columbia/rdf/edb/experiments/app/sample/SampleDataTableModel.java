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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleDataTableModel.
 */
public class SampleDataTableModel extends AbstractTableModel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The column names. */
  private String[] columnNames = { "Field", "Value" };

  /** The data. */
  private List<List<String>> data = new ArrayList<List<String>>();

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public final int getColumnCount() {
    return columnNames.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public final int getRowCount() {
    // System.out.println("row count" + dataSection.getRows().size());

    return data.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.table.AbstractTableModel#getColumnName(int)
   */
  public final String getColumnName(int col) {
    // System.out.println("cname:" + columnNames.get(col));

    return columnNames[col];
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public final Object getValueAt(int row, int col) {
    if (col == 0) {
      // make fields look correct
      return data.get(row).get(col) + ":";
    } else {
      return data.get(row).get(col);
    }
  }

  /**
   * Instantiates a new sample data table model.
   *
   * @param data the data
   */
  public SampleDataTableModel(List<List<String>> data) {

    this.data = data;

    this.fireTableDataChanged();
  }
}
