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

import javax.swing.Box;

import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.button.ModernCheckBox;
import org.jebtk.modern.dialog.ModernDialogBorderButtonsBox;
import org.jebtk.modern.dialog.ModernDialogButton;
import org.jebtk.modern.dialog.ModernDialogSectionSeparator;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernDialogWindow;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.ModernLineBorderPanel;
import org.jebtk.modern.panel.ModernPaddedPanel;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleDownloadDialog.
 */
public class SampleDownloadDialog extends ModernDialogWindow implements ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The ok button. */
  private ModernButton okButton = new ModernDialogButton(UI.BUTTON_OK);

  /** The cancel button. */
  private ModernButton cancelButton = new ModernDialogButton(UI.BUTTON_CANCEL);

  /** The check cel. */
  private ModernCheckBox checkCel = new ModernCheckBox("CEL");

  /** The check chp. */
  private ModernCheckBox checkChp = new ModernCheckBox("CHP");

  /** The check mas 5. */
  private ModernCheckBox checkMas5 = new ModernCheckBox("MAS5");

  /** The check rma. */
  private ModernCheckBox checkRma = new ModernCheckBox("RMA");

  /**
   * Instantiates a new sample download dialog.
   *
   * @param parent
   *          the parent
   */
  public SampleDownloadDialog(ModernWindow parent) {
    super(parent);

    setTitle("Sample Download");

    setResizable(false);

    setSize(400, 300);

    createUi();

    setup();
  }

  /**
   * Creates the ui.
   */
  private final void createUi() {
    Box box = Box.createVerticalBox();

    box.add(new ModernDialogSectionSeparator("Select file types"));

    box.add(ModernPanel.createVGap());
    box.add(checkCel);
    box.add(ModernPanel.createVGap());
    box.add(checkChp);
    box.add(ModernPanel.createVGap());
    box.add(checkMas5);
    box.add(ModernPanel.createVGap());
    box.add(checkRma);
    box.setBorder(ModernWidget.DOUBLE_BORDER);

    setBody(new ModernPaddedPanel(new ModernLineBorderPanel(box)));

    Box buttonPanel = new ModernDialogBorderButtonsBox();

    buttonPanel.add(okButton);
    buttonPanel.add(ModernPanel.createHGap());
    buttonPanel.add(cancelButton);

    setFooter(buttonPanel);

    UI.centerWindowToScreen(this);
  }

  /**
   * Setup.
   */
  private void setup() {
    cancelButton.addClickListener(this);
    okButton.addClickListener(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.event.
   * ModernClickEvent)
   */
  public final void clicked(ModernClickEvent e) {
    if (e.getSource().equals(okButton)) {
      setStatus(ModernDialogStatus.OK);
    }

    close();
  }

  /**
   * Gets the download cel.
   *
   * @return the download cel
   */
  public boolean getDownloadCel() {
    return checkCel.isSelected();
  }

  /**
   * Gets the download CHP.
   *
   * @return the download CHP
   */
  public boolean getDownloadCHP() {
    return checkChp.isSelected();
  }

  /**
   * Gets the download MAS 5.
   *
   * @return the download MAS 5
   */
  public boolean getDownloadMAS5() {
    return checkMas5.isSelected();
  }

  /**
   * Gets the download RMA.
   *
   * @return the download RMA
   */
  public boolean getDownloadRMA() {
    return checkRma.isSelected();
  }
}
