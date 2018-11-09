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

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Set;

import org.jebtk.modern.ModernComponent;

import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.VfsFile;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class FilesPanel.
 */
public abstract class FilesPanel extends ModernComponent {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m files. */
  protected Set<VfsFile> mFiles = null;

  /**
   * Sets the sample files.
   *
   * @param samples the new sample files
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  public void setSampleFiles(Collection<Sample> samples)
      throws IOException, ParseException {
    Repository repository = RepositoryService.getInstance()
        .getRepository(RepositoryService.DEFAULT_REP);

    mFiles = repository.getSampleFiles(samples);
  }

  /**
   * Gets the selected files.
   *
   * @return the selected files
   */
  public abstract Set<VfsFile> getSelectedFiles();
}
