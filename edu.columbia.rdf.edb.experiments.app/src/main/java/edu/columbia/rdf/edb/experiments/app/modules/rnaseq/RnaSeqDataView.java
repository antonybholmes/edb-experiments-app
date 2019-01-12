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

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.io.PathUtils;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.ui.DataViewXmlHandler;

/**
 * Plugin for display of microarray data.
 * 
 * @author Antony Holmes
 *
 */
public class RnaSeqDataView extends DataView {

  public static final java.nio.file.Path XML_VIEW_FILE = PathUtils
      .getPath("res/modules/rnaseq.xml");

  /**
   * Instantiates a new rna seq data view.
   */
  public RnaSeqDataView() {
    super("RNA-seq");

    try {
      DataViewXmlHandler.loadXml(XML_VIEW_FILE, this);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      e.printStackTrace();
    }

    /*
     * DataViewSection section = new DataViewSection("Sample");
     * section.addField(new DataViewField(new Path("/RNA-seq/Sample/Seq_Id"),
     * "Sequence Id")); section.addField(new DataViewField(new
     * Path("/Sample/Organism"), "Organism")); section.addField(new
     * DataViewField(new Path("/RNA-seq/Sample/Genome"), "Genome"));
     * section.addField(new DataViewField(new
     * Path("/RNA-seq/Sample/Read_Length"), "Read Length"));
     * 
     * addSection(section);
     */
  }

}
