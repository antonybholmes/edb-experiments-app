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

import java.awt.Color;
import java.net.URL;

import org.jebtk.modern.button.ModernUrlTextLink;
import org.jebtk.modern.theme.ThemeService;

// TODO: Auto-generated Javadoc
/**
 * The Class PersonButton.
 */
public class PersonButton extends ModernUrlTextLink {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	
	/** The color2. */
	private static Color COLOR2 = 
			ThemeService.getInstance().colors().getHighlight32(12);

	/**
	 * Instantiates a new person button.
	 *
	 * @param title the title
	 * @param email the email
	 */
	public PersonButton(String title, String email) {
		super(title, email, SummaryLabel.COLOR1, COLOR2);
	}

	/**
	 * Instantiates a new person button.
	 *
	 * @param title the title
	 * @param url the url
	 */
	public PersonButton(String title, URL url) {
		super(title, url, SummaryLabel.COLOR1, COLOR2);
	}
}
