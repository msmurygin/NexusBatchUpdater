package ru.rencredit.app;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;

@Component
@Slf4j
public class PomReaderImpl implements PomReader {
    private Artifact pomDescription = new Artifact();

    @SneakyThrows
    public Artifact read(File file) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            if (doc.hasChildNodes()) {
                printNote(doc.getChildNodes());
            }
            log.info("Getting pom : " + pomDescription);

        }catch (Exception e){
            log.error("ERROR: "+e.getMessage());
        }finally {
            return this.pomDescription;
        }
    }

    private void printNote(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                if (tempNode.getNodeName().equalsIgnoreCase("groupId")){
                    this.pomDescription.setGroupId(tempNode.getTextContent().trim());
                }
                if (tempNode.getNodeName().equalsIgnoreCase("artifactId")){
                    this.pomDescription.setArtifactId(tempNode.getTextContent().trim());
                }
                if (tempNode.getNodeName().equalsIgnoreCase("version")){
                    this.pomDescription.setVersion(tempNode.getTextContent().trim());
                }
                if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes());
                }
            }
        }
    }

}
