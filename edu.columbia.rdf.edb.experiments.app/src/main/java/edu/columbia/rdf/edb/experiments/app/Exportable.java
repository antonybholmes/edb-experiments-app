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

import java.awt.Frame;

/**
 * Indicates that the panel provides a GUI to export the data it contains.
 *
 * @author Antony Holmes
 *
 */
public interface Exportable {

  /**
   * Exports the data. The parent is provided so that modal file dialogs can be
   * created.
   *
   * @param parent the parent
   */
  public void export(Frame parent);
}
