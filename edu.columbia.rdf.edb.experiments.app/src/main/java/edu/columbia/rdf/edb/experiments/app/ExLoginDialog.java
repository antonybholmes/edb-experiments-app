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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.help.GuiAppInfo;

import edu.columbia.rdf.edb.EDBWLogin;
import edu.columbia.rdf.edb.ui.EDBRepositorySession;
import edu.columbia.rdf.edb.ui.LoginDialog;
import edu.columbia.rdf.edb.ui.Repository;
import edu.columbia.rdf.edb.ui.RepositoryService;
import edu.columbia.rdf.edb.ui.RepositorySession;
import edu.columbia.rdf.edb.ui.network.ServerException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExLoginDialog.
 */
public class ExLoginDialog extends LoginDialog {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new ex login dialog.
   *
   * @param appInfo the app info
   * @param login the login
   */
  public ExLoginDialog(GuiAppInfo appInfo, EDBWLogin login) {
    super(appInfo, login);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.columbia.rdf.edb.ui.LoginDialog#success()
   */
  @Override
  protected void success() {

    RepositorySession session = null;

    // try {
    // session = new ExperimentDbRepositorySession(login);
    /// } catch (UnsupportedEncodingException e1) {
    // e1.printStackTrace();
    // }

    try {
      session = new EDBRepositorySession(mLogin);
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

    if (session == null) {
      ModernMessageDialog.createDialog(null,
          getAppInfo().getName(),
          "The ExperimentDB server is not responding.",
          MessageDialogType.WARNING);

      return;
    }

    // SetupTask setupTask = new SetupTask(session);
    // setupTask.execute();

    Repository repository = null;

    try {
      repository = session.restore();

      RepositoryService.getInstance()
          .setRepository(RepositoryService.DEFAULT_REP, repository);

      MainExperimentsWindow window = new MainExperimentsWindow(getAppInfo());

      window.setVisible(true);
    } catch (ServerException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
  }
}
