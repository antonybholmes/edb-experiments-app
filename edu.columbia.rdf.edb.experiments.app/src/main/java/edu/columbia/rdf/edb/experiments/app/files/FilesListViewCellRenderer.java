/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors 
 *     may be used to endorse or promote products derived from this software 
 *     without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.columbia.rdf.edb.experiments.app.files;

import java.awt.Component;
import java.awt.Graphics2D;

import org.jebtk.modern.AssetService;
import org.jebtk.modern.dataview.ModernData;
import org.jebtk.modern.dataview.ModernDataCellRenderer;
import org.jebtk.modern.graphics.icons.FileVectorIcon;
import org.jebtk.modern.graphics.icons.FolderVectorIcon;
import org.jebtk.modern.graphics.icons.ModernIcon;

// TODO: Auto-generated Javadoc
/**
 * The class ModernDataListCellRenderer.
 */
public class FilesListViewCellRenderer extends ModernDataCellRenderer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /** The Constant FILE_ICON. */
  private static final ModernIcon FILE_ICON = new FileVectorIcon();

  /** The Constant DIR_ICON. */
  private static final ModernIcon DIR_ICON = AssetService.getInstance()
      .loadIcon(FolderVectorIcon.class, 32);

  /** The m text. */
  private String mText;

  /** The m icon. */
  private ModernIcon mIcon;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.lib.ui.modern.ModernWidget#drawForegroundAA(java.awt.Graphics2D)
   */
  @Override
  public void drawForegroundAA(Graphics2D g2) {
    mIcon.drawIcon(g2, PADDING, (getHeight() - 32) / 2, 32);

    String t = getTruncatedText(g2, mText, 0, getWidth() - DOUBLE_PADDING - 32);

    g2.setColor(TEXT_COLOR);
    g2.drawString(t, 32 + DOUBLE_PADDING, getTextYPosCenter(g2, getHeight()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.ui.modern.dataview.ModernDataCellRenderer#
   * getCellRendererComponent(org.abh.lib.ui.modern.dataview.ModernData,
   * java.lang.Object, boolean, boolean, boolean, int, int)
   */
  public Component getCellRendererComponent(ModernData table,
      Object value,
      boolean highlight,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column) {

    mText = value.toString();

    if (table.getValueAt(row, 1).equals("File Folder")) {
      mIcon = DIR_ICON;
    } else {
      mIcon = FILE_ICON;
    }

    return super.getCellRendererComponent(table,
        value,
        highlight,
        isSelected,
        hasFocus,
        row,
        column);
  }
}