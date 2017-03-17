package com.github.skjolber.xmlfilter.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.skjolber.xmlfilter.core.SingleXPathXmlIndentationFilter;
import com.skjolberg.xmlfilter.XmlFilter;
import com.skjolberg.xmlfilter.filter.AbstractXPathFilter.FilterType;

public class SingleXPathXmlIndentationFilterTest extends BaseXmlFilterTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void filter_prune() throws Exception {
		String[] regularXPaths = {DEFAULT_XPATH, DEFAULT_WILDCARD_XPATH};
		
		List<XmlFilter> filters = new ArrayList<>();
		for(String xpath : regularXPaths) {
			filters.add(new SingleXPathXmlIndentationFilter(true, xpath, FilterType.PRUNE, indent));
			filters.add(new SingleXPathXmlIndentationFilter(false, xpath, FilterType.PRUNE, indent));
		}
		assertProcess(filters);
		assertValidXmlConformant(filters);
	}

	@Test
	public void filter_anon() throws Exception {
		String[] regularXPaths = {DEFAULT_XPATH, DEFAULT_WILDCARD_XPATH, DEFAULT_ATTRIBUTE_XPATH, DEFAULT_ATTRIBUTE_WILDCARD_XPATH};
		
		List<XmlFilter> filters = new ArrayList<>();
		for(String xpath : regularXPaths) {
			filters.add(new SingleXPathXmlIndentationFilter(true, xpath, FilterType.ANON, indent));
			filters.add(new SingleXPathXmlIndentationFilter(false, xpath, FilterType.ANON, indent));
		}
		assertProcess(filters);
		assertValidXmlConformant(filters);
	}

	@Test
	public void filter_textWithAny_throwsException() throws Exception {
		exception.expect(IllegalArgumentException.class);

		new SingleXPathXmlIndentationFilter(true, DEFAULT_ANY_XPATH, FilterType.PRUNE, indent);
	}
	
	@Test
	public void filter_pruneTextWithAttribute_throwsException() throws Exception {
		exception.expect(IllegalArgumentException.class);
		new SingleXPathXmlIndentationFilter(true, DEFAULT_ATTRIBUTE_XPATH, FilterType.PRUNE, indent);
	}
	
	@Test
	public void filter_invalidXML_noFiltering() throws Exception {
		XmlFilter filter = new SingleXPathXmlIndentationFilter(true, DEFAULT_XPATH, FilterType.PRUNE, indent);
		Assert.assertNull(filter.process("</xml>"));
	}
	
	@Test
	public void filter_invalidRange_noFiltering() throws Exception {
		XmlFilter filter = new SingleXPathXmlIndentationFilter(true, DEFAULT_XPATH, FilterType.PRUNE, indent);
		Assert.assertFalse(filter.process("<xml></xml>".toCharArray(), 0, 100, new StringBuilder()));
	}
}
