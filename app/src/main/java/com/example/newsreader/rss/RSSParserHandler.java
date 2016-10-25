package com.example.newsreader.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RSSParserHandler extends DefaultHandler {
    private RSSFeed feed;
    private RSSItem newItem;
    private RSSTags activeTag;

    public RSSParserHandler() {
        feed = new RSSFeed();
        newItem = null;
        activeTag = RSSTags.NONE;
    }

    public RSSFeed getFeed() {
        return feed;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("item"))
            newItem = new RSSItem();
        else if (qName.equals("title") && newItem != null)
            activeTag = RSSTags.TITLE_TAG;
        else if (qName.equals("link") && newItem != null)
            activeTag = RSSTags.LINK_TAG;
        else if (qName.equals("pubDate") && newItem != null)
            activeTag = RSSTags.DATE_TAG;
        else if (qName.equals("enclosure") && newItem != null) {
            activeTag = RSSTags.IMAGE_TAG;
            if(attributes.getValue("type").equals("image/jpeg"))
                newItem.setImageLink(attributes.getValue("url"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("item")) {
            feed.addItem(newItem);
            newItem = null;
        }
        else
            activeTag = RSSTags.NONE;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(activeTag == RSSTags.TITLE_TAG)
            newItem.setTitle(new String(ch, start, length));
        if(activeTag == RSSTags.LINK_TAG)
            newItem.setLink(new String(ch, start, length));
        if(activeTag == RSSTags.DATE_TAG)
            newItem.setPubDate(new String(ch, start, length));
    }
}
