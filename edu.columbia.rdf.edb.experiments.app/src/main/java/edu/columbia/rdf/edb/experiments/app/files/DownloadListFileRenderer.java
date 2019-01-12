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
import java.io.File;

import org.jebtk.modern.AssetService;
import org.jebtk.modern.list.ModernList;
import org.jebtk.modern.list.ModernListTwoLineCellRenderer;

// TODO: Auto-generated Javadoc
/**
 * Renders a file as a list item.
 * 
 * @author Antony Holmes
 *
 */
public class DownloadListFileRenderer extends ModernListTwoLineCellRenderer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new download list file renderer.
   */
  public DownloadListFileRenderer() {
    super(AssetService.getInstance().loadIcon("file", AssetService.ICON_SIZE_32));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.list.ModernListTwoLineCellRenderer#
   * getCellRendererComponent (org.abh.common.ui.list.ModernList,
   * java.lang.Object, boolean, boolean, boolean, int)
   */
  public Component getCellRendererComponent(ModernList<File> list,
      File value,
      boolean highlight,
      boolean isSelected,
      boolean hasFocus,
      int row) {

    if (value != null) {
      setText(value.getName(), "Downloaded");
    } else {
      setText("", "");
    }

    return super.getCellRendererComponent(list,
        value,
        highlight,
        isSelected,
        hasFocus,
        row);
  }
}