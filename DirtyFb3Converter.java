import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;	
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.xml.sax.SAXException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

public class DirtyFb3Converter {
    protected static final String CONTENT_TYPES_FILENAME ="[Content_Types].xml";
    protected static final String RELS_DIR ="_rels";
    protected static final String RELS_EXTENSION = ".rels";
    protected static final String RELS_TYPE_COVER ="http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail";
    protected static final String RELS_TYPE_CORE ="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties";
    protected static final String RELS_TYPE_DESCRIPTION ="http://www.fictionbook.org/FictionBook3/relationships/Book";
    protected static final String CONTENT_TYPE_CORE="application/vnd.openxmlformats-package.core-properties+xml";
    protected static final String CONTENT_TYPE_DESCRIPTION="application/fb3-description+xml";
    protected static final String CONTENT_TYPE_BODY="application/fb3-body+xml";
    protected static final String DEFAULT_BODY_FILENAME = "fb3/body.xml";
    protected static final String DEFAULT_DESCRIPTION_FILENAME = "fb3/description.xml";
    protected static final String DEFAULT_COVER_FILE_ID = "default_cover_file_id" ;
    protected static final String TAG_FB3_BODY = "fb3-body";
    protected static final String TAG_FB2_ROOT = "FictionBook";
    protected static final String TAG_FB2_BODY = "body";
    protected static final String TAG_IMG = "img";
    protected static final String TAG_IMAGE = "image";
    protected static final String TAG_EM = "em";
    protected static final String TAG_EMPHASIS = "emphasis";
    protected static final String TAG_A = "a";
    protected static final String TAG_P = "p";
    protected static final String TAG_NOTE = "note";
    protected static final String TAG_NOTES = "notes";
    protected static final String TAG_NOTEBODY = "notebody";
    protected static final String TAG_BLOCKQUOTE = "blockquote";
    protected static final String TAG_SECTION = "section";
    protected static final String TAG_CITE = "cite";    
    protected static final String TAG_DIV = "div";    
    protected static final String TAG_BR = "br";    
    protected static final String TAG_EMPTY_LINE = "empty-line";    
    protected static final String TAG_BINARY = "binary";
    protected static final String TAG_TEXT = "#text";
    protected static final String TAG_DESCRIPTION = "description";
    protected static final String TAG_CP_COREPROPERTIES = "cp:coreProperties";
    protected static final String TAG_CP_CATEGORY = "cp:category";
    protected static final String TAG_TITLE_INFO = "title-info";
    protected static final String TAG_GENRE = "genre";
    protected static final String TAG_DOCUMENT_INFO = "document-info";
    protected static final String TAG_PUBLISH_INFO = "publish-info";
    protected static final String TAG_FB3_DESCRIPTION = "fb3-description";
    protected static final String TAG_FB3_RELATIONS = "fb3-relations";
    protected static final String TAG_SUBJECT = "subject";
    protected static final String TAG_FIRST_NAME = "first-name";
    protected static final String TAG_LAST_NAME = "last-name";
    protected static final String TAG_MIDDLE_NAME = "middle-name";
    protected static final String TAG_ID = "id";
    protected static final String TAG_AUTHOR = "author";
    protected static final String TAG_PUBLISHER = "publisher";
    protected static final String TAG_TRANSLATOR = "translator";
    protected static final String TAG_SEQUENCE = "sequence";
    protected static final String TAG_TITLE = "title";
    protected static final String TAG_MAIN = "main";
    protected static final String TAG_LANG = "lang";
    protected static final String TAG_WRITTEN = "written";
    protected static final String TAG_SRC_LANG = "src-lang";
    protected static final String TAG_SRC_TITLE_INFO = "src-title-info";
    protected static final String TAG_DATE =   "date";
    protected static final String TAG_TRANSLATED = "translated";
    protected static final String TAG_PAPER_PUBLISH_INFO = "paper-publish-info";
    protected static final String TAG_ISBN = "isbn";
    protected static final String TAG_BOOK_NAME = "book-name";
    protected static final String TAG_BOOK_TITLE = "book-title";
    protected static final String TAG_ANNOTATION = "annotation";
    protected static final String TAG_COVERPAGE  = "coverpage";
    protected static final String TAG_RELATIONSHIPS  = "Relationships";
    protected static final String TAG_RELATIONSHIP  = "Relationship";
    protected static final String TAG_SUBSCRIPTION  = "subscription"; 
    protected static final String TAG_TEXT_AUTHOR  = "text-author";


    protected static final String ATTR_NAME_XMLNS = "xmlns";
    protected static final String ATTR_NAME_SRC = "src";
    protected static final String ATTR_NAME_HREF = "href";
    protected static final String ATTR_NAME_TYPE = "type";
    protected static final String ATTR_NAME_CONTENT_TYPE = "content-type";
    protected static final String ATTR_NAME_ID = "id";
    protected static final String ATTR_NAME_LINK = "link";
    protected static final String ATTR_NAME_NAME = "name";
    protected static final String ATTR_NAME_NUMBER = "number";
    protected static final String ATTR_NAME_VALUE = "value";
    protected static final String ATTR_NAME_TITLE = "title";
	protected static final String ATTR_NAME_OVERRIDE = "Override";
	protected static final String ATTR_NAME_CONTENTTYPE = "ContentType";
	protected static final String ATTR_NAME_PARTNAME = "PartName";	

 
    protected static final String NAMESPACE_PREFIX_FB2 = "l"; 

    protected static final String ATTR_VALUE_A_TYPE_NOTE = "note";
    protected static final String ATTR_VALUE_LINK_AUTHOR = "author";
    protected static final String ATTR_VALUE_LINK_PUBLISHER = "publisher";
    protected static final String ATTR_VALUE_LINK_TRANSLATOR = "translator";
    protected static final String ATTR_VALUE_BODY_NAME_NOTES  = "notes";

    protected static final String ATTR_VALUE_XMLNS = "http://www.gribuser.ru/xml/fictionbook/2.0";
    protected static final String ATTR_VALUE_XMLNS_L = "http://www.w3.org/1999/xlink";
    
    protected static String coverFilename = null;
    protected static String coreFilename = null;
    protected static String descriptionFilename = null;
    protected static String bodyFilename = null;
    protected static String relDescriptionFilename = null;
    protected static String relBodyFilename = null;
    protected static String coverRelId = null;
    protected static String namespacePrefixFb3 =  "xlink";
    protected static HashMap<String, String> imagesMap     = new HashMap<>();
    protected static HashMap<String, String> imagesExtMap  = new HashMap<>();
    protected static HashMap<String, Boolean> imagesUsedMap = new HashMap<>();
    protected static DocumentBuilderFactory xmlFactory;
    protected static VirtualFileSystem vfs = null;


    public static void main(String[] args) throws Exception {
        String inputDir = null;
        String outputFile = null;
        String inputPath = null;

        // Reading command line parameters
        for (String arg : args) {
            if (arg.startsWith("--unzipped_fb3_dir=")) {
                inputPath = arg.split("=")[1];
                vfs = new FileBasedVFS(new File(inputPath));
            } else if (arg.startsWith("--fb3_file=")) {
               inputPath = arg.split("=")[1]; 
                try (InputStream fis = new FileInputStream(new File(inputPath))) {
                   vfs = new ZipMemoryVFS(inputPath, fis);
                }
                inputPath = ""; // ZIP base path 
            } else if (arg.startsWith("--output=")) {
                outputFile = arg.split("=")[1];  
            }
        }
        // Check that the parameters are set
        if (vfs == null || outputFile == null) {
            System.err.println("Usage: java DirtyFb3Converter --unzipped_fb3_dir=<fb3_dir>|--fb3_file=<zip> --output=<saved_fb2>\n\n"+
			                   "--unzipped_fb3_dir - path to the unzipped book in fb3 format\n" +
							   "--fb3_file - path to fb3 file (zipped fb3 dir)\n"+
							   "--output - path where to save the completed fb2");
            return;
        }

        try { 
            xmlFactory = DocumentBuilderFactory.newInstance();
 
            readDocumentProperty();
            loadImages(relBodyFilename);
            Document document = StartConvertFb3ToFb2();
			if(document==null) return;
            replaceFb3TagsIntoFb2(document);
            generateDescription(document);
            insertImagesContent(document);    
            System.out.println("Complete");             

                // Saving changes to file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(outputFile));
                transformer.transform(source, result);

        } catch (Exception e) {
                System.out.println("Convertation Error" +e);
        }
 
    }
//reading the structure of the fb3 document and filling in the main parameters to generate the output file	
    public static void  readDocumentProperty() throws IOException, ParserConfigurationException,SAXException{

			Document doc = parseDocumentFromPath(RELS_DIR +"/"+RELS_EXTENSION	);
 

            
// Find the "Relationships" node
            NodeList relationships = doc.getElementsByTagName(TAG_RELATIONSHIPS);
            if (relationships.getLength() > 0) {
                // Get the contents of the "Relationships" node
                Element relationshipsElement = (Element) relationships.item(0);
                NodeList relationshipNodes = relationshipsElement.getElementsByTagName(TAG_RELATIONSHIP);
                
                // Iterate over all "Relationship" nodes and output the "Target" attribute
                for (int i = 0; i < relationshipNodes.getLength(); i++) {
                    Element relationship = (Element) relationshipNodes.item(i);
                    String relType =  relationship.getAttribute("Type");
                    String relTarget = relationship.getAttribute("Target");					

                    if(relType.equals(RELS_TYPE_COVER)){ coverFilename =   relTarget.replaceAll("^/+", "");}
                    else if(relType.equals(RELS_TYPE_CORE)){ coreFilename =  relTarget.replaceAll("^/+", "");}
                    else if(relType.equals(RELS_TYPE_DESCRIPTION)){ descriptionFilename =   relTarget.replaceAll("^/+", "");}

                }
            }

              doc = parseDocumentFromPath(CONTENT_TYPES_FILENAME);
            
            // Find the "Types" node
             relationships = doc.getElementsByTagName("Types");
            
            if (relationships.getLength() > 0) {
                // Get the contents of the "Types" node
                 Element relationshipsElement = (Element) relationships.item(0);
                 NodeList relationshipNodes = relationshipsElement.getElementsByTagName(ATTR_NAME_OVERRIDE);
                
                // Iterate over all "Override" nodes and output the "PartName" and "Contenttype" attributes
                for (int i = 0; i < relationshipNodes.getLength(); i++) {
                    Element relationship = (Element) relationshipNodes.item(i);
                    String relType =  relationship.getAttribute(ATTR_NAME_CONTENTTYPE);
                    String relTarget = relationship.getAttribute(ATTR_NAME_PARTNAME);

                    if(relType.equals(CONTENT_TYPE_CORE)){ if(coreFilename == null ) coreFilename =  relTarget.replaceAll("^/+", "");}
                    else if(relType.equals(CONTENT_TYPE_DESCRIPTION)){  if(descriptionFilename == null ) descriptionFilename =   relTarget.replaceAll("^/+", "");}
                    else if(relType.equals(CONTENT_TYPE_BODY)){ if(bodyFilename == null ) bodyFilename =   relTarget.replaceAll("^/+", "");}					
                }
            }

            if(bodyFilename == null )        bodyFilename =   DEFAULT_BODY_FILENAME;
            if(descriptionFilename == null ) descriptionFilename =  DEFAULT_DESCRIPTION_FILENAME;			
            Path FilePath = Paths.get(bodyFilename);
            String pathToBody = FilePath.getParent().toString();
            relBodyFilename = FilePath.getParent().toString() + "/" + RELS_DIR + "/" +FilePath.getFileName().toString()  + RELS_EXTENSION;  
            FilePath = Paths.get(descriptionFilename);
            relDescriptionFilename = FilePath.getParent().toString() + "/" + RELS_DIR + "/" +FilePath.getFileName().toString()  + RELS_EXTENSION;  


              
 
         }
//Start of formation of fb2 document by copying content from body.xml		 
    public static Document  StartConvertFb3ToFb2() throws IOException, ParserConfigurationException,SAXException{
 
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = parseDocumentFromPath(bodyFilename);

            // Getting the root element
            Element fb3RootElement = document.getDocumentElement();

            if (fb3RootElement.getNodeName().equals(TAG_FB3_BODY)) {
                NamedNodeMap attributes = fb3RootElement.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    String [] attrParts = attribute.getNodeName().split(":");
                    if(attrParts.length == 2 && attrParts[0].equals(ATTR_NAME_XMLNS)){
                       namespacePrefixFb3 = attrParts[1];
                    }
                }                
                Element fb2NewRoot = document.createElementNS(ATTR_VALUE_XMLNS, TAG_FB2_ROOT);
                fb2NewRoot.setAttribute(ATTR_NAME_XMLNS +":"+ NAMESPACE_PREFIX_FB2, ATTR_VALUE_XMLNS_L);
                Element newBody = document.createElement(TAG_FB2_BODY);
                fb2NewRoot.appendChild(newBody);

                // Move all child nodes from TAG_FB3_BODY to TAG_FB2_BODY
                NodeList childNodes = fb3RootElement.getChildNodes();
                while (childNodes.getLength() > 0) {
                    Node child = childNodes.item(0);
                    newBody.appendChild(child);
                }

                // Replace the old root element with the new one 
                document.replaceChild(fb2NewRoot, fb3RootElement);
            } else {
                System.out.println("Root element is not 'fb3-body'.");
				return null;
            }
 

            return  document;

    }
// 	Replacing fb3-specific tags with similar ones in fb2
    public static void replaceFb3TagsIntoFb2( Document document) throws IOException, ParserConfigurationException,SAXException{

        NodeList fromChildNodes;
        Node child;
        Element fb2RootElement = document.getDocumentElement();
        NodeList childNodes = fb2RootElement.getChildNodes();
        String fromTag = null, toTag = null;
        if (childNodes.getLength() > 0) {
           for(int numOfReplaceTag = 0; numOfReplaceTag <= 9 ;numOfReplaceTag++){
             try {               
               switch (numOfReplaceTag) {
               case 0:
                 fromTag = TAG_IMG;
                 toTag   = TAG_IMAGE;
                 break;
               case 1:
                 fromTag = TAG_EM;
                 toTag   = TAG_EMPHASIS;
                 break;
               case 2:
                 fromTag = TAG_A;
                 toTag   = TAG_A;
                 break;
               case 3:
                 fromTag = TAG_NOTE;
                 toTag   = TAG_A;
                 break;
               case 4:
                 fromTag = TAG_BLOCKQUOTE;
                 toTag   = TAG_CITE;
                 break;
               case 5:
                 fromTag = TAG_DIV;
                 toTag   = TAG_EMPTY_LINE;
                 break;
               case 6:
                 fromTag = TAG_NOTEBODY;
                 toTag   = TAG_SECTION;
                 break;
               case 7:
                 fromTag = TAG_NOTES;
                 toTag   = TAG_FB2_BODY;
                 break;
               case 8:
                 fromTag = TAG_SUBSCRIPTION;
                 toTag   = TAG_TEXT_AUTHOR;
                 break;
               case 9:
                 fromTag = TAG_BR;
                 toTag   = TAG_EMPTY_LINE;
                 break;

               default: ;
                 break; 
               }      
            NodeList fromTagNodes = document.getElementsByTagName(fromTag);

            // We have to go through the tags in reverse order because the replacement will change the structure
            for (int i = fromTagNodes.getLength() - 1; i >= 0; i--) {
                Node fromTagNode = fromTagNodes.item(i);
                if (fromTagNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fromTagElement = (Element) fromTagNode;
                    boolean doAttrCopy = true;
                    // Create new tags
                    Element toTagElement = document.createElement(toTag);
                    if(fromTag.equals(TAG_IMG)){
                       String attrValue =  fromTagElement.getAttribute(ATTR_NAME_SRC);
                       if(imagesMap.containsKey(attrValue)){
                          imagesUsedMap.put(attrValue,true);
                          toTagElement.setAttribute( NAMESPACE_PREFIX_FB2 + ":"+ ATTR_NAME_HREF,"#"+attrValue+"."+imagesExtMap.get(attrValue));
                       }else{
                          toTagElement.setAttribute( NAMESPACE_PREFIX_FB2 + ":"+ ATTR_NAME_HREF,"#");
                       }
                       doAttrCopy = false;
                    }else if(fromTag.equals(TAG_A)){
                        String attrValue =  fromTagElement.getAttribute(namespacePrefixFb3 + ":"+ ATTR_NAME_HREF);
                        if(attrValue != null)
                            toTagElement.setAttribute( NAMESPACE_PREFIX_FB2 + ":"+ ATTR_NAME_HREF,attrValue);
                     doAttrCopy = false;
                    }else if(fromTag.equals(TAG_NOTE)){
                        String attrValue =  fromTagElement.getAttribute( ATTR_NAME_HREF);
                        if(attrValue != null)
                            toTagElement.setAttribute( NAMESPACE_PREFIX_FB2 + ":"+ ATTR_NAME_HREF,"#"+attrValue);
                            toTagElement.setAttribute( ATTR_NAME_TYPE,ATTR_VALUE_A_TYPE_NOTE);
                        doAttrCopy = false;
                    }else if(fromTag.equals(TAG_DIV)){
                      Node parentNode = fromTagNode.getParentNode();
                      // Get nested div nodes
                      childNodes = fromTagElement.getChildNodes();
                      int lengthOfNodes = childNodes.getLength(); 
                      if(lengthOfNodes == 1 && childNodes.item(0).getNodeName().equals(TAG_P)){
                         childNodes = childNodes.item(0).getChildNodes();
                      } else if(lengthOfNodes == 2 && childNodes.item(0).getNodeName().equals(TAG_P)
                               && childNodes.item(1).getNodeName().equals(TAG_TEXT)
                               && childNodes.item(1).getNodeValue().trim().length() == 0){
                         childNodes = childNodes.item(0).getChildNodes();
                      } else if(lengthOfNodes == 2 && childNodes.item(1).getNodeName().equals(TAG_P)
                               && childNodes.item(0).getNodeName().equals(TAG_TEXT)
                               && childNodes.item(0).getNodeValue().trim().length() == 0){
                         childNodes = childNodes.item(1).getChildNodes();
                      } else if(lengthOfNodes == 3 && childNodes.item(1).getNodeName().equals(TAG_P)
                               && childNodes.item(0).getNodeName().equals(TAG_TEXT)
                               && childNodes.item(0).getNodeValue().trim().length() == 0
                               && childNodes.item(2).getNodeName().equals(TAG_TEXT)
                               && childNodes.item(2).getNodeValue().trim().length() == 0){
                         childNodes = childNodes.item(1).getChildNodes();
                      }

                      parentNode.insertBefore(toTagElement, fromTagElement); 
                      // Move all nested nodes to the next level
                      for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
                         child = childNodes.item(childIndex);
                         Node importedNode = document.importNode(child, true);  
                         parentNode.insertBefore(importedNode, fromTagElement); 
                      }
                      parentNode.removeChild(fromTagNode);
                      continue; 
                    }else if(fromTag.equals(TAG_NOTES)){
                      toTagElement.setAttribute( ATTR_NAME_NAME,ATTR_VALUE_BODY_NAME_NOTES);
                      doAttrCopy = false;
		      fromChildNodes = fromTagElement.getChildNodes();
                      while (fromChildNodes.getLength() > 0) {
                        child = fromChildNodes.item(0);
                        toTagElement.appendChild(child);
                      }
					  fb2RootElement.appendChild(toTagElement);
					  fromTagNode.getParentNode().removeChild(fromTagNode);
                      continue;
                    }
                    // Move all child nodes
                    fromChildNodes = fromTagElement.getChildNodes();
                    while (fromChildNodes.getLength() > 0) {
                        child = fromChildNodes.item(0);
                        toTagElement.appendChild(child);
                    }
                    if(doAttrCopy == true){
                        NamedNodeMap attributes = ((Element) fromChildNodes).getAttributes();
                        for (int attrCount = 0; attrCount < attributes.getLength(); attrCount++) {
                            Node attribute = attributes.item(attrCount);           
                            toTagElement.setAttribute( attribute.getNodeName(),attribute.getNodeValue());
                        }
                    }                

                    // Replacement
                    fromTagNode.getParentNode().replaceChild(toTagElement, fromTagElement);

 
                }
            }                        
             } catch (Exception e) {
               
             } 
           }
 
        }

      
    }
///// Getting a list of all images in a document from body.xml.rels
    protected static void loadImages(String ImagePath) {
        File coverFile = null;
        try {
          if(coverFilename != null && coverFilename.length() > 0 ){
             coverFile = new File(coverFilename);
          }
        } catch (Exception e) {
        }
 
        try { 
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Loading XML document
            Document doc =  parseDocumentFromPath(ImagePath);
            
            // Find the "Relationships" node 
            NodeList relationships = doc.getElementsByTagName(TAG_RELATIONSHIPS);
            String newPath = ImagePath;
            newPath = newPath.replace("\\","/");
            int splitIndex = newPath.lastIndexOf('/');
            newPath = newPath.substring(0,splitIndex);
            splitIndex = newPath.lastIndexOf('/');
            newPath = newPath.substring(0,splitIndex);
            
            if (relationships.getLength() > 0) {
                // Get the contents of the "Relationships" node 
                Element relationshipsElement = (Element) relationships.item(0);
                NodeList relationshipNodes = relationshipsElement.getElementsByTagName(TAG_RELATIONSHIP);
                
                // Iterate over all "Relationship" nodes and output the "Target" attribute
                for (int i = 0; i < relationshipNodes.getLength(); i++) {
                    Element relationship = (Element) relationshipNodes.item(i);
                    String Id =  relationship.getAttribute("Id");
                    String target = relationship.getAttribute("Target");
                    imagesMap.put(Id, newPath+"/"+ target);
                    File imgFile = new File(newPath+"/"+ target);
                    String fileName = imgFile.getName();
                    splitIndex = fileName.lastIndexOf('.');
                    imagesExtMap.put(Id, (splitIndex == -1) ? "" : fileName.substring(splitIndex+1));
              // Processing for Cover image
                    if(coverFilename == null || coverFilename.length() == 0){
                        coverFilename  = newPath+"/"+ target;
                        coverRelId  = Id;
                    } else if(coverFile != null && coverRelId ==  null ) {
                        if (coverFile.compareTo(imgFile) == 0) {
                            coverRelId  = Id;  
                        }                      
                    }
                    
                }
				
            } else {
                System.out.println("Image node 'Relationships' not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
//special treatment for cover image		
        if(coverFile != null && coverRelId ==  null ) {
 
          coverRelId = DEFAULT_COVER_FILE_ID;
          imagesMap.put(coverRelId, coverFilename);
          String fileName = coverFile.getName();
          int splitIndex = fileName.lastIndexOf('.');
          imagesExtMap.put(coverRelId, (splitIndex == -1) ? "" : fileName.substring(splitIndex+1));
        }
        if(coverRelId!= null){
          imagesUsedMap.put(coverRelId,true);         
        }
 
    }
// 	generating a book description in the description tag
    public static void generateDescription(Document document) throws IOException, ParserConfigurationException,SAXException{
 
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
           

       Element fb2RootElement = document.getDocumentElement();
       Element srcTitleInfoElement = null;
       if (fb2RootElement.getNodeName().equals(TAG_FB2_ROOT)) {
             NodeList childNodes = fb2RootElement.getChildNodes();
             if (childNodes.getLength() > 0) {
                   Element descriptionElement = document.createElement(TAG_DESCRIPTION);
                   fb2RootElement.insertBefore(descriptionElement, childNodes.item(0)); 
                   Element titleInfoElement = document.createElement(TAG_TITLE_INFO);
                   descriptionElement.appendChild(titleInfoElement); 
                   Element documentInfoElement = document.createElement( TAG_DOCUMENT_INFO);
                   descriptionElement.appendChild(documentInfoElement); 
                   Element publishInfoElement = document.createElement( TAG_PUBLISH_INFO);
                   descriptionElement.appendChild(publishInfoElement); 

            //core.xml
                   try{
						    Document docCore = parseDocumentFromPath(coreFilename);
                          Element corePropertiesElement = docCore.getDocumentElement();

                          if (corePropertiesElement.getNodeName().equals(TAG_CP_COREPROPERTIES)) { 
                              NodeList categoryList = corePropertiesElement.getElementsByTagName(TAG_CP_CATEGORY);
                              if (categoryList.getLength() > 0) {
                                  Element categoryElement = (Element) categoryList.item(0);
                                  String [] catTab = categoryElement.getTextContent().split(" "); 
                                  for(int i=0;i< catTab.length;i++){
                                    if(catTab[i].trim().length() > 0){
                                         Element genreElement = document.createElement(TAG_GENRE);
                                         genreElement.appendChild(document.createTextNode(catTab[i]));   
                                         titleInfoElement.appendChild(genreElement);                                  
                                    }

                                 }
                              }
                          }

                   } catch (Exception e) {
                       e.printStackTrace();
                   }
             //description.xml
                   try{
						  Document docDescription = parseDocumentFromPath(descriptionFilename);
                          Element fb3DescriptionElement = docDescription.getDocumentElement();

                          if (fb3DescriptionElement.getNodeName().equals(TAG_FB3_DESCRIPTION)) { 
                              NodeList fb3RelationsList = fb3DescriptionElement.getElementsByTagName(TAG_FB3_RELATIONS);
                              if (fb3RelationsList.getLength() > 0) {
                                  Element fb3RelationsElement = (Element) fb3RelationsList.item(0);
                                  NodeList subjectList = fb3RelationsElement.getElementsByTagName(TAG_SUBJECT); 
                                  for (int subjectCount = 0; subjectCount < subjectList.getLength(); subjectCount++) {
                                      Element subjectElement = (Element) subjectList.item(subjectCount);
                                      String linkValue =  subjectElement.getAttribute(ATTR_NAME_LINK);
                                      Element newSubjElement = null;
                                      if(linkValue ==null){
                                          continue; 
                                      } else if(linkValue.equals(ATTR_VALUE_LINK_AUTHOR)){
                                         newSubjElement = document.createElement(TAG_AUTHOR);
                                         titleInfoElement.appendChild(newSubjElement); 
                                      } else if(linkValue.equals(ATTR_VALUE_LINK_PUBLISHER)){
                                         newSubjElement = document.createElement(TAG_PUBLISHER);
                                         documentInfoElement.appendChild(newSubjElement); 
                                      } else if(linkValue.equals(ATTR_VALUE_LINK_TRANSLATOR)){
                                         newSubjElement = document.createElement(TAG_TRANSLATOR);
                                         titleInfoElement.appendChild(newSubjElement); 
                                      } else{ continue; }
                                      String idValue =  subjectElement.getAttribute(ATTR_NAME_ID);
                                      Element newSubjChildElement=null;
                                      if(idValue.length()>0){    
                                         newSubjChildElement = document.createElement(TAG_ID);
                                         newSubjChildElement.appendChild(document.createTextNode(idValue));   
                                         newSubjElement.appendChild(newSubjChildElement); 
                                      }
                                      NodeList subjectChildNodes = subjectElement.getChildNodes();
                                      for (int subjectChildCount = 0; subjectChildCount < subjectChildNodes.getLength(); subjectChildCount++) {
                                         Element subjectChildElement = (Element) subjectChildNodes.item(subjectChildCount);
                                         if(subjectChildElement.getNodeName().equals(TAG_FIRST_NAME)||
                                            subjectChildElement.getNodeName().equals(TAG_LAST_NAME)||
                                            subjectChildElement.getNodeName().equals(TAG_MIDDLE_NAME)){

                                            newSubjChildElement = document.createElement(subjectChildElement.getNodeName());
                                            newSubjChildElement.appendChild(document.createTextNode(subjectChildElement.getTextContent()));   
                                            newSubjElement.appendChild(newSubjChildElement); 
                                            
                                         } 
                                          
                                      }
                                  }
                              }
///////////////////////////TAG_COVERPAGE
                              if(coverRelId!=null && coverRelId.length()>0){
                                  if(imagesMap.containsKey(coverRelId)){   
                                      Element newCoverpageElement = document.createElement(TAG_COVERPAGE);  
                                      Element newImageElement = document.createElement(TAG_IMAGE);
                                      newImageElement.setAttribute( NAMESPACE_PREFIX_FB2 + ":"+ ATTR_NAME_HREF,"#"+coverRelId+"."+imagesExtMap.get(coverRelId));
                                      newCoverpageElement.appendChild(newImageElement); 
                                      titleInfoElement.appendChild(newCoverpageElement);
                                   }

                              }
//////////////////////////TAG_BOOK_TITLE
                              NodeList fb3DescriptionChildList = fb3DescriptionElement.getElementsByTagName(TAG_TITLE);
                              if (fb3DescriptionChildList.getLength() > 0) {
                                      Element titleElement = (Element) fb3DescriptionChildList.item(0);
                                      NodeList mainList = titleElement.getElementsByTagName(TAG_MAIN);
                                      if (mainList.getLength() > 0) {
                                            Element mainElement =  (Element) mainList.item(0);
                                            Element newBookTitleElement = document.createElement(TAG_BOOK_TITLE);  
                                            newBookTitleElement.appendChild(document.createTextNode(mainElement.getTextContent()));                   
                                            titleInfoElement.appendChild(newBookTitleElement); 
                                         
                                      }                               
                                   
                              }                                          
//////////////////////////TAG_SEQUENCE
                              NodeList sequenceList = fb3DescriptionElement.getElementsByTagName(TAG_SEQUENCE);
                              if (sequenceList.getLength() > 0) {
                                  for (int sequenceCount = 0; sequenceCount < sequenceList.getLength(); sequenceCount++) {
                                      Element sequenceElement = (Element) sequenceList.item(sequenceCount);
                                      NodeList titleList = sequenceElement.getElementsByTagName(TAG_TITLE);
                
                                      if (titleList.getLength() > 0) {
                                          Element titleElement = (Element) titleList.item(0);
                                          NodeList mainList = titleElement.getElementsByTagName(TAG_MAIN);
                                          if (mainList.getLength() > 0) {
                                                Element mainElement =  (Element) mainList.item(0);
                                                Element newMainElement = document.createElement(TAG_SEQUENCE);
                                                Attr attr = document.createAttribute(ATTR_NAME_NAME);
                                                attr.setValue(mainElement.getTextContent());
                                                newMainElement.setAttributeNode(attr);
                                                String sequenceNumber =  sequenceElement.getAttribute(ATTR_NAME_NUMBER);
                                                if(sequenceNumber != null && sequenceNumber.length() > 0){
                                                    attr = document.createAttribute(ATTR_NAME_NUMBER);
                                                    attr.setValue(sequenceNumber);
                                                    newMainElement.setAttributeNode(attr); 
                                                }                    
                                                titleInfoElement.appendChild(newMainElement); 
                                         
                                          }                               
                                      }
                                  }
                              }

                              fb3DescriptionChildList = fb3DescriptionElement.getChildNodes( );
                              for (int fb3DescriptionCount = 0; fb3DescriptionCount < fb3DescriptionChildList.getLength(); fb3DescriptionCount++) {
                                  Element fb3DescriptionChild = (Element) fb3DescriptionChildList.item(fb3DescriptionCount);
/////////////////////// lang  src-lang TAG_LANG
                                  if (fb3DescriptionChild.getNodeType() == Node.ELEMENT_NODE && fb3DescriptionChild.getNodeName().equals(TAG_LANG)) {
                                      Element newTiElement = document.createElement(TAG_LANG);
                                      newTiElement.appendChild(document.createTextNode(fb3DescriptionChild.getTextContent()));   
                                      titleInfoElement.appendChild(newTiElement);                                       
 
                                  } else if(fb3DescriptionChild.getNodeType() == Node.ELEMENT_NODE && fb3DescriptionChild.getNodeName().equals(TAG_WRITTEN)) {
                                      NodeList langList = fb3DescriptionChild.getElementsByTagName(TAG_LANG);
                                      if (langList.getLength() > 0) {
                                          Element newTiElement = document.createElement(TAG_SRC_LANG);
                                          newTiElement.appendChild(document.createTextNode(langList.item(0).getTextContent()));   
                                          descriptionElement.appendChild(newTiElement);                                       

                                          if(srcTitleInfoElement == null){
                                               srcTitleInfoElement = document.createElement(TAG_SRC_TITLE_INFO);
                                               descriptionElement.appendChild(srcTitleInfoElement);
                                          } 
                                          newTiElement = document.createElement(TAG_LANG);
                                          newTiElement.appendChild(document.createTextNode(langList.item(0).getTextContent()));   
                                          srcTitleInfoElement.appendChild(newTiElement);                                       

 
                                      }
                                      NodeList dateList = fb3DescriptionChild.getElementsByTagName(TAG_DATE);
                                      if (dateList.getLength() > 0) {
                                          if(srcTitleInfoElement == null){
                                               srcTitleInfoElement = document.createElement(TAG_SRC_TITLE_INFO);
                                               descriptionElement.appendChild(srcTitleInfoElement);
                                          } 
                                          Element newTiElement = document.createElement(TAG_DATE);
                                          newTiElement.appendChild(document.createTextNode(dateList.item(0).getTextContent()));                                                                                    
                                          String dateValue = ((Element) dateList.item(0)).getAttribute(ATTR_NAME_VALUE);
                                          if(dateValue != null && dateValue.length()>0){
                                                Attr attr = document.createAttribute(ATTR_NAME_VALUE);
                                                attr.setValue(dateValue);
                                                newTiElement.setAttributeNode(attr); 
                                          }
                                          srcTitleInfoElement.appendChild(newTiElement);
                                      }
////////////////////////////////TAG_TRANSLATED
                                  } else if(fb3DescriptionChild.getNodeType() == Node.ELEMENT_NODE && fb3DescriptionChild.getNodeName().equals(TAG_TRANSLATED)) {
                                      Element newTiElement = document.createElement(TAG_DATE);
                                      newTiElement.appendChild(document.createTextNode(fb3DescriptionChild.getTextContent()));                                                                                    
                                      String dateValue = fb3DescriptionChild.getAttribute(ATTR_NAME_VALUE);
                                      if(dateValue != null && dateValue.length()>0){
                                            Attr attr = document.createAttribute(ATTR_NAME_VALUE);
                                            attr.setValue(dateValue);
                                            newTiElement.setAttributeNode(attr); 
                                      }
                                      documentInfoElement.appendChild(newTiElement);
////////////////////////////paper-publish-info      
                                  } else if(fb3DescriptionChild.getNodeType() == Node.ELEMENT_NODE && fb3DescriptionChild.getNodeName().equals(TAG_PAPER_PUBLISH_INFO)) {
                                        String titleText =  fb3DescriptionChild.getAttribute(ATTR_NAME_TITLE);
                                        if(titleText != null && titleText.length() > 0){
                                            Element bookNameElement = document.createElement(TAG_BOOK_NAME);
                                            bookNameElement.appendChild(document.createTextNode(titleText));  
                                            publishInfoElement.appendChild(bookNameElement);
                                        }
                                        NodeList isbnList = fb3DescriptionChild.getElementsByTagName(TAG_ISBN);
                                        if (isbnList.getLength() > 0) {
                                            Element newIsbnElement = document.createElement(TAG_ISBN);
                                            newIsbnElement.appendChild(document.createTextNode(isbnList.item(0).getTextContent()));   
                                            publishInfoElement.appendChild(newIsbnElement);

                                        }
////////////////////////////annotation
                                  } else if(fb3DescriptionChild.getNodeType() == Node.ELEMENT_NODE && fb3DescriptionChild.getNodeName().equals(TAG_ANNOTATION)) {
                                         Element newAnnotation = document.createElement(TAG_ANNOTATION);
                                         NodeList annotationChildNodes = fb3DescriptionChild.getChildNodes();

             
                                         for (int annotationCount = 0; annotationCount < annotationChildNodes.getLength(); annotationCount++) {
                                             Node annotationChild = annotationChildNodes.item(annotationCount);
                                             Node newAnnotationChild = document.importNode(annotationChild, true);
                                             newAnnotation.appendChild(newAnnotationChild);                                       
                                         }                                         
                                         titleInfoElement.appendChild(newAnnotation);                                       
////////////////////////////   
  
                                  }
                              }                              
                          }

                   } catch (Exception e) {
                       e.printStackTrace();
                   }

//coreFilename
             }
       }        
    }
//getting the image content
    public static void insertImagesContent(Document document) throws IOException, ParserConfigurationException,SAXException{
       Element fb2RootElement = document.getDocumentElement();

       if (fb2RootElement.getNodeName().equals(TAG_FB2_ROOT)) {

           for(HashMap.Entry<String, String> currentImageMap : imagesMap.entrySet()) {
                String imageKey = currentImageMap.getKey();
                String imagePath = currentImageMap.getValue();
                if(!imagesUsedMap.containsKey(imageKey)){
                   continue;
                }
 
                String fileExt = imagesExtMap.get(imageKey);
                Element binaryElement = document.createElement(TAG_BINARY);
                binaryElement.appendChild(document.createTextNode(encodeFileToBase64Binary(imagePath)));
                Attr attr = document.createAttribute(ATTR_NAME_CONTENT_TYPE);
                attr.setValue(getFileMimetype(imagePath));
                binaryElement.setAttributeNode(attr);                    
                attr = document.createAttribute(ATTR_NAME_ID);
                attr.setValue(imageKey+"."+fileExt);
                binaryElement.setAttributeNode(attr);                    
                fb2RootElement.appendChild(binaryElement); 

            }  
        }

    }
    private static String encodeFileToBase64Binary(String fileName) throws IOException {

      Encoder encoder = Base64.getEncoder();
        byte[] bytes = readAllBytesFromPath(fileName);
 

      return encoder.encodeToString(bytes);
    }
    private static byte[] loadFile(File file) throws IOException {
      InputStream is = new FileInputStream(file);

      long length = file.length();
      if (length > Integer.MAX_VALUE) {
          throw new IOException("Image file "+file.getName() + " too large");
      }
      byte[] bytes = new byte[(int)length];

      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length
             && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
          offset += numRead;
      }

      if (offset < bytes.length) {
        throw new IOException("Could not completely read file "+file.getName());
      }

      is.close();
      return bytes;
  }
    private static String getFileMimetype(String fileName)  throws IOException {
       Path source = Paths.get(fileName);
       return Files.probeContentType(source);
    }
	
	
    private static InputStream openAsStream(String path) throws IOException {
        return vfs.openStream(path);
    }

    private static byte[] readAllBytesFromPath(String path) throws IOException {
        return vfs.readAllBytes(path);
    }

    private static Document parseDocumentFromPath(String path) throws IOException, ParserConfigurationException, SAXException {
        if (!vfs.exists(path)) {
			System.out.println("File not found: " + path);
			throw new IOException();
        }
 
        DocumentBuilder builder = xmlFactory.newDocumentBuilder();
        try (InputStream is = openAsStream(path)) {
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();
            return doc;
        }
    }
////////////////////////////////////////////////////////////////////////////////
/////////interface VirtualFileSystem
////////////////////////////////////////////////////////////////////////////////
    interface VirtualFileSystem {
        InputStream openStream(String path) throws IOException;
        byte[] readAllBytes(String path) throws IOException;
        boolean exists(String path);
        String getName();
    }
	
////////////////////////////////////////////////////////////////////////////////
/////////class FileBasedVFS
////////////////////////////////////////////////////////////////////////////////
    static class FileBasedVFS implements VirtualFileSystem {
        private final File baseDir;
        public FileBasedVFS(File baseDir){ this.baseDir = baseDir; }
        public File getBaseDir(){ return baseDir; }
        public InputStream openStream(String path) throws IOException {
            File f = path.startsWith(baseDir.getPath()) ? new File(path) : new File(baseDir, path);
            return new FileInputStream(f);
        }
        public byte[] readAllBytes(String path) throws IOException {
            File f = path.startsWith(baseDir.getPath()) ? new File(path) : new File(baseDir, path);
            return Files.readAllBytes(f.toPath());
        }
        public boolean exists(String path){
            File f = path.startsWith(baseDir.getPath()) ? new File(path) : new File(baseDir, path);
            return f.exists();
        }
        public String getName(){ return baseDir.getAbsolutePath(); }
    }

////////////////////////////////////////////////////////////////////////////////
/////////class ZipMemoryVFS
////////////////////////////////////////////////////////////////////////////////
    static class ZipMemoryVFS implements VirtualFileSystem {
        private final Map<String, byte[]> entries = new HashMap<>();
        private final String name;
        public ZipMemoryVFS(String name, InputStream zipStream) throws IOException {
            this.name = name;
            ZipInputStream zis = new ZipInputStream(zipStream);
            ZipEntry ze;
            while((ze = zis.getNextEntry()) != null){
                if(ze.isDirectory()) { zis.closeEntry(); continue; }
                String entryName = ze.getName();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[8192];
                int r;
                while((r = zis.read(buf)) != -1) baos.write(buf,0,r);
                entries.put(entryName, baos.toByteArray());
                zis.closeEntry();
            }
            zis.close();
        }
        public InputStream openStream(String path) throws IOException {
            String p = normalize(path);
            byte[] data = entries.get(p);
            if(data == null) throw new IOException("File not found in zip: "+path);
            return new ByteArrayInputStream(data);
        }
        public byte[] readAllBytes(String path) throws IOException {
            String p = normalize(path);
            byte[] data = entries.get(p);
            if(data == null) throw new IOException("File not found in zip: "+path);
            return data;
        }
        public boolean exists(String path){ return entries.containsKey(normalize(path)); }
        private String normalize(String path){
            String p = path;
            if(p.startsWith("/")) p = p.substring(1);
            return p;
        }
        public String getName(){ return name; }
    }
	
 }
 