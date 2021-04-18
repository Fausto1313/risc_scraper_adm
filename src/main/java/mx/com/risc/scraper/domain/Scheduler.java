package mx.com.risc.scraper.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Scheduler.
 */
@Entity
@Table(name = "scheduler")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Scheduler implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "actor", nullable = false)
    private Integer actor;

    @Column(name = "schedule")
    private String schedule;

    @NotNull
    @Column(name = "project", nullable = false)
    private String project;

    @NotNull
    @Column(name = "spider", nullable = false)
    private String spider;

    @NotNull
    @Column(name = "jhi_timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "jobidentifier")
    private String jobidentifier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Scheduler identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getStatus() {
        return status;
    }

    public Scheduler status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getActor() {
        return actor;
    }

    public Scheduler actor(Integer actor) {
        this.actor = actor;
        return this;
    }

    public void setActor(Integer actor) {
        this.actor = actor;
    }

    public String getSchedule() {
        return schedule;
    }

    public Scheduler schedule(String schedule) {
        this.schedule = schedule;
        return this;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getProject() {
        return project;
    }

    public Scheduler project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSpider() {
        return spider;
    }

    public Scheduler spider(String spider) {
        this.spider = spider;
        return this;
    }

    public void setSpider(String spider) {
        this.spider = spider;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Scheduler timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getDuration() {
        return duration;
    }

    public Scheduler duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getJobidentifier() {
        return jobidentifier;
    }

    public Scheduler jobidentifier(String jobidentifier) {
        this.jobidentifier = jobidentifier;
        return this;
    }

    public void setJobidentifier(String jobidentifier) {
        this.jobidentifier = jobidentifier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scheduler scheduler = (Scheduler) o;
        if (scheduler.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduler.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Scheduler{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", status=" + getStatus() +
            ", actor=" + getActor() +
            ", schedule='" + getSchedule() + "'" +
            ", project='" + getProject() + "'" +
            ", spider='" + getSpider() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", duration=" + getDuration() +
            ", jobidentifier='" + getJobidentifier() + "'" +
            "}";
    }
}
