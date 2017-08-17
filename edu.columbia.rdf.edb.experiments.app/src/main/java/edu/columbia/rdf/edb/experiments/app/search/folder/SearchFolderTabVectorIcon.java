/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors 
 *     may be used to endorse or promote products derived from this software 
 *     without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.columbia.rdf.edb.experiments.app.search.folder;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jebtk.core.Mathematics;
import org.jebtk.modern.graphics.icons.ModernVectorScalableIcon;


// TODO: Auto-generated Javadoc
/**
 * The class FolderVectorIcon.
 */
public class SearchFolderTabVectorIcon extends ModernVectorScalableIcon {
	
	

	/**
	 * The constant WIDTH_SCALE.
	 */
	private static final double WIDTH_SCALE = 1;
	
	/**
	 * The constant CORNER_SCALE.
	 */
	//private static final double CORNER_SCALE_TAB = 0.1;
	
	private static final double CORNER_SCALE = 0.2;
	
	/** The Constant TAB_HEIGHT. */
	private static final double TAB_HEIGHT = 0.09;
	
	/** The Constant TAB_WIDTH. */
	private static final double TAB_WIDTH = 0.5;
	
	/** The Constant HEIGHT_SCALE. */
	protected static final double HEIGHT_SCALE = 0.9;
	
	/** The Constant MIN_SIZE. */
	private static final int MIN_SIZE = 2;

	
	/* (non-Javadoc)
	 * @see org.abh.lib.ui.modern.icons.ModernIcon#drawForeground(java.awt.Graphics2D, java.awt.Rectangle)
	 */
	@Override
	public void drawIcon(Graphics2D g2, int x, int y, int w, int h, Object... params) {
		Color c = (Color)params[0];
		
		w = Mathematics.makeMult2(w * WIDTH_SCALE);
		h = Mathematics.makeMult2(w * HEIGHT_SCALE);
		
		int tabHeight = Math.max(MIN_SIZE, Mathematics.makeMult2(h * TAB_HEIGHT));
		int tabWidth = Math.max(MIN_SIZE, Mathematics.makeMult2(w * TAB_WIDTH));
		
		
		
		x = x + (w - w) / 2;
		y = y + (h - h) / 2;
		
		// Rear
		
		g2.setColor(c);
		
		int corner = Math.max(MIN_SIZE, (int)(w * CORNER_SCALE));
		
		g2.fillRoundRect(x, y, tabWidth, h, corner, corner);
		
		//corner = (int)(w * CORNER_SCALE);
		
		int rh = h - tabHeight;
		
		g2.fillRoundRect(x, y + tabHeight, w, rh, corner, corner);
	}
}
