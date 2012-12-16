package de.nichtsohnegrund.dev.restbucks.representation;

import java.net.URI;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAX-RS representation for HATEOAS Links.
 * 
 * @author <a href="mailto:pgrund">pgrund</a>
 */
@XmlRootElement(name="link", namespace=Representation.DAP_NAMESPACE)
public class LinkRepresentation {

    private String rel;
    private URI uri;
    private String title;
    private String type;

    @XmlAttribute
    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @XmlAttribute(name="href")
    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkRepresentation() {}
    
    public LinkRepresentation(String rel, URI uri) {
        this.rel = rel;
        this.uri = uri;
    }
    public LinkRepresentation(String rel, URI uri, String type) {
        this.rel = rel;
        this.uri = uri;
        this.type = type;
    }

    public LinkRepresentation(String rel, URI uri, String title, String type) {
        this.rel = rel;
        this.uri = uri;
        this.title = title;
        this.type = type;
    }
    
}
