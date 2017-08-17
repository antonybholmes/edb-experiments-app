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
package edu.columbia.rdf.edb.experiments.app.search;

import java.awt.Graphics2D;

import org.jebtk.modern.graphics.icons.Window32VectorIcon;
import org.jebtk.modern.widget.ModernWidget;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryPane32VectorIcon.
 */
public class CategoryPane32VectorIcon extends Window32VectorIcon {

	/** The Constant PANE_WIDTH_1. */
	private static final int PANE_WIDTH_1 = 8;
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.graphics.icons.Window32VectorIcon#drawIcon(java.awt.Graphics2D, int, int, int, int)
	 */
	@Override
	public void drawIcon(Graphics2D g2, int x, int y, int w, int h, Object... params) {
		super.drawIcon(g2, x, y, w, h, params);
		
		x = x + (w - WIDTH) / 2 + 2;
		y = y + (h - HEIGHT) / 2 + BAR_HEIGHT;
		
		int x2 = x + 1;
		
		int w1 = PANE_WIDTH_1 - 3;
		int y1 = y + 2;

		g2.setColor(ModernWidget.LINE_COLOR);
		
		g2.drawLine(x + PANE_WIDTH_1, y, x + PANE_WIDTH_1, y + HEIGHT - 6);
		
		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);

		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		y1 += 4;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		
		x2 = x + PANE_WIDTH_1 + 2;
		y1 = y + 2;
		
		g2.drawLine(x + 2 * PANE_WIDTH_1 + 1, y, x + 2 * PANE_WIDTH_1 + 1, y + HEIGHT - 6);
		
		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);

		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		y1 += 4;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		y1 += 2;
		
		g2.drawLine(x2, y1, x2 + w1, y1);
		
		
	}
}
