/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.nichtsohnegrund.dev;

import java.util.UUID;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

/**
 *
 * @author <a href="mailto:pgrund">pgrund</a>
 */
public class CustomerFormatRegistryBean extends
    FormattingConversionServiceFactoryBean {

  public void installFormatters(FormatterRegistry registry) {
    super.installFormatters(registry);
     registry.addFormatterForFieldType(UUID.class,
       new UUIDFormatter());
  }
}