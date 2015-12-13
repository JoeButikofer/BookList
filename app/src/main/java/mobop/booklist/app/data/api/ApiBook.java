package mobop.booklist.app.data.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import mobop.booklist.app.data.generic.book.IApiBook;

public class ApiBook implements IApiBook {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("id")
    private String id;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("selfLink")
    private String selfLink;
    @JsonProperty("volumeInfo")
    private VolumeInfo volumeInfo;
    @JsonProperty("layerInfo")
    private LayerInfo layerInfo;
    @JsonProperty("saleInfo")
    private SaleInfo saleInfo;
    @JsonProperty("accessInfo")
    private AccessInfo accessInfo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The kind
     */
    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     * The kind
     */
    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     * The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The etag
     */
    @JsonProperty("etag")
    public String getEtag() {
        return etag;
    }

    /**
     *
     * @param etag
     * The etag
     */
    @JsonProperty("etag")
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     *
     * @return
     * The selfLink
     */
    @JsonProperty("selfLink")
    public String getSelfLink() {
        return selfLink;
    }

    /**
     *
     * @param selfLink
     * The selfLink
     */
    @JsonProperty("selfLink")
    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    /**
     *
     * @return
     * The volumeInfo
     */
    @JsonProperty("volumeInfo")
    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    /**
     *
     * @param volumeInfo
     * The volumeInfo
     */
    @JsonProperty("volumeInfo")
    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    /**
     *
     * @return
     * The layerInfo
     */
    @JsonProperty("layerInfo")
    public LayerInfo getLayerInfo() {
        return layerInfo;
    }

    /**
     *
     * @param layerInfo
     * The layerInfo
     */
    @JsonProperty("layerInfo")
    public void setLayerInfo(LayerInfo layerInfo) {
        this.layerInfo = layerInfo;
    }

    /**
     *
     * @return
     * The saleInfo
     */
    @JsonProperty("saleInfo")
    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    /**
     *
     * @param saleInfo
     * The saleInfo
     */
    @JsonProperty("saleInfo")
    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    /**
     *
     * @return
     * The accessInfo
     */
    @JsonProperty("accessInfo")
    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    /**
     *
     * @param accessInfo
     * The accessInfo
     */
    @JsonProperty("accessInfo")
    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String getName() {
        return this.volumeInfo.getTitle();
    }

    @Override
    public void setName(String value) {
        this.volumeInfo.setTitle(value);
    }

    @Override
    public String getGenre() {
        return this.volumeInfo.getCategories().get(0);
    }

    @Override
    public void setGenre(String value) {
        this.volumeInfo.getCategories().remove(0);
        this.volumeInfo.getCategories().add(0, value);
    }

    @Override
    public int getPages() {
        Integer pages = volumeInfo.getPageCount();
        if(pages == null)
            return -1;
        return pages;
    }

    @Override
    public void setPages(int value) {
        this.volumeInfo.setPageCount(value);
    }

    @Override
    public double getRatings() {
        Double ratings = this.volumeInfo.getAverageRating();
        if(ratings == null)
            return -1;
        return ratings;
    }

    @Override
    public void setRatings(double value) {
        this.volumeInfo.setAverageRating(value);
    }

    @Override
    public String getImagePath() {

        if(this.volumeInfo.getImageLinks() != null) {
           return this.volumeInfo.getImageLinks().getThumbnail(); //TODO prendre depuis autre source aussi (small, large,...)
        }
        return null;
    }

    @Override
    public void setImagePath(String value) {
        this.volumeInfo.getImageLinks().setThumbnail(value);
    }


    @Override
    public String toString() {
        return "ApiBook{" +
                "kind='" + kind + '\'' +
                ", id='" + id + '\'' +
                ", etag='" + etag + '\'' +
                ", selfLink='" + selfLink + '\'' +
                ", volumeInfo=" + volumeInfo +
                ", layerInfo=" + layerInfo +
                ", saleInfo=" + saleInfo +
                ", accessInfo=" + accessInfo +
                ", additionalProperties=" + additionalProperties +
                ", super=" + super.toString() +
                '}';
    }
}

class AccessInfo implements Serializable{

    @JsonProperty("country")
    private String country;
    @JsonProperty("viewability")
    private String viewability;
    @JsonProperty("embeddable")
    private Boolean embeddable;
    @JsonProperty("publicDomain")
    private Boolean publicDomain;
    @JsonProperty("textToSpeechPermission")
    private String textToSpeechPermission;
    @JsonProperty("epub")
    private Epub epub;
    @JsonProperty("pdf")
    private Pdf pdf;
    @JsonProperty("webReaderLink")
    private String webReaderLink;
    @JsonProperty("accessViewStatus")
    private String accessViewStatus;
    @JsonProperty("quoteSharingAllowed")
    private Boolean quoteSharingAllowed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The viewability
     */
    @JsonProperty("viewability")
    public String getViewability() {
        return viewability;
    }

    /**
     *
     * @param viewability
     * The viewability
     */
    @JsonProperty("viewability")
    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    /**
     *
     * @return
     * The embeddable
     */
    @JsonProperty("embeddable")
    public Boolean getEmbeddable() {
        return embeddable;
    }

    /**
     *
     * @param embeddable
     * The embeddable
     */
    @JsonProperty("embeddable")
    public void setEmbeddable(Boolean embeddable) {
        this.embeddable = embeddable;
    }

    /**
     *
     * @return
     * The publicDomain
     */
    @JsonProperty("publicDomain")
    public Boolean getPublicDomain() {
        return publicDomain;
    }

    /**
     *
     * @param publicDomain
     * The publicDomain
     */
    @JsonProperty("publicDomain")
    public void setPublicDomain(Boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    /**
     *
     * @return
     * The textToSpeechPermission
     */
    @JsonProperty("textToSpeechPermission")
    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    /**
     *
     * @param textToSpeechPermission
     * The textToSpeechPermission
     */
    @JsonProperty("textToSpeechPermission")
    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    /**
     *
     * @return
     * The epub
     */
    @JsonProperty("epub")
    public Epub getEpub() {
        return epub;
    }

    /**
     *
     * @param epub
     * The epub
     */
    @JsonProperty("epub")
    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    /**
     *
     * @return
     * The pdf
     */
    @JsonProperty("pdf")
    public Pdf getPdf() {
        return pdf;
    }

    /**
     *
     * @param pdf
     * The pdf
     */
    @JsonProperty("pdf")
    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    /**
     *
     * @return
     * The webReaderLink
     */
    @JsonProperty("webReaderLink")
    public String getWebReaderLink() {
        return webReaderLink;
    }

    /**
     *
     * @param webReaderLink
     * The webReaderLink
     */
    @JsonProperty("webReaderLink")
    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    /**
     *
     * @return
     * The accessViewStatus
     */
    @JsonProperty("accessViewStatus")
    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    /**
     *
     * @param accessViewStatus
     * The accessViewStatus
     */
    @JsonProperty("accessViewStatus")
    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    /**
     *
     * @return
     * The quoteSharingAllowed
     */
    @JsonProperty("quoteSharingAllowed")
    public Boolean getQuoteSharingAllowed() {
        return quoteSharingAllowed;
    }

    /**
     *
     * @param quoteSharingAllowed
     * The quoteSharingAllowed
     */
    @JsonProperty("quoteSharingAllowed")
    public void setQuoteSharingAllowed(Boolean quoteSharingAllowed) {
        this.quoteSharingAllowed = quoteSharingAllowed;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "AccessInfo{" +
                "country='" + country + '\'' +
                ", viewability='" + viewability + '\'' +
                ", embeddable=" + embeddable +
                ", publicDomain=" + publicDomain +
                ", textToSpeechPermission='" + textToSpeechPermission + '\'' +
                ", epub=" + epub +
                ", pdf=" + pdf +
                ", webReaderLink='" + webReaderLink + '\'' +
                ", accessViewStatus='" + accessViewStatus + '\'' +
                ", quoteSharingAllowed=" + quoteSharingAllowed +
                ", additionalProperties=" + additionalProperties +
                ", super=" + super.toString() +
                '}';
    }
}

class Dimensions implements Serializable{

    @JsonProperty("height")
    private String height;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The height
     */
    @JsonProperty("height")
    public String getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    @JsonProperty("height")
    public void setHeight(String height) {
        this.height = height;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class Epub implements Serializable{

    @JsonProperty("isAvailable")
    private Boolean isAvailable;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The isAvailable
     */
    @JsonProperty("isAvailable")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     *
     * @param isAvailable
     * The isAvailable
     */
    @JsonProperty("isAvailable")
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class ImageLinks implements Serializable{

    @JsonProperty("smallThumbnail")
    private String smallThumbnail;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The smallThumbnail
     */
    @JsonProperty("smallThumbnail")
    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    /**
     *
     * @param smallThumbnail
     * The smallThumbnail
     */
    @JsonProperty("smallThumbnail")
    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    /**
     *
     * @return
     * The thumbnail
     */
    @JsonProperty("thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     *
     * @param thumbnail
     * The thumbnail
     */
    @JsonProperty("thumbnail")
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class Layer implements Serializable{

    @JsonProperty("layerId")
    private String layerId;
    @JsonProperty("volumeAnnotationsVersion")
    private String volumeAnnotationsVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The layerId
     */
    @JsonProperty("layerId")
    public String getLayerId() {
        return layerId;
    }

    /**
     *
     * @param layerId
     * The layerId
     */
    @JsonProperty("layerId")
    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    /**
     *
     * @return
     * The volumeAnnotationsVersion
     */
    @JsonProperty("volumeAnnotationsVersion")
    public String getVolumeAnnotationsVersion() {
        return volumeAnnotationsVersion;
    }

    /**
     *
     * @param volumeAnnotationsVersion
     * The volumeAnnotationsVersion
     */
    @JsonProperty("volumeAnnotationsVersion")
    public void setVolumeAnnotationsVersion(String volumeAnnotationsVersion) {
        this.volumeAnnotationsVersion = volumeAnnotationsVersion;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class LayerInfo implements Serializable {

    @JsonProperty("layers")
    private List<Layer> layers = new ArrayList<Layer>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The layers
     */
    @JsonProperty("layers")
    public List<Layer> getLayers() {
        return layers;
    }

    /**
     *
     * @param layers
     * The layers
     */
    @JsonProperty("layers")
    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class Pdf implements Serializable{

    @JsonProperty("isAvailable")
    private Boolean isAvailable;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The isAvailable
     */
    @JsonProperty("isAvailable")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     *
     * @param isAvailable
     * The isAvailable
     */
    @JsonProperty("isAvailable")
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class ReadingModes implements Serializable {

    @JsonProperty("text")
    private Boolean text;
    @JsonProperty("image")
    private Boolean image;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The text
     */
    @JsonProperty("text")
    public Boolean getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    @JsonProperty("text")
    public void setText(Boolean text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The image
     */
    @JsonProperty("image")
    public Boolean getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    @JsonProperty("image")
    public void setImage(Boolean image) {
        this.image = image;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class SaleInfo implements Serializable{

    @JsonProperty("country")
    private String country;
    @JsonProperty("saleability")
    private String saleability;
    @JsonProperty("isEbook")
    private Boolean isEbook;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The saleability
     */
    @JsonProperty("saleability")
    public String getSaleability() {
        return saleability;
    }

    /**
     *
     * @param saleability
     * The saleability
     */
    @JsonProperty("saleability")
    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    /**
     *
     * @return
     * The isEbook
     */
    @JsonProperty("isEbook")
    public Boolean getIsEbook() {
        return isEbook;
    }

    /**
     *
     * @param isEbook
     * The isEbook
     */
    @JsonProperty("isEbook")
    public void setIsEbook(Boolean isEbook) {
        this.isEbook = isEbook;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

class VolumeInfo implements Serializable{

    @JsonProperty("title")
    private String title;
    @JsonProperty("authors")
    private List<String> authors = new ArrayList<String>();
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("publishedDate")
    private String publishedDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("readingModes")
    private ReadingModes readingModes;
    @JsonProperty("pageCount")
    private Integer pageCount;
    @JsonProperty("printedPageCount")
    private Integer printedPageCount;
    @JsonProperty("dimensions")
    private Dimensions dimensions;
    @JsonProperty("printType")
    private String printType;
    @JsonProperty("averageRating")
    private Double averageRating;
    @JsonProperty("ratingsCount")
    private Integer ratingsCount;
    @JsonProperty("maturityRating")
    private String maturityRating;
    @JsonProperty("allowAnonLogging")
    private Boolean allowAnonLogging;
    @JsonProperty("contentVersion")
    private String contentVersion;
    @JsonProperty("imageLinks")
    private ImageLinks imageLinks;
    @JsonProperty("language")
    private String language;
    @JsonProperty("previewLink")
    private String previewLink;
    @JsonProperty("infoLink")
    private String infoLink;
    @JsonProperty("canonicalVolumeLink")
    private String canonicalVolumeLink;
    @JsonProperty("mainCategory")
    private String mainCategory;
    @JsonProperty("categories")
    private List<String> categories;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The authors
     */
    @JsonProperty("authors")
    public List<String> getAuthors() {
        return authors;
    }

    /**
     *
     * @param authors
     * The authors
     */
    @JsonProperty("authors")
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    /**
     *
     * @return
     * The publisher
     */
    @JsonProperty("publisher")
    public String getPublisher() {
        return publisher;
    }

    /**
     *
     * @param publisher
     * The publisher
     */
    @JsonProperty("publisher")
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     *
     * @return
     * The publishedDate
     */
    @JsonProperty("publishedDate")
    public String getPublishedDate() {
        return publishedDate;
    }

    /**
     *
     * @param publishedDate
     * The publishedDate
     */
    @JsonProperty("publishedDate")
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     *
     * @return
     * The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The readingModes
     */
    @JsonProperty("readingModes")
    public ReadingModes getReadingModes() {
        return readingModes;
    }

    /**
     *
     * @param readingModes
     * The readingModes
     */
    @JsonProperty("readingModes")
    public void setReadingModes(ReadingModes readingModes) {
        this.readingModes = readingModes;
    }

    /**
     *
     * @return
     * The pageCount
     */
    @JsonProperty("pageCount")
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     *
     * @param pageCount
     * The pageCount
     */
    @JsonProperty("pageCount")
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     *
     * @return
     * The printedPageCount
     */
    @JsonProperty("printedPageCount")
    public Integer getPrintedPageCount() {
        return printedPageCount;
    }

    /**
     *
     * @param printedPageCount
     * The printedPageCount
     */
    @JsonProperty("printedPageCount")
    public void setPrintedPageCount(Integer printedPageCount) {
        this.printedPageCount = printedPageCount;
    }

    /**
     *
     * @return
     * The dimensions
     */
    @JsonProperty("dimensions")
    public Dimensions getDimensions() {
        return dimensions;
    }

    /**
     *
     * @param dimensions
     * The dimensions
     */
    @JsonProperty("dimensions")
    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    /**
     *
     * @return
     * The printType
     */
    @JsonProperty("printType")
    public String getPrintType() {
        return printType;
    }

    /**
     *
     * @param printType
     * The printType
     */
    @JsonProperty("printType")
    public void setPrintType(String printType) {
        this.printType = printType;
    }

    /**
     *
     * @return
     * The averageRating
     */
    @JsonProperty("averageRating")
    public Double getAverageRating() {
        return averageRating;
    }

    /**
     *
     * @param averageRating
     * The averageRating
     */
    @JsonProperty("averageRating")
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     *
     * @return
     * The ratingsCount
     */
    @JsonProperty("ratingsCount")
    public Integer getRatingsCount() {
        return ratingsCount;
    }

    /**
     *
     * @param ratingsCount
     * The ratingsCount
     */
    @JsonProperty("ratingsCount")
    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    /**
     *
     * @return
     * The maturityRating
     */
    @JsonProperty("maturityRating")
    public String getMaturityRating() {
        return maturityRating;
    }

    /**
     *
     * @param maturityRating
     * The maturityRating
     */
    @JsonProperty("maturityRating")
    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    /**
     *
     * @return
     * The allowAnonLogging
     */
    @JsonProperty("allowAnonLogging")
    public Boolean getAllowAnonLogging() {
        return allowAnonLogging;
    }

    /**
     *
     * @param allowAnonLogging
     * The allowAnonLogging
     */
    @JsonProperty("allowAnonLogging")
    public void setAllowAnonLogging(Boolean allowAnonLogging) {
        this.allowAnonLogging = allowAnonLogging;
    }

    /**
     *
     * @return
     * The contentVersion
     */
    @JsonProperty("contentVersion")
    public String getContentVersion() {
        return contentVersion;
    }

    /**
     *
     * @param contentVersion
     * The contentVersion
     */
    @JsonProperty("contentVersion")
    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    /**
     *
     * @return
     * The imageLinks
     */
    @JsonProperty("imageLinks")
    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    /**
     *
     * @param imageLinks
     * The imageLinks
     */
    @JsonProperty("imageLinks")
    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    /**
     *
     * @return
     * The language
     */
    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The previewLink
     */
    @JsonProperty("previewLink")
    public String getPreviewLink() {
        return previewLink;
    }

    /**
     *
     * @param previewLink
     * The previewLink
     */
    @JsonProperty("previewLink")
    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    /**
     *
     * @return
     * The infoLink
     */
    @JsonProperty("infoLink")
    public String getInfoLink() {
        return infoLink;
    }

    /**
     *
     * @param infoLink
     * The infoLink
     */
    @JsonProperty("infoLink")
    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    /**
     *
     * @return
     * The canonicalVolumeLink
     */
    @JsonProperty("canonicalVolumeLink")
    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    /**
     *
     * @param canonicalVolumeLink
     * The canonicalVolumeLink
     */
    @JsonProperty("canonicalVolumeLink")
    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    @JsonProperty("mainCategory")
    public String getMainCategory() {
        return mainCategory;
    }

    @JsonProperty("mainCategory")
    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    @JsonProperty("categories")
    public List<String> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "VolumeInfo{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", description='" + description + '\'' +
                ", readingModes=" + readingModes +
                ", pageCount=" + pageCount +
                ", printedPageCount=" + printedPageCount +
                ", dimensions=" + dimensions +
                ", printType='" + printType + '\'' +
                ", averageRating=" + averageRating +
                ", ratingsCount=" + ratingsCount +
                ", maturityRating='" + maturityRating + '\'' +
                ", allowAnonLogging=" + allowAnonLogging +
                ", contentVersion='" + contentVersion + '\'' +
                ", imageLinks=" + imageLinks +
                ", language='" + language + '\'' +
                ", previewLink='" + previewLink + '\'' +
                ", infoLink='" + infoLink + '\'' +
                ", canonicalVolumeLink='" + canonicalVolumeLink + '\'' +
                ", mainCategory='" + mainCategory + '\'' +
                ", categories=" + categories +
                ", additionalProperties=" + additionalProperties +
                ", super=" + super.toString() +
                '}';
    }
}