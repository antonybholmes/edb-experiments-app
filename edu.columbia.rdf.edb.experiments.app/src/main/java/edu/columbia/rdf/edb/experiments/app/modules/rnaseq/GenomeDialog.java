/**
 * Copyright 2016 Antony Holmes
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
package edu.columbia.rdf.edb.experiments.app.modules.rnaseq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;

import org.jebtk.bioinformatics.annotation.Genome;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButtonGroup;
import org.jebtk.modern.button.ModernRadioButton;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.widget.ModernTwoStateWidget;
import org.jebtk.modern.window.ModernWindow;

/**
 * The class BRTDialog.
 */
public class GenomeDialog extends ModernDialogTaskWindow
implements ModernClickListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The m check sample.
   */
  private ModernRadioButton mCheckCounts = new ModernRadioButton("Counts");
  private ModernRadioButton mCheckFPKM = new ModernRadioButton("FPKM");
  private ModernRadioButton mCheckTPM = new ModernRadioButton("TPM", true);


  /** The m peaks map. */
  private Map<Genome, ModernTwoStateWidget> mGenomesMap = 
      new HashMap<Genome, ModernTwoStateWidget>();

  private Iterable<Genome> mGenomes;

  /**
   * Instantiates a new BRT dialog.
   *
   * @param parent The parent.
   * @param sample The sample.
   * @param isBrt Contains both read and signal data.
   * @param peakAssembly the peak assembly
   */
  public GenomeDialog(ModernWindow parent, Iterable<Genome> genomes) {
    super(parent); 

    mGenomes = genomes;

    setTitle("Genomes");

    try {
      createUi();
    } catch (IOException e) {
      e.printStackTrace();
    }

    UI.centerWindowToScreen(this);
  }

  /**
   * Creates the ui.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void createUi() throws IOException {

    Box box = VBox.create();

    sectionHeader("Genomes", box);
    
    ModernButtonGroup g = new ModernButtonGroup();
    
    for (Genome genome : mGenomes) {
      ModernRadioButton button = new ModernRadioButton(genome.getName(), mGenomesMap.size() == 0);
      
      mGenomesMap.put(genome, button);
      
      g.add(button);
      
      box.add(button);
      
      box.add(UI.createVGap(5));
    }
    
    sectionHeader("Data types", box);

    box.add(mCheckCounts);
    box.add(UI.createVGap(5));
    box.add(mCheckFPKM);
    box.add(UI.createVGap(5));
    box.add(mCheckTPM);
    
    new ModernButtonGroup(mCheckCounts, mCheckFPKM, mCheckTPM);

    setCard(box);

    setSize(400, 400);
  }

  public Genome getGenome() {
    for (Entry<Genome, ModernTwoStateWidget> e : mGenomesMap.entrySet()) {
      if (e.getValue().isSelected()) {
        return e.getKey();
      }
    }
    
    return null;
  }

  public String getDataType() {
    if (mCheckCounts.isSelected()) {
      return "counts";
    } else if (mCheckFPKM.isSelected()) {
      return "fpkm";
    } else {
      return "tpm";
    }
  }
}
