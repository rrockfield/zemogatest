package com.rockfield.zemogatest.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Rockfield
 */
@Entity
@Table(name = "portfolio")
@NamedQueries({
    @NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p"),
    @NamedQuery(name = "Portfolio.findByTwitterUserName", query = "SELECT p FROM Portfolio p "
            + " WHERE p.twitterUserName = :twitterUserName ")
})
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idportfolio")
    private Long idportfolio;
    @Column(name = "imageURL")
    private String imageURL;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "twitterUserName")
    private String twitterUserName;

    public Portfolio() {
    }

    public Portfolio(Long idportfolio) {
        this.idportfolio = idportfolio;
    }

    public Long getIdportfolio() {
        return idportfolio;
    }

    public void setIdportfolio(Long idportfolio) {
        this.idportfolio = idportfolio;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitterUserName() {
        return twitterUserName;
    }

    public void setTwitterUserName(String twitterUserName) {
        this.twitterUserName = twitterUserName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idportfolio != null ? idportfolio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Portfolio)) {
            return false;
        }
        Portfolio other = (Portfolio) object;
        if ((this.idportfolio == null && other.idportfolio != null) || (this.idportfolio != null && !this.idportfolio.equals(other.idportfolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rockfield.zemogatest.model.Portfolio[ idportfolio=" + idportfolio + " ]";
    }

}
