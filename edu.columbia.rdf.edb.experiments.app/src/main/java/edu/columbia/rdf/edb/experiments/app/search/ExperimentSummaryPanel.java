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

import java.util.Collection;
import java.util.List;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.modern.UI;
import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.scrollpane.ScrollBarLocation;
import org.jebtk.modern.scrollpane.ScrollBarPolicy;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernMultilineLabel;
import org.jebtk.modern.theme.ThemeService;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.sample.SampleModel;

// TODO: Auto-generated Javadoc
/**
 * Displays an experiment's details and contacts.
 *
 * @author Antony Holmes Holmes
 */
public class ExperimentSummaryPanel extends ModernPanel
    implements ModernSelectionListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m model. */
  private SampleModel mModel;

  /**
   * Instantiates a new experiment summary panel.
   *
   * @param model the model
   */
  public ExperimentSummaryPanel(SampleModel model) {
    mModel = model;

    setBackground(ThemeService.getInstance().colors().getHighlight32(0));

    setBorder(LARGE_BORDER);

    model.addSelectionListener(this);
  }

  /**
   * Display.
   *
   * @param experiments the experiments
   */
  private void display(Collection<Experiment> experiments) {
    Box box = VBox.create();

    // add(new ModernHeadingLabel("Details"), BorderLayout.PAGE_START);

    // add(new ModernHeadingLabel("Experiment"));
    // add(Ui.createVerticalGap(DOUBLE_PADDING));

    for (Experiment experiment : experiments) {
      box.add(new SummaryTitleLabel("Experiment Title"));
      box.add(new ModernAutoSizeLabel(experiment.getName()));

      box.add(UI.createVGap(10));
      box.add(new SummaryTitleLabel("Experiment Id"));
      box.add(new ModernAutoSizeLabel(experiment.getPublicId()));

      // add(Ui.createVerticalGap(10));
      // add(new SummaryTitleLabel("Sample Types"));
      // add(new
      // ModernLabel(TextUtils.join(ArrayUtils.sort(experiment.getSampleTypes()),
      // TextUtils.COMMA_DELIMITER)));

      // add(Ui.createVerticalGap(10));
      // add(new SummaryTitleLabel("Organisms"));
      // add(new
      // ModernLabel(TextUtils.join(ArrayUtils.sort(experiment.getOrganisms()),
      // TextUtils.COMMA_DELIMITER)));

      // if (experiment.getArrayDesigns() != null) {
      // add(Ui.createVerticalGap(10));
      // add(new SummaryTitleLabel("Array Designs"));
      // add(new
      // ModernLabel(TextUtils.join(ArrayUtils.sort(experiment.getArrayDesigns()),
      // TextUtils.COMMA_DELIMITER)));
      // }

      // add(Ui.createVerticalGap(10));

      /*
       * add(new SummaryTitleLabel("Contacts"));
       * 
       * Map<String, Person> contacts = new HashMap<String, Person>();
       * 
       * //for (Person contact : experiment.getPersons()) { // String name =
       * contact.getName();
       * 
       * // contacts.put(name, contact); //}
       * 
       * List<String> names = ArrayUtils.sortKeys(contacts);
       * 
       * for (String name : names) { Person contact = contacts.get(name);
       * 
       * List<String> roles = new ArrayList<String>();
       * 
       * for (String role : contact.getRoles()) {
       * roles.add(SubstitutionService.getInstance().getSubstitute(role)); }
       * 
       * Collections.sort(roles);
       * 
       * add(new ModernLabel(contact.getName() + " (" + TextUtils.join(roles,
       * TextUtils.FORMATTED_LIST_DELIMITER) + ")"));
       * 
       * 
       * add(new ModernLabel(contact.getEmailAddress()));
       * 
       * add(ModernTheme.createVerticalGap()); }
       */

      box.add(UI.createVGap(10));
      box.add(new SummaryTitleLabel("Description"));

      ModernMultilineLabel textArea = new ModernMultilineLabel(
          experiment.getDescription());
      box.add(textArea);
    }

    ModernScrollPane scrollPane = new ModernScrollPane(box);

    scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setScrollBarLocation(ScrollBarLocation.FLOATING);

    removeAll();
    setBody(scrollPane);

    revalidate();
    repaint();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernSelectionListener#selectionChanged(org.abh.
   * common.event.ChangeEvent)
   */
  @Override
  public void selectionChanged(ChangeEvent e) {
    if (mModel.size() == 0) {
      return;
    }

    Sample sample = mModel.get(0);

    List<Experiment> sortedExperiments = CollectionUtils
        .asList(sample.getExperiment());

    display(sortedExperiments);
  }
}
