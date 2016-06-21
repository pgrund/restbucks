/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.grund.dev.restbucks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author grund
 */
@Controller
@ExposesResourceFor(Link.class)
@RequestMapping("/rels")
public class RelationController {
        
    private final static Logger LOG = Logger.getLogger(RelationController.class.getName());

    private final EntityLinks entityLinks;

    @Autowired
    public RelationController(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String getOverviewHTML() {
        LOG.info("get relation overview ...");
        return "/relations.html";
    
    }
    @RequestMapping(
            path = "/{rel}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String getRelation(@PathVariable("rel") String relation) {
        LOG.log(Level.INFO, "get relation info for {0}", relation);
        return "redirect:/relations.html#" + relation;
    }
    
    @RequestMapping(
            path = "/{rel}",
            method = RequestMethod.GET,
            produces = "text/javascript"
    )
    public String getScriptForRelation(@PathVariable("rel") String relation) {
        LOG.log(Level.INFO, "get javascript functions for {0}", relation);
        return "/js/"+relation+ ".js";
    }
}
