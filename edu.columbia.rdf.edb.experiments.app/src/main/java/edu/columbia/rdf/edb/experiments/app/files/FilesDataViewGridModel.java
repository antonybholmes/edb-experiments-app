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

import org.jebtk.core.text.TextUtils;
import org.jebtk.modern.table.ModernTableModel;

import edu.columbia.rdf.edb.FileType;
import edu.columbia.rdf.edb.VfsFile;

// TODO: Auto-generated Javadoc
/**
 * The Class FilesDataViewGridModel.
 */
public class FilesDataViewGridModel extends ModernTableModel {

  /** The Constant HEADINGS. */
  private static final String[] HEADINGS = { "Name", "Type", "Date" };

  /** The m items. */
  private List<VfsFile> mItems = new ArrayList<VfsFile>();

  /**
   * Instantiates a new files data view grid model.
   */
  FilesDataViewGridModel() {
    // do nothing
  }

  /**
   * Instantiates a new files data view grid model.
   *
   * @param files the files
   */
  public FilesDataViewGridModel(List<VfsFile> files) {

    mItems = files;

    // Collections.sort(items);

    /*
     * for (ArrayDbFileDescriptor f : files) {
     * 
     * items.add(f); }
     */

    // this.fireTableDataChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getColumnCount()
   */
  @Override
  public final int getColCount() {
    return HEADINGS.length;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.table.ModernTableModel#getColumns().getAnnotation(int)
   */
  @Override
  public final String getColumnName(int column) {
    if (column < HEADINGS.length) {
      return HEADINGS[column];
    } else {
      return TextUtils.EMPTY_STRING;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getRowCount()
   */
  public final int getRowCount() {
    // System.out.println("row count" + dataSection.getRows().size());

    return mItems.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataModel#getValueAt(int, int)
   */
  public final Object getValueAt(int row, int col) {
    if (mItems.size() == 0) {
      return "";
    }

    switch (col) {
    case 0:
      return mItems.get(row).getName();
    case 1:
      if (mItems.get(row).getType() == FileType.DIR) {
        return "File Folder";
      } else {
        return mItems.get(row).getExt();
      }
    case 2:
      return mItems.get(row).getDate();
    }

    return "";
  }
}
