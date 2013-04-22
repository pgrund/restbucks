@XmlSchema(  
    xmlns = {   
        @XmlNs(namespaceURI = Representation.RESTBUCKS_NAMESPACE, prefix = "rb"),  
        @XmlNs(namespaceURI = Representation.RESTBUCKS_NAMESPACE + "/dap", prefix = "dap")  
    },  
    elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)  
  
package de.nichtsohnegrund.dev.restbucks.representation;

import javax.xml.bind.annotation.XmlNs;  
import javax.xml.bind.annotation.XmlSchema; 
