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

import edu.columbia.rdf.edb.ui.search.UserSearch;

// TODO: Auto-generated Javadoc
/**
 * A client to connect to a caarray server and search it.
 *
 * @author Antony Holmes Holmes
 */
public class SearchFolder implements Comparable<SearchFolder> {
	
	/** The m name. */
	private String mName;
	
	/** The m search. */
	private UserSearch mSearch = null;

	/**
	 * Instantiates a new search folder.
	 *
	 * @param name the name
	 * @param search the search
	 */
	public SearchFolder(String name, UserSearch search) {
		mName = name;
		mSearch = search;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public final String getName() {
		return mName;
	}
	
	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public final UserSearch getSearch() {
		return mSearch;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SearchFolder f) {
		return mName.compareTo(f.mName);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SearchFolder)) {
			return false;
		}
		
		return compareTo((SearchFolder)o) == 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return mName.hashCode();
	}
}
