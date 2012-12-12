package de.nichtsohnegrund.dev.restbucks.representation;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract class for all JAX-RS representations of restbucks.
 * 
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public abstract class Representation {

    /**
     * where to find information about relation types
     */
    public static final String RELATIONS_URI = "http://relations.restbucks.com/";
    
    /**
     * default restbucks namespace
     */
    public static final String RESTBUCKS_NAMESPACE = "http://schemas.restbucks.com";
    
    /**
     * namespace of restbucks domain application protocol (HATEAOS)
     */
    public static final String DAP_NAMESPACE = RESTBUCKS_NAMESPACE + "/dap";
    
    /**
     * media type
     */
    public static final String RESTBUCKS_MEDIATYPE = "application/vnd.restbucks+xml";
    
    /**
     * self relation
     */
    public static final String SELF_REL_VALUE = "self";
    
    @XmlElement(name = "link", namespace = DAP_NAMESPACE)
    protected List<LinkRepresentation> links;
    
    protected LinkRepresentation getLinkByName(String uriName) {
        if(links == null) {
            return null;
        }
        for (LinkRepresentation l : links){
            if(l.getRel().equalsIgnoreCase(uriName)){
                return l;
            }
        }
        return null;
    }
}
