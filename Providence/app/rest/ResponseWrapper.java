package rest;

/**
 * @author Maxim Ochenashko
 */
public class ResponseWrapper {

    private MetadataWrapper metadata;
    private Object content;

    public ResponseWrapper(MetadataWrapper metadata, Object content) {
        this.metadata = metadata;
        this.content = content;
    }

    public ResponseWrapper(MetadataWrapper metadata) {
        this(metadata, null);
    }

    public MetadataWrapper getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataWrapper metadata) {
        this.metadata = metadata;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
