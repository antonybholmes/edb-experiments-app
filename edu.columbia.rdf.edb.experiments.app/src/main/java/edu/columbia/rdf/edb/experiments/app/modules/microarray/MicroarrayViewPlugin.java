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

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.ColorUtils;
import org.jebtk.core.NetworkFileException;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.path.Path;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.SelectionModel;
import org.jebtk.modern.button.ModernButtonWidget;
import org.jebtk.modern.dialog.ModernDialogStatus;
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
import edu.columbia.rdf.edb.ui.microarray.Mas5Dialog;
import edu.columbia.rdf.edb.ui.microarray.MicroarrayDataView;
import edu.columbia.rdf.edb.ui.microarray.MicroarrayNormalizationType;
import edu.columbia.rdf.edb.ui.microarray.RMADialog;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;

/**
 * Plugin for display of microarray data.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class MicroarrayViewPlugin extends ViewPlugin
    implements ModernClickListener {

  /** The Constant BUTTON_MAS5. */
  public static final String BUTTON_MAS5 = "MAS5";

  /** The Constant BUTTON_RMA. */
  public static final String BUTTON_RMA = "RMA";

  /** The m mas 5 button. */
  private ModernButtonWidget mMas5Button = new RibbonLargeButton(BUTTON_MAS5,
      new Raster32Icon(
          new Window32VectorIcon(ColorUtils.decodeHtmlColor("#e580ff"))));

  /** The m rma button. */
  private ModernButtonWidget mRmaButton = new RibbonLargeButton(BUTTON_RMA,
      new Raster32Icon(
          new Window32VectorIcon(ColorUtils.decodeHtmlColor("#ff8080"))));

  /** The m parent. */
  private ModernRibbonWindow mParent;

  /** The m selected samples. */
  private SelectionModel<Sample> mSelectedSamples;

  /** The m status model. */
  private StatusModel mStatusModel;

  /** The m data view. */
  private DataView mDataView;

  /** The m display field 1. */
  private Path mDisplayField1 = new Path(
      "/Microarray/Sample/Source/Characteristic/Organism_Part");

  /** The m display field 2. */
  private Path mDisplayField2 = new Path(
      "/Microarray/Sample/Source/Characteristic/Organism");

  /**
   * Instantiates a new microarray view plugin.
   */
  public MicroarrayViewPlugin() {
    mDataView = new MicroarrayDataView();

    mMas5Button.addClickListener(this);
    mRmaButton.addClickListener(this);
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
    SampleSortService.getInstance().add(new SortSamplesByArrayPlatform());
    SampleSortService.getInstance().add(new SortSamplesBySourceCellType());
    SampleSortService.getInstance().add(new SortSamplesBySourceDiseaseState());
    SampleSortService.getInstance().add(new SortSamplesBySourceDiseaseStatus());
    SampleSortService.getInstance().add(new SortSamplesBySourceGender());
    SampleSortService.getInstance().add(new SortSamplesByMicroarrayBasedClassification());
    SampleSortService.getInstance().add(new SortSamplesBySourceMaterialType());
    SampleSortService.getInstance().add(new SortSamplesBySourceOrganismPart());
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
     * group = new SearchCategoryGroup("Source Section"); group.addCategory(new
     * SearchCategory(new StrictPath("/microarray/source/material_type"),
     * "Source Material Type")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_organism"), "Organism",
     * "Search by organism, e.g. Homo sapiens.")); group.addCategory(new
     * SearchCategory(new
     * StrictPath("/microarray/source/characteristics_organism_part"),
     * "Organism Part")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_disease_state"),
     * "Disease State")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_cell_type"),
     * "Cell Type")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_treatment"),
     * "Treatment")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_time_point"),
     * "Time Point", "Search by when sample was treated, e.g. 8h."));
     * group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_tumor_cells"),
     * "% Tumor Cells")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_disease_status"),
     * "Disease Status")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_gender"), "Gender",
     * "Search by gender, e.g. male, female, or unknown."));
     * group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_age"), "Age",
     * "Search by patient age, e.g. 3 yr.")); group.addCategory(new
     * SearchCategory(new
     * StrictPath("/microarray/source/characteristics_clinical_parameters"),
     * "Clinical Parameters")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_cytogenetics"),
     * "Cytogenetics")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_igv_status"),
     * "IgV Status")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_gep_based_classification")
     * , "GEP based classification")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_biosourceprovider"),
     * "BioSourceProvider")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/source/characteristics_source_protocol"),
     * "Source Protocol")); searchCategoryService.addGroup(group);
     * 
     * group = new SearchCategoryGroup("Sample Section"); group.addCategory(new
     * SearchCategory(new StrictPath("/microarray/sample/material_type"),
     * "Sample Material Type")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/sample/characteristics_sample_protocol"),
     * "Sample Protocol")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/sample/characteristics_sample_operator"),
     * "Sample Operator")); searchCategoryService.addGroup(group);
     * 
     * group = new SearchCategoryGroup("Extract Section"); group.addCategory(new
     * SearchCategory(new StrictPath("/microarray/extract/material_type"),
     * "Extract Material Type")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/extract/characteristics_extract_protocol"),
     * "Extract Protocol")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/extract/characteristics_extract_operator"),
     * "Extract Operator")); searchCategoryService.addGroup(group);
     * 
     * group = new SearchCategoryGroup("Labeled Extract Section");
     * group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/labeled_extract/label"), "Label"));
     * group.addCategory(new SearchCategory(new StrictPath(
     * "/microarray/labeled_extract/characteristics_labeled_extract_protocol"),
     * "Labeled Extract Protocol")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_labeled_extract_operator"),
     * "Labeled Extract Operator")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/labeled_extract/material_type"),
     * "Labeled Extract Material Type")); group.addCategory(new
     * SearchCategory(new
     * StrictPath("/microarray/labeled_extract/characteristics_array_platform"),
     * "Array Platform", "Search by array platform, e.g. HG-U133_Plus_2."));
     * group.addCategory(new SearchCategory(new StrictPath(
     * "/microarray/labeled_extract/characteristics_hybridization_protocol"),
     * "Hybridization Protocol")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_scanning_protocol"),
     * "Scanning Protocol")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_chp_normalization_method"),
     * "CHP Normalization Method")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_hybridization_facility"),
     * "Hybridization Facility")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_mas5_normalization"),
     * "MAS5 Normalization")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_mas5_normalization_file"),
     * "MAS5 Normalization File")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_rma_normalization"),
     * "RMA Normalization")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_rma_normalization_file"),
     * "RMA Normalization File")); group.addCategory(new SearchCategory(new
     * StrictPath(
     * "/microarray/labeled_extract/characteristics_geo_series_accession" ),
     * "GEO Series Accession", "Search by GEO series accession e.g. GSE2350."));
     * group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/labeled_extract/characteristics_geo_accession"),
     * "GEO Accession", "Search by GEO accession e.g. GSM44066."));
     * group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/labeled_extract/characteristics_geo_platform"),
     * "GEO Platform", "Search by GEO platform, e.g. GPL8300."));
     * searchCategoryService.addGroup(group);
     * 
     * group = new SearchCategoryGroup("Hybridization Section");
     * group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/hybridization/array_data_file"),
     * "Array Data File")); group.addCategory(new SearchCategory(new
     * StrictPath("/microarray/hybridization/derived_array_data_file"),
     * "Derived Array Data File"));
     */
    
    
    try {
      SearchCategoryService.getInstance().loadXml(MicroarrayDataView.XML_VIEW_FILE);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      e.printStackTrace();
    }

    /*
    SearchCategoryGroup group = new SearchCategoryGroup("Microarray Source Section");

    group.addCategory(new SearchCategory("Source Name",
        new Path("/Microarray/Sample/Source/Source_Name")));
    group.addCategory(new SearchCategory("Material Type",
        new Path("/Microarray/Sample/Source/Material_Type")));
    group.addCategory(new SearchCategory("Organism",
        new Path("/Microarray/Sample/Source/Characteristic/Organism")));
    group.addCategory(new SearchCategory("Organism Part",
        new Path("/Microarray/Sample/Source/Characteristic/Organism_Part")));
    group.addCategory(new SearchCategory("Disease State",
        new Path("/Microarray/Sample/Source/Characteristic/Disease_State")));
    group.addCategory(new SearchCategory("Cell Type",
        new Path("/Microarray/Sample/Source/Characteristic/Cell_Type")));
    group.addCategory(new SearchCategory("Treatment",
        new Path("/Microarray/Sample/Source/Characteristic/Treatment")));
    group.addCategory(new SearchCategory("Time Point",
        new Path("/Microarray/Sample/Source/Characteristic/Time_Point")));
    group.addCategory(new SearchCategory("% Tumor Cells", new Path(
        "/Microarray/Sample/Source/Characteristic/Percent_Tumor_Cells")));
    group.addCategory(new SearchCategory("Disease Status",
        new Path("/Microarray/Sample/Source/Characteristic/Disease_Status")));
    group.addCategory(new SearchCategory("Gender",
        new Path("/Microarray/Sample/Source/Characteristic/Gender")));
    group.addCategory(new SearchCategory("Age",
        new Path("/Microarray/Sample/Source/Characteristic/Age")));
    group.addCategory(new SearchCategory("Clinical Parameters", new Path(
        "/Microarray/Sample/Source/Characteristic/Clinical_Parameters")));
    group.addCategory(new SearchCategory("Cytogenetics",
        new Path("/Microarray/Sample/Source/Characteristic/Cytogenetics")));
    group.addCategory(new SearchCategory("IgV Status",
        new Path("/Microarray/Sample/Source/Characteristic/IgV_Status")));
    group.addCategory(new SearchCategory("GEP based classification", new Path(
        "/Microarray/Sample/Source/Characteristic/GEP_Based_Classification")));
    group.addCategory(new SearchCategory("BioSourceProvider", new Path(
        "/Microarray/Sample/Source/Characteristic/BioSourceProvider")));
    group.addCategory(new SearchCategory("Source Protocol",
        new Path("/Microarray/Sample/Source/Characteristic/Source_Protocol")));
    SearchCategoryService.getInstance().addGroup(group);

    group = new SearchCategoryGroup("Microarray Sample Section");
    group.addCategory(new SearchCategory("Sample Name",
        new Path("/Microarray/Sample/Sample/Sample_Name")));
    group.addCategory(new SearchCategory("Material Type",
        new Path("/Microarray/Sample/Sample/Material_Type")));
    group.addCategory(new SearchCategory("Sample Protocol",
        new Path("/Microarray/Sample/Sample/Characteristic/Sample_Protocol")));
    group.addCategory(new SearchCategory("Sample Operator",
        new Path("/Microarray/Sample/Sample/Characteristic/Sample_Operator")));
    SearchCategoryService.getInstance().addGroup(group);

    group = new SearchCategoryGroup("Microarray Extract Section");
    group.addCategory(new SearchCategory("Extract Name",
        new Path("/Microarray/Sample/Extract/Extract_Name")));
    group.addCategory(new SearchCategory("Material Type",
        new Path("/Microarray/Sample/Extract/Material_Type")));
    group.addCategory(new SearchCategory("Extract Protocol", new Path(
        "/Microarray/Sample/Extract/Characteristic/Extract_Protocol")));
    group.addCategory(new SearchCategory("Extract Operator", new Path(
        "/Microarray/Sample/Extract/Characteristic/Extract_Operator")));
    SearchCategoryService.getInstance().addGroup(group);

    group = new SearchCategoryGroup("Microarray Labeled Extract Section");
    group.addCategory(new SearchCategory("Labeled Extract Name",
        new Path("/Microarray/Sample/Labeled_Extract/Labeled_Extract_Name")));
    group.addCategory(new SearchCategory("Labeled Extract Label",
        new Path("/Microarray/Sample/Labeled_Extract/Label")));
    group.addCategory(new SearchCategory("Labeled Extract Protocol", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/Labeled_Extract_Protocol")));
    group.addCategory(new SearchCategory("Labeled Extract Operator", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/Labeled_Extract_Operator")));
    group.addCategory(new SearchCategory("Material Type",
        new Path("/Microarray/Sample/Labeled_Extract/Material_Type")));
    group.addCategory(new SearchCategory("Array Platform", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/Array_Platform")));
    group.addCategory(new SearchCategory("Hybridization Protocol", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/Hybridization_Protocol")));
    group.addCategory(new SearchCategory("Scanning Protocol", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/Scanning_Protocol")));
    group.addCategory(new SearchCategory("CHP Normalization Method", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/CHP_Normalization_Method")));
    group.addCategory(new SearchCategory("Hybridization Facility", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/Hybridization_Facility")));
    group.addCategory(new SearchCategory("MAS5 Normalization", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/MAS5_Normalization")));
    group.addCategory(new SearchCategory("MAS5 Normalization File", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/MAS5_Normalization_File")));
    group.addCategory(new SearchCategory("RMA Normalization", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/RMA_Normalization")));
    group.addCategory(new SearchCategory("RMA Normalization File", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/RMA_Normalization_File")));
    group.addCategory(new SearchCategory("GEO Series Accession", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/GEO_Series_Accession")));
    group.addCategory(new SearchCategory("GEO Accession", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/GEO_Accession")));
    group.addCategory(new SearchCategory("GEO Platform", new Path(
        "/Microarray/Sample/Labeled_Extract/Characteristic/GEO_Platform")));
    SearchCategoryService.getInstance().addGroup(group);

    group = new SearchCategoryGroup("Microarray Hybridization Section");
    group.addCategory(new SearchCategory("Hybridization Name",
        new Path("/Microarray/Sample/Hybridization/Hybridization_Name")));
    group.addCategory(new SearchCategory("Array Data File",
        new Path("/Microarray/Sample/Hybridization/Array_Data_File")));
    group.addCategory(new SearchCategory("Derived Array Data File",
        new Path("/Microarray/Sample/Hybridization/Derived_Array_Data_File")));
    SearchCategoryService.getInstance().addGroup(group);
    */
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
    return new MicroarraySampleDataPanel(sample, mDataView);
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

    parent.getRibbon().getHomeToolbar().getSection(getDataType())
        .add(mMas5Button);
    parent.getRibbon().getHomeToolbar().getSection(getDataType())
        .add(mRmaButton);

    mMas5Button.setToolTip("MAS5 Expression Data",
        "Download MAS5 normalized expression data for the currently selected samples.");
    mRmaButton.setToolTip("RMA Expression Data",
        "Download RMA normalized expression data for the currently selected samples.");
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
    menu.add(new ModernTitleMenuItem("Microarray"));

    ModernIconMenuItem mas5MenuItem = new ModernIconMenuItem(BUTTON_MAS5);
    mas5MenuItem.addClickListener(this);

    ModernIconMenuItem rmaMenuItem = new ModernIconMenuItem(BUTTON_RMA);
    rmaMenuItem.addClickListener(this);

    menu.add(mas5MenuItem);
    menu.add(rmaMenuItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getExpressionType()
   */
  @Override
  public String getDataType() {
    return "Microarray";
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.ui.event.ModernClickListener#clicked(org.abh.common.ui.
   * event. ModernClickEvent)
   */
  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(BUTTON_MAS5)) {
      try {
        showExpressionData(MicroarrayNormalizationType.MAS5);
      } catch (NetworkFileException | IOException | ParseException ex) {
        ex.printStackTrace();
      }
    } else if (e.getMessage().equals(BUTTON_RMA)) {
      try {
        showExpressionData(MicroarrayNormalizationType.RMA);
      } catch (NetworkFileException | IOException | ParseException ex) {
        ex.printStackTrace();
      }
    } else {
      // Do nothing
    }
  }

  /**
   * Show expression data.
   *
   * @param type the type
   * @throws NetworkFileException the network file exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParseException the parse exception
   */
  private void showExpressionData(MicroarrayNormalizationType type)
      throws NetworkFileException, IOException, ParseException {
    List<Sample> samples = mSelectedSamples.getItems();

    if (samples.size() == 0) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You must select at least one sample.");

      return;
    }

    boolean correctType = true;

    for (Sample sample : samples) {
      if (!sample.getDataType().getName().equals("Microarray")) {
        correctType = false;
        break;
      }
    }

    if (!correctType) {
      ModernMessageDialog.createWarningDialog(mParent,
          "Some of the samples you have selected do not contain expression data.");

      return;
    }

    // Remove locked

    if (checkForLocked(samples)) {
      ModernMessageDialog.createWarningDialog(mParent,
          "You have selected one or more locked samples. These will not be loaded.");

      samples = getUnlockedSamples(samples);
    }

    MicroarrayExpressionData expressionData = new MicroarrayExpressionData();

    if (type == MicroarrayNormalizationType.MAS5) {
      Mas5Dialog dialog = new Mas5Dialog(mParent);

      dialog.setVisible(true);

      if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
        return;
      }

      expressionData.showTables(mParent,
          samples,
          type,
          dialog.getColumns(),
          dialog.getColumnAnnotations(),
          true,
          mStatusModel);
    } else {
      RMADialog dialog = new RMADialog(mParent);

      dialog.setVisible(true);

      if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
        return;
      }

      expressionData.showTables(mParent,
          samples,
          type,
          CollectionUtils.asList(true),
          dialog.getColumnAnnotations(),
          true,
          mStatusModel);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.ViewPlugin#getDataView()
   */
  @Override
  public DataView getDataView() {
    return mDataView;
  }
}
