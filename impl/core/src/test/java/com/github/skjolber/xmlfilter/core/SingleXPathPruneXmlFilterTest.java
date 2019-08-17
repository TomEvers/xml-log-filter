package com.github.skjolber.xmlfilter.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.skjolber.xmlfilter.XmlFilter;

public class SingleXPathPruneXmlFilterTest extends BaseXmlFilterTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void filter_text_filtered() throws Exception {
		String[] regularXPaths = {DEFAULT_XPATH, DEFAULT_WILDCARD_XPATH};
		
		List<XmlFilter> filters = new ArrayList<>();
		for(String xpath : regularXPaths) {
			filters.add(new SingleXPathPruneXmlFilter(true, xpath));
			filters.add(new SingleXPathPruneXmlFilter(false, xpath));
		}
		assertProcess(filters);
		assertValidXmlConformant(filters);
	}
	
	@Test
	public void filter_passthrough() throws Exception {
		String[] passthroughXPaths = {PASSTHROUGH_XPATH};
		List<XmlFilter> filters = new ArrayList<>();
		for(String xpath : passthroughXPaths) {
			filters.add(new SingleXPathPruneXmlFilter(true, xpath));
			filters.add(new SingleXPathPruneXmlFilter(false, xpath));
		}
		assertProcess(filters);
		assertValidXmlConformant(filters);
	}
	
	@Test
	public void filter_textWithAny_throwsException() throws Exception {
		exception.expect(IllegalArgumentException.class);
		
		new SingleXPathPruneXmlFilter(true, DEFAULT_ANY_XPATH);
	}
	
	@Test
	public void filter_pruneTextWithAttribute_throwsException() throws Exception {
		exception.expect(IllegalArgumentException.class);
		new SingleXPathPruneXmlFilter(true,DEFAULT_ATTRIBUTE_XPATH);
	}
	
	@Test
	public void filter_invalidXML_noFiltering() throws Exception {
		XmlFilter filter = new SingleXPathPruneXmlFilter(true, DEFAULT_XPATH);
		Assert.assertNull(filter.process("</xml>"));
	}
	
	@Test
	public void filter_invalidRange_noFiltering() throws Exception {
		XmlFilter filter = new SingleXPathPruneXmlFilter(true, DEFAULT_XPATH);
		Assert.assertFalse(filter.process("<xml></xml>".toCharArray(), 0, 100, new StringBuilder()));
	}
}
