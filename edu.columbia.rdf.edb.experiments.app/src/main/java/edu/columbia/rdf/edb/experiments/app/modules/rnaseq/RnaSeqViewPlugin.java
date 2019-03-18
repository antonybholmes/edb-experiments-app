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
package edu.columbia.rdf.edb.experiments.app.modules.rnaseq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.bioinformatics.annotation.Genome;
import org.jebtk.core.ColorUtils;
import org.jebtk.core.NetworkFileException;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.collections.CountMap;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.TmpService;
import org.jebtk.core.path.Path;
import org.jebtk.core.text.TextUtils;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.SelectionModel;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.Raster24Icon;
import org.jebtk.modern.graphics.icons.Window32VectorIcon;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.status.StatusModel;
import org.jebtk.modern.window.ModernRibbonWindow;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.VfsFile;
import edu.columbia.rdf.edb.experiments.app.cart.SampleCartService;
import edu.columbia.rdf.edb.ui.FileDownloader;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;
import edu.columbia.rdf.edb.ui.SampleSortService;
import edu.columbia.rdf.edb.ui.ViewPlugin;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;
import edu.columbia.rdf.htsview.chipseq.SortSamplesByGenome;
import edu.columbia.rdf.matcalc.bio.app.MainBioMatCalc;

/**
 * Plugin for display of microarray data.
 * 
 * @author Antony Holmes
 *
 */
public class RnaSeqViewPlugin extends ViewPlugin
implements ModernClickListener {

  private static final int ANNOTATION_COLUMNS = 3;

  /** The m fpkm button. */
  private ModernButtonWidget mFpkmButton = new RibbonLargeButton("Exp",
      new Raster24Icon(
          new Window32VectorIcon(ColorUtils.decodeHtmlColor("#e580ff"))));

  /** The m parent. */
  private ModernRibbonWindow mParent;

  /** The m selected samples. */
  //private SelectionModel<Sample> mSelectedSamples;

  /** The m view. */
  private DataView mView;

  /** The m display field 1. */
  private Path mDisplayField1 = new Path("/Sample/Organism");

  /** The m display field 2. */
  private Path mDisplayField2 = new Path("/RNA-Seq/Sample/Genome");

  /**
   * Instantiates a new rna seq view plugin.
   */
  public RnaSeqViewPlugin() {
    mView = new RnaSeqDataView();

    mFpkmButton.addClickListener(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getDisplayField1()
   */
  @Override
  public Path getDisplayField1() {
    return mDisplayField1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getDisplayField2()
   */
  @Override
  public Path getDisplayField2() {
    return mDisplayField2;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.ViewPlugin#initSampleSorters(org.abh.common.ui.
   * search .SortModel)
   */
  @Override
  public void initSampleSorters() {
    SampleSortService.getInstance().add(new SortSamplesByGenome());
    SampleSortService.getInstance().add(new SortSamplesBySeqId());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.ViewPlugin#initSearchCategories(edu.columbia.rdf.
   * edb. ui.search.SearchCategoryService)
   */
  @Override
  public void initSearchCategories() {
    /*
    SearchCategoryGroup group;

    group = new SearchCategoryGroup("RNA-seq");
    group.addCategory(
        new SearchCategory("Sequence Id", new Path("/RNA-seq/Sample/Seq_Id")));
    group.addCategory(
        new SearchCategory("Genome", new Path("/RNA-seq/Sample/Genome")));

    SearchCategoryService.getInstance().addGroup(group);
     */

    try {
      SearchCategoryService.getInstance().loadXml(RnaSeqDataView.XML_VIEW_FILE);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.ViewPlugin#getSamplePanel(edu.columbia.rdf.edb.
   * Sample)
   */
  @Override
  public ModernComponent getSamplePanel(Sample sample) {
    return new RnaSeqSampleDataPanel(sample, mView);
  }



  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#init(org.abh.common.ui.window.
   * ModernRibbonWindow, org.abh.common.ui.status.StatusModel,
   * org.abh.common.ui.widget.tooltip.ModernToolTipModel,
   * org.abh.common.ui.SelectionModel)
   */
  @Override
  public void init(ModernRibbonWindow parent,
      StatusModel statusModel,
      SelectionModel<Sample> selectedSamples) {
    mParent = parent;
    //mSelectedSamples = selectedSamples;

    parent.getRibbon().getHomeToolbar().getSection(getDataType())
    .add(mFpkmButton);

    mFpkmButton.setToolTip("Expression Data",
        "Download RNA-seq expression data for the currently selected samples.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getExpressionType()
   */
  @Override
  public String getDataType() {
    return "RNA-Seq";
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getSource().equals(mFpkmButton)) {
      try {
        showData();
      } catch (NetworkFileException | IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Show expression data.
   *
   * @throws NetworkFileException the network file exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void showData()
      throws NetworkFileException, IOException {
    List<Sample> samples = SampleCartService.getInstance().toList(); //mSelectedSamples.getItems();

    if (samples.size() == 0) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You must select at least one sample.");

      return;
    }

    boolean correctType = true;

    for (Sample sample : samples) {
      if (!sample.getDataType().getName().equals("RNA-Seq")) {
        correctType = false;
        break;
      }
    }

    if (!correctType) {
      ModernMessageDialog.createWarningDialog(mParent,
          "Some of the samples are not RNA-Seq.");

      return;
    }

    // Lets see what genomes these samples have in common

    Repository rep = RepositoryService.getInstance().getRepository();

    CountMap<Genome> genomeMap = new CountMap<Genome>();

    for (Sample sample : samples) {
      genomeMap.putAll(rep.getGenomes(sample));
    }

    List<Genome> genomes = new ArrayList<Genome>();

    for (Entry<Genome, Integer> e : genomeMap.entrySet()) {
      if (e.getValue() == samples.size()) {
        genomes.add(e.getKey());
      }
    }


    GenomeDialog dialog = new GenomeDialog(mParent, genomes);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
      return;
    }

    Genome genome = dialog.getGenome();
    String type = dialog.getDataType();

    List<VfsFile> files = new ArrayList<VfsFile>();
    // Where to write the files locally
    Map<Sample, java.nio.file.Path> localFiles = new TreeMap<Sample, java.nio.file.Path>();

    FileDownloader downloader = RepositoryService.getInstance()
        .getRepository().getFileDownloader();

    for (Sample sample : samples) {
      for (VfsFile file : rep.getGenomeFiles(sample, genome)) {
        if (file.getName().contains(type)) {
          files.add(file);

          java.nio.file.Path localFile = TmpService.getInstance().newTmpFile(file.getName());

          localFiles.put(sample, localFile);

          downloader.downloadFile(file, localFile);
        }
      }
    }

    java.nio.file.Path mergedFile = pasteFiles(localFiles);

    try {
      MainBioMatCalc.autoOpen(mergedFile, 3);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static java.nio.file.Path pasteFiles(Map<Sample, java.nio.file.Path> sampleFileMap) throws IOException {
    java.nio.file.Path tempFile1 = TmpService.getInstance().newTmpFile("txt");

    String line;

    BufferedReader reader;

    //
    // Create a list of file handlers for each file
    //

    List<Sample> samples = CollectionUtils.sort(sampleFileMap.keySet());

    List<BufferedReader> readers = new ArrayList<BufferedReader>();

    for (Entry<Sample, java.nio.file.Path> entry : sampleFileMap.entrySet()) {
      readers.add(FileUtils.newBufferedReader(entry.getValue()));
    }


    //
    // Do some writing
    //

    BufferedWriter writer = FileUtils.newBufferedWriter(tempFile1);

    // Create a global header
    List<String> tokens;

    try {
      writer.write("Gene");
      writer.write(TextUtils.TAB_DELIMITER);
      writer.write("Location");
      writer.write(TextUtils.TAB_DELIMITER);
      writer.write("Strand");


      for (int i = 0; i < samples.size(); ++i) {
        // Sample sample = samples.get(i);

        reader = readers.get(i);

        line = reader.readLine();

        tokens = TextUtils.tabSplit(line);

        // don't need the first two columns as these are duplicated in
        // every expression file
        tokens = CollectionUtils.subList(tokens, ANNOTATION_COLUMNS);

        //
        // Now we deal with each of the columns in the annotation
        // file, notably to replace the names with the real sample
        // name to account for changes in the file and the database

        for (int c = 0; c < tokens.size(); ++c) {
          String header = tokens.get(c);
          writer.write(TextUtils.TAB_DELIMITER);
          writer.write(header);
        }
      }

      writer.newLine();

      boolean stop = false;

      while(!stop) {
        // For each sample, skip lines until we find the probe of interest
        for (int i = 0; i < samples.size(); ++i) {
          reader = readers.get(i);

          line = reader.readLine();

          if (line == null) {
            stop = true;
            break;
          }

          tokens = TextUtils.tabSplit(line);

          if (i == 0) {
            writer.write(tokens.get(0));
            writer.write(TextUtils.TAB_DELIMITER);
            writer.write(tokens.get(1));
            writer.write(TextUtils.TAB_DELIMITER);
            writer.write(tokens.get(2));
          }

          // Skip the annotation columns in each file
          tokens = CollectionUtils.subList(tokens, ANNOTATION_COLUMNS);

          // Write out the annotation for this probe for this sample
          for (int c = 0; c < tokens.size(); ++c) {
            writer.write(TextUtils.TAB_DELIMITER);
            writer.write(tokens.get(c));
          }
        }

        // Finally write a new line since we have written the annotations
        // for each sample
        writer.newLine();
      }
    } finally {
      for (BufferedReader r : readers) {
        r.close();
      }

      writer.close();
    }

    return tempFile1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getDataView()
   */
  @Override
  public DataView getDataView() {
    return mView;
  }
}
