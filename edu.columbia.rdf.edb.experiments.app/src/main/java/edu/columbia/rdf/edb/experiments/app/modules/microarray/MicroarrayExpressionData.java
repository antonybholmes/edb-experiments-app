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
package edu.columbia.rdf.edb.experiments.app.modules.microarray;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jebtk.core.NetworkFileException;
import org.jebtk.core.collections.ArrayListCreator;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.collections.DefaultHashMap;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.Io;
import org.jebtk.core.io.Temp;
import org.jebtk.core.text.TextUtils;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.status.StatusModel;
import org.jebtk.modern.window.ModernWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.columbia.rdf.edb.DataViewField;
import edu.columbia.rdf.edb.VfsFile;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.DownloadManager;
import edu.columbia.rdf.edb.ui.FileDownloader;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;
import edu.columbia.rdf.edb.ui.microarray.MicroarrayNormalizationType;
import edu.columbia.rdf.matcalc.bio.app.MainBioMatCalc;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroarrayExpressionData.
 */
public class MicroarrayExpressionData {

  /** The Constant RMA_FILE. */
  public static final File RMA_FILE = new File("expression.rma");

  /** The Constant MAS5_FILE. */
  public static final File MAS5_FILE = new File("expression.mas5");

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory
      .getLogger(MicroarrayExpressionData.class);

  /*
   * private class ShowSamples implements Runnable {
   * 
   * private File mFile; private String mPlatform;
   * 
   * public ShowSamples(File allSamplesFile, String platform) { mFile =
   * allSamplesFile; mPlatform = platform; }
   * 
   * @Override public void run() { System.err.println("platform " + mPlatform);
   * 
   * try { MainTableFilter.main(mFile, 3, mPlatform); } catch (SAXException e) {
   * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } catch
   * (ParserConfigurationException e) { e.printStackTrace(); } } }
   */

  /**
   * The Class PlotSamples.
   */
  private class PlotSamples implements Runnable {

    /** The m file. */
    private Path mFile;

    /**
     * Instantiates a new plot samples.
     *
     * @param allSamplesFile the all samples file
     * @param platform the platform
     */
    public PlotSamples(Path allSamplesFile, String platform) {
      mFile = allSamplesFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
      try {
        MainBioMatCalc.open(mFile, 3);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Shows the expression data.
   *
   * @param parent the parent
   * @param samples the samples
   * @param type the type
   * @param columns the columns
   * @param columnAnnotations the column annotations
   * @param checkIfFileExists the check if file exists
   * @param statusModel the status model
   * @throws NetworkFileException the network file exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  public void showTables(ModernWindow parent,
      Collection<Sample> samples,
      MicroarrayNormalizationType type,
      List<Boolean> columns,
      List<DataViewField> columnAnnotations,
      boolean checkIfFileExists,
      StatusModel statusModel)
      throws NetworkFileException, IOException, ParseException {

    // Create a set of pseudo experiments to store samples

    Map<String, Set<Sample>> samplesGroupedByArray = Sample
        .sortByArrayDesign(samples);

    boolean areDifferentExperiments = samplesGroupedByArray.size() > 1;

    if (areDifferentExperiments) {
      String types = TextUtils
          .listAsSentence(CollectionUtils.sort(samplesGroupedByArray.keySet()));

      ModernDialogStatus status = ModernMessageDialog.createDialog(parent,
          parent.getAppInfo().getName(),
          MessageDialogType.WARNING_OK_CANCEL,
          "You have selected samples on arrays:",
          types,
          "Are you sure you want to group them?");

      if (status == ModernDialogStatus.CANCEL) {
        return;
      }
    }

    /*
     * for threading ExpressionDataTask expressionTask = new
     * ExpressionDataTask(parent, experiments, samplesGroupedByArray, type,
     * columns, columnAnnotations, checkIfFileExists, statusModel);
     * 
     * expressionTask.execute();
     */

    download(parent,
        samplesGroupedByArray,
        type,
        columns,
        columnAnnotations,
        checkIfFileExists,
        statusModel);
  }

  /**
   * Download and merge samples.
   *
   * @param parent the parent
   * @param samplesGroupedByArray the samples grouped by array
   * @param type the type
   * @param columns the columns
   * @param columnAnnotations the column annotations
   * @param checkIfFileExists the check if file exists
   * @param statusModel the status model
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws NetworkFileException the network file exception
   * @throws ParseException the parse exception
   */
  public void download(Frame parent,
      Map<String, Set<Sample>> samplesGroupedByArray,
      MicroarrayNormalizationType type,
      List<Boolean> columns,
      List<DataViewField> columnAnnotations,
      boolean checkIfFileExists,
      StatusModel statusModel)
      throws IOException, NetworkFileException, ParseException {

    //
    // Download the experiments so we can extract data from them
    //

    Map<Sample, Path> sampleFiles = new TreeMap<Sample, Path>();

    for (String arrayDesign : samplesGroupedByArray.keySet()) {
      for (Sample sample : samplesGroupedByArray.get(arrayDesign)) {
        Path localFile = downloadExpressionData(sample,
            type,
            checkIfFileExists,
            statusModel);

        sampleFiles.put(sample, localFile);
      }
    }

    //
    // Now lets merge all of the experiment data together
    //

    Path allSamplesFile = pasteFiles(sampleFiles,
        columns,
        columnAnnotations,
        statusModel);

    // If the first column is set to true and the others are false, this
    // means only the signal is required. In that case we can load the
    // data directly into plot

    /*
     * boolean signal = columns.get(0);
     * 
     * boolean other = false;
     * 
     * for (int i = 1; i < columns.size(); ++i) { other = columns.get(i);
     * 
     * if (other) { break; } }
     */

    LOG.info("Opening {}...", allSamplesFile);

    try {
      MainBioMatCalc.autoOpen(allSamplesFile, 3);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // SwingUtilities.invokeLater(new PlotSamples(allSamplesFile,
    // samplesGroupedByArray.keySet().iterator().next()));

    /*
     * if (signal && !other) { // A numerical matrix can be sent straight to the
     * plot tool SwingUtilities.invokeLater(new PlotSamples(allSamplesFile,
     * samplesGroupedByArray.keySet().iterator().next())); } else {
     * SwingUtilities.invokeLater(new ShowSamples(allSamplesFile,
     * samplesGroupedByArray.keySet().iterator().next())); }
     */
  }

  /**
   * Takes a list of files and pastes them together.
   *
   * @param sampleFileMap the sample file map
   * @param columns the columns
   * @param columnAnnotations the column annotations
   * @param statusModel the status model
   * @return the path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private Path pasteFiles(Map<Sample, Path> sampleFileMap,
      List<Boolean> columns,
      List<DataViewField> columnAnnotations,
      StatusModel statusModel) throws IOException {
    LOG.info("Merging expression data...");

    Path tempFile1 = TmpService.getInstance().newTmpFile("txt");

    String line;

    BufferedReader reader;

    //
    // Create a list of file handlers for each file
    //

    List<Sample> samples = CollectionUtils.sort(sampleFileMap.keySet());

    List<BufferedReader> readers = new ArrayList<BufferedReader>();

    for (Entry<Sample, Path> entry : sampleFileMap.entrySet()) {
      readers.add(FileUtils.newBufferedReader(entry.getValue()));
    }

    //
    // First make a list of the common probes
    //

    LOG.info("Finding common probes...");

    List<String> probes = new ArrayList<String>();
    Map<String, Integer> probeMap = new HashMap<String, Integer>();

    for (BufferedReader r : readers) {
      try {

        // Skip header
        r.readLine();

        while ((line = r.readLine()) != null) {
          if (Io.isEmptyLine(line)) {
            continue;
          }

          List<String> tokens = TextUtils.tabSplit(line);

          String probe = tokens.get(0);

          if (probe.equals("")) {
            continue;
          }

          if (probeMap.containsKey(probe)) {
            probeMap.put(probe, probeMap.get(probe) + 1);
          } else {
            // initially set
            probeMap.put(probe, 1);
            probes.add(probe);
          }
        }
      } finally {
        r.close();
      }
    }

    LOG.info("Sorting common probes...");

    List<String> commonProbesList = new ArrayList<String>();
    Set<String> commonProbesSet = new HashSet<String>();

    for (String probe : probes) {
      if (probeMap.get(probe) < sampleFileMap.size()) {
        continue;
      }

      commonProbesList.add(probe);
      commonProbesSet.add(probe);
    }

    // Allow memory to be freed if necessary
    probes.clear();
    probeMap.clear();

    LOG.info("Building common annotations...");

    // We allow for repeating probe ids where the gene annotation is different
    Map<String, List<String>> annotationMap = DefaultHashMap
        .create(new ArrayListCreator<String>());

    // Since all the files have the same annotation, use the first
    reader = FileUtils.newBufferedReader(sampleFileMap.get(samples.get(0)));

    try {
      // Skip header
      reader.readLine();

      while ((line = reader.readLine()) != null) {
        if (Io.isEmptyLine(line)) {
          continue;
        }

        List<String> tokens = TextUtils.tabSplit(line);

        String probe = tokens.get(0);

        if (probe.equals("")) {
          continue;
        }

        if (!commonProbesSet.contains(probe)) {
          continue;
        }

        // The first three columns are annotation (probe, entrez, gene symbol)
        // so
        // we store them with the probe since this doesn't change regardless
        // of the number of samples

        // Since there can be multiple gene annotations for a given probe,
        // we store all of them
        annotationMap.get(probe)
            .add(TextUtils.tabJoin(CollectionUtils.head(tokens, 3)));
      }
    } finally {
      reader.close();
    }

    //
    // Do some writing
    //

    LOG.info("Writing common probes...");

    BufferedWriter writer = FileUtils.newBufferedWriter(tempFile1);

    readers = new ArrayList<BufferedReader>();

    for (Sample sample : samples) {
      readers.add(FileUtils.newBufferedReader(sampleFileMap.get(sample)));
    }

    // Create a global header

    List<String> tokens;

    // System.err.println("Writing to " + tempFile1 + " " +
    // commonProbesList.size()
    // + " " + samples.size());

    try {
      writer.write("Probe ID");
      writer.write(TextUtils.TAB_DELIMITER);
      writer.write("Entrez ID");
      writer.write(TextUtils.TAB_DELIMITER);
      writer.write("Gene Symbol");

      for (int i = 0; i < samples.size(); ++i) {
        Sample sample = samples.get(i);
        reader = readers.get(i);

        line = reader.readLine();

        tokens = TextUtils.tabSplit(line);

        // don't need the first three columns as these are duplicated in
        // every expression file
        tokens = CollectionUtils.subList(tokens, 3);

        //
        // Now we deal with each of the columns in the annotation
        // file, notably to replace the names with the real sample
        // name to account for changes in the file and the database

        for (int c = 0; c < tokens.size(); ++c) {
          if (!columns.get(c % columns.size())) {
            continue;
          }

          String header = tokens.get(c);

          header = header.replace("rma-", "");
          header = header.replace("mas5-", "");

          // replace the sample name at the beginning with the real
          // sample name
          header = header.replaceFirst("^.+?\\.", sample.getName() + " ");

          if (columnAnnotations != null) {
            List<String> v = new ArrayList<String>();

            for (DataViewField a : columnAnnotations) {
              v.add(sample.getTags().getTag(a.getPath()).getValue());
            }

            if (v.size() > 0) {
              header += " " + TextUtils
                  .parenthesis(TextUtils.join(v, TextUtils.COMMA_DELIMITER));
            }
          }

          writer.write(TextUtils.TAB_DELIMITER);
          writer.write(header);
        }
      }

      writer.newLine();

      // write the probes

      for (String probe : commonProbesList) {
        // For each different gene annotation of a probe, write out
        // the expression

        // System.err.println("common probe " + probe);

        for (String annotation : annotationMap.get(probe)) {
          writer.write(annotation);

          tokens = null;

          // For each sample, skip lines until we find the probe of interest
          for (int i = 0; i < samples.size(); ++i) {
            reader = readers.get(i);

            boolean skip = false;

            while (true) {
              line = reader.readLine();

              if (Io.isEmptyLine(line)) {
                skip = true;
                break;
              }

              tokens = TextUtils.tabSplit(line);

              // System.err.println(tokens.get(0) + " " + probe + " " +
              // annotation + " " +
              // samples.get(0).getName() + " " + samples.get(1).getName() + " "
              // +
              // commonProbesList.size() + " " +
              // annotationMap.get(probe).size());

              if (tokens.get(0).equals(probe)) {
                break;
              }
            }

            if (skip) {
              continue;
            }

            // Skip the annotation columns in each file
            tokens = CollectionUtils.subList(tokens, 3);

            // Write out the annotation for this probe for this sample
            for (int c = 0; c < tokens.size(); ++c) {
              if (!columns.get(c % columns.size())) {
                continue;
              }

              writer.write(TextUtils.TAB_DELIMITER);
              writer.write(tokens.get(c));
            }
          }

          // Finally write a new line since we have written the annotations
          // for each sample
          writer.newLine();
        }
      }
    } finally {
      for (BufferedReader r : readers) {
        r.close();
      }

      writer.close();
    }

    LOG.info("Finished pasting {}", tempFile1);

    return tempFile1;

    // Now we have a list of common probes

    // statusModel.setStatus("Concatenating files...");

    /*
     * // re-read the files
     * 
     * for (Entry<Sample, File> entry : sampleFileMap.entrySet()) {
     * readers.put(entry.getKey(), new BufferedReader(new
     * FileReader(entry.getValue()))); }
     * 
     * List<String> tokens;
     * 
     * for (Entry<Sample, BufferedReader> entry : readers.entrySet()) { Sample
     * sample = entry.getKey(); reader = entry.getValue();
     * 
     * try { line = reader.readLine();
     * 
     * System.err.println(entry.getKey().getName());
     * 
     * tokens = TextUtils.fastSplit(line, TextUtils.TAB_DELIMITER);
     * 
     * // don't need the id column as this is duplicated in // every expression
     * file tokens = ArrayUtils.subList(tokens, 1);
     * 
     * for (int c = 0; c < tokens.size(); ++c) { if (!columns.get(c %
     * columns.size())) { continue; }
     * 
     * String h = tokens.get(c).replace(".rma-Signal",
     * "").replace(".mas5-Signal", "");
     * 
     * //header.add(h);
     * 
     * if (columnAnnotations != null) { List<String> v = new
     * ArrayList<String>();
     * 
     * for (DataViewField a : columnAnnotations) { // TODO check if getName
     * correct
     * v.add(sample.getSection(a.getSection().getType()).getField(a.getName()));
     * }
     * 
     * if (v.size() > 0) { h += " (" + TextUtils.join(v,
     * TextUtils.COMMA_DELIMITER) + ")"; } }
     * 
     * header.add(h); }
     * 
     * while ((line = reader.readLine()) != null) { if (Io.isEmptyLine(line)) {
     * continue; }
     * 
     * tokens = TextUtils.fastSplit(line, TextUtils.TAB_DELIMITER);
     * 
     * String probe = tokens.get(0);
     * 
     * if (!probeText.containsKey(probe)) { continue; }
     * 
     * tokens = ArrayUtils.subList(tokens, 1);
     * 
     * List<String> printColumns = new ArrayList<String>();
     * 
     * for (int c = 0; c < tokens.size(); ++c) { if (!columns.get(c %
     * columns.size())) { continue; }
     * 
     * printColumns.add(tokens.get(c)); }
     * 
     * // store the probe probeText.put(probe, TextUtils.join(printColumns,
     * TextUtils.TAB_DELIMITER)); } } finally { reader.close(); }
     * 
     * // for this particular file we can now write it out // by concatenating
     * it with the temp file
     * 
     * // first the header
     * 
     * System.err.println("header " + header.toString() + " " + tempFile1 + " "
     * + commonProbes.size());
     * 
     * reader = new BufferedReader(new FileReader(tempFile1)); writer = new
     * BufferedWriter(new FileWriter(tempFile2));
     * 
     * try { //writer.write(reader.readLine());
     * //writer.write(TextUtils.TAB_DELIMITER); //System.err.println("headers "
     * + header);
     * 
     * //writer.write(TextUtils.join(header, TextUtils.TAB_DELIMITER));
     * //writer.newLine();
     * 
     * // now the probes. Since the temp file is in order // of common probes,
     * we simply write it out in order
     * 
     * for (String probe : commonProbes) { //System.err.println("probe " +
     * probe);
     * 
     * writer.write(reader.readLine()); writer.write(TextUtils.TAB_DELIMITER);
     * writer.write(probeText.get(probe)); writer.newLine(); } } finally {
     * reader.close(); writer.close(); }
     * 
     * Io.delete(tempFile1);
     * 
     * // now move temp2 to temp1 and begin again boolean success =
     * tempFile2.renameTo(tempFile1);
     * 
     * System.err.println("rename " + tempFile2 + " to " + tempFile1 + " " +
     * success); }
     * 
     * // Create the final output file
     * 
     * reader = new BufferedReader(new FileReader(tempFile1)); writer = new
     * BufferedWriter(new FileWriter(tempFile2));
     * 
     * try { writer.write(TextUtils.join(header, TextUtils.TAB_DELIMITER));
     * writer.newLine();
     * 
     * while ((line = reader.readLine()) != null) { writer.write(line);
     * writer.newLine(); } } finally { reader.close(); writer.close(); }
     * 
     * Io.delete(tempFile1);
     * 
     * //statusModel.setReady();
     * 
     * return tempFile2;
     */

    /*
     * 
     * statusModel.setStatus("Adding annotation...");
     * 
     * reader = new BufferedReader(new FileReader(tempFile1)); writer = new
     * BufferedWriter(new FileWriter(tempFile2));
     * 
     * try { // the header information
     * 
     * writer.write(DataMatrix.EST_VERSION_1); writer.newLine();
     * 
     * //number of rows writer.write(DataMatrix.EST_ROWS);
     * writer.write(TextUtils.TAB_DELIMITER);
     * writer.write(Integer.toString(commonProbes.size())); writer.newLine();
     * 
     * //columns writer.write(DataMatrix.EST_COLUMNS);
     * writer.write(TextUtils.TAB_DELIMITER);
     * writer.write(Integer.toString(header.size())); writer.newLine();
     * 
     * // text rows (row annotation)
     * writer.write(DataMatrix.EST_ANNOTATION_GROUPS);
     * writer.write(TextUtils.TAB_DELIMITER); writer.write("0");
     * writer.newLine();
     * 
     * // text rows (row annotation)
     * writer.write(DataMatrix.EST_ANNOTATION_ROWS);
     * writer.write(TextUtils.TAB_DELIMITER); writer.write("0");
     * writer.newLine();
     * 
     * // text rows (row annotation)
     * writer.write(DataMatrix.EST_ANNOTATION_COLUMNS);
     * writer.write(TextUtils.TAB_DELIMITER);
     * 
     * if (columnAnnotations != null) {
     * writer.write(Integer.toString(columnAnnotations.size()));
     * 
     * writer.write(TextUtils.TAB_DELIMITER);
     * 
     * for (DataViewField a : columnAnnotations) {
     * writer.write(TextUtils.TAB_DELIMITER); writer.write(a.getDisplayName());
     * } } else { writer.write("0"); }
     * 
     * writer.newLine();
     * 
     * // Now the column annotations
     * 
     * writer.write(DataMatrix.ROW_NAMES);
     * writer.write(TextUtils.TAB_DELIMITER);
     * writer.write(TextUtils.join(header, TextUtils.TAB_DELIMITER));
     * writer.newLine();
     * 
     * // additional annotations if (columnAnnotations != null) { for
     * (DataViewField a : columnAnnotations) { // to account for the probe id
     * //writer.write(Text.TAB_DELIMITER);
     * 
     * for (Sample sample : readers.keySet()) {
     * writer.write(TextUtils.TAB_DELIMITER);
     * writer.write(sample.getSection(a.getSection().getType()).getField(a)); }
     * 
     * writer.newLine(); } }
     * 
     * // copy all of the lines as is while ((line = reader.readLine()) != null)
     * { writer.write(line); writer.newLine(); } } finally { reader.close();
     * writer.close(); }
     * 
     * Io.delete(tempFile1);
     * 
     * // now move temp2 to temp1 and begin again tempFile2.renameTo(tempFile1);
     * 
     * return tempFile1;
     */
  }

  /**
   * Formats a sample name to match a file name to remove inconsistencies in the
   * way files are named and the way samples are named.
   *
   * @param name the name
   * @return the string
   */
  public final String convertSampleNameToFilename(String name) {
    String formattedName = name;

    formattedName = name.replaceAll("\\s", "_");
    formattedName = name.replaceAll("\\(", "_");
    formattedName = name.replaceAll("\\)", "_");
    formattedName = name.replaceAll("_$", "");

    return formattedName;
  }

  /**
   * Download a file associated with a given sample.
   *
   * @param sample the sample
   * @param type the type
   * @param checkExists the check exists
   * @param statusModel the status model
   * @return the path
   * @throws NetworkFileException the network file exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  private final Path downloadExpressionData(Sample sample,
      MicroarrayNormalizationType type,
      boolean checkExists,
      StatusModel statusModel)
      throws NetworkFileException, IOException, ParseException {
    LOG.info("Downloading expression data for sample {} ...", sample.getName());

    FileDownloader downloader = RepositoryService.getInstance()
        .getRepository(RepositoryService.DEFAULT_REP).getFileDownloader();

    VfsFile arrayFile = getRemoteExpressionFile(sample, type);

    Path localFile = TmpService.getInstance().newTmpFile();

    downloader.downloadFile(arrayFile, localFile);

    return localFile;
  }

  /**
   * For a given sample, returns the file accessor for its expression file in
   * either MAS5 or RMA form.
   *
   * @param sample the sample
   * @param normalisationType the normalisation type
   * @return the remote expression file
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  private final VfsFile getRemoteExpressionFile(Sample sample,
      MicroarrayNormalizationType normalisationType)
      throws IOException, ParseException {

    String type;

    if (normalisationType == MicroarrayNormalizationType.RMA) {
      type = DownloadManager.RMA;
    } else {
      type = DownloadManager.MAS5;
    }

    Repository repository = RepositoryService.getInstance()
        .getRepository(RepositoryService.DEFAULT_REP);

    for (VfsFile f : repository.getSampleFiles(sample)) {
      if (f.getExt().equals(type)) {
        return f;
      }
    }

    return null;
  }
}
