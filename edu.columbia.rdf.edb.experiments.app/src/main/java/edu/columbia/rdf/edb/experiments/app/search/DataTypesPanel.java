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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.collections.IterHashMap;
import org.jebtk.core.collections.IterMap;
import org.jebtk.core.collections.IterTreeMap;
import org.jebtk.modern.button.ModernCheckSwitch;
import org.jebtk.modern.button.ModernTwoStateWidget;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickEventProducer;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.event.ModernClickListeners;
import org.jebtk.modern.panel.HBox;

import edu.columbia.rdf.edb.Species;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;

/**
 * The Class DataTypesPanel.
 */
public class DataTypesPanel extends HBox
    implements ModernClickEventProducer, ModernClickListener {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m types map. */
  private IterMap<ModernTwoStateWidget, Type> mTypesMap = new IterHashMap<ModernTwoStateWidget, Type>();

  /** The m species map. */
  private IterMap<ModernTwoStateWidget, Species> mSpeciesMap = new IterHashMap<ModernTwoStateWidget, Species>();

  /** The m listeners. */
  private ModernClickListeners mListeners = new ModernClickListeners();

  /**
   * Instantiates a new data types panel.
   */
  public DataTypesPanel() {

    // add(new ModernLabel("Show:", 40));
    // add(UI.createHGap(10));

    Repository rep = RepositoryService.getInstance()
        .getRepository(RepositoryService.DEFAULT_REP);

    Collection<Type> types = rep.getDataTypes();

    IterTreeMap<String, Type> typeMap = new IterTreeMap<String, Type>();

    for (Type t : types) {
      typeMap.put(t.getName(), t);
    }

    for (Entry<String, Type> f : typeMap) {
      ModernTwoStateWidget check = new ModernCheckSwitch(f.getKey(), true); // new
                                                                      // FilterButton(name,
                                                                      // true);

      check.addClickListener(this);

      mTypesMap.put(check, f.getValue());

      add(check);
      // add(UI.createHGap(5));
    }

    // add(UI.createHGap(5));

    Collection<Species> species = rep.getOrganisms();

    IterTreeMap<String, Species> speciesMap = new IterTreeMap<String, Species>();

    for (Species s : species) {
      speciesMap.put(s.getName(), s);
    }

    for (Entry<String, Species> f : speciesMap) {
      ModernTwoStateWidget check = new ModernCheckSwitch(f.getKey(), true);

      check.addClickListener(this);

      mSpeciesMap.put(check, f.getValue());

      add(check);
      // add(UI.createHGap(5));
    }
  }

  /**
   * Gets the data types.
   *
   * @return the data types
   */
  public Collection<Type> getDataTypes() {
    List<Type> ret = new ArrayList<Type>(mTypesMap.size());

    for (Entry<ModernTwoStateWidget, Type> f : mTypesMap) {
      if (f.getKey().isSelected()) {
        ret.add(f.getValue());
      }
    }

    // Searching for all types is equivelent to not specifying so
    // we can return an empty set
    if (ret.size() < mTypesMap.size()) {
      return ret;
    } else {
      return Repository.ALL_TYPES;
    }
  }

  /**
   * Get organisms.
   *
   * @return the organisms
   */
  public Collection<Species> getOrganisms() {
    List<Species> ret = new ArrayList<Species>(mSpeciesMap.size());

    for (Entry<ModernTwoStateWidget, Species> f : mSpeciesMap) {
      if (f.getKey().isSelected()) {
        ret.add(f.getValue());
      }
    }

    if (ret.size() < mSpeciesMap.size()) {
      return ret;
    } else {
      return Repository.ALL_ORGANISMS;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickEventProducer#addClickListener(org.abh.
   * common.ui.event.ModernClickListener)
   */
  public void addClickListener(ModernClickListener l) {
    mListeners.addClickListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.ui.event.ModernClickEventProducer#removeClickListener(org.
   * abh. common.ui.event.ModernClickListener)
   */
  @Override
  public void removeClickListener(ModernClickListener l) {
    mListeners.removeClickListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickEventProducer#fireClicked(org.abh.
   * common. ui.event.ModernClickEvent)
   */
  @Override
  public void fireClicked(ModernClickEvent e) {
    mListeners.fireClicked(e);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    fireClicked(e);
  }

}
