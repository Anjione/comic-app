package com.example.comicbe.controller;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController

public class XmlController {
    @GetMapping("/xml-structure")
    public Set<String> getXmlStructure() {
        Set<String> elementNames = new LinkedHashSet<>(); // lưu tên tag duy nhất, giữ thứ tự
        String filePath = "uploads/komik25com.xml"; // đổi thành đường dẫn file của bạn

        try (InputStream inputStream = new FileInputStream(filePath)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(inputStream);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String elementName = event.asStartElement().getName().getLocalPart();
                    elementNames.add(elementName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return elementNames; // trả về tên các tag duy nhất
    }

    @GetMapping("/xml-tree")
    public XmlNode getXmlTree() {
        String filePath = "uploads/komik25com.xml"; // đổi đường dẫn

        XmlNode root = new XmlNode("ROOT"); // node gốc ảo
        Stack<XmlNode> stack = new Stack<>();
        stack.push(root);

        try (InputStream inputStream = new FileInputStream(filePath)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(inputStream);

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    String name = event.asStartElement().getName().getLocalPart();
                    XmlNode parent = stack.peek();
                    XmlNode child = parent.addChild(name);
                    stack.push(child);
                } else if (event.isEndElement()) {
                    stack.pop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    @GetMapping("/xml-tree2")
    public String getXmlTree2() {
        String filePath = "uploads/komik25com.xml"; // đổi đường dẫn

        Map<String, Object> root = new LinkedHashMap<>();
        Stack<Map<String, Object>> stack = new Stack<>();
        Stack<String> tagStack = new Stack<>();

        stack.push(root);

        try (InputStream inputStream = new FileInputStream(filePath)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(inputStream);

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement start = event.asStartElement();
                    String tagName = start.getName().getLocalPart();

                    Map<String, Object> newMap = new LinkedHashMap<>();
                    Map<String, Object> parent = stack.peek();

                    // nếu tag trùng, thêm số để tránh ghi đè
                    if (parent.containsKey(tagName)) {
                        Object existing = parent.get(tagName);
                        List<Map<String, Object>> list;
                        if (existing instanceof List) {
                            list = (List<Map<String, Object>>) existing;
                        } else {
                            list = new ArrayList<>();
                            list.add((Map<String, Object>) existing);
                        }
                        list.add(newMap);
                        parent.put(tagName, list);
                    } else {
                        parent.put(tagName, newMap);
                    }

                    stack.push(newMap);
                    tagStack.push(tagName);

                } else if (event.isEndElement()) {
                    stack.pop();
                    tagStack.pop();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // dễ đọc
        try (FileWriter writer = new FileWriter("uploads/exportTree.txt")) {
            mapper.writeValue(writer, root);
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "ok";
    }

    class XmlNode {
        private String name;
        private Map<String, XmlNode> children = new LinkedHashMap<>(); // giữ thứ tự

        public XmlNode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Map<String, XmlNode> getChildren() {
            return children;
        }

        public XmlNode addChild(String childName) {
            return children.computeIfAbsent(childName, XmlNode::new);
        }
    }
}
