package com.bupt.webservice.com.inspur;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.inspur package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetChannelResponse_QNAME = new QName(
            "http://inspur.com/", "getChannelResponse");
    private final static QName _GetAllEquipCardByAmp_QNAME = new QName(
            "http://inspur.com/", "getAllEquipCardByAmp");
    private final static QName _GetAllEquipCardByAmpResponse_QNAME = new QName(
            "http://inspur.com/", "getAllEquipCardByAmpResponse");
    private final static QName _GetAllEquipLinkResponse_QNAME = new QName(
            "http://inspur.com/", "getAllEquipLinkResponse");
    private final static QName _GetChannel_QNAME = new QName(
            "http://inspur.com/", "getChannel");
    private final static QName _GetAllEquip_QNAME = new QName(
            "http://inspur.com/", "getAllEquip");
    private final static QName _GetAllEquipResponse_QNAME = new QName(
            "http://inspur.com/", "getAllEquipResponse");
    private final static QName _GetAllEquipLink_QNAME = new QName(
            "http://inspur.com/", "getAllEquipLink");

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: com.inspur
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllEquipLinkResponse }
     */
    public GetAllEquipLinkResponse createGetAllEquipLinkResponse() {
        return new GetAllEquipLinkResponse();
    }

    /**
     * Create an instance of {@link GetAllEquipLink }
     */
    public GetAllEquipLink createGetAllEquipLink() {
        return new GetAllEquipLink();
    }

    /**
     * Create an instance of {@link GetAllEquipCardByAmpResponse }
     */
    public GetAllEquipCardByAmpResponse createGetAllEquipCardByAmpResponse() {
        return new GetAllEquipCardByAmpResponse();
    }

    /**
     * Create an instance of {@link GetChannel }
     */
    public GetChannel createGetChannel() {
        return new GetChannel();
    }

    /**
     * Create an instance of {@link GetAllEquip }
     */
    public GetAllEquip createGetAllEquip() {
        return new GetAllEquip();
    }

    /**
     * Create an instance of {@link GetAllEquipResponse }
     */
    public GetAllEquipResponse createGetAllEquipResponse() {
        return new GetAllEquipResponse();
    }

    /**
     * Create an instance of {@link GetChannelResponse }
     */
    public GetChannelResponse createGetChannelResponse() {
        return new GetChannelResponse();
    }

    /**
     * Create an instance of {@link GetAllEquipCardByAmp }
     */
    public GetAllEquipCardByAmp createGetAllEquipCardByAmp() {
        return new GetAllEquipCardByAmp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChannelResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getChannelResponse")
    public JAXBElement<GetChannelResponse> createGetChannelResponse(
            GetChannelResponse value) {
        return new JAXBElement<GetChannelResponse>(_GetChannelResponse_QNAME,
                GetChannelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEquipCardByAmp }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getAllEquipCardByAmp")
    public JAXBElement<GetAllEquipCardByAmp> createGetAllEquipCardByAmp(
            GetAllEquipCardByAmp value) {
        return new JAXBElement<GetAllEquipCardByAmp>(
                _GetAllEquipCardByAmp_QNAME, GetAllEquipCardByAmp.class, null,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEquipCardByAmpResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getAllEquipCardByAmpResponse")
    public JAXBElement<GetAllEquipCardByAmpResponse> createGetAllEquipCardByAmpResponse(
            GetAllEquipCardByAmpResponse value) {
        return new JAXBElement<GetAllEquipCardByAmpResponse>(
                _GetAllEquipCardByAmpResponse_QNAME,
                GetAllEquipCardByAmpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEquipLinkResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getAllEquipLinkResponse")
    public JAXBElement<GetAllEquipLinkResponse> createGetAllEquipLinkResponse(
            GetAllEquipLinkResponse value) {
        return new JAXBElement<GetAllEquipLinkResponse>(
                _GetAllEquipLinkResponse_QNAME, GetAllEquipLinkResponse.class,
                null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChannel }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getChannel")
    public JAXBElement<GetChannel> createGetChannel(GetChannel value) {
        return new JAXBElement<GetChannel>(_GetChannel_QNAME, GetChannel.class,
                null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEquip }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getAllEquip")
    public JAXBElement<GetAllEquip> createGetAllEquip(GetAllEquip value) {
        return new JAXBElement<GetAllEquip>(_GetAllEquip_QNAME,
                GetAllEquip.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEquipResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getAllEquipResponse")
    public JAXBElement<GetAllEquipResponse> createGetAllEquipResponse(
            GetAllEquipResponse value) {
        return new JAXBElement<GetAllEquipResponse>(_GetAllEquipResponse_QNAME,
                GetAllEquipResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEquipLink }{@code >}}
     */
    @XmlElementDecl(namespace = "http://inspur.com/", name = "getAllEquipLink")
    public JAXBElement<GetAllEquipLink> createGetAllEquipLink(
            GetAllEquipLink value) {
        return new JAXBElement<GetAllEquipLink>(_GetAllEquipLink_QNAME,
                GetAllEquipLink.class, null, value);
    }

}
