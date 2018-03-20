/**
 * Copyright 2018 Antony Holmes
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

import org.jebtk.core.AppVersion;
import org.jebtk.modern.UIService;
import org.jebtk.modern.help.GuiAppInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class ExperimentsInfo.
 */
public class ExperimentsInfo extends GuiAppInfo {

  /**
   * Instantiates a new experiments info.
   */
  public ExperimentsInfo() {
    super("Experiments", new AppVersion(38),
        "Copyright (C) 2011-${year} Antony Holmes.",
        UIService.getInstance().loadIcon(ExperimentsIcon.class, 32),
        UIService.getInstance().loadIcon(ExperimentsIcon.class, 128),
        "Experiment Database Browser");
  }
}
