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
import java.awt.Graphics2D;

import org.jebtk.modern.AssetService;
import org.jebtk.modern.graphics.icons.FileVectorIcon;
import org.jebtk.modern.list.ModernList;
import org.jebtk.modern.list.ModernListTwoLineCellRenderer;

import edu.columbia.rdf.edb.VfsFile;

// TODO: Auto-generated Javadoc
/**
 * Renders a file as a list item showing the name, type and size.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class FilesListCellRenderer extends ModernListTwoLineCellRenderer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The filename. */
  private String filename;

  /** The type. */
  private String type;

  /**
   * Instantiates a new files list cell renderer.
   */
  FilesListCellRenderer() {
    setIcon(AssetService.getInstance().loadIcon(FileVectorIcon.class, 32));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.list.ModernListTwoLineCellRenderer#drawForegroundAA(
   * java.awt.Graphics2D)
   */
  @Override
  public void drawForegroundAA(Graphics2D g2) {
    int x = PADDING;
    int y = (getHeight() - AssetService.ICON_SIZE_32) / 2;

    mIcon.drawIcon(g2, x, y, AssetService.ICON_SIZE_32);

    x += PADDING + 48;

    y = (getHeight() / 2 - g2.getFontMetrics().getDescent()
        + g2.getFontMetrics().getAscent()) / 2;

    g2.setColor(TEXT_COLOR);
    g2.drawString(filename, x, y);

    y += getHeight() / 2;

    g2.setColor(color2);
    g2.drawString(type, x, y);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.list.ModernListTwoLineCellRenderer#
   * getCellRendererComponent (org.abh.common.ui.list.ModernList,
   * java.lang.Object, boolean, boolean, boolean, int)
   */
  @Override
  public Component getCellRendererComponent(ModernList<?> list,
      Object value,
      boolean highlight,
      boolean isSelected,
      boolean hasFocus,
      int row) {

    VfsFile file = (VfsFile) value;

    if (value != null) {
      filename = file.getName();
      type = file.getExt();
    } else {
      filename = "";
      type = "";
    }

    return super.getCellRendererComponent(list,
        value,
        highlight,
        isSelected,
        hasFocus,
        row);
  }
}