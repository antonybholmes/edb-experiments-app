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
import java.text.ParseException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.bioinformatics.genomic.Genome;
import org.jebtk.bioinformatics.genomic.GenomeService;
import org.jebtk.core.collections.IterMap;
import org.jebtk.core.path.Path;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.SelectionModel;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.Raster24Icon;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.status.StatusModel;
import org.jebtk.modern.window.ModernRibbonWindow;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.EDBWLogin;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.experiments.app.cart.SampleCartService;
import edu.columbia.rdf.edb.ui.SampleSortService;
import edu.columbia.rdf.edb.ui.ViewPlugin;
import edu.columbia.rdf.edb.ui.network.ServerException;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;
import edu.columbia.rdf.htsview.app.HTSViewInfo;
import edu.columbia.rdf.htsview.app.MainHtsView;
import edu.columbia.rdf.htsview.chipseq.SortSamplesByCellType;
import edu.columbia.rdf.htsview.chipseq.SortSamplesByClassification;
import edu.columbia.rdf.htsview.chipseq.SortSamplesByGenome;
import edu.columbia.rdf.htsview.chipseq.SortSamplesByTreatment;

/**
 * Plugin for display of microarray data.
 * 
 * @author Antony Holmes
 *
 */
public class ChipSeqViewPlugin extends ViewPlugin
    implements ModernClickListener {

  /** The Constant CHIPSEQ. */
  public static final String CHIPSEQ = "ChIP-Seq";

  /** The m view. */
  private DataView mView;

  /** The m display field 1. */
  private Path mDisplayField1 = new Path("/ChIP-Seq/Sample/Seq_Id");

  /** The m display field 2. */
  private Path mDisplayField2 = new Path("/ChIP-Seq/Sample/Treatment");

  /** The m view button. */
  private RibbonLargeButton mViewButton = new RibbonLargeButton(
      new HTSViewInfo().getName(), new Raster24Icon(new HTSViewInfo().getIcon()));

  /** The m parent. */
  private ModernRibbonWindow mParent;

  /** The m selected samples. */
  //private SelectionModel<Sample> mSelectedSamples;

  /** The m login. */
  private EDBWLogin mLogin;

  /**
   * Instantiates a new chip seq view plugin.
   *
   * @param login the login
   */
  public ChipSeqViewPlugin(EDBWLogin login) {
    mLogin = login;

    mView = new ChipSeqDataView();

    mViewButton.addClickListener(this);
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
    SampleSortService.getInstance().add(new SortSamplesByCellType());
    SampleSortService.getInstance().add(new SortSamplesByClassification());
    SampleSortService.getInstance().add(new SortSamplesByGenome());
    SampleSortService.getInstance().add(new SortSamplesByTreatment());
    // sampleSortModel.add(new SortSamplesBySourceCellType());
    // sampleSortModel.add(new SortSamplesBySourceDiseaseState());
    // sampleSortModel.add(new SortSamplesBySourceDiseaseStatus());
    // sampleSortModel.add(new SortSamplesBySourceGender());
    // sampleSortModel.add(new SortSamplesByMicroarrayBasedClassification());
    // sampleSortModel.add(new SortSamplesBySourceMaterialType());
    // sampleSortModel.add(new SortSamplesBySourceOrganismPart());
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

    group = new SearchCategoryGroup("ChIP-Seq");
    group.addCategory(
        new SearchCategory("Sequence Id", new Path("/ChIP-Seq/Sample/Seq_Id")));
    group.addCategory(new SearchCategory("Classification",
        new Path("/ChIP-Seq/Sample/Classification")));
    group.addCategory(new SearchCategory("Cell Type",
        new Path("/ChIP-Seq/Sample/Cell_Type")));
    group.addCategory(new SearchCategory("Treatment",
        new Path("/ChIP-Seq/Sample/Treatment")));
    group.addCategory(
        new SearchCategory("Genome", new Path("/ChIP-Seq/Sample/Genome")));

    SearchCategoryService.getInstance().addGroup(group);
    */
    
    try {
      SearchCategoryService.getInstance().loadXml(ChipSeqDataView.XML_VIEW_FILE);
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
    return new ChipSeqSampleDataPanel(sample, mView);
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
        .add(mViewButton);

    mViewButton.setToolTip("View ChIP-seq data",
        "View ChIP-seq data for the currently selected samples.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    try {
      showSamples();
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (ServerException e1) {
      e1.printStackTrace();
    } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    } catch (SAXException e1) {
      e1.printStackTrace();
    } catch (ParserConfigurationException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * Show samples.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ServerException the server exception
   * @throws ClassNotFoundException the class not found exception
   * @throws ParseException the parse exception
   * @throws SAXException the SAX exception
   * @throws ParserConfigurationException the parser configuration exception
   */
  private void showSamples()
      throws IOException, ServerException, ClassNotFoundException,
      SAXException, ParserConfigurationException {
    List<Sample> samples = SampleCartService.getInstance().toList(); //mSelectedSamples.getItems();

    if (samples.size() == 0) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You must select at least one sample.");

      return;
    }

    boolean correctType = true;

    for (Sample sample : samples) {
      if (!sample.getDataType().getName().equals(getDataType())) {
        correctType = false;
        break;
      }
    }

    if (!correctType) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You can only view track data for ChIP-seq samples.");

      return;
    }

    // Remove locked

    if (checkForLocked(samples)) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You have selected one or more locked samples. These will not be shown.");

      samples = getUnlockedSamples(samples);
    }

    IterMap<String, Set<Sample>> sorted = Sample.sortByGenome(samples);

    if (sorted.size() > 0) {
      for (Entry<String, Set<Sample>> item : sorted) {
        Genome g = GenomeService.getInstance().guessGenome(item.getKey());
        
        MainHtsView.main(mLogin, g, item.getValue());
      }
    }
  }

  

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getExpressionType()
   */
  @Override
  public String getDataType() {
    return "ChIP-Seq";
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
