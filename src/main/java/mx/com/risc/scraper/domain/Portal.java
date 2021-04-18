package mx.com.risc.scraper.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Portal.
 */
@Entity
@Table(name = "portal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Portal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idscrapy_data")
    private Integer idscrapyData;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "domain", nullable = false)
    private String domain;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "header_path", nullable = false)
    private String headerPath;

    @NotNull
    @Column(name = "body_path", nullable = false)
    private String bodyPath;

    @NotNull
    @Column(name = "autor_path", nullable = false)
    private String autorPath;

    @NotNull
    @Column(name = "date_path", nullable = false)
    private String datePath;

    @NotNull
    @Column(name = "resume_path", nullable = false)
    private String resumePath;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @NotNull
    @Column(name = "xpath", nullable = false)
    private Boolean xpath;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @OneToOne    @JoinColumn(unique = true)
    private Scheduler scheduler;

    @OneToOne    @JoinColumn(unique = true)
    private PortalType portalType;

    @OneToMany(mappedBy = "portal")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pages> pages = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("portals")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdscrapyData() {
        return idscrapyData;
    }

    public Portal idscrapyData(Integer idscrapyData) {
        this.idscrapyData = idscrapyData;
        return this;
    }

    public void setIdscrapyData(Integer idscrapyData) {
        this.idscrapyData = idscrapyData;
    }

    public String getName() {
        return name;
    }

    public Portal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public Portal domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public Portal url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeaderPath() {
        return headerPath;
    }

    public Portal headerPath(String headerPath) {
        this.headerPath = headerPath;
        return this;
    }

    public void setHeaderPath(String headerPath) {
        this.headerPath = headerPath;
    }

    public String getBodyPath() {
        return bodyPath;
    }

    public Portal bodyPath(String bodyPath) {
        this.bodyPath = bodyPath;
        return this;
    }

    public void setBodyPath(String bodyPath) {
        this.bodyPath = bodyPath;
    }

    public String getAutorPath() {
        return autorPath;
    }

    public Portal autorPath(String autorPath) {
        this.autorPath = autorPath;
        return this;
    }

    public void setAutorPath(String autorPath) {
        this.autorPath = autorPath;
    }

    public String getDatePath() {
        return datePath;
    }

    public Portal datePath(String datePath) {
        this.datePath = datePath;
        return this;
    }

    public void setDatePath(String datePath) {
        this.datePath = datePath;
    }

    public String getResumePath() {
        return resumePath;
    }

    public Portal resumePath(String resumePath) {
        this.resumePath = resumePath;
        return this;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    public String getPath() {
        return path;
    }

    public Portal path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isXpath() {
        return xpath;
    }

    public Portal xpath(Boolean xpath) {
        this.xpath = xpath;
        return this;
    }

    public void setXpath(Boolean xpath) {
        this.xpath = xpath;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Portal identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUsuario() {
        return usuario;
    }

    public Portal usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Portal fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Portal scheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public PortalType getPortalType() {
        return portalType;
    }

    public Portal portalType(PortalType portalType) {
        this.portalType = portalType;
        return this;
    }

    public void setPortalType(PortalType portalType) {
        this.portalType = portalType;
    }

    public Set<Pages> getPages() {
        return pages;
    }

    public Portal pages(Set<Pages> pages) {
        this.pages = pages;
        return this;
    }

    public Portal addPages(Pages pages) {
        this.pages.add(pages);
        pages.setPortal(this);
        return this;
    }

    public Portal removePages(Pages pages) {
        this.pages.remove(pages);
        pages.setPortal(null);
        return this;
    }

    public void setPages(Set<Pages> pages) {
        this.pages = pages;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Portal customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        Portal portal = (Portal) o;
        if (portal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Portal{" +
            "id=" + getId() +
            ", idscrapyData=" + getIdscrapyData() +
            ", name='" + getName() + "'" +
            ", domain='" + getDomain() + "'" +
            ", url='" + getUrl() + "'" +
            ", headerPath='" + getHeaderPath() + "'" +
            ", bodyPath='" + getBodyPath() + "'" +
            ", autorPath='" + getAutorPath() + "'" +
            ", datePath='" + getDatePath() + "'" +
            ", resumePath='" + getResumePath() + "'" +
            ", path='" + getPath() + "'" +
            ", xpath='" + isXpath() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
