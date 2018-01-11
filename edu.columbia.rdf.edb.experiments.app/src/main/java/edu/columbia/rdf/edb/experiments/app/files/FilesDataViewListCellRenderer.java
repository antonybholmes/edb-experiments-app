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

import java.awt.Component;

import org.jebtk.modern.dataview.ModernData;
import org.jebtk.modern.dataview.ModernDataListCellRenderer;

import edu.columbia.rdf.edb.FileDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Class FilesDataViewListCellRenderer.
 */
public class FilesDataViewListCellRenderer extends ModernDataListCellRenderer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.dataview.ModernDataListCellRenderer#
   * getCellRendererComponent(org.abh.common.ui.dataview.ModernData,
   * java.lang.Object, boolean, boolean, boolean, int, int)
   */
  @Override
  public final Component getCellRendererComponent(ModernData dataView,
      Object value,
      boolean highlight,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column) {

    FileDescriptor file = (FileDescriptor) value;

    setText(file.getName());

    return super.getCellRendererComponent(dataView,
        value,
        highlight,
        isSelected,
        hasFocus,
        row,
        column);
  }
}