package mx.com.risc.scraper.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Pages.
 */
@Entity
@Table(name = "pages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idscrapy_data", nullable = false)
    private Integer idscrapyData;

    @Lob
    @Column(name = "header_data")
    private String headerData;

    @Lob
    @Column(name = "body_data")
    private String bodyData;

    @Lob
    @Column(name = "autor_data")
    private String autorData;

    @Lob
    @Column(name = "date_data")
    private String dateData;

    @Lob
    @Column(name = "header_clean")
    private String headerClean;

    @Lob
    @Column(name = "body_clean")
    private String bodyClean;

    @Lob
    @Column(name = "autor_clean")
    private String autorClean;

    @Lob
    @Column(name = "date_clean")
    private String dateClean;

    @Lob
    @Column(name = "resume_clean")
    private String resumeClean;

    @Lob
    @Column(name = "resume_data")
    private String resumeData;

    @Lob
    @Column(name = "screenshot")
    private byte[] screenshot;

    @Column(name = "screenshot_content_type")
    private String screenshotContentType;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonIgnoreProperties("pages")
    private Portal portal;

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

    public Pages idscrapyData(Integer idscrapyData) {
        this.idscrapyData = idscrapyData;
        return this;
    }

    public void setIdscrapyData(Integer idscrapyData) {
        this.idscrapyData = idscrapyData;
    }

    public String getHeaderData() {
        return headerData;
    }

    public Pages headerData(String headerData) {
        this.headerData = headerData;
        return this;
    }

    public void setHeaderData(String headerData) {
        this.headerData = headerData;
    }

    public String getBodyData() {
        return bodyData;
    }

    public Pages bodyData(String bodyData) {
        this.bodyData = bodyData;
        return this;
    }

    public void setBodyData(String bodyData) {
        this.bodyData = bodyData;
    }

    public String getAutorData() {
        return autorData;
    }

    public Pages autorData(String autorData) {
        this.autorData = autorData;
        return this;
    }

    public void setAutorData(String autorData) {
        this.autorData = autorData;
    }

    public String getDateData() {
        return dateData;
    }

    public Pages dateData(String dateData) {
        this.dateData = dateData;
        return this;
    }

    public void setDateData(String dateData) {
        this.dateData = dateData;
    }

    public String getHeaderClean() {
        return headerClean;
    }

    public Pages headerClean(String headerClean) {
        this.headerClean = headerClean;
        return this;
    }

    public void setHeaderClean(String headerClean) {
        this.headerClean = headerClean;
    }

    public String getBodyClean() {
        return bodyClean;
    }

    public Pages bodyClean(String bodyClean) {
        this.bodyClean = bodyClean;
        return this;
    }

    public void setBodyClean(String bodyClean) {
        this.bodyClean = bodyClean;
    }

    public String getAutorClean() {
        return autorClean;
    }

    public Pages autorClean(String autorClean) {
        this.autorClean = autorClean;
        return this;
    }

    public void setAutorClean(String autorClean) {
        this.autorClean = autorClean;
    }

    public String getDateClean() {
        return dateClean;
    }

    public Pages dateClean(String dateClean) {
        this.dateClean = dateClean;
        return this;
    }

    public void setDateClean(String dateClean) {
        this.dateClean = dateClean;
    }

    public String getResumeClean() {
        return resumeClean;
    }

    public Pages resumeClean(String resumeClean) {
        this.resumeClean = resumeClean;
        return this;
    }

    public void setResumeClean(String resumeClean) {
        this.resumeClean = resumeClean;
    }

    public String getResumeData() {
        return resumeData;
    }

    public Pages resumeData(String resumeData) {
        this.resumeData = resumeData;
        return this;
    }

    public void setResumeData(String resumeData) {
        this.resumeData = resumeData;
    }

    public byte[] getScreenshot() {
        return screenshot;
    }

    public Pages screenshot(byte[] screenshot) {
        this.screenshot = screenshot;
        return this;
    }

    public void setScreenshot(byte[] screenshot) {
        this.screenshot = screenshot;
    }

    public String getScreenshotContentType() {
        return screenshotContentType;
    }

    public Pages screenshotContentType(String screenshotContentType) {
        this.screenshotContentType = screenshotContentType;
        return this;
    }

    public void setScreenshotContentType(String screenshotContentType) {
        this.screenshotContentType = screenshotContentType;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Pages date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getFullPath() {
        return fullPath;
    }

    public Pages fullPath(String fullPath) {
        this.fullPath = fullPath;
        return this;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getUrl() {
        return url;
    }

    public Pages url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Portal getPortal() {
        return portal;
    }

    public Pages portal(Portal portal) {
        this.portal = portal;
        return this;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
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
        Pages pages = (Pages) o;
        if (pages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pages{" +
            "id=" + getId() +
            ", idscrapyData=" + getIdscrapyData() +
            ", headerData='" + getHeaderData() + "'" +
            ", bodyData='" + getBodyData() + "'" +
            ", autorData='" + getAutorData() + "'" +
            ", dateData='" + getDateData() + "'" +
            ", headerClean='" + getHeaderClean() + "'" +
            ", bodyClean='" + getBodyClean() + "'" +
            ", autorClean='" + getAutorClean() + "'" +
            ", dateClean='" + getDateClean() + "'" +
            ", resumeClean='" + getResumeClean() + "'" +
            ", resumeData='" + getResumeData() + "'" +
            ", screenshot='" + getScreenshot() + "'" +
            ", screenshotContentType='" + getScreenshotContentType() + "'" +
            ", date='" + getDate() + "'" +
            ", fullPath='" + getFullPath() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
