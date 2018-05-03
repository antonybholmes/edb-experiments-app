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
package edu.columbia.rdf.edb.experiments.app.modules.chipseq;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.io.PathUtils;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.ui.DataViewXmlHandler;

/**
 * Plugin for display of microarray data.
 * 
 * @author Antony Holmes Holmes
 */
public class ChipSeqDataView extends DataView {

  public static final java.nio.file.Path XML_VIEW_FILE = PathUtils
      .getPath("res/modules/chipseq.xml");

  /**
   * Instantiates a new chip seq data view.
   */
  public ChipSeqDataView() {
    super("ChIP-seq");

    try {
      DataViewXmlHandler.loadXml(XML_VIEW_FILE, this);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      e.printStackTrace();
    }

    /*
     * DataViewSection section;
     * 
     * section = new DataViewSection("Sample"); section.addField(new
     * DataViewField(new Path("/ChIP-Seq/Sample/Seq_Id"), "Sequence Id"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Classification"), "Classification"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Cell_Type"), "Cell Type")); section.addField(new
     * DataViewField(new Path("/ChIP-Seq/Sample/Treatment"), "Treatment"));
     * section.addField(new DataViewField(new Path("/ChIP-Seq/Sample/Genome"),
     * "Genome")); section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Read_Length"), "Read Length"));
     * section.addField(new DataViewField(new Path("/ChIP-Seq/Sample/Reads"),
     * "Reads")); section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Mapped_Reads"), "Mapped Read"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Duplicate_Reads"), "Duplicate Reads"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Percent_Duplicate_Reads"), "% Duplicate Reads"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Unique_Reads"), "Unique Reads"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Percent_Unique_Reads"), "% Unique Reads"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Peak_Caller"), "Peak Caller"));
     * section.addField(new DataViewField(new
     * Path("/ChIP-Seq/Sample/Peak_Caller_Parameters"),
     * "Peak Caller Parameters"));
     * 
     * addSection(section);
     */
  }
}
