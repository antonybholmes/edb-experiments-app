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

import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.text.DateUtils;
import org.jebtk.modern.table.ModernColumnHeaderTableModel;

import edu.columbia.rdf.edb.VfsFile;

// TODO: Auto-generated Javadoc
/**
 * The Class FilesTableModel.
 */
public class FilesTableModel extends ModernColumnHeaderTableModel {

  /** The Constant HEADINGS. */
  private static final String[] HEADINGS = { "Name", "Type", "Date" };

  // private Experiment experiment;
  // private Biomaterial biomaterial;

  /** The items. */
  private List<VfsFile> items = new ArrayList<VfsFile>();

  /**
   * Instantiates a new files table model.
   */
  FilesTableModel() {

    // do nothing
  }

  /**
   * Instantiates a new files table model.
   *
   * @param files the files
   */
  public FilesTableModel(List<VfsFile> files) {

    for (VfsFile f : files) {

      items.add(f);
    }

    // this.fireTableDataChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getColumnCount()
   */
  @Override
  public final int getColumnCount() {
    return HEADINGS.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getRowCount()
   */
  @Override
  public final int getRowCount() {
    return items.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.table.ModernTableModel#getColumnAnnotationText(int)
   */
  @Override
  public final List<String> getColumnAnnotationText(int column) {
    return CollectionUtils.asList(HEADINGS[column]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getValueAt(int, int)
   */
  @Override
  public final Object getValueAt(int row, int col) {
    if (items.size() == 0) {
      return "";
    }

    switch (col) {
    case 0:
      return items.get(row).getName();
    case 1:
      return items.get(row).getExt();
    case 2:
      return DateUtils.getRevFormattedDate(items.get(row).getDate());
    default:
      return "";
    }
  }
}
