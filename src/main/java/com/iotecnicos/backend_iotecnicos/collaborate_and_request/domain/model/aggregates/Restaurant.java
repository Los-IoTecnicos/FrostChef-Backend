package com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.CreateRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.valueobjects.Project;
import com.iotecnicos.backend_iotecnicos.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import io.jsonwebtoken.lang.Strings;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.Date;

@Getter
@Entity
public class Restaurant extends AuditableAbstractAggregateRoot<Restaurant> {

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String contactInfo;

    @Column
    private Date createdDate;

    @Embedded
    private Project project;

    public Restaurant() {
        this.name= Strings.EMPTY;
        this.location= Strings.EMPTY;
        this.contactInfo= Strings.EMPTY;
        this.createdDate= new Date();
        this.project=new Project(null);
    }

    public Restaurant(String name, String location, String contactInfo, Date createdDate, Long project) {
        this();
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.createdDate = createdDate;
        this.project = new Project(project);
    }

    public Restaurant(CreateRestaurantCommand command) {
        this.name= command.name();
        this.location= command.location();
        this.contactInfo= command.contactInfo();
        this.createdDate= new Date();
        this.project = new Project(command.project());
    }

    public Restaurant updateInformation(String name, String location, String contactInfo, Date createdDate){
        this.name= name;
        this.location= location;
        this.contactInfo= contactInfo;
        this.createdDate= createdDate;
        return this;
    }

    public Long getProjectId() {return project.projectEnt();}
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
