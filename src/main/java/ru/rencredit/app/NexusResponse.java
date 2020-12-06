package ru.rencredit.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
@XmlRootElement
public class NexusResponse implements Serializable {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "message")
    private String message;
}
