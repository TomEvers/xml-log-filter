package com.skjoblerg.xmlfilter.filter;

import static com.skjoblerg.xmlfilter.filter.XPathExpressions.INVALID_XPATH;
import static com.skjoblerg.xmlfilter.filter.XPathExpressions.PASSTHROUGH_XPATH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.skjolber.indent.Indent;
import com.github.skjolber.xmlfilter.test.XmlFilterRunner;
import com.skjolberg.xmlfilter.filter.AbstractSingleXPathXmlFilter;
import com.skjolberg.xmlfilter.filter.AbstractXPathFilter.FilterType;

public class AbstractSingleXPathXmlFilterTest {

	private class DefaultSingleXPathXmlFilter extends AbstractSingleXPathXmlFilter {
	
		public DefaultSingleXPathXmlFilter(boolean declaration, Indent indentation, int maxTextNodeLength, int maxCDATANodeLength, String expression, FilterType type) {
			super(declaration, indentation, maxTextNodeLength, maxCDATANodeLength, expression, type);
		}

		@Override
		public boolean process(char[] chars, int offset, int length, StringBuilder output) {
			if(XmlFilterRunner.isWellformed(new String(chars, offset, length))) {
				output.append(chars, offset, length);
				
				return true;
			}
			return false;
		}
		
	};
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void construct_invalidAnonymizeXPath_throwsException() {
		exception.expect(IllegalArgumentException.class);

		new DefaultSingleXPathXmlFilter(true, null, -1, -1, INVALID_XPATH, FilterType.ANON);
	}
	
	@Test
	public void construct_invalidPruneXPath_throwsException() {
		exception.expect(IllegalArgumentException.class);

		new DefaultSingleXPathXmlFilter(true, null, -1, -1, INVALID_XPATH, FilterType.PRUNE);
	}
	
	@Test
	public void construct_validPruneXPath_constructed() {
		DefaultSingleXPathXmlFilter xmlFilter = new DefaultSingleXPathXmlFilter(true, null, -1, -1, PASSTHROUGH_XPATH, FilterType.PRUNE);
		assertThat(xmlFilter.getPruneFilters()[0], is(PASSTHROUGH_XPATH));
		assertThat(xmlFilter.getAnonymizeFilters().length, is(0));
	}

	@Test
	public void construct_validAnonymizeXPath_constructed() {
		DefaultSingleXPathXmlFilter xmlFilter = new DefaultSingleXPathXmlFilter(true, null, -1, -1, PASSTHROUGH_XPATH, FilterType.ANON);
		
		assertThat(xmlFilter.getAnonymizeFilters()[0], is(PASSTHROUGH_XPATH));
		assertThat(xmlFilter.getPruneFilters().length, is(0));
		
	}
	
	
}
