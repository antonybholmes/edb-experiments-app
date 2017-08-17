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
package edu.columbia.rdf.edb.experiments.app.plugins.view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.modern.UI;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.widget.ModernWidget;

import edu.columbia.rdf.edb.Person;
import edu.columbia.rdf.edb.Sample;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleSummaryPanel.
 */
public class SampleSummaryPanel extends VBox {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The label size. */
	protected static Dimension LABEL_SIZE = 
			new Dimension(100, ModernWidget.getWidgetHeight());

	/**
	 * Instantiates a new sample summary panel.
	 *
	 * @param sample the sample
	 */
	public SampleSummaryPanel(Sample sample) {
		Box box;
		
		ModernAutoSizeLabel label = new SummaryTitleLabel(sample.getName() + " (" + sample.getExpressionType() + ")");
		

		add(UI.createVGap(5));
		
		add(label);

		if (sample.getAliases().size() > 1) {
			add(UI.createVGap(5));
			box = HBox.create();
			box.add(new ModernAutoSizeLabel("Aliases:", LABEL_SIZE));
			box.add(new SummaryLabel(sample.getAliases().toString()));
			add(box);
			
		}
		
		add(UI.createVGap(5));
		
		box = HBox.create();
		box.add(sample.getPersons().size() > 1 ? new ModernAutoSizeLabel("Contacts:", LABEL_SIZE) : new ModernAutoSizeLabel("Contact:", LABEL_SIZE));

		List<Person> persons = CollectionUtils.sort(sample.getPersons());
		
		if (persons.size() > 0) {
			for (int i = 0; i < persons.size(); ++i) {
				Person person = persons.get(i);
				
				//box.add(new ModernAutoSizeLabel(person.getName()));
				//box.add(UI.createHGap(5));
				PersonButton button = new PersonButton(person.getName(), person.getEmail());
				box.add(button);
				
				if (i < persons.size() - 1) {
					box.add(new ModernAutoSizeLabel(","));
				}
				
				box.add(UI.createHGap(5));
			}
		}
		
		/*
		for (int i = 0; i < persons.size(); ++i) {
			buffer.append(persons.get(i).getName());
			//buffer.append(" (").append(TextUtils.join(ArrayUtils.sort(persons.get(i).getRoles()), TextUtils.COMMA_DELIMITER)).append(")");
			buffer.append(" <").append(persons.get(i).getEmail()).append(">");
			
			if (i < persons.size() - 1) {
				buffer.append(TextUtils.COMMA_DELIMITER);
			}
		}
		*/
		
		//add(UI.createVGap(5));
		
		add(box);
		
		box = HBox.create();
		box.add(new ModernAutoSizeLabel("Organism:", LABEL_SIZE));
		box.add(new SummaryLabel(sample.getOrganism().getScientificName()));
		add(box);
	}
}
