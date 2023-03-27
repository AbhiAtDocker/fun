package com.example.test.jdom;

import static com.example.test.jdom.helper.ClarinsConstants.column;
import static com.example.test.jdom.helper.ClarinsConstants.copyToFeatureStr;
import static com.example.test.jdom.helper.ClarinsConstants.dataTypeStr;
import static com.example.test.jdom.helper.ClarinsConstants.enabled;
import static com.example.test.jdom.helper.ClarinsConstants.featureStr;
import static com.example.test.jdom.helper.ClarinsConstants.keyPrefixStr;
import static com.example.test.jdom.helper.ClarinsConstants.mandatory;
import static com.example.test.jdom.helper.ClarinsConstants.updation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.jdom.helper.ClarinsBean;


@RestController
public class JDomController {
	
	
	Logger logger = LoggerFactory.getLogger(JDomController.class);
	
	@Autowired
	private JDomUtil jdomUtil;
	
	@Value("${jdom.fileLoc}")
	private String fileName;
	@Value("${column,mapping.file.url}")
	private String fileUrl;
	
	@GetMapping(path="/perform")
	public String perform()  {
		String message = "operation Completed";
		
		try
		  {
			//Path fileName = Path.of(this.fileName);
			String xml = readFromUrl(fileUrl);
			//String xml = Files.readString(fileName);
			SAXBuilder sb = new SAXBuilder();
	        Document doc = sb.build(new StringReader(xml));
		    System.out.println(doc.toString());
		    Element rootElement = doc.getRootElement();
		    List<Element> childElements = rootElement.getContent(Filters.element());
		    ClarinsBean cb =  new ClarinsBean();
		    populateBean(cb,childElements);
		  } 
		  catch (IOException  | JDOMException e) 
		  {
		    e.printStackTrace();
		  }
		  
		
		
		return message;
		
		
	}

   /****
    * 
    * @param cb
    * @param childElements
    */
	private void populateBean(ClarinsBean cb, List<Element> childElements) {
		if(childElements!=null && !childElements.isEmpty()) {
		    logger.info("Clarins: processing column mapping configuration -- ");
		    List<Map<String, String>> allColumns = new ArrayList<>();
		    defineColMapValues(childElements,cb,allColumns);
		    cb.setAllColumns(allColumns);
		    logger.info("Clarins: processing column mapping configuration completed. ");
		}else {
			logger.error("Processing failed. <Mapping/> elements are missing from the configuration");
		}
		
	}

	
	
	
	/***
	 * 
	 * @param childElements
	 * @param mappingBean
	 * @param allColumns
	 */
	private void defineColMapValues(List<Element> childElements, ClarinsBean mappingBean,
			List<Map<String, String>> allColumns) {
		int colIndex = 0;
		List<Integer> enabledFields = new ArrayList<>();
		List<Integer> mandatoryFields = new ArrayList<>();
		List<Integer> updateFields = new ArrayList<>();
		for (Element element : childElements) {
			Map<String, String> mapAttr = new HashMap<>();
			setEnableMandatory(element, mapAttr, enabledFields, mandatoryFields, colIndex);
			putMapperValues(element, mapAttr, colIndex, updateFields);
			mappingBean.setMandatoryColumn(mandatoryFields);
			mappingBean.setEnabledColumn(enabledFields);
			mappingBean.setUpdateColumn(updateFields);
			allColumns.add(mapAttr);
			colIndex++;
		}
	}

   /****
    * 
    * @param element
    * @param mapAttr
    * @param colIndex
    * @param updateFields
    */
	private void putMapperValues(Element element, Map<String, String> mapAttr, int colIndex,
			List<Integer> updateFields) {
		if (element.getAttribute(updation) != null) {
			mapAttr.put(updation, element.getAttribute(updation).getValue());
			if (element.getAttribute(updation).getValue().equalsIgnoreCase("true")) {
				updateFields.add(colIndex);
			}
		}
		if (element.getAttribute(dataTypeStr) != null) {
			mapAttr.put(dataTypeStr, element.getAttribute(dataTypeStr).getValue());
		}
		if (element.getAttribute(keyPrefixStr) != null) {
			mapAttr.put(keyPrefixStr, element.getAttribute(keyPrefixStr).getValue());
		}
		if (element.getAttribute(featureStr) != null) {
			mapAttr.put(featureStr, element.getAttribute(featureStr).getValue());
		}
		if (element.getAttribute(column) != null) {
			mapAttr.put(column, element.getAttribute(column).getValue());
		}
		if (element.getAttribute(copyToFeatureStr) != null) {
			mapAttr.put(copyToFeatureStr, element.getAttribute(copyToFeatureStr).getValue());
		}
		
	}

   /***
    * 
    * @param element
    * @param mapAttr
    * @param enabledFields
    * @param mandatoryFields
    * @param colIndex
    */
	private void setEnableMandatory(Element element, Map<String, String> mapAttr, List<Integer> enabledFields,
			List<Integer> mandatoryFields, int colIndex) {
		if (element.getAttribute(enabled) != null) {
			mapAttr.put(enabled, element.getAttribute(enabled).getValue());
			if (element.getAttribute(enabled).getValue().equalsIgnoreCase("true")) {
				enabledFields.add(colIndex);
			}
		}
		if (element.getAttribute(mandatory) != null) {
			mapAttr.put(mandatory, element.getAttribute(mandatory).getValue());
			if (element.getAttribute(mandatory).getValue().equalsIgnoreCase("true")) {
				mandatoryFields.add(colIndex);
			}
		}
		
	}
	
	
	/***
	 * 
	 * @param urlStr
	 * @return
	 */
	public String readFromUrl(String urlStr) {
		
		StringBuilder sb = new StringBuilder();
		 try {      
		URL url = new URL(urlStr);
		
		BufferedReader read = new BufferedReader(
		        new InputStreamReader(url.openStream()));

		        String i;
		        while ((i = read.readLine()) != null)
		        	sb.append(i);
		        
		        
		        logger.info("JDOMCOntroller: {} ", sb.toString());
		        read.close();
		 }catch(Exception ex) {
			 System.out.println(ex);
		 }
		
		return sb.toString();
	}
	
	
}
