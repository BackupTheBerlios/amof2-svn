package cmofimpl.reflection.query;

import java.util.*;

/**
 * <b>Syntax</b>
 * <pre>meta-classifier-id ":" element-id ("/" meta-classifier-id ":" element-id)*</pre>
 *
 */
public class Query {
	public static Query createQuery(String query) throws ParseException {
		Query result = new Query();
		result.parse(query);
		return result;
	}
	
	class QueryItem {
		final String metaId;
		final String id;
		QueryItem(String metaId, String id) {
			this.metaId = metaId;
			this.id = id;
		}
	}
	private List<QueryItem> queryItems; 
	
	private void parse(String query) throws ParseException {
		queryItems = new Vector<QueryItem>();
		String queryItemStrings[] = query.split("/");
		for (String queryItemString: queryItemStrings) {
			String ids[] = queryItemString.split(":");
			if (ids.length != 2) {
				throw new ParseException();
			}
			queryItems.add(new QueryItem(ids[0], ids[1]));
		}
		if (queryItems.size() < 1) {
			throw new ParseException();
		}
	}
	
	private boolean match(QueryItem item, cmof.reflection.Object theObject) {
		
		//TODO use the real ids ...
		if (theObject.getMetaClass() != null) {
			if (!item.metaId.equals(theObject.getMetaClass().getName())) {
				return false;
			}
		}
		if (!item.id.equals(((cmofimpl.reflection.ObjectImpl)theObject).get("name"))) {
			return false;
		}
		return true;
	}
	
	public cmof.reflection.Object evaluate(cmof.reflection.Object theObject) {
		if (!match(queryItems.get(0), theObject)) {
			return null;
		}
		QueryItems: for (int i = 1; i < queryItems.size(); i++) {
			for (cmof.reflection.Object component: theObject.getComponents()) {
				if (match(queryItems.get(i), component)) {
					theObject = component;
					continue QueryItems;
				}
			}
			return null;
		}
		return theObject;
	}
}
