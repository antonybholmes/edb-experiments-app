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
package edu.columbia.rdf.edb.experiments.app.sample;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.text.TextUtils;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.io.TsvGuiFileFilter;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.DataViewField;
import edu.columbia.rdf.edb.DataViewSection;
import edu.columbia.rdf.edb.Sample;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleManager.
 */
public class SampleManager {

  /**
   * Export.
   *
   * @param parent the parent
   * @param sample the sample
   * @param view the view
   * @return the path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path export(ModernWindow parent, Sample sample, DataView view)
      throws IOException {
    List<Sample> samples = new ArrayList<Sample>();

    samples.add(sample);

    return export(parent, samples, view);
  }

  /**
   * Export.
   *
   * @param parent the parent
   * @param samples the samples
   * @param view the view
   * @return the path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final static Path export(ModernWindow parent,
      List<Sample> samples,
      DataView view) throws IOException {

    if (samples == null || samples.size() == 0) {
      ModernMessageDialog.createDialog(parent,
          parent.getAppInfo().getName(),
          "You must select at least one sample to export.",
          MessageDialogType.WARNING);

      return null;
    }

    JFileChooser fc = new JFileChooser();

    TsvGuiFileFilter tsvFilter = new TsvGuiFileFilter();

    fc.addChoosableFileFilter(tsvFilter);
    fc.setAcceptAllFileFilterUsed(false);
    fc.setFileFilter(tsvFilter);

    // Show it.
    int returnVal = fc.showDialog(parent, "Export");

    // Process the results.
    if (returnVal == JFileChooser.CANCEL_OPTION) {
      return null;
    }

    java.nio.file.Path output = PathUtils
        .addExtension(fc.getSelectedFile().toPath(), "txt");

    if (FileUtils.exists(output)) {
      ModernDialogStatus n = ModernMessageDialog.createFileReplaceDialog(parent,
          output);

      if (n == ModernDialogStatus.CANCEL) {
        return export(parent, samples, view);
      }
    }

    export(samples, view, output);

    ModernMessageDialog
        .createFileSavedDialog(parent, parent.getAppInfo().getName(), output);

    return output;
  }

  /**
   * Export.
   *
   * @param samples the samples
   * @param view the view
   * @param output the output
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void export(List<Sample> samples, DataView view, Path output)
      throws IOException {
    BufferedWriter out = FileUtils.newBufferedWriter(output);

    try {

      //
      // Output a header
      //

      List<String> tokens = new ArrayList<String>();

      for (DataViewSection section : view) {
        tokens.add(section.getName());
        tokens.add(TextUtils.emptyCells(section.size() - 1));
      }

      out.write(TextUtils.join(tokens, TextUtils.TAB_DELIMITER));
      out.newLine();

      tokens = new ArrayList<String>();

      for (DataViewSection section : view) {
        for (DataViewField field : section) {
          tokens.add(field.getName());
        }
      }

      out.write(TextUtils.join(tokens, TextUtils.TAB_DELIMITER));
      out.newLine();

      //
      // items
      //

      for (Sample s : samples) {
        tokens = new ArrayList<String>();

        for (DataViewSection section : view) {
          for (DataViewField field : section) {
            // System.err.println(field + ":" +
            // s.getSection(section.getType()).getData(field));

            tokens.add(s.getTags().getTag(field.getPath()).getValue());
          }
        }

        out.write(TextUtils.join(tokens, TextUtils.TAB_DELIMITER));
        out.newLine();
      }
    } finally {
      out.close();
    }
  }

  /*
   * public static void exportExperiments(ModernWindow parent, Experiment
   * experiment) throws IOException { List<Experiment> experiments = new
   * ArrayList<Experiment>();
   * 
   * experiments.add(experiment);
   * 
   * exportExperiments(parent, experiments); }
   */

  /*
   * public final static File exportExperiments(ModernWindow parent,
   * List<Experiment> experiments) throws IOException {
   * 
   * if (experiments == null || experiments.size() == 0) {
   * ModernMessageDialog.createDialog(parent, parent.getAppInfo().getName(),
   * "You must select at least one experiment to export.",
   * MessageDialogType.WARNING);
   * 
   * return null; }
   * 
   * JFileChooser fc = new JFileChooser();
   * 
   * TsvGuiFileFilter tsvFilter = new TsvGuiFileFilter();
   * 
   * fc.addChoosableFileFilter(tsvFilter); fc.setAcceptAllFileFilterUsed(false);
   * fc.setFileFilter(tsvFilter);
   * 
   * //Show it. int returnVal = fc.showDialog(parent, "Export");
   * 
   * //Process the results. if (returnVal == JFileChooser.CANCEL_OPTION) {
   * return null; }
   * 
   * File output = Io.addExtension(fc.getSelectedFile(), "txt");
   * 
   * if (output.exists()) { ModernDialogStatus n =
   * ModernMessageDialog.createFileReplaceDialog(parent, output);
   * 
   * if (n == ModernDialogStatus.CANCEL) { return exportExperiments(parent,
   * experiments); } }
   * 
   * boolean ret = exportExperiments(experiments, output);
   * 
   * if (ret) { ModernMessageDialog.createFileSavedDialog(parent,
   * parent.getAppInfo().getName(), output);
   * 
   * return output; } else { return null; } }
   */

  /*
   * public static boolean exportExperiments(List<Experiment> experiments, File
   * output) {
   * 
   * try{ BufferedWriter out = new BufferedWriter(new FileWriter(output));
   * 
   * try { for (Experiment experiment : experiments) {
   * out.write("Experiment Title"); out.write(TextUtils.TAB_DELIMITER);
   * 
   * out.write(experiment.getName()); out.newLine();
   * 
   * if (experiment.getArrayDesigns() != null) { out.write("Array Designs");
   * out.write(TextUtils.TAB_DELIMITER);
   * 
   * List<String> values = new ArrayList<String>();
   * 
   * for (ArrayDesign arrayDesign : experiment.getArrayDesigns()) {
   * values.add(arrayDesign.getName() + " (" + arrayDesign.getProvider() + ")");
   * }
   * 
   * Collections.sort(values);
   * 
   * out.write(TextUtils.join(values, TextUtils.COMMA_DELIMITER));
   * out.newLine(); }
   * 
   * out.write("Organisms"); out.write(TextUtils.TAB_DELIMITER);
   * out.write(TextUtils.join(ArrayUtils.sort(experiment.getOrganisms()),
   * TextUtils.COMMA_DELIMITER)); out.newLine();
   * 
   * out.write("Type"); out.write(TextUtils.TAB_DELIMITER);
   * out.write(TextUtils.join(ArrayUtils.sort(experiment.getSampleTypes()),
   * TextUtils.COMMA_DELIMITER)); out.newLine();
   * 
   * out.write("Description"); out.write(TextUtils.TAB_DELIMITER);
   * 
   * out.write(experiment.getDescription()); out.newLine();
   * 
   * out.write("Persons"); out.newLine();
   * 
   * for (Person contact : experiment.getPersons()) {
   * out.write(contact.getName()); out.write(TextUtils.TAB_DELIMITER);
   * out.write(contact.getEmailAddress()); out.newLine();
   * 
   * List<String> roles = new ArrayList<String>();
   * 
   * for (String role : contact.getRoles()) {
   * roles.add(SubstitutionService.getInstance().getSubstitute(role)); }
   * 
   * Collections.sort(roles);
   * 
   * out.write(TextUtils.join(roles, TextUtils.TAB_DELIMITER)); out.newLine(); }
   * 
   * out.newLine(); } } finally { out.close(); } } catch (Exception e) {
   * e.printStackTrace();
   * 
   * return false; }
   * 
   * return true; }
   */
}
