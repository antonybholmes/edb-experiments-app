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
package edu.columbia.rdf.edb.experiments.app.plugins.view;

import java.net.MalformedURLException;
import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * The Class GEOUrlLinkButton.
 */
public class GEOUrlLinkButton extends PersonButton {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new GEO url link button.
   *
   * @param accession the accession
   * @throws MalformedURLException the malformed URL exception
   */
  public GEOUrlLinkButton(String accession) throws MalformedURLException {
    this(accession, accession);
  }

  /**
   * Instantiates a new GEO url link button.
   *
   * @param title the title
   * @param accession the accession
   * @throws MalformedURLException the malformed URL exception
   */
  public GEOUrlLinkButton(String title, String accession)
      throws MalformedURLException {
    super(title, new URL(
        "http://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=" + accession));
  }
}
