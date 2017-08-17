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
package edu.columbia.rdf.edb.experiments.app.sample;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import org.jebtk.modern.collapsepane.ModernCollapseNodeRenderer;
import org.jebtk.modern.tree.ModernTreeBranchNodeRenderer;
import org.jebtk.modern.widget.ModernWidget;



// TODO: Auto-generated Javadoc
/**
 * Provides a rudimentary implementation of a node renderer that
 * detects if is selected and what the tree branching depth is.
 * This forms the basis of concrete implementations of renderers.
 *
 * @author Antony Holmes Holmes
 */
public class SectionPanelCollapseNodeRenderer extends ModernCollapseNodeRenderer {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant FONT. */
	private static final Font FONT = ModernWidget.SUB_HEADING_FONT;
	

	/* (non-Javadoc)
	 * @see org.abh.common.ui.collapsepane.ModernCollapseNodeRenderer#drawForegroundAAText(java.awt.Graphics2D)
	 */
	@Override
	public void drawForegroundAAText(Graphics2D g2) {
		int x = 0;

		int y = (getHeight() - 16) / 2;
		
		if (mIsHighlighted) {
			if (mIsExpanded) {
				ModernTreeBranchNodeRenderer.BRANCH_OPEN_ICON.drawIcon(g2, x, y, 16);
			} else {
				ModernTreeBranchNodeRenderer.BRANCH_CLOSED_ICON.drawIcon(g2, x, y, 16);
			}
		}

		x += ModernTreeBranchNodeRenderer.BRANCH_OPEN_ICON.getWidth(); //+ ModernTheme.getInstance().getClass("widget").getInt("padding");

		Point p = getStringCenterPlotCoordinates(g2, getRect(), mName);
			
		g2.setColor(getForeground());
		
		g2.setFont(FONT);
		
		g2.drawString(getTruncatedText(g2, mName, x, mRect.getW()), x, p.y);
	}
}