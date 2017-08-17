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
package edu.columbia.rdf.edb.experiments.app.search.folder;

import java.util.ArrayDeque;
import java.util.Deque;

import org.jebtk.core.path.Path;
import org.jebtk.core.search.SearchStackOperator;
import org.jebtk.core.tree.TreeNode;
import org.jebtk.core.tree.TreeRootNode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.columbia.rdf.edb.ui.search.SearchCategory;
import edu.columbia.rdf.edb.ui.search.UserSearch;
import edu.columbia.rdf.edb.ui.search.UserSearchEntry;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchFolderXmlHandler.
 */
public class SearchFolderXmlHandler extends DefaultHandler {
	
	/** The m folder stack. */
	private Deque<TreeNode<UserSearch>> mFolderStack =
			new ArrayDeque<TreeNode<UserSearch>>();
	
	/**
	 * Instantiates a new search folder xml handler.
	 */
	public SearchFolderXmlHandler() {
		mFolderStack.push(new TreeRootNode<UserSearch>()); //new SearchFolderTreeNode("Search Folders"));
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public final void startElement(String uri,
    		String localName,
    		String qName,
    		Attributes attributes) throws SAXException {

		if (qName.equals("search-folder")) {
			TreeNode<UserSearch> child = 
					new SearchFolderTreeNode(attributes.getValue("name"));
			
			mFolderStack.peek().addChild(child);
			mFolderStack.push(child);
			
		} else if (qName.equals("user-search")) {
    		if (mFolderStack.peek().getValue() == null) {
    			mFolderStack.peek().setValue(new UserSearch());
    		}
    		
    		UserSearchEntry entry = new UserSearchEntry(SearchStackOperator.parseOperator(attributes.getValue("operator")),
    				new SearchCategory(attributes.getValue("field-name"), new Path(attributes.getValue("field-path"))),
    				attributes.getValue("search"));
    		
    		mFolderStack.peek().getValue().add(entry);
    	} else {
    		// do nothing
    	}
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public final void endElement(String uri,
    		String localName,
    		String qName) throws SAXException {

		if (qName.equals("search-folder")) {
			mFolderStack.pop();
		}
	}
	
	/*
	@Override
	public void characters(char[] ch, int start, int length) {
		if (mMode.equals("operator")) {
			mOperator = SearchStackOperator.parseOperator(new String(ch, start, length));
		} else if (mMode.equals("search")) {
			mSearch = new String(ch, start, length);
		} else {
			
		}
	}
    */
	
    /**
	 * Gets the search folders.
	 *
	 * @return the search folders
	 */
	public TreeRootNode<UserSearch> getSearchFolders() {
    	return (TreeRootNode<UserSearch>)mFolderStack.pop();
    }
}
