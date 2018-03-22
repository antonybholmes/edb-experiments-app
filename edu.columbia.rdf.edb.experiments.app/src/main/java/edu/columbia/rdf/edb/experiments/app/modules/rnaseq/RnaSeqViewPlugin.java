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
import java.text.ParseException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.ColorUtils;
import org.jebtk.core.NetworkFileException;
import org.jebtk.core.path.Path;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.SelectionModel;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.Raster32Icon;
import org.jebtk.modern.graphics.icons.Window32VectorIcon;
import org.jebtk.modern.menu.ModernIconMenuItem;
import org.jebtk.modern.menu.ModernPopupMenu2;
import org.jebtk.modern.menu.ModernTitleMenuItem;
import org.jebtk.modern.ribbon.RibbonLargeButton;
import org.jebtk.modern.status.StatusModel;
import org.jebtk.modern.window.ModernRibbonWindow;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.DataView;
import edu.columbia.rdf.edb.Sample;
import edu.columbia.rdf.edb.ui.SampleSortService;
import edu.columbia.rdf.edb.ui.ViewPlugin;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;
import edu.columbia.rdf.htsview.chipseq.SortSamplesByGenome;

// TODO: Auto-generated Javadoc
/**
 * Plugin for display of microarray data.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class RnaSeqViewPlugin extends ViewPlugin
    implements ModernClickListener {

  /** The m fpkm button. */
  private ModernButtonWidget mFpkmButton = new RibbonLargeButton("FPKM",
      new Raster32Icon(
          new Window32VectorIcon(ColorUtils.decodeHtmlColor("#e580ff"))));

  /** The m parent. */
  private ModernRibbonWindow mParent;

  /** The m selected samples. */
  private SelectionModel<Sample> mSelectedSamples;

  /** The m status model. */
  private StatusModel mStatusModel;

  /** The m view. */
  private DataView mView;

  /** The m display field 1. */
  private Path mDisplayField1 = new Path("/Sample/Organism");

  /** The m display field 2. */
  private Path mDisplayField2 = new Path("/RNA-seq/Sample/Genome");

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
    mStatusModel = statusModel;
    mSelectedSamples = selectedSamples;

    parent.getRibbon().getHomeToolbar().getSection(getExpressionType())
        .add(mFpkmButton);

    mFpkmButton.setToolTip("MAS5 Expression Data",
        "Download MAS5 normalized expression data for the currently selected samples.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.edb.ui.ViewPlugin#customizeSampleMenu(org.abh.common.ui.
   * menu .ModernPopupMenu)
   */
  @Override
  public void customizeSampleMenu(ModernPopupMenu2 menu) {
    menu.add(new ModernTitleMenuItem("RNA-seq"));

    ModernIconMenuItem fpkmMenuItem = new ModernIconMenuItem("FPKM");
    fpkmMenuItem.addClickListener(this);

    menu.add(fpkmMenuItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getExpressionType()
   */
  @Override
  public String getExpressionType() {
    return "RNA-seq";
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
        showExpressionData();
      } catch (NetworkFileException | IOException | ParseException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Show expression data.
   *
   * @throws NetworkFileException the network file exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  private void showExpressionData()
      throws NetworkFileException, IOException, ParseException {
    List<Sample> samples = mSelectedSamples.getItems();

    if (samples.size() == 0) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You must select at least one sample.");

      return;
    }

    boolean correctType = true;

    for (Sample sample : samples) {
      if (!sample.getExpressionType().getName().equals("RNA-seq")) {
        correctType = false;
        break;
      }
    }

    if (!correctType) {
      ModernMessageDialog.createWarningDialog(mParent,
          "Some of the samples you have selected do not contain expression data.");

      return;
    }

    if (checkForLocked(samples)) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You have selected one or more locked samples. These will not be shown.");

      samples = getUnlockedSamples(samples);
    }

    if (samples.size() > 0) {
      RnaSeqData expressionData = new RnaSeqData();

      expressionData.showTables(mParent, samples, true, mStatusModel);
    }
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
