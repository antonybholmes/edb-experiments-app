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
package edu.columbia.rdf.edb.experiments.app;

import java.awt.FontFormatException;
import java.io.IOException;
import java.rmi.ServerException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.AppService;
import org.jebtk.core.dictionary.DictionaryService;
import org.jebtk.core.dictionary.SubstitutionService;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.modern.help.GuiAppInfo;
import org.jebtk.modern.search.SearchTermsService;
import org.jebtk.modern.theme.ThemeService;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.EDBWLogin;
import edu.columbia.rdf.edb.experiments.app.plugins.view.chipseq.ChipSeqViewPlugin;
import edu.columbia.rdf.edb.experiments.app.plugins.view.microarray.MicroarrayViewPlugin;
import edu.columbia.rdf.edb.experiments.app.plugins.view.rnaseq.RnaSeqViewPlugin;
import edu.columbia.rdf.edb.experiments.app.sample.sort.SampleSortService;
import edu.columbia.rdf.edb.ui.ViewPlugin;
import edu.columbia.rdf.edb.ui.ViewPluginService;
import edu.columbia.rdf.edb.ui.search.SearchCategoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class MainExperiments.
 */
public class MainExperiments {

  /**
   * The main method.
   *
   * @param args the arguments
   * @throws ServerException the server exception
   * @throws SAXException the SAX exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException the parser configuration exception
   * @throws KeyManagementException the key management exception
   * @throws NoSuchAlgorithmException the no such algorithm exception
   * @throws FontFormatException the font format exception
   * @throws ClassNotFoundException the class not found exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   * @throws UnsupportedLookAndFeelException the unsupported look and feel
   *           exception
   */
  public static final void main(String[] args) throws ServerException,
  SAXException, IOException, ParserConfigurationException,
  KeyManagementException, NoSuchAlgorithmException, FontFormatException,
  ClassNotFoundException, InstantiationException, IllegalAccessException,
  UnsupportedLookAndFeelException {
    main();
  }

  /**
   * Main.
   *
   * @throws SAXException the SAX exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ParserConfigurationException the parser configuration exception
   * @throws KeyManagementException the key management exception
   * @throws NoSuchAlgorithmException the no such algorithm exception
   * @throws FontFormatException the font format exception
   * @throws ClassNotFoundException the class not found exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   * @throws UnsupportedLookAndFeelException the unsupported look and feel
   *           exception
   */
  public static void main()
      throws SAXException, IOException, ParserConfigurationException,
      KeyManagementException, NoSuchAlgorithmException, FontFormatException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      UnsupportedLookAndFeelException {
    AppService.getInstance().setAppInfo("experiments");

    ThemeService.getInstance().setTheme();

    // Network.disableSLLChecks();

    GuiAppInfo appInfo = new ExperimentsInfo();

    // ApplicationInformation.loadSettings(appInfo);

    // ProductServer.getInstance().set(new ExperimentDBProductDetails());

    // load program settings
    // Settings.getInstance().loadXml(Settings.DEFAULT_XML_FILE);

    // DataViewService.getInstance().loadXml();

    // Load search categories
    //SearchCategoryService.getInstance()
    //   .loadXml(SearchCategoryService.SEARCH_CATEGORIES_XML_FILE);

    // load plugins from the plugin directory
    // PluginService.getInstance().scanDirectory(Settings.getInstance().getChild("main.plugins-directory").getValue());

    // RecentFilesService.getInstance().setPwd(new
    // File(SettingsService.getInstance().getSetting("experiments.working-directory").getValue()));

    // load previous search terms
    //SearchTermsService.getInstance()
    //   .loadXml(SearchTermsService.DEFAULT_XML_FILE);


    //DictionaryService.getInstance().loadXml(DictionaryService.DEFAULT_FILE);

    //SubstitutionService.getInstance()
    //    .loadTSVFile(SubstitutionService.DEFAULT_FILE);

    EDBWLogin login = EDBWLogin.loadFromSettings();

    // Configure default views

    if (SettingsService.getInstance().getAsBool("edb.modules.microarray.enabled")) {
      ViewPluginService.getInstance().register(new MicroarrayViewPlugin());
    }

    if (SettingsService.getInstance().getAsBool("edb.modules.chipseq.enabled")) {
      ViewPluginService.getInstance().register(new ChipSeqViewPlugin(login));
    }

    if (SettingsService.getInstance().getAsBool("edb.modules.rnaseq.enabled")) {
      ViewPluginService.getInstance().register(new RnaSeqViewPlugin());
    }

    for (ViewPlugin plugin : ViewPluginService.getInstance()) {
      plugin.initSampleSorters(SampleSortService.getInstance());
      // plugin.initSampleFolders(sampleFolderModel);
      plugin.initSearchCategories(SearchCategoryService.getInstance());
    }

    //
    // User selects login
    //

    ExLoginDialog window = new ExLoginDialog(appInfo, login);
    window.setVisible(true);
  }
}
